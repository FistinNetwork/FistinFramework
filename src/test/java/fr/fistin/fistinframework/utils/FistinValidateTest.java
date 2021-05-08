package fr.fistin.fistinframework.utils;

import org.junit.Test;

public class FistinValidateTest
{
    @Test(expected = FistinFrameworkException.class)
    public void testNotNullWithNull()
    {
        FistinValidate.notNull(null, "Null");
    }

    @Test
    public void testNotNullWithEmptyString()
    {
        FistinValidate.notNull("", "Null");
    }

    @Test(expected = FistinFrameworkException.class)
    public void testNotEqualsWithEquals()
    {
        FistinValidate.notEquals("foo", "foo", "not equals");
    }

    @Test
    public void testNotEqualsWithNotEquals()
    {
        FistinValidate.notEquals("foo", "bar", "not equals");
    }

    @Test(expected = FistinFrameworkException.class)
    public void testNumberInferiorWithSuperior()
    {
        FistinValidate.numberInferior(2, 1, "not inferior");
    }

    @Test
    public void testNumberInferiorWithEquals()
    {
        FistinValidate.numberInferior(1, 1, "not inferior");
    }

    @Test
    public void testNumberInferiorWithInferior()
    {
        FistinValidate.numberInferior(0, 1, "var mustn't be inferior");
    }

    @Test(expected = FistinFrameworkException.class)
    public void testNumberSuperiorWithInferior()
    {
        FistinValidate.numberSuperior(0, 1, "not superior");
    }

    @Test(expected = FistinFrameworkException.class)
    public void testNumberSuperiorWithEquals()
    {
        FistinValidate.numberSuperior(1, 1, "not superior");
    }

    @Test
    public void testNumberSuperiorWithSuperior()
    {
        FistinValidate.numberSuperior(2, 1, "not superior");
    }

    @Test(expected = FistinFrameworkException.class)
    public void testNumberPositiveWithNegative()
    {
        FistinValidate.numberPositive(-1, "not positive");
    }

    @Test
    public void testNumberPositiveWithZero()
    {
        FistinValidate.numberPositive(0, "not positive");
    }

    @Test
    public void testNumberPositiveWithPositive()
    {
        FistinValidate.numberPositive(1, "not positive");
    }
}
