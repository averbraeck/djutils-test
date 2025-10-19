package org.djutils.test;

import java.util.ArrayList;
import java.util.List;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

/**
 * ClassList contains two helper methods that check whether all classes in a package implement a given interface or method.
 * <p>
 * Copyright (c) 2025-2025 Delft University of Technology, Jaffalaan 5, 2628 BX Delft, the Netherlands. All rights reserved. See
 * for project information <a href="https://djutils.org" target="_blank"> https://djutils.org</a>. The DJUTILS project is
 * distributed under a three-clause BSD-style license, which can be found at
 * <a href="https://djutils.org/docs/license.html" target="_blank"> https://djutils.org/docs/license.html</a>.
 * </p>
 * @author <a href="https://github.com/averbraeck">Alexander Verbraeck</a>
 * @author <a href="https://github.com/peter-knoppers">Peter Knoppers</a>
 * @author <a href="https://github.com/wjschakel">Wouter Schakel</a>
 */
public final class ClassList
{
    /** */
    private ClassList()
    {
        //
    }

    /**
     * Return a list with the class names without an explicitly declared method. By default, the check ignores anonymous inner
     * classes, but includes explicit local or static inner classes. It only looks at classes, not at interfaces, records,
     * annotation classes or enums.
     * @param methodName the method to check for
     * @param packageNameList a list of package names to check
     * @return a list of classes within the given packages without a toString() method
     */
    public static List<String> classesWithoutMethod(final String methodName, final String... packageNameList)
    {
        List<String> result = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph() // .verbose() logs activities
            .overrideClasspath("target/classes") // don't include test classes
            .enableAllInfo() // Scan classes, methods, fields, annotations
            .acceptPackages(packageNameList) // Scan what's in pkg and subpackages (omit to scan all packages)
            .scan())
        {
            scanResult.getAllClasses()
                .stream()
                .filter(ci -> !ci.isInterface() && !ci.isEnum() && !ci.isAnnotation() && !ci.isRecord())
                .filter(ci -> !ci.isAnonymousInnerClass())
                .filter(ci -> !ci.hasDeclaredMethod(methodName))
                .forEach(classInfo ->
                { result.add(classInfo.getName()); });
        }
        return result;
    }

    /**
     * Prints a list with the class names without an explicitly declared method. By default, the check ignores anonymous inner
     * classes, but includes explicit local or static inner classes. It only looks at classes, not at interfaces, records,
     * annotation classes or enums.
     * @param methodName the method to check for
     * @param packageNameList a list of package names to check
     */
    public static void printClassesWithoutMethod(final String methodName, final String... packageNameList)
    {
        System.out.println("Classes without toString() method:");
        classesWithoutMethod(methodName, packageNameList).forEach(System.out::println);
    }

    /**
     * Return a list with the class names that do not implement the given interface. By default, the check ignores anonymous
     * inner classes, but includes explicit local or static inner classes. It only looks at classes, not at interfaces, records,
     * annotation classes or enums.
     * @param interfaceClass the interface to check for
     * @param packageNameList a list of package names to check
     * @return a list of classes within the given packages that do not implement the provided interface
     */
    public static List<String> classesWithoutInterface(final Class<?> interfaceClass, final String... packageNameList)
    {
        List<String> result = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph() // .verbose() logs activities
            .overrideClasspath("target/classes") // don't include test classes
            .enableAllInfo() // Scan classes, methods, fields, annotations
            .acceptPackages(packageNameList) // Scan what's in pkg and subpackages (omit to scan all packages)
            .scan())
        {
            scanResult.getAllClasses()
                .stream()
                .filter(ci -> !ci.isInterface() && !ci.isEnum() && !ci.isAnnotation() && !ci.isRecord())
                .filter(ci -> !ci.isAnonymousInnerClass())
                .filter(ci -> !ci.implementsInterface(interfaceClass))
                .forEach(classInfo ->
                { result.add(classInfo.getName()); });
        }
        return result;
    }

    /**
     * Prints a list with the class names that do not implement the given interface. By default, the check ignores anonymous
     * inner classes, but includes explicit local or static inner classes. It only looks at classes, not at interfaces, records,
     * annotation classes or enums.
     * @param interfaceClass the interface to check for
     * @param packageNameList a list of package names to check
     */
    public static void printClassesWithoutInterface(final Class<?> interfaceClass, final String... packageNameList)
    {
        System.out.println("Classes without interface " + interfaceClass.getName());
        classesWithoutInterface(interfaceClass, packageNameList).forEach(System.out::println);
    }

    /**
     * Walk the path names and make a list of the classes that do not implement toString().
     * @param args can contain the package name(s) to inspect; org.djutils will be taken if the args are empty
     */
    public static void main(final String... args)
    {
        printClassesWithoutMethod("toString", args.length > 0 ? args : new String[] {"org.djutils"});
    }

}
