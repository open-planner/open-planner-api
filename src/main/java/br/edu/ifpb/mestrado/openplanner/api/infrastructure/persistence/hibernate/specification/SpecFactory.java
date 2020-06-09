package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.DateUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.StringUtils;

public class SpecFactory<T> {

    private static final String POSTGRESQL_UNACCENT_FUNCTION = "unaccent";

    public Specification<T> create(Field field, Object value) {
        SpecField specField = field.getAnnotation(SpecField.class);
        String property = specField != null && !StringUtils.isBlank(specField.value()) ? specField.value() : field.getName();
        Operation operation = specField != null ? specField.operation() : Operation.EQUAL;

        if (value == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(SpecUtils.getExpression(root, property, Object.class));
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

    public Specification<T> create(String property, Long value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> x = SpecUtils.getExpression(root, property, Long.class);
            Long y = value;

            switch (operation) {
                case GREATER_THAN:
                    return criteriaBuilder.greaterThan(x, y);
                case LESS_THAN:
                    return criteriaBuilder.lessThan(x, y);
                case GREATER_THAN_OR_EQUAL:
                    return criteriaBuilder.greaterThan(x, y);
                case LESS_THAN_OR_EQUAL:
                    return criteriaBuilder.lessThanOrEqualTo(x, y);
                default:
                    return criteriaBuilder.equal(x, y);
            }
        };
    }

    public Specification<T> create(String property, Integer value, Operation operation) {
        return create(property, Long.valueOf(value), operation);
    }

    public Specification<T> create(String property, Boolean value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(SpecUtils.getExpression(root, property, Boolean.class), value);
    }

    public Specification<T> create(String property, Enum<?> value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(SpecUtils.getExpression(root, property, Enum.class), value);
    }

    public Specification<T> create(String property, LocalDate value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<LocalDate> x = SpecUtils.getExpression(root, property, LocalDate.class);
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

    public Specification<T> create(String property, LocalDateTime value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<LocalDateTime> x = SpecUtils.getExpression(root, property, LocalDateTime.class);
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
            In<Object> predicate = criteriaBuilder.in(SpecUtils.getExpression(root, property, Object.class));
            values.stream().forEach(value -> predicate.value(value));

            return predicate;
        };
    }

    public Specification<T> create(String property, String value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<String> x = SpecUtils.getExpression(root, property, String.class);
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

    public Specification<T> create(SpecBetween specBetween, Object value) {
        Specification<T> x = null;
        Specification<T> y = null;

        if (value instanceof Number) {
            x = create(specBetween.left(), Long.valueOf(value.toString()), Operation.LESS_THAN_OR_EQUAL);
            y = create(specBetween.right(), Long.valueOf(value.toString()), Operation.GREATER_THAN_OR_EQUAL);
        } else if (value instanceof LocalDate) {
            x = create(specBetween.left(), LocalDate.parse(value.toString()), Operation.LESS_THAN_OR_EQUAL);
            y = create(specBetween.right(), LocalDate.parse(value.toString()), Operation.GREATER_THAN_OR_EQUAL);
        }

        if (x == null || y == null) {
            return null;
        }

        return new SpecBuilder<T>().add(x).add(y).build();
    }

    public Specification<T> create(SpecJoin specJoin, Field field, Object value) {
        return (root, query, criteriaBuilder) -> {
//            root.join(getExpression(root, property, type));
            return null; // TODO implementar join
        };
    }

    private String prepareForLike(String value) {
        return "%" + value.replaceAll("\\s+", "%") + "%";
    }

}
