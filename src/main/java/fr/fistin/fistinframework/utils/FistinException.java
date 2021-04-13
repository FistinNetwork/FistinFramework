package fr.fistin.fistinframework.utils;

public class FistinException extends RuntimeException
{
    public FistinException(String message)
    {
        super(message);
    }

    public FistinException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FistinException(Throwable cause)
    {
        super(cause);
    }
}
