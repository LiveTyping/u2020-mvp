package ru.ltst.u2020mvp.util;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnumPreferencesTest {
    @Mock
    SharedPreferences sharedPreferences;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getEnumValue() throws Exception {
        when(sharedPreferences.getString("key1", null))
                .thenReturn("ITEM_TWO");
        when(sharedPreferences.getString("key2", null))
                .thenReturn("ITEM_THREE");


        TestEnumeration defaultVal = TestEnumeration.ITEM_ONE;
        assertEquals(TestEnumeration.ITEM_TWO, EnumPreferences.getEnumValue(sharedPreferences,
                TestEnumeration.class, "key1", defaultVal));
        assertEquals(TestEnumeration.ITEM_THREE, EnumPreferences.getEnumValue(sharedPreferences,
                TestEnumeration.class, "key2", defaultVal));
        when(sharedPreferences.getString("key3", defaultVal.name()))
                .thenReturn(defaultVal.name());
        assertEquals(defaultVal, EnumPreferences.getEnumValue(sharedPreferences,
                TestEnumeration.class, "key3", defaultVal));

        expectedException.expect(NullPointerException.class);
        TestEnumeration enumeration = EnumPreferences.getEnumValue(sharedPreferences,
                null, "key2", defaultVal);
        enumeration = EnumPreferences.getEnumValue(null, null, "key2", defaultVal);

        when(sharedPreferences.getString("key4", null))
                .thenReturn(null);
        assertNull(EnumPreferences.getEnumValue(sharedPreferences, TestEnumeration.class,
                "key4", null));
    }

    @SuppressLint("CommitPrefEdits")
    @Test
    public void saveEnumValue() throws Exception {
        when(sharedPreferences.edit())
                .thenReturn(mock(SharedPreferences.Editor.class));
        when(sharedPreferences.edit().putString(anyString(), eq(TestEnumeration.ITEM_ONE.name())))
                .thenReturn(mock(SharedPreferences.Editor.class));
        EnumPreferences.saveEnumValue(sharedPreferences, "key", TestEnumeration.ITEM_ONE);
        expectedException.expect(NullPointerException.class);
        EnumPreferences.saveEnumValue(null, "key", TestEnumeration.ITEM_ONE);
        EnumPreferences.saveEnumValue(sharedPreferences, null, TestEnumeration.ITEM_ONE);
        EnumPreferences.saveEnumValue(sharedPreferences, "key", null);
    }

    private enum TestEnumeration {
        ITEM_ONE,
        ITEM_TWO,
        ITEM_THREE
    }

}