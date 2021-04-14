package fr.fistin.fistinframework.utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static fr.fistin.fistinframework.runnable.RunnableUtils.convertToTick;
import static org.junit.Assert.assertEquals;

public class RunnableUtilsTest
{
    @Test
    public void testConvertToTickAfter()
    {
        final long from = 2L;
        assertEquals(40L, convertToTick(from, TimeUnit.SECONDS));
        assertEquals(2400L, convertToTick(from, TimeUnit.MINUTES));
        assertEquals(144000L, convertToTick(from, TimeUnit.HOURS));
        assertEquals(3456000L, convertToTick(from, TimeUnit.DAYS));
    }

    @Test
    public void testConvertToTickBefore()
    {
        final long from = 98500000L;
        assertEquals(1970000L, convertToTick(from, TimeUnit.MILLISECONDS));
        assertEquals(1970L, convertToTick(from, TimeUnit.MICROSECONDS));
        assertEquals(2L, convertToTick(from, TimeUnit.NANOSECONDS));
    }

    @Test(expected = FistinFrameworkException.class)
    public void testConvertToTickMilliError()
    {
        convertToTick(20L, TimeUnit.MILLISECONDS);
    }

    @Test(expected = FistinFrameworkException.class)
    public void testConvertToTickMicroError()
    {
        convertToTick(20L, TimeUnit.MICROSECONDS);
    }

    @Test(expected = FistinFrameworkException.class)
    public void testConvertToTickNanoError()
    {
        convertToTick(20L, TimeUnit.NANOSECONDS);
    }
}
