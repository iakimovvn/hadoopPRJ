package ru.yakimovvn.hadoop_worker.util;

import ru.yakimovvn.hadoop_worker.api.ComponentBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public class ComponentUtil {
    public static ComponentBase createInstance(String className, Map<String, Object> configuration)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(Map.class);
        return (ComponentBase)constructor.newInstance(configuration);
    }
}
