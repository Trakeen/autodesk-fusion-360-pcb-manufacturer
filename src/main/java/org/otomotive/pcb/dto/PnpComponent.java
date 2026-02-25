package org.otomotive.pcb.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.otomotive.pcb.utils.CsvReader;

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
    private double x;
    private double y;
    private double angle;
    private String value;
    private String packageName;

    /**
     * Create a {@link PnpComponent} from a line of a pick and place file.
     *
     * @param line Line of a pick and place file
     * @return A {@link PnpComponent} instance
     */
    public static PnpComponent fromLine(final String line) {

        final String[] fields = CsvReader.readLine(line, 6);

        return PnpComponent.builder()
                           .name(fields[0])
                           .x(Double.parseDouble(fields[1]))
                           .y(Double.parseDouble(fields[2]))
                           .angle(Double.parseDouble(fields[3]))
                           .value(fields[4])
                           .packageName(fields[5])
                           .build();
    }
}
