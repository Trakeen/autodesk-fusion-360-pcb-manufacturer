package org.otomotive.pcb.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.otomotive.pcb.Constants.EMPTY;
import static org.otomotive.pcb.Constants.SPACE;

/**
 * Autodesk Fusion 360 CSV reader
 */
public class FixedLengthTxtReader {

    public static Map<String, String> readLine(final String line, final List<String> headers) {

        if (EMPTY.equals(line) || line.startsWith("Partlist exported from") || line.startsWith(headers.get(0))) {

            return null;
        }

        final Map<String, String> properties = new TreeMap<>();
        int start = 0;

        for (final String header : headers) {

            final int end = start + header.length();

            if (end >= line.length()) {

                properties.put(header.trim(), EMPTY);
                continue;
            }

            final String cell = line.substring(start, end);

            start = end;
            properties.put(header.trim(), cell.trim());
        }

        return properties;
    }

    public static List<String> readHeaders(final String[] lines) {

        return readHeader(lines[2]);
    }

    private static List<String> readHeader(final String line) {

        final List<String> headers = new ArrayList<>();
        final StringBuilder header = new StringBuilder();
        boolean inSpaces = false;

        for (int i = 0; i < line.length(); ++i) {

            char c = line.charAt(i);

            if (c == SPACE) {

                inSpaces = true;
            }
            // New header
            else if (inSpaces) {

                inSpaces = false;
                headers.add(header.toString());
                header.setLength(0);
            }

            header.append(c);
        }

        headers.add(header.toString());

        return headers;
    }
}
