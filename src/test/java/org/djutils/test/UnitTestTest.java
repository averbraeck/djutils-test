package org.djutils.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

/**
 * UnitTestTest tests the methods in the UnitTest class.
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
public class UnitTestTest
{
    /**
     * Test the fail methods in the Try class using assignments with lambda functions.
     * @throws RuntimeException if that happens (uncaught); this test has failed.
     */
    @SuppressWarnings({"checkstyle:methodlength", "null"})
    @Test
    public void tryFailTestAssignLambda() throws RuntimeException
    {
        // test case from Issue #7.
        try
        {
            Double dn = null;
            Double d1 = Double.valueOf(1.0);
            UnitTest.testFail(() -> d1.toString());
            UnitTest.testFail(() -> dn.toString());
        }
        catch (AssertionError e)
        {
            // ok, one of the two should have thrown an error
        }

        UnitTest.testFail(() -> fnfAssignment());
        UnitTest.testFail(() -> npeAssignment());
        UnitTest.testFail(() -> fnfAssignment(), "message");
        UnitTest.testFail(() -> npeAssignment(), "message");
        UnitTest.testFail(() -> fnfAssignment(), Exception.class);
        UnitTest.testFail(() -> npeAssignment(), Exception.class);
        UnitTest.testFail(() -> fnfAssignment(), FileNotFoundException.class);
        UnitTest.testFail(() -> fnfAssignment(), IOException.class);
        UnitTest.testFail(() -> npeAssignment(), NullPointerException.class);
        UnitTest.testFail(() -> fnfAssignment(), "message", Exception.class);
        UnitTest.testFail(() -> npeAssignment(), "message", Exception.class);
        UnitTest.testFail(() -> fnfAssignment(), "message", FileNotFoundException.class);
        UnitTest.testFail(() -> fnfAssignment(), "message", IOException.class);
        UnitTest.testFail(() -> npeAssignment(), "message", NullPointerException.class);

        // test assignment methods that do not throw exceptions
        try
        {
            UnitTest.testFail(() -> Math.abs(-1.0));
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment did not throw any exception"));
        }

        try
        {
            UnitTest.testFail(() -> Math.abs(-1.0), "xyz");
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment did not throw any exception"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        try
        {
            UnitTest.testFail(() -> Math.abs(-1.0), NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment did not throw any exception"));
        }

        try
        {
            UnitTest.testFail(() -> Math.abs(-1.0), "xyz", NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment did not throw any exception"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        // test assignment methods that throw the wrong exception

        try
        {
            UnitTest.testFail(() -> fnfAssignment(), NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
        }

        try
        {
            UnitTest.testFail(() -> fnfAssignment(), "xyz", NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        try
        {
            UnitTest.testFail(() -> npeAssignment(), IllegalStateException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
        }

        try
        {
            UnitTest.testFail(() -> npeAssignment(), "xyz", IllegalStateException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
            assertTrue(t.getMessage().contains("xyz"));
        }
    }

    /**
     * Test the fail methods in the Try class using Assignments.
     * @throws RuntimeException if that happens (uncaught); this test has failed.
     */
    @SuppressWarnings("checkstyle:methodlength")
    @Test
    public void tryFailTestAssign() throws RuntimeException
    {
        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign() throws FileNotFoundException
            {
                return fnfAssignment();
            }
        });

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign()
            {
                return npeAssignment();
            }
        });

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign() throws FileNotFoundException
            {
                return fnfAssignment();
            }
        }, "message");

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign()
            {
                return npeAssignment();
            }
        }, "message");

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign() throws FileNotFoundException
            {
                return fnfAssignment();
            }
        }, Exception.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign()
            {
                return npeAssignment();
            }
        }, Exception.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign() throws FileNotFoundException
            {
                return fnfAssignment();
            }
        }, FileNotFoundException.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign() throws FileNotFoundException
            {
                return fnfAssignment();
            }
        }, IOException.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign()
            {
                return npeAssignment();
            }
        }, NullPointerException.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign() throws FileNotFoundException
            {
                return fnfAssignment();
            }
        }, "message", Exception.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign()
            {
                return npeAssignment();
            }
        }, "message", Exception.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign() throws FileNotFoundException
            {
                return fnfAssignment();
            }
        }, "message", FileNotFoundException.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign() throws FileNotFoundException
            {
                return fnfAssignment();
            }
        }, "message", IOException.class);

        UnitTest.testFail(new UnitTest.Assignment<Object>()
        {
            @Override
            public Object assign()
            {
                return npeAssignment();
            }
        }, "message", NullPointerException.class);

        // test assignment methods that do not throw exceptions
        try
        {
            UnitTest.testFail(new UnitTest.Assignment<Object>()
            {
                @Override
                public Object assign()
                {
                    return Math.abs(-1.0);
                }
            });
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment did not throw any exception"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Assignment<Object>()
            {
                @Override
                public Object assign()
                {
                    return Math.abs(-1.0);
                }
            }, "xyz");
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment did not throw any exception"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Assignment<Object>()
            {
                @Override
                public Object assign()
                {
                    return Math.abs(-1.0);
                }
            }, NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment did not throw any exception"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Assignment<Object>()
            {
                @Override
                public Object assign()
                {
                    return Math.abs(-1.0);
                }
            }, "xyz", NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment did not throw any exception"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        // test assignment methods that throw the wrong exception

        try
        {
            UnitTest.testFail(new UnitTest.Assignment<Object>()
            {
                @Override
                public Object assign() throws FileNotFoundException
                {
                    return fnfAssignment();
                }
            }, NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Assignment<Object>()
            {
                @Override
                public Object assign() throws FileNotFoundException
                {
                    return fnfAssignment();
                }
            }, "xyz", NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Assignment<Object>()
            {
                @Override
                public Object assign()
                {
                    return npeAssignment();
                }
            }, IllegalStateException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Assignment<Object>()
            {
                @Override
                public Object assign()
                {
                    return npeAssignment();
                }
            }, "xyz", IllegalStateException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Assignment"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
            assertTrue(t.getMessage().contains("xyz"));
        }
    }

    /**
     * Test the fail methods in the Try class using lambda executions.
     * @throws RuntimeException if that happens (uncaught); this test has failed.
     */
    @SuppressWarnings("checkstyle:methodlength")
    @Test
    public void tryFailTestExecuteLambda() throws RuntimeException
    {
        UnitTest.testFail(() -> fnfExecution());
        UnitTest.testFail(() -> npeExecution());
        UnitTest.testFail(() -> fnfExecution(), "message");
        UnitTest.testFail(() -> npeExecution(), "message");
        UnitTest.testFail(() -> fnfExecution(), Exception.class);
        UnitTest.testFail(() -> npeExecution(), Exception.class);
        UnitTest.testFail(() -> fnfExecution(), FileNotFoundException.class);
        UnitTest.testFail(() -> fnfExecution(), IOException.class);
        UnitTest.testFail(() -> npeExecution(), NullPointerException.class);
        UnitTest.testFail(() -> fnfExecution(), "message", Exception.class);
        UnitTest.testFail(() -> npeExecution(), "message", Exception.class);
        UnitTest.testFail(() -> fnfExecution(), "message", FileNotFoundException.class);
        UnitTest.testFail(() -> fnfExecution(), "message", IOException.class);
        UnitTest.testFail(() -> npeExecution(), "message", NullPointerException.class);

        // test execution methods that do not throw exceptions
        try
        {
            UnitTest.testFail(() -> new HashSet<String>().clear());
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution did not throw any exception"));
        }

        try
        {
            UnitTest.testFail(() -> new HashSet<String>().clear(), "xyz");
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution did not throw any exception"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        try
        {
            UnitTest.testFail(() -> new HashSet<String>().clear(), NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution did not throw any exception"));
        }

        try
        {
            UnitTest.testFail(() -> new HashSet<String>().clear(), "xyz", NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution did not throw any exception"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        // test execution methods that throw the wrong exception

        try
        {
            UnitTest.testFail(() -> fnfExecution(), NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
        }

        try
        {
            UnitTest.testFail(() -> fnfExecution(), "xyz", NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        try
        {
            UnitTest.testFail(() -> npeExecution(), IllegalStateException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
        }

        try
        {
            UnitTest.testFail(() -> npeExecution(), "xyz", IllegalStateException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
            assertTrue(t.getMessage().contains("xyz"));
        }
    }

    /**
     * Test the fail methods in the Try class using Executions.
     * @throws RuntimeException if that happens (uncaught); this test has failed.
     */
    @SuppressWarnings("checkstyle:methodlength")
    @Test
    public void tryFailTestExecute() throws RuntimeException
    {
        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute() throws FileNotFoundException
            {
                fnfExecution();
            }
        });

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute()
            {
                npeExecution();
            }
        });

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute() throws FileNotFoundException
            {
                fnfExecution();
            }
        }, "message");

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute()
            {
                npeExecution();
            }
        }, "message");

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute() throws FileNotFoundException
            {
                fnfExecution();
            }
        }, Exception.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute()
            {
                npeExecution();
            }
        }, Exception.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute() throws FileNotFoundException
            {
                fnfExecution();
            }
        }, FileNotFoundException.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute() throws FileNotFoundException
            {
                fnfExecution();
            }
        }, IOException.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute()
            {
                npeExecution();
            }
        }, NullPointerException.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute() throws FileNotFoundException
            {
                fnfExecution();
            }
        }, "message", Exception.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute()
            {
                npeExecution();
            }
        }, "message", Exception.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute() throws FileNotFoundException
            {
                fnfExecution();
            }
        }, "message", FileNotFoundException.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute() throws FileNotFoundException
            {
                fnfExecution();
            }
        }, "message", IOException.class);

        UnitTest.testFail(new UnitTest.Execution()
        {
            @Override
            public void execute()
            {
                npeExecution();
            }
        }, "message", NullPointerException.class);

        // test assignment methods that do not throw exceptions
        try
        {
            UnitTest.testFail(new UnitTest.Execution()
            {
                @Override
                public void execute()
                {
                    new HashSet<Double>().clear();
                }
            });
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution did not throw any exception"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Execution()
            {
                @Override
                public void execute()
                {
                    new HashSet<Double>().clear();
                }
            }, "xyz");
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution did not throw any exception"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Execution()
            {
                @Override
                public void execute()
                {
                    new HashSet<Double>().clear();
                }
            }, NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution did not throw any exception"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Execution()
            {
                @Override
                public void execute()
                {
                    new HashSet<Double>().clear();
                }
            }, "xyz", NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution did not throw any exception"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        // test assignment methods that throw the wrong exception

        try
        {
            UnitTest.testFail(new UnitTest.Execution()
            {
                @Override
                public void execute() throws FileNotFoundException
                {
                    fnfExecution();
                }
            }, NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Execution()
            {
                @Override
                public void execute() throws FileNotFoundException
                {
                    fnfExecution();
                }
            }, "xyz", NullPointerException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
            assertTrue(t.getMessage().contains("xyz"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Execution()
            {
                @Override
                public void execute()
                {
                    npeExecution();
                }
            }, IllegalStateException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
        }

        try
        {
            UnitTest.testFail(new UnitTest.Execution()
            {
                @Override
                public void execute()
                {
                    npeExecution();
                }
            }, "xyz", IllegalStateException.class);
        }
        catch (Throwable t)
        {
            assertTrue(t instanceof AssertionError);
            assertTrue(t.getMessage().contains("Execution"));
            assertTrue(t.getMessage().contains("unexpected Throwable"));
            assertTrue(t.getMessage().contains("xyz"));
        }
    }

    /**
     * Test method that throws FNFE.
     * @return Object (never happens)
     * @throws FileNotFoundException to test FNF and IO exceptions
     */
    private Object fnfAssignment() throws FileNotFoundException
    {
        throw new FileNotFoundException();
    }

    /**
     * Test method that throws FNFE.
     * @throws FileNotFoundException to test FNF and IO exceptions
     */
    private void fnfExecution() throws FileNotFoundException
    {
        throw new FileNotFoundException();
    }

    /**
     * Test method that throws NPE.
     * @return Object (never happens)
     */
    private Object npeAssignment()
    {
        throw new NullPointerException();
    }

    /**
     * Test method that throws NPE.
     */
    private void npeExecution()
    {
        throw new NullPointerException();
    }

}
