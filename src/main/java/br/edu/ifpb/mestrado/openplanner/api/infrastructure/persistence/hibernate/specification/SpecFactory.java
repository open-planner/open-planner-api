package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.DateUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.FieldUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.StringUtils;

public class SpecFactory<T> {

    private static final String POSTGRESQL_UNACCENT_FUNCTION = "unaccent";

    public Specification<T> create(String property, Long value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<Long> x = getExpression(root, property, Long.class);
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
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(getExpression(root, property, Boolean.class), value);
    }

    public Specification<T> create(String property, Enum<?> value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(getExpression(root, property, Enum.class), value);
    }

    public Specification<T> create(String property, LocalDate value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<LocalDate> x = getExpression(root, property, LocalDate.class);
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
            Expression<LocalDateTime> x = getExpression(root, property, LocalDateTime.class);
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
            In<Object> predicate = criteriaBuilder.in(getExpression(root, property, Object.class));
            values.stream().forEach(value -> predicate.value(value));

            return predicate;
        };
    }

    public Specification<T> create(String property, String value, Operation operation) {
        return (root, query, criteriaBuilder) -> {
            Expression<String> x = getExpression(root, property, String.class);
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

        return build(Arrays.asList(x, y), Operator.AND);
    }

    public Specification<T> create(Object filter, Class<T> entityClass) {
        return create(filter, entityClass, Operator.AND);
    }

    public Specification<T> create(Object filter, Class<T> entityClass, Operator operator) {
        List<Field> filterFields = FieldUtils.getAllFields(filter.getClass());
        List<Field> entityFields = FieldUtils.getAllFields(entityClass);
        List<Specification<T>> specs = new ArrayList<>();

        for (Field filterField : filterFields) {
            try {
                Method getterMethod = FieldUtils.findGetterMethod(filterField.getName(), filter.getClass());
                Object value = getterMethod.invoke(filter);
                SpecField specField = filterField.getAnnotation(SpecField.class);

                if (value == null && (specField == null || !specField.canBeNull())) {
                    continue;
                }

                SpecGroup specGroup = filterField.getAnnotation(SpecGroup.class);

                if (specGroup != null) {
                    specs.add(create(value, entityClass, specGroup.operator()));
                    continue;
                }

                SpecBetween specBetween = filterField.getAnnotation(SpecBetween.class);

                if (specBetween != null) {
                    specs.add(create(specBetween, value));
                    continue;
                }

                if (hasProperty(filterField, entityFields)) {
                    specs.add(create(filterField, value));
                }
            } catch (Exception exception) {
//                exception.printStackTrace();
                continue;
            }
        }

        return build(specs, operator);
    }

    private Boolean hasProperty(Field filterField, List<Field> entityFields) {
        SpecBetween specBetween = filterField.getAnnotation(SpecBetween.class);

        if (specBetween != null) {
            return hasProperty(specBetween.left(), entityFields) && hasProperty(specBetween.right(), entityFields);
        }

        SpecField specField = filterField.getAnnotation(SpecField.class);
        String value = specField != null && !StringUtils.isBlank(specField.value())
                ? specField.value()
                : filterField.getName();

        return hasProperty(value, entityFields);
    }

    private Boolean hasProperty(String value, List<Field> entityFields) {
        return entityFields.stream().filter(ef -> {
            if (isDeepProperty(value)) {
                return hasDeepProperty(value, ef);
            }

            return ef.getName().equals(value);
        }).findFirst().isPresent();
    }

    private Boolean isDeepProperty(String value) {
        return value.contains(".");
    }

    private Boolean hasDeepProperty(String value, Field entityField) {
        String[] splittedValue = value.split("\\.");

        if (!entityField.getName().equals(splittedValue[0])) {
            return false;
        }

        Integer index = 1;

        do {
            List<Field> deepEntityFields = FieldUtils.getAllFields(entityField.getType());

            if (!hasProperty(splittedValue[index], deepEntityFields)) {
                return false;
            }

            index++;
        } while (index < splittedValue.length);

        return true;
    }

    @SuppressWarnings("unchecked")
    private <U> Expression<U> getExpression(Root<T> root, String property, Class<U> type) {
        if (!isDeepProperty(property)) {
            return root.get(property);
        }

        String[] splittedProperty = property.split("\\.");
        Path<Object> path = root.get(splittedProperty[0]);

        for (int i = 1; i < splittedProperty.length; i++) {
            path = path.get(splittedProperty[i]);
        }

        return (Expression<U>) path;
    }

    private Specification<T> create(Field field, Object value) {
        SpecField specField = field.getAnnotation(SpecField.class);
        String property = specField != null && !StringUtils.isBlank(specField.value()) ? specField.value() : field.getName();
        Operation operation = specField != null ? specField.operation() : Operation.EQUAL;

        if (value == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(getExpression(root, property, Object.class));
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

    private String prepareForLike(String value) {
        return "%" + value.replaceAll("\\s+", "%") + "%";
    }

    private Specification<T> build(List<Specification<T>> specs, Operator operator) {
        if (specs.isEmpty()) {
            return null;
        }

        Specification<T> result = specs.get(0);

        if (specs.size() > 1) {
            for (int i = 1; i < specs.size(); i++) {
                result = Specification.where(result);
                result = operator.equals(Operator.AND)
                        ? result.and(specs.get(i))
                        : result.or(specs.get(i));
            }
        }

        return result;
    }

}
