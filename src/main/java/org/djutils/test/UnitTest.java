package org.djutils.test;

/**
 * UnitTest has the methods to do a testFail(..) method for a unit test.
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
public final class UnitTest
{
    /** private constructor for utility class. */
    private UnitTest()
    {
        // utility class
    }

    /**
     * Method for unit tests to test if an expected exception is thrown on an assignment. This method does not provide an
     * explanation, and it is not checking for a specific type of exception to be thrown. The testFail() method throws an
     * AssertionError when the assignment does not throw any exception. A way to use the method is, for instance: <br>
     * 
     * <pre>
     * <code>
     *   UnitTest.testFail(() -&gt; methodFailsOnNull(null));
     * </code>
     * </pre>
     * 
     * or
     * 
     * <pre><code>
     *   UnitTest.testFail(new Try.Assignment&lt;Double&gt;()
     *   {
     *       {@literal @}Override
     *       public Double assign() throws Throwable
     *       {
     *           return methodFailsOnNull(null);
     *       }
     *   });
     * </code></pre>
     * 
     * @param assignment functional interface to assign value
     * @param <V> value type, which is the return type of the assignment
     * @return assigned value
     * @throws AssertionError when the assignment fails to throw an exception
     */
    public static <V> V testFail(final Assignment<V> assignment)
    {
        return testFail(assignment, null, Throwable.class);
    }

    /**
     * Method for unit tests to test if an expected exception is thrown on an assignment. This method provides an explanation
     * message, but it is not checking for a specific type of exception to be thrown. The testFail() method throws an
     * AssertionError when the assignment does not throw an exception. A way to use the method is, for instance: <br>
     * 
     * <pre>
     * <code>
     *   UnitTest.testFail(() -&gt; methodFailsOnNull(null), "call should have thrown an NPE");
     * </code>
     * </pre>
     * 
     * or
     * 
     * <pre><code>
     *   UnitTest.testFail(new Try.Assignment&lt;Double&gt;()
     *   {
     *       {@literal @}Override
     *       public Double assign() throws Throwable
     *       {
     *           return methodFailsOnNull(null);
     *       }
     *   }, "call should have thrown an NPE");
     * </code></pre>
     * 
     * @param assignment functional interface to assign value
     * @param <V> value type, which is the return type of the assignment
     * @param message message to use in the AssertionError when the assignment succeeds
     * @return assigned value
     * @throws AssertionError when the assignment fails to throw an exception
     */
    public static <V> V testFail(final Assignment<V> assignment, final String message)
    {
        return testFail(assignment, message, Throwable.class);
    }

    /**
     * Method for unit tests to test if an expected exception is thrown on an assignment. This method does not provide an
     * explanation, but it is checking for a specific type of exception to be thrown. The testFail() method throws an
     * AssertionError when the assignment does not throw an exception, or when it throws a different exception than
     * expectedThrowableClass. A way to use the method is, for instance: <br>
     * 
     * <pre>
     * <code>
     *   UnitTest.testFail(() -&gt; methodFailsOnNull(null), NullPointerException.class);
     * </code>
     * </pre>
     * 
     * or
     * 
     * <pre><code>
     *   UnitTest.testFail(new Try.Assignment&lt;Double&gt;()
     *   {
     *       {@literal @}Override
     *       public Double assign() throws Throwable
     *       {
     *           return methodFailsOnNull(null);
     *       }
     *   }, NullPointerException.class);
     * </code></pre>
     * 
     * @param assignment functional interface to assign value
     * @param expectedThrowableClass the class of the exception we expect the assignment to throw
     * @param <V> value type, which is the return type of the assignment
     * @param <T> throwable type, which ensures that we provide a throwable class as the argument
     * @return assigned value
     * @throws AssertionError when the assignment fails to throw an exception or the correct exception
     */
    public static <V, T extends Throwable> V testFail(final Assignment<V> assignment, final Class<T> expectedThrowableClass)
    {
        return testFail(assignment, null, expectedThrowableClass);
    }

    /**
     * Method for unit tests to test if an expected exception is thrown on an assignment. This method provides an explanation
     * message, and it is checking for a specific type of exception to be thrown. The testFail() method throws an AssertionError
     * when the assignment does not throw an exception, or when it throws a different exception than expectedThrowableClass. A
     * way to use the method is, for instance: <br>
     * 
     * <pre>
     * <code>
     *   UnitTest.testFail(() -&gt; methodFailsOnNull(null), "call should have thrown an NPE", NullPointerException.class);
     * </code>
     * </pre>
     * 
     * or
     * 
     * <pre><code>
     *   UnitTest.testFail(new Try.Assignment&lt;Double&gt;()
     *   {
     *       {@literal @}Override
     *       public Double assign() throws Throwable
     *       {
     *           return methodFailsOnNull(null);
     *       }
     *   }, "call should have thrown an NPE", NullPointerException.class);
     * </code></pre>
     * 
     * @param assignment functional interface to assign value
     * @param message message to use in the AssertionError when the test fails
     * @param expectedThrowableClass the class of the exception we expect the assignment to throw
     * @param <V> value type, which is the return type of the assignment
     * @param <T> throwable type, which ensures that we provide a throwable class as the argument
     * @return assigned value
     */
    public static <V, T extends Throwable> V testFail(final Assignment<V> assignment, final String message,
            final Class<T> expectedThrowableClass)
    {
        try
        {
            assignment.assign();
        }
        catch (Throwable cause)
        {
            if (!expectedThrowableClass.isAssignableFrom(cause.getClass()))
            {
                throw new AssertionError(message + "; Assignment failed on unexpected Throwable, expected ("
                        + expectedThrowableClass.getSimpleName() + "), but got (" + cause.getClass().getSimpleName() + ").");
            }
            return null;
        }
        throw new AssertionError(message + "; Assignment did not throw any exception");
    }

    /**
     * Method for unit tests to test if an expected exception is thrown on code execution. This method does not provide an
     * explanation message, nor is it checking for a specific type of exception to be thrown. The testFail() method throws an
     * AssertionError when the execution does not throw an exception. A way to use the method is, for instance: <br>
     * 
     * <pre>
     * <code>
     *   UnitTest.testFail(() -&gt; methodFailsOnNull(null));
     * </code>
     * </pre>
     * 
     * or
     * 
     * <pre><code>
     *   UnitTest.testFail(new Try.Execution()
     *   {
     *       {@literal @}Override
     *       public void execute() throws Throwable
     *       {
     *           methodFailsOnNull(null);
     *       }
     *   });
     * </code></pre>
     * 
     * @param execution functional interface to execute a method that does not need to return a value
     */
    public static void testFail(final Execution execution)
    {
        testFail(execution, null, Throwable.class);
    }

    /**
     * Method for unit tests to test if an expected exception is thrown on code execution. This method provides an explanation
     * message, but it is not checking for a specific type of exception to be thrown. The testFail() method throws an
     * AssertionError when the execution does not throw an exception, or when it throws a different exception than
     * expectedThrowableClass. A way to use the method is, for instance: <br>
     * 
     * <pre>
     * <code>
     *   UnitTest.testFail(() -&gt; methodFailsOnNull(null), "call should have thrown an NPE");
     * </code>
     * </pre>
     * 
     * or
     * 
     * <pre><code>
     *   UnitTest.testFail(new Try.Execution()
     *   {
     *       {@literal @}Override
     *       public void execute() throws Throwable
     *       {
     *           methodFailsOnNull(null);
     *       }
     *   }, "call should have thrown an NPE");
     * </code></pre>
     * 
     * @param execution functional interface to execute a method that does not need to return a value
     * @param message message to use in the AssertionError when the test fails
     */
    public static void testFail(final Execution execution, final String message)
    {
        testFail(execution, message, Throwable.class);
    }

    /**
     * Method for unit tests to test if an expected exception is thrown on code execution. This method does not provide an
     * explanation message, but it is checking for a specific type of exception to be thrown. The testFail() method throws an
     * AssertionError when the execution does not throw an exception, or when it throws a different exception than
     * expectedThrowableClass. A way to use the method is, for instance: <br>
     * 
     * <pre>
     * <code>
     *   UnitTest.testFail(() -&gt; methodFailsOnNull(null), NullPointerException.class);
     * </code>
     * </pre>
     * 
     * or
     * 
     * <pre><code>
     *   UnitTest.testFail(new Try.Execution()
     *   {
     *       {@literal @}Override
     *       public void execute() throws Throwable
     *       {
     *           methodFailsOnNull(null);
     *       }
     *   }, NullPointerException.class);
     * </code></pre>
     * 
     * @param execution functional interface to execute a method that does not need to return a value
     * @param expectedThrowableClass the class of the exception we expect the execution to throw
     * @param <T> throwable type, which ensures that we provide a throwable class as the argument
     */
    public static <T extends Throwable> void testFail(final Execution execution, final Class<T> expectedThrowableClass)
    {
        testFail(execution, null, expectedThrowableClass);
    }

    /**
     * Method for unit tests to test if an expected exception is thrown on code execution. This method provides an explanation
     * message, and it is checking for a specific type of exception to be thrown. The testFail() method throws an AssertionError
     * when the execution does not throw an exception, or when it throws a different exception than expectedThrowableClass. A
     * way to use the method is, for instance: <br>
     * 
     * <pre>
     * <code>
     *   UnitTest.testFail(() -&gt; methodFailsOnNull(null), "call should have thrown an NPE", NullPointerException.class);
     * </code>
     * </pre>
     * 
     * or
     * 
     * <pre><code>
     *   UnitTest.testFail(new Try.Execution()
     *   {
     *       {@literal @}Override
     *       public void execute() throws Throwable
     *       {
     *           methodFailsOnNull(null);
     *       }
     *   }, "call should have thrown an NPE", NullPointerException.class);
     * </code></pre>
     * 
     * @param execution functional interface to execute a method that does not need to return a value
     * @param message message to use in the AssertionError when the test fails
     * @param expectedThrowableClass the class of the exception we expect the execution to throw
     * @param <T> throwable type, which ensures that we provide a throwable class as the argument
     */
    public static <T extends Throwable> void testFail(final Execution execution, final String message,
            final Class<T> expectedThrowableClass)
    {
        try
        {
            execution.execute();
        }
        catch (Throwable cause)
        {
            if (!expectedThrowableClass.isAssignableFrom(cause.getClass()))
            {
                throw new AssertionError(message + "; Execution failed on unexpected Throwable, expected ("
                        + expectedThrowableClass.getSimpleName() + "), but got (" + cause.getClass().getSimpleName() + ").");
            }
            // expected to fail
            return;
        }
        throw new AssertionError(message + "; Execution did not throw any exception");
    }

    // Interfaces

    /**
     * Functional interface for calls to Try.assign(...). For this a lambda expression can be used.
     * 
     * <pre>
     * FileInputStream fis = Try.assign(() -&gt; new FileInputStream(fileString), IllegalArgumentException.class,
     *         "File %s is not a valid file.", fileString);
     * </pre>
     * <p>
     * Copyright (c) 2013-2025 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="https://djutils.org/docs/current/djutils/licenses.html">DJUTILS License</a>.
     * </p>
     * @author <a href="https://www.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="https://www.tudelft.nl/staff/p.knoppers/">Peter Knoppers</a>
     * @param <V> value type
     */
    @FunctionalInterface
    public interface Assignment<V>
    {
        /**
         * Returns a value which is obtained from the context in which the Assignment was created.
         * @return value which is obtained from the context in which the Assignment was created
         * @throws Throwable on any throwable in the try
         */
        V assign() throws Throwable;
    }

    /**
     * Functional interface for calls to Try.execute(...). For this a lambda expression can be used.
     * 
     * <pre>
     * Try.execute(() -&gt; fis.close(), "Could not close the file.");
     * </pre>
     * <p>
     * Copyright (c) 2013-2025 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="https://djutils.org/docs/current/djutils/licenses.html">DJUTILS License</a>.
     * </p>
     * @author <a href="https://www.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="https://www.tudelft.nl/staff/p.knoppers/">Peter Knoppers</a>
     */
    @FunctionalInterface
    public interface Execution
    {
        /**
         * Executes some code using the context in which the Execution was created.
         * @throws Throwable on any throwable in the try
         */
        void execute() throws Throwable;
    }

}
