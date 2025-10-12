package com.servifacil.SF_BackEnd.utils;

import java.lang.reflect.Field;

public class MergeUtils {

    public static <S, T> void mergeNonNullFields(S source, T target) {
        Field[] sourceFields = source.getClass().getDeclaredFields();

        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);
            try {
                Object value = sourceField.get(source);
                if (value != null) {
                    // Tenta encontrar o campo equivalente no target
                    try {
                        Field targetField = target.getClass().getDeclaredField(sourceField.getName());
                        targetField.setAccessible(true);
                        targetField.set(target, value);
                    } catch (NoSuchFieldException e) {
                        // Ignora campos que existem no DTO mas não no Model
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Erro ao atualizar campo: " + sourceField.getName(), e);
            }
        }
    }
}
