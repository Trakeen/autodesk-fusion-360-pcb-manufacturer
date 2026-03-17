package org.otomotive.pcb.dto;

import io.quarkus.logging.Log;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import static org.otomotive.pcb.Constants.SUFFIX_CORRECTION_ANGLE;
import static org.otomotive.pcb.Constants.TAB;

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
     * Get correction angle.
     *
     * @param bomComponent BOM component
     * @param manufacturer Manufacturer
     * @return Angle in degrees
     */
    public double getCorrectionAngle(
            final BomComponent bomComponent,
            final Manufacturer manufacturer
    ) {
        final Map<String, String> properties = bomComponent.getProperties();
        final String angleCorrection = properties.get(manufacturer.name().concat(SUFFIX_CORRECTION_ANGLE));
        double correction = angle;

        if (angleCorrection != null && !angleCorrection.isBlank()) {

            try {

                correction += Double.parseDouble(angleCorrection);
            }
            catch (final NumberFormatException e) {

                Log.errorf(e, "addPnp=%s msg=Invalid angle correction %s", name, angleCorrection);
            }
        }

        return correction;
    }
}
