package ru.ltst.u2020mvp.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.threeten.bp.Instant;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class InstantAdapterTest {
    @Test
    public void toJson() throws Exception {
        Instant input = Instant.now();
        String expected = input.toString();
        assertEquals(expected, new InstantAdapter().toJson(input));
    }

    @Test
    public void fromJson() throws Exception {
        Instant expected = Instant.now();
        String input = expected.toString();
        assertEquals(expected, new InstantAdapter().fromJson(input));
    }
}