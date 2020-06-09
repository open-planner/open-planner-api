package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.DateUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.StringUtils;

public class SpecFactory<T> {

    private static final String POSTGRESQL_UNACCENT_FUNCTION = "unaccent";

    public Specification<T> create(Field field, Object value) {
        String property = SpecUtils.getPropertyName(field);
        Operation operation = SpecUtils.getOperation(field);

        if (value == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(SpecUtils.getExpression(root, property));
        }

        if (value instanceof Number) {
            return create(property, Long.valueOf(value.toString()), operation);
        }

        if (value instanceof Boolean) {
            return create(property, (Boolean) value);
        }

        if (value instanceof LocalDate) {
            LocalDate date = LocalDate.parse(value.toString());

            if (operation.equals(Operation.DATETIME_TO_DATE)) {
                return create(property, date.atStartOfDay(), operation);
            }

            return create(property, date, operation);
        }

        if (value instanceof LocalDateTime) {
            return create(property, LocalDateTime.parse(value.toString()), operation);
        }

        if (value instanceof Enum<?>) {
            return create(property, (Enum<?>) value);
        }

        if (value instanceof Collection) {
            return create(property, (Collection<?>) value);
        }

        return create(property, value.toString(), operation);
    }

    @SuppressWarnings("unchecked")
    public Specification<T> create(Path<?> path, Field field, Object value) {
        Operation operation = SpecUtils.getOperation(field);

        if (value == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(path);
        }

        if (value instanceof Number) {
            return create((Path<Long>) path, Long.valueOf(value.toString()), operation);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Specification<T> create(String property, Long value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> x = (Expression<Long>) SpecUtils.getExpression(root, property);

            return create(x, value, operation).toPredicate(root, query, criteriaBuilder);
        };
    }

    public Specification<T> create(Expression<Long> expression, Long value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Long y = value;

            switch (operation) {
                case GREATER_THAN:
                    return criteriaBuilder.greaterThan(expression, y);
                case LESS_THAN:
                    return criteriaBuilder.lessThan(expression, y);
                case GREATER_THAN_OR_EQUAL:
                    return criteriaBuilder.greaterThan(expression, y);
                case LESS_THAN_OR_EQUAL:
                    return criteriaBuilder.lessThanOrEqualTo(expression, y);
                default:
                    return criteriaBuilder.equal(expression, y);
            }
        };
    }

    public Specification<T> create(String property, Integer value, Operation operation) {
        return create(property, Long.valueOf(value), operation);
    }

    public Specification<T> create(String property, Boolean value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(SpecUtils.getExpression(root, property), value);
    }

    public Specification<T> create(String property, Enum<?> value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(SpecUtils.getExpression(root, property), value);
    }

