package fr.fistin.fistinframework.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FistinValidateTest
{
    @Test
    public void testNotNullWithNull()
    {
        assertThrows(FistinFrameworkException.class, () -> FistinValidate.notNull(null, "Null"));
    }

    @Test
    public void testNotNullWithEmptyString()
    {
        FistinValidate.notNull("", "Null");
    }

    @Test
    public void testNotEqualsWithEquals()
    {
        assertThrows(FistinFrameworkException.class, () -> FistinValidate.notEquals("foo", "foo", "not equals"));
    }

    @Test
    public void testNotEqualsWithNotEquals()
    {
        FistinValidate.notEquals("foo", "bar", "not equals");
    }

    @Test
    public void testEqualsWithNotEquals()
    {
        assertThrows(FistinFrameworkException.class, () -> FistinValidate.equals("foo", "bar", "equals"));
    }

    @Test
    public void testEqualsWithEquals()
    {
        FistinValidate.equals("foo", "foo", "equals");
    }

    @Test
    public void testAssertTrueWithFalse()
    {
        assertThrows(FistinFrameworkException.class, () -> FistinValidate.assertTrue(false, "assert true"));
    }

    @Test
    public void testAssertTrueWithTrue()
    {
        FistinValidate.assertTrue(true, "assert true");
    }

    @Test
    public void testNumberInferiorWithSuperior()
    {
        assertThrows(FistinFrameworkException.class, () -> FistinValidate.numberInferior(2, 1, "not inferior"));
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

    @Test
    public void testNumberSuperiorWithInferior()
    {
        assertThrows(FistinFrameworkException.class, () -> FistinValidate.numberSuperior(0, 1, "not superior"));
    }

    @Test
    public void testNumberSuperiorWithEquals()
    {
        assertThrows(FistinFrameworkException.class, () -> FistinValidate.numberSuperior(1, 1, "not superior"));
    }

    @Test
    public void testNumberSuperiorWithSuperior()
    {
        FistinValidate.numberSuperior(2, 1, "not superior");
    }

    @Test
    public void testNumberPositiveWithNegative()
    {
        assertThrows(FistinFrameworkException.class, () -> FistinValidate.numberPositive(-1, "not positive"));
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
