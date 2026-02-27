package org.otomotive.pcb.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

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
    private PnpType pnpType;
    private String inputName;
    private List<C> components;
}
