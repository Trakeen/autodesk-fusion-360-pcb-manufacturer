package org.otomotive.pcb.dto;

import io.quarkus.logging.Log;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class PnpComponent {

    private String name;
    private PnpType pnpType;
    private double x;
    private double y;
    private double angle;
    private String value;
    private String packageName;

    /**
     * Create a {@link PnpComponent} from a line of a pick and place file.
     *
     * @param line    Line of a pick and place file
     * @param pnpType Pick and place type
     * @return A {@link PnpComponent} instance
     */
    public static PnpComponent fromLine(final String line, final PnpType pnpType) {

        final String[] fields = line.split(TAB);

        return PnpComponent.builder()
                           .name(fields[0])
                           .pnpType(pnpType)
                           .x(Double.parseDouble(fields[1]))
                           .y(Double.parseDouble(fields[2]))
                           .angle(Double.parseDouble(fields[3]))
                           .value(fields[4])
                           .packageName(fields[5])
                           .build();
    }

    /**
     * Get angle correction.
     *
     * @param bomComponent BOM component
     * @param manufacturer Manufacturer
     * @return Corrected angle
     */
    public double getCorrectionAngle(
            final BomComponent bomComponent,
            final Manufacturer manufacturer
    ) {
        return getCorrection(bomComponent, manufacturer, angle, SUFFIX_CORRECTION_ANGLE);
    }

    /**
     * Get X correction.
     *
     * @param bomComponent BOM component
     * @param manufacturer Manufacturer
     * @return Corrected X
     */
    public double getCorrectionX(
            final BomComponent bomComponent,
            final Manufacturer manufacturer
    ) {
        return getCorrection(bomComponent, manufacturer, x, SUFFIX_CORRECTION_X);
    }

    /**
     * Get Y correction.
     *
     * @param bomComponent BOM component
     * @param manufacturer Manufacturer
     * @return Corrected Y
     */
    public double getCorrectionY(
            final BomComponent bomComponent,
            final Manufacturer manufacturer
    ) {
        return getCorrection(bomComponent, manufacturer, y, SUFFIX_CORRECTION_Y);
    }

    /**
     * Get correction.
     *
     * @param bomComponent BOM component
     * @param manufacturer Manufacturer
     * @return Corrected number
     */
    public double getCorrection(
            final BomComponent bomComponent,
            final Manufacturer manufacturer,
            final double initNumber,
            final String suffix
    ) {
        final Map<String, String> properties = bomComponent.getProperties();
        final String key = manufacturer.name().concat(suffix);
        final String strCorrection = properties.get(key);
        double correction = initNumber;

        if (strCorrection != null && !strCorrection.isBlank()) {

            try {

                correction += Double.parseDouble(strCorrection);
            }
            catch (final NumberFormatException e) {

                Log.errorf(e, "addPnp=%s msg=Invalid %s correction %s", name, key, strCorrection);

                throw new IllegalArgumentException(String.format("Check %s attribute %s: %s", name, key, strCorrection));
            }
        }

        return correction;
    }
}
