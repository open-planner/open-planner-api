package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.FieldUtils;

public class SpecBuilder<T> {

    private List<Specification<T>> specs = new ArrayList<>();
    private SpecFactory<T> specFactory = new SpecFactory<>();

    public void clear() {
        specs = new ArrayList<>();
    }

    public SpecBuilder<T> add(Specification<T> spec) {
        specs.add(spec);

        return this;
    }

    public SpecBuilder<T> add(Object filter) {
        return add(filter, Operator.AND);
    }

    @SuppressWarnings("unchecked")
    public SpecBuilder<T> add(Object filter, Operator operator) {
        Class<T> entityClass = (Class<T>) filter.getClass().getAnnotation(SpecEntity.class).value();

        return add(filter, entityClass, operator);
    }

    public Specification<T> build() {
        return build(Operator.AND);
    }

    public Specification<T> build(Operator operator) {
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

    private SpecBuilder<T> add(Object filter, Class<T> entityClass, Operator operator) {
        List<Field> filterFields = FieldUtils.getAllFields(filter.getClass());
        List<Field> entityFields = FieldUtils.getAllFields(entityClass);

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
                    specs.add(new SpecBuilder<T>()
                            .add(value, entityClass, specGroup.operator())
                            .build(operator));
                    continue;
                }

                SpecBetween specBetween = filterField.getAnnotation(SpecBetween.class);

                if (specBetween != null) {
                    specs.add(specFactory.between(specBetween.left(), specBetween.right(), value));
                    continue;
                }

                SpecJoin specJoin = filterField.getAnnotation(SpecJoin.class);

                if (specJoin != null) {
                    specs.add(specFactory.join(filterField, value, specJoin.entity(), specJoin.properties()));
                    continue;
                }

                if (SpecUtils.hasProperty(filterField, entityFields)) {
                    specs.add(specFactory.create(filterField, value));
                }
            } catch (Exception exception) {
                continue;
            }
        }

        return this;
    }

}
