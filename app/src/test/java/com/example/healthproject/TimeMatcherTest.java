package com.example.healthproject;

import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class TimeMatcherTest {
    String pattern = "^(2[0-3]|[0-1][0-9]|[0-9]):([0-5][0-9]|[0-9])$";
    Pattern matcher = Pattern.compile(pattern);

    @Test
    public void testTimes(){
        assertTrue(matcher.matcher("17:00").matches());
        assertFalse(matcher.matcher("").matches());
        assertTrue(matcher.matcher("09:13").matches());
        assertTrue(matcher.matcher("10:0").matches());
        assertTrue(matcher.matcher("1:00").matches());
        assertFalse(matcher.matcher("9").matches());
        assertFalse(matcher.matcher("9:").matches());
    }


}
