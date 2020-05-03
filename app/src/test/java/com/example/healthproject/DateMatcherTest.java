package com.example.healthproject;

import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class DateMatcherTest {
    String pattern = "^([0-2][0-9]|3[0-1]|[0-9])/(0[0-9]|1[0-2]|[0-9])/([0-9][0-9])?[0-9][0-9]$";
    Pattern matcher = Pattern.compile(pattern);

    @Test
    public void testDates(){
        assertTrue(matcher.matcher("17/02/2020").matches());
        assertTrue(matcher.matcher("31/01/2020").matches());
        assertTrue(matcher.matcher("13/1/2020").matches());
        assertTrue(matcher.matcher("13/1/20").matches());
        assertFalse(matcher.matcher("").matches());
        assertFalse(matcher.matcher("2/2/").matches());
        assertFalse(matcher.matcher("32/02/2020").matches());
        assertFalse(matcher.matcher("32/02/2020").matches());
        assertFalse(matcher.matcher("02/13/2020").matches());
        assertFalse(matcher.matcher("1/02/1").matches());
        assertFalse(matcher.matcher("1/02/999").matches());
        assertFalse(matcher.matcher("1/092/999").matches());
        assertFalse(matcher.matcher("1/02/0").matches());
        assertFalse(matcher.matcher("//").matches());


    }
}
