package org.otomotive.pcb.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.otomotive.pcb.utils.CsvReader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.otomotive.pcb.Constants.*;

/**
 * Pick and place component.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
public class BomComponent {

    private int quantity;
    private String value;
    private String device;
    private String packageName;
    private String parts;
    private String description;
    private Map<String, String> properties;

    /**
     * Create a {@link BomComponent} from a line of a pick and place file.
     *
     * @param headers Headers of a pick and place file
     * @param line Line of a pick and place file
     * @return A {@link BomComponent} instance
     */
    public static BomComponent fromLine(final List<String> headers, final String line) {

        if (line.startsWith(headers.get(0))) {

            return null;
        }

        final Map<String, String> properties = CsvReader.readLine(line, headers);

        return BomComponent.builder()
                           .quantity(Integer.parseInt(properties.remove("Qty")))
                           .value(properties.remove("Value"))
                           .device(properties.remove("Device"))
                           .packageName(properties.remove("Package"))
                           .parts(properties.remove("Parts"))
                           .description(properties.remove("Description"))
                           .properties(properties)
                           .build();
    }
}
