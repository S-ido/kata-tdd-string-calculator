package com.chebdowski.katatddstringcalc;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Piotr Chebdowski on 12.06.2017.
 */

public class StringCalculatorTest {

    @Test
    public void add_emptyParameter_returnsZero() {
        int result = StringCalculator.add("");
        assertThat(result, is(0));
    }

    @Test
    public void add_oneParameter_returnsGivenParameter() {
        int result = StringCalculator.add("12");
        assertThat(result, is(12));
    }

    @Test
    public void add_twoNumbers_returnsSum() {
        int result = StringCalculator.add("2,3");
        assertThat(result, is(5));
    }

    @Test
    public void add_threeNumbers_returnsSum() {
        int result = StringCalculator.add("2,3,5");
        assertThat(result, is(10));
    }

    @Test
    public void add_anyNumberOfParameters_returnsSum() {
        int result = StringCalculator.add("2,23,43,67,1");
        assertThat(result, is(2 + 23 + 43 + 67 + 1));
    }

    @Test(expected = NumberFormatException.class)
    public void add_incorrectParameter_exceptionThrown() {
        StringCalculator.add("1+6");
    }

    @Test
    public void add_newLineAsSplitRegex_returnsSum() {
        int value = StringCalculator.add("1\n6");
        assertThat(value, is(1+6));
    }

    @Test
    public void add_useCustomDelimiter_returnsSum() {
        int result = StringCalculator.add("//[:]\n1:6");
        assertThat(result, is(7));
    }

    @Test
    public void add_useDelimiterOfAnyLength_returnsSum() {
        int result = StringCalculator.add("//[koko]\n34koko67koko2koko1koko982");
        assertThat(result, is(34+67+2+1+982));
    }

    @Test(expected = RuntimeException.class)
    public void add_negativeValues_runtimeExceptionThrown() {
        StringCalculator.add("3, 6, 34, -1");
    }

    @Test
    public void add_negativeValues_runtimeExceptionHasProperMessage() {
        RuntimeException exception = null;

        try {
            StringCalculator.add("1,-4,2,-23,6");
        } catch(RuntimeException e) {
            exception = e;
        }

        assertThat(exception, is(notNullValue()));
        assertThat(exception.getMessage(), is("Negatives not allowed: [-4, -23]"));
    }

    @Test
    public void add_ignoreNumbersBiggerThan1000_returnsSum() {
        int result = StringCalculator.add("1,9,1234");
        assertThat(result, is(10));
    }

   @Test
   public void add_anyNumberOfDelimiters_returnsSum() {
       int result = StringCalculator.add("//[koko][<][>]\n4>34<2koko10");
       assertThat(result, is(4+34+2+10));
   }
}
