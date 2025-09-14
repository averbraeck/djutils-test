package org.djutils.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests for ClassList.
 * <p>
 * Copyright (c) 2024-2025 Delft University of Technology, Jaffalaan 5, 2628 BX Delft, the Netherlands. All rights reserved. See
 * for project information <a href="https://djutils.org" target="_blank"> https://djutils.org</a>. The DJUTILS project is
 * distributed under a three-clause BSD-style license, which can be found at
 * <a href="https://djutils.org/docs/license.html" target="_blank"> https://djutils.org/docs/license.html</a>.
 * </p>
 * @author <a href="https://github.com/averbraeck">Alexander Verbraeck</a>
 * @author <a href="https://github.com/peter-knoppers">Peter Knoppers</a>
 * @author <a href="https://github.com/wjschakel">Wouter Schakel</a>
 */
public class ClassListTest
{

    /**
     * Test classesWithoutMethod.
     */
    @Test
    public void testClassesWithoutMethod()
    {
        List<String> clist1 = ClassList.classesWithoutMethod("xyzMethod", "org.djutils");
        assertTrue(clist1.size() > 0);
        List<String> clist2 = ClassList.classesWithoutMethod("classesWithoutMethod", "org.djutils");
        assertTrue(clist2.size() < clist1.size());

        var outSave = System.out;
        try
        {
            var baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            ClassList.printClassesWithoutMethod("xyzMethod", "org.djutils");
            String result = baos.toString();
            assertTrue(result.length() > 0);
        }
        finally
        {
            System.setOut(outSave);
        }
    }
    
    /**
     * Test classesWithoutInterface.
     */
    @Test
    public void testClassesWithoutInterface()
    {
        List<String> clist1 = ClassList.classesWithoutInterface(Serializable.class, "org.djutils");
        assertTrue(clist1.size() > 0);

        var outSave = System.out;
        try
        {
            var baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            ClassList.printClassesWithoutInterface(Serializable.class, "org.djutils");
            String result = baos.toString();
            assertTrue(result.length() > 0);
        }
        finally
        {
            System.setOut(outSave);
        }
    }
    
}