    @SuppressWarnings("unchecked")
    public Specification<T> create(String property, LocalDate value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<LocalDate> x = (Expression<LocalDate>) SpecUtils.getExpression(root, property);
            LocalDate y = value;

            switch (operation) {
                case GREATER_THAN:
                    return criteriaBuilder.greaterThan(x, y);
                case LESS_THAN:
                    return criteriaBuilder.lessThan(x, y);
                case GREATER_THAN_OR_EQUAL:
                    return criteriaBuilder.greaterThanOrEqualTo(x, y);
                case LESS_THAN_OR_EQUAL:
                    return criteriaBuilder.lessThanOrEqualTo(x, y);
                default:
                    return criteriaBuilder.equal(x, y);
            }
        };
    }

    @SuppressWarnings("unchecked")
    public Specification<T> create(String property, LocalDateTime value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<LocalDateTime> x = (Expression<LocalDateTime>) SpecUtils.getExpression(root, property);
            LocalDateTime y = value;

            switch (operation) {
                case GREATER_THAN:
                    return criteriaBuilder.greaterThan(x, y);
                case LESS_THAN:
                    return criteriaBuilder.lessThan(x, y);
                case GREATER_THAN_OR_EQUAL:
                    return criteriaBuilder.greaterThanOrEqualTo(x, y);
                case LESS_THAN_OR_EQUAL:
                    return criteriaBuilder.lessThanOrEqualTo(x, y);
                case DATETIME_TO_DATE:
                    LocalDateTime startOfDay = DateUtils.atStartOfDay(y);
                    LocalDateTime endOfDay = DateUtils.atEndOfDay(y);
                    return criteriaBuilder.between(x, startOfDay, endOfDay);
                default:
                    return criteriaBuilder.equal(x, y);
            }
        };
    }

    public Specification<T> create(String property, Collection<?> values) {
        return (root, query, criteriaBuilder) -> {
            In<Object> predicate = criteriaBuilder.in(SpecUtils.getExpression(root, property));
            values.stream().forEach(value -> predicate.value(value));

            return predicate;
        };
    }

    @SuppressWarnings("unchecked")
    public Specification<T> create(String property, String value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<String> x = (Expression<String>) SpecUtils.getExpression(root, property);
            String y;

            switch (operation) {
                case EQUAL_IGNORE_CASE:
                    x = criteriaBuilder.lower(x);
                    y = value.toLowerCase();
                    return criteriaBuilder.equal(x, y);
                case LIKE:
                    y = prepareForLike(value);
                    return criteriaBuilder.like(x, y);
                case LIKE_IGNORE_CASE:
                    x = criteriaBuilder.lower(x);
                    y = prepareForLike(value.toLowerCase());
                    return criteriaBuilder.like(x, y);
                case EQUALS_IGNORE_CASE_UNACCENT:
                    x = criteriaBuilder.function(POSTGRESQL_UNACCENT_FUNCTION, String.class, criteriaBuilder.lower(x));
                    y = StringUtils.unaccent(value.toLowerCase());
                    return criteriaBuilder.equal(x, y);
                case LIKE_IGNORE_CASE_UNACCENT:
                    x = criteriaBuilder.function(POSTGRESQL_UNACCENT_FUNCTION, String.class, criteriaBuilder.lower(x));
                    y = prepareForLike(StringUtils.unaccent(value.toLowerCase()));
                    return criteriaBuilder.like(x, y);
                default:
                    y = value;
                    return criteriaBuilder.equal(x, y);
            }
        };
    }

    public Specification<T> between(String leftProperty, String rightProperty, Object value) {
        Specification<T> x = null;
        Specification<T> y = null;

        if (value instanceof Number) {
            x = create(leftProperty, Long.valueOf(value.toString()), Operation.LESS_THAN_OR_EQUAL);
            y = create(rightProperty, Long.valueOf(value.toString()), Operation.GREATER_THAN_OR_EQUAL);
        } else if (value instanceof LocalDate) {
            x = create(leftProperty, LocalDate.parse(value.toString()), Operation.LESS_THAN_OR_EQUAL);
            y = create(rightProperty, LocalDate.parse(value.toString()), Operation.GREATER_THAN_OR_EQUAL);
        }

        if (x == null || y == null) {
            return null;
        }

        return new SpecBuilder<T>().add(x).add(y).build();
    }

    public <U> Specification<T> join(Field field, Object value, Class<U> joinClass, String[] joinProperties) {
        return (root, query, criteriaBuilder) -> {
            Join<T, Object> joinRoot = root.join(joinProperties[0]);

            if (joinProperties.length > 1) {
                Integer index = 1;

                do {
                    joinRoot = joinRoot.join(joinProperties[index]);
                } while (index < joinProperties.length - 1);
            }

            return create(joinRoot.get(SpecUtils.getPropertyName(field)), field, value)
                    .toPredicate(root, query, criteriaBuilder);
        };
    }

    private String prepareForLike(String value) {
        return "%" + value.replaceAll("\\s+", "%") + "%";
    }

}
