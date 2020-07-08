package ru.yakimovvn.hadoop_worker.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URLDecoder;
import java.security.CodeSource;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public class ClassLoaderUtil {

    private ClassLoaderUtil(){}

    /**
     * Данный метод возвращает путь к папке, где находится исполняемый jar файл с указанным классом
     * @param aclass Класс
     * @return Путь к папке
     * @throws Exception
     */
    public static String GetJarContainingFolder(Class<?> aclass)
            throws Exception
    {
        CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();

        File jarFile;

        if (codeSource.getLocation() != null)
        {
            jarFile = new File(codeSource.getLocation().toURI());
        }
        else
        {
            String path = aclass.getResource(aclass.getSimpleName() + ".class").getPath();
            String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
            jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
            jarFile = new File(jarFilePath);
        }
        return jarFile.getParentFile().getAbsolutePath();
    }


    public static String GetApplicationRootPath(Class<?> appClass) throws Exception
    {
        String jarPath = GetJarContainingFolder(appClass);

        if(jarPath.endsWith("lib"))
            return jarPath.replace("lib", "");

        if(jarPath.endsWith("build"+File.separator+"classes"))
            return jarPath.replace("build"+File.separator+"classes", "");

        if (jarPath.endsWith("out"+File.separator+"production"))
            return jarPath.replace("out"+File.separator+"production", "");

        if (jarPath.endsWith("config"))
            return jarPath.replace("config", "");

        return jarPath;
    }

    @SuppressWarnings("unchecked")
    public static <TClass> TClass  LoadClassWithDefaultConstructor(String className)
            throws Exception
    {
        Class<?> clazz = Class.forName(className);
        Constructor<?> defaultConstructor = clazz.getConstructor();
        return (TClass)defaultConstructor.newInstance();
    }
}
