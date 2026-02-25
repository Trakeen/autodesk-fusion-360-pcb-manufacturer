package org.otomotive.pcb.utils;

import java.util.*;

import static org.otomotive.pcb.Constants.DOUBLE_QUOTE;

/**
 * Autodesk Fusion 360 CSV reader
 */
public class CsvReader {

    /**
     * Comma.
     */
    private static final char COMMA = ',';

    public static Map<String, String> readLine(final String line, final List<String> headers) {

        final Map<String, String> properties = new HashMap<>();

        Arrays.stream(readLine(line, headers.size()))
                .forEach(column -> properties.put(headers.get(properties.size()), column));

        return properties;
    }

    public static String[] readLine(final String line, final int columnsCount) {

        final String[] columns = new String[columnsCount];
        final StringBuilder column = new StringBuilder();
        int columnIndex = 0;
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); ++i) {

            char c = line.charAt(i);

            // Column inside double quotes
            if (c == DOUBLE_QUOTE) {

                inQuotes = !inQuotes;
            }
            // New column
            else if (c == COMMA && !inQuotes) {

                columns[columnIndex++] = column.toString();
                column.setLength(0);
            }
            else {

                column.append(c);
            }
        }

        columns[columnIndex] = column.toString();

        return columns;
    }
}
