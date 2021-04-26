package fr.fistin.fistinframework.utils;

public class FistinFrameworkException extends RuntimeException
{
    public FistinFrameworkException(String message)
    {
        super(message);
    }

    public FistinFrameworkException(String message, Object... params)
    {
        super(String.format(message, params));
    }

    public FistinFrameworkException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FistinFrameworkException(Throwable cause)
    {
        super(cause);
    }
}
