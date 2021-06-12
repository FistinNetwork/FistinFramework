package fr.fistin.fistinframework.utils;

public class FistinValidate
{
    public static void notNull(Object object, String message)
    {
        if (object == null) throw new FistinFrameworkException(message);
    }

    public static void notNull(Object object, String message, Object... format)
    {
        if (object == null) throw new FistinFrameworkException(message, format);
    }

    public static void notEquals(Object first, Object second, String message)
    {
        if(first.equals(second))
            throw new FistinFrameworkException(message);
    }

    public static void notEquals(Object first, Object second, String message, Object... format)
    {
        if(first.equals(second))
            throw new FistinFrameworkException(message, format);
    }

    public static void equals(Object first, Object second, String message)
    {
        if(!first.equals(second))
            throw new FistinFrameworkException(message);
    }

    public static void equals(Object first, Object second, String message, Object... format)
    {
        if(!first.equals(second))
            throw new FistinFrameworkException(message, format);
    }

    public static void assertTrue(boolean expr, String message, Object... format)
    {
        if(!expr)
            throw new FistinFrameworkException(message, format);
    }

    public static void assertTrue(boolean expr, String message)
    {
        if(!expr)
            throw new FistinFrameworkException(message);
    }

    public static void numberInferior(long number, long limit, String message)
    {
        if(number > limit)
            throw new FistinFrameworkException(message);
    }

    public static void numberInferior(long number, long limit, String message, Object... format)
    {
        if(number > limit)
            throw new FistinFrameworkException(message, format);
    }

    public static void numberSuperior(long number, long limit, String message)
    {
        if(number <= limit)
            throw new FistinFrameworkException(message);
    }

    public static void numberSuperior(long number, long limit, String message, Object... format)
    {
        if(number <= limit)
            throw new FistinFrameworkException(message, format);
    }

    public static void numberPositive(long number, String message)
    {
        if(number < 0)
            throw new FistinFrameworkException(message);
    }

    public static void numberPositive(long number, String message, Object... format)
    {
        if(number < 0)
            throw new FistinFrameworkException(message, format);
    }

    public static void numberPositive(double number, String message)
    {
        if(number < 0)
            throw new FistinFrameworkException(message);
    }

    public static void numberPositive(double number, String message, Object... format)
    {
        if(number < 0)
            throw new FistinFrameworkException(message, format);
    }
}
