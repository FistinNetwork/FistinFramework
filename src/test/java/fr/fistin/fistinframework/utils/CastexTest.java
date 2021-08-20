package fr.fistin.fistinframework.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CastexTest
{
    @Test
    public void testCast()
    {
        final A a = new B();
        final B b = a.cast();

        final String testA = a.test();
        final String testB = b.test();

        assertEquals(testB, testA);
        assertEquals(a, b);
    }

    private static class A implements Castex<A>
    {
        String test()
        {
            return "A";
        }
    }

    private static class B extends A
    {
        @Override
        String test()
        {
            return "B";
        }
    }
}
