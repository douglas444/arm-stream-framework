package br.ufu.facom.armstream.core.util;

import br.ufu.facom.armstream.api.ArmActiveCategorizer;
import br.ufu.facom.armstream.api.ArmBaseClassifier;
import br.ufu.facom.armstream.api.ArmMetaCategorizer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.text.MessageFormat.format;

public class ImplementationLoader {

    public static final String ERROR_LOADING_CLASS = "Error loading class {0}";
    public static final String ERROR_LOADING_JAR = "Error loading JAR {0}";
    public static final String ERROR_LOADING_DIRECTORY = "Error loading directory {0}";
    public static final String ERROR_READING_FILE = "Error reading file {0}";
    public static final String NO_IMPLEMENTATION_FOUND_FOR_INTERFACE =
            "No implementation with a default constructor was found for interface {0}";

    public static HashMap<Class<?>, HashMap<String, Class<?>>> load(final File[] classpathArray)
            throws ImplementationLoaderException {

        final URLClassLoader classLoader = buildClassLoader(classpathArray);
        final HashMap<Class<?>, HashMap<String, Class<?>>> classMapByInterface = new HashMap<>();

        classMapByInterface.put(ArmBaseClassifier.class, new HashMap<>());
        classMapByInterface.put(ArmMetaCategorizer.class, new HashMap<>());
        classMapByInterface.put(ArmActiveCategorizer.class, new HashMap<>());

        for (final File classpath : classpathArray) {

            final File[] files;

            if (classpath.isFile()) {
                files = new File[]{classpath};
            } else {
                files = classpath.listFiles();
                if (files == null) {
                    continue;
                }
            }

            for (final File file : files) {

                final List<String> classesNames = discoverClasses(file);
                for (String className : classesNames) {

                    final Class<?> clazz;
                    try {
                        clazz = classLoader.loadClass(className);
                    } catch (NoClassDefFoundError | ClassNotFoundException e) {
                        throw new ImplementationLoaderException(format(ERROR_LOADING_CLASS, className), e);
                    }

                    try {
                        DependencyFinder.getDependencies(clazz);
                    } catch (Exception e) {
                        throw new ImplementationLoaderException( format(ERROR_LOADING_CLASS, className), e);
                    }

                    final Object instance;
                    try {
                        Constructor<?> constructor = clazz.getConstructor();
                        instance = constructor.newInstance();
                    } catch (Exception e) {
                        continue;
                    } catch (NoClassDefFoundError e) {
                        throw new ImplementationLoaderException(format(ERROR_LOADING_CLASS, className), e);
                    }

                    if (instance instanceof ArmBaseClassifier) {
                        classMapByInterface.get(ArmBaseClassifier.class).put(clazz.getSimpleName(), clazz);
                    } else if (instance instanceof ArmMetaCategorizer) {
                        classMapByInterface.get(ArmMetaCategorizer.class).put(clazz.getSimpleName(), clazz);
                    } else if (instance instanceof ArmActiveCategorizer) {
                        classMapByInterface.get(ArmActiveCategorizer.class).put(clazz.getSimpleName(), clazz);
                    }

                }
            }
        }

        if (classMapByInterface.get(ArmBaseClassifier.class).isEmpty()) {
            throw new ImplementationLoaderException(
                    format(NO_IMPLEMENTATION_FOUND_FOR_INTERFACE, ArmBaseClassifier.class.getName()));
        }
        if (classMapByInterface.get(ArmMetaCategorizer.class).isEmpty()) {
            throw new ImplementationLoaderException(
                    format(NO_IMPLEMENTATION_FOUND_FOR_INTERFACE, ArmMetaCategorizer.class.getName()));
        }
        if (classMapByInterface.get(ArmActiveCategorizer.class).isEmpty()) {
            throw new ImplementationLoaderException(
                    format(NO_IMPLEMENTATION_FOUND_FOR_INTERFACE, ArmActiveCategorizer.class.getName()));
        }

        try {
            classLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classMapByInterface;

    }

    private static URLClassLoader buildClassLoader(final File[] classpathArray) throws ImplementationLoaderException {

        final List<URL> urls = new ArrayList<>();

        for (final File classpath : classpathArray) {

            if (classpath.getName().endsWith(".jar")) {

                try {
                    urls.add(new URL(toJarPath(classpath.getAbsolutePath())));
                } catch (MalformedURLException e) {
                    throw new ImplementationLoaderException(
                            format(ERROR_LOADING_JAR, classpath.getAbsolutePath()), e);
                }

            } else if (classpath.isDirectory()) {

                try {
                    urls.add(classpath.toURI().toURL());
                } catch (MalformedURLException e) {
                    throw new ImplementationLoaderException(
                            format(ERROR_LOADING_DIRECTORY, classpath.getAbsolutePath()), e);
                }

                final File[] files = classpath.listFiles();
                if (files == null) {
                    continue;
                }

                final List<String> jarsPaths = discoverJarsInRepository(classpath);

                for (final String jarPath : jarsPaths) {
                    try {
                        urls.add(new URL(toJarPath(jarPath)));
                    } catch (MalformedURLException e) {
                        throw new ImplementationLoaderException(
                                format(ERROR_LOADING_JAR, classpath.getAbsolutePath()), e);
                    }
                }
            }

        }

        return URLClassLoader.newInstance(urls.toArray(new URL[]{}));

    }

    private static List<String> discoverJarsInRepository(final File repository) {
        final List<String> paths = new ArrayList<>();
        discoverJarsInRepository(repository, paths);
        return paths;
    }

    private static void discoverJarsInRepository(final File repository, final List<String> paths) {

        if (repository.isDirectory()) {

            final File[] files = repository.listFiles();

            if(files != null) {
                for (final File file : files) {
                    discoverJarsInRepository(file, paths);
                }
            }

        } else if (repository.getName().endsWith(".jar")) {
            paths.add(repository.getAbsolutePath());
        }

    }

    private static List<String> discoverClasses(final File root) throws ImplementationLoaderException {
        final List<String> classesNames = new ArrayList<>();
        discoverClasses(root, "", classesNames);
        return classesNames;
    }

    private static void discoverClassesInJar(final JarFile jar, final List<String> classesNames) {


        Enumeration<JarEntry> enumeration = jar.entries();

        while (enumeration.hasMoreElements()) {

            final JarEntry je = enumeration.nextElement();

            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }

            final String className = je.getName().substring(0, je.getName().length() - ".class".length());
            classesNames.add(className.replace('/', '.'));

        }

    }

    private static void discoverClasses(final File root, String prefix, final List<String> classesNames)
            throws ImplementationLoaderException {

        if (!prefix.isEmpty()) {
            prefix += ".";
        }

        if (root.isDirectory()) {

            prefix += root.getName();

            final File[] files = root.listFiles();

            if(files != null) {
                for (final File file : files) {
                    discoverClasses(file, prefix, classesNames);
                }
            }

        } else if (root.getName().endsWith(".jar")) {

            try {
                discoverClassesInJar(new JarFile(root), classesNames);
            } catch (IOException e) {
                throw new ImplementationLoaderException(format(ERROR_READING_FILE, root.getAbsolutePath()), e);
            }

        } else if (root.getName().endsWith(".class")) {

            classesNames.add(prefix + root.getName().replace(".class", ""));

        }

    }

    private static String toJarPath(final String path) {
        return "jar:file:" + path + "!/";
    }

}
