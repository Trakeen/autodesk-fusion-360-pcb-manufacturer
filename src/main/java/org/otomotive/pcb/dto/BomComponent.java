package org.otomotive.pcb.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.otomotive.pcb.utils.FixedLengthTxtReader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.otomotive.pcb.Constants.EMPTY;

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
    private List<String> parts;
    private String description;
    private Map<String, String> properties;

    /**
     * Create a {@link BomComponent} from a line of a pick and place file.
     *
     * @param headers Headers of a pick and place file
     * @param line    Line of a pick and place file
     * @return A {@link BomComponent} instance
     */
    public static BomComponent fromLine(final List<String> headers, final String line) {

        final Map<String, String> properties = FixedLengthTxtReader.readLine(line, headers);

        if (properties == null) {

            return null;
        }

        final String[] aParts = properties.remove("Parts").split(", ");

        return BomComponent.builder()
                           .quantity(Integer.parseInt(properties.remove("Qty")))
                           .value(properties.remove("Value"))
                           .device(properties.remove("Device"))
                           .parts(Arrays.asList(aParts))
                           .description(properties.remove("Description"))
                           .properties(properties)
                           .build();
    }

    /**
     * Get manufacturer.
     *
     * @return Manufacturer
     */
    public String getManufacturer() {

        String manufacturer = properties.get("MANUFACTURER");

        if (manufacturer != null && !manufacturer.isEmpty()) {

            return manufacturer;
        }

        manufacturer = properties.get("MF");

        if (manufacturer != null && !manufacturer.isEmpty()) {

            return manufacturer;
        }

        manufacturer = properties.get("MANUFACTURER_NAME");

        if (manufacturer != null && !manufacturer.isEmpty()) {

            return manufacturer;
        }

        return EMPTY;
    }

    /**
     * Get package name.
     *
     * @return Package name.
     */
    public String getPackageName() {

        String packageName = properties.get("MP");

        if (packageName != null && !packageName.isEmpty()) {

            return packageName;
        }

        packageName = properties.get("Package");

        if (packageName != null && !packageName.isEmpty()) {

            return packageName;
        }

        return EMPTY;
    }
}
