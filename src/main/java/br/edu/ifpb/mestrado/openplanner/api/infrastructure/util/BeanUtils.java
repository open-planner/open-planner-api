package br.edu.ifpb.mestrado.openplanner.api.infrastructure.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ArrayUtils;

public class BeanUtils {

    public static void copyProperties(Object source, Object target) {
        String[] ignoreProperties = getNullProperties(source);
        deepCopyProperties(source, target, ignoreProperties);
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    public static void copyProperties(Object source, Object target, String ...ignoreProperties) {
        ignoreProperties = ArrayUtils.addAll(getNullProperties(source), ignoreProperties);
        deepCopyProperties(source, target, ignoreProperties);
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    private static void deepCopyProperties(Object source, Object target, String ...ignoreProperties) {
        List<Field> sourceFields = FieldUtils.getAllFields(source.getClass());
        List<String> ignoreList = Arrays.asList(ignoreProperties);

        for (Field sourceField : sourceFields) {
            if (ignoreList.contains(sourceField.getName())) {
                continue;
            }

            Embedded embedded = sourceField.getAnnotation(Embedded.class);

            if (embedded != null) {
                sourceField.setAccessible(true);

                try {
                    Field targetField = FieldUtils.getField(target.getClass(), sourceField.getName());
                    targetField.setAccessible(true);
                    copyProperties(sourceField.get(source), targetField.get(target));
                    sourceField.set(source, targetField.get(target));
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private static String[] getNullProperties(Object source) {
        return FieldUtils.getAllFields(source.getClass()).stream().filter(field -> StreamUtils.propagate(() -> {
            NotNull notNull = field.getAnnotation(NotNull.class);

            if (notNull == null) {
                return false;
            }

            field.setAccessible(true);

            return field.get(source) == null;
        })).map(Field::getName).toArray(String[]::new);
    }

}
