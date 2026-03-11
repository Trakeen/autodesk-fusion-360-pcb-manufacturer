package org.otomotive.pcb.dto;

import io.quarkus.logging.Log;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Output file.
 *
 * @param <C> Component type
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
public class OutputFile<C> {

    private OutputType type;
    private String inputName;
    private List<C> components;
    private Class<C> componentType;

    /**
     * Merge with another output file.
     *
     * @param other Other output file
     * @return Merged output file
     */
    public OutputFile<C> merge(final OutputFile<C> other) {

        if (other == null) {

            return this;
        }

        Log.infof("merge=%s other=%s", inputName, other.inputName);

        final List<C> mergedComponents = new ArrayList<>(components);

        mergedComponents.addAll(other.getComponents());

        return new OutputFile<C>(
                type,
                String.format("%s+%s", inputName, other.getInputName()),
                mergedComponents,
                componentType
        );
    }
}
