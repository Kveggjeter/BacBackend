package com.bac.bacbackend.domain.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;
import static java.util.Arrays.stream;

public class DataMapper {

    private DataMapper() {

    }

    public static <T> T toDomain(Object o, Class<T> t) {
        try {
            Map<String, Object> values = new HashMap<>();

            for (Method m : o.getClass().getMethods()) {
                if(isGetter(m)) {
                    String prop = m.getName().substring(3);
                    Object value = m.invoke(o);
                    values.put(prop.toLowerCase(), value);
                }
            }

            Constructor<?> ctor = t.getDeclaredConstructors()[0];
            String[] paramNames = getRecordParamNames(t);

            Object[] args = new Object[paramNames.length];
            for (int i = 0; i < paramNames.length; i++) {
                args[i] = values.get(paramNames[i].toLowerCase());
            }

            return t.cast(ctor.newInstance(args));

        } catch (Exception e) {
            throw new RuntimeException("Failed to map entity to domain", e);
        }
    }

    public static <T> T toEntity(Object o, Class<T> t) {
        try {
            T entity = t.getDeclaredConstructor().newInstance();

            for(Method m: o.getClass().getMethods()) {
                if(isGetter(m)) {

                    if (m.getName().startsWith("component")) continue;

                    String field = m.getName().substring(3);
                    Object value = m.invoke(o);

                    String setterName = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);

                    try {
                        Method setter = t.getMethod(setterName, m.getReturnType());
                        setter.invoke(entity, value);
                    } catch (NoSuchMethodException ignored) {}
                }
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map domain to entity", e);
        }
    }

    private static boolean isGetter(Method m) {
        return m.getName().startsWith("get")
                && m.getParameterCount() == 0
                && !void.class.equals(m.getReturnType());
    }

    private static String[] getRecordParamNames(Class<?> recordClass) {
        return stream(recordClass.getRecordComponents())
                .map(RecordComponent::getName)
                .toArray(String[]::new);
    }
}
