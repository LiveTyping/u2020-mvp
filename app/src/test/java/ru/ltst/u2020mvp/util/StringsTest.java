package ru.ltst.u2020mvp.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringsTest {
    @Test
    public void isBlank() throws Exception {
        assertFalse(Strings.isBlank("123"));
        assertTrue(Strings.isBlank(" "));
        assertTrue(Strings.isBlank(""));
        assertTrue(Strings.isBlank(null));
    }

    @Test
    public void valueOrDefault() throws Exception {
        String defaultVal = "default";
        assertEquals(defaultVal, Strings.valueOrDefault(" ", defaultVal));
        assertEquals(defaultVal, Strings.valueOrDefault("", defaultVal));
        assertNotEquals(defaultVal, Strings.valueOrDefault(",", defaultVal));
        assertEquals(defaultVal, Strings.valueOrDefault(null, defaultVal));
    }

    @Test
    public void truncateAt() throws Exception {
        assertEquals("", Strings.truncateAt("abc", 0));
        assertEquals("abc", Strings.truncateAt("abcdef", 3));
        assertEquals("abc", Strings.truncateAt("abc", 6));
        assertEquals("", Strings.truncateAt("", 4));
        assertNull(Strings.truncateAt(null, 2));
        assertEquals("abc", Strings.truncateAt("abc", -1));
        assertNull(Strings.truncateAt(null, -1));
    }

}