package org.otomotive.pcb.utils;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Tests for {@link FixedLengthTxtReader}.
 */
public class TxtReaderTest extends Assertions {

    /**
     * Test for {@link FixedLengthTxtReader#readHeaders(String[])} and {@link FixedLengthTxtReader#readLine(String, List)}.
     */
    @Test
    public void readLine() throws IOException {

        final File file = new File("target/test-classes/bom.txt");
        final String[] lines = FileUtils.readLines(file, StandardCharsets.UTF_8)
                                        .toArray(String[]::new);

        assertEquals(13, lines.length);

        final List<String> headers = FixedLengthTxtReader.readHeaders(lines);

        assertEquals(46, headers.size());

        final Map<String, String> properties = FixedLengthTxtReader.readLine(lines[3], headers);

        assertEquals(46, properties.size());

        assertNotLine(headers, lines, 0);
        assertNotLine(headers, lines, 1);
        assertNotLine(headers, lines, 2);
    }

    private void assertNotLine(final List<String> headers, final String[] lines, int lineIndex) {

        final String line = lines[lineIndex];
        final Map<String, String> properties = FixedLengthTxtReader.readLine(line, headers);

        assertNull(properties);
    }
}
