package org.otomotive.pcb.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Output file type.
 */
@Getter
@AllArgsConstructor
@RegisterForReflection
public enum OutputType {

    /**
     * Bill of materials.
     */
    BOM,

    /**
     * Pick and place.
     */
    PNP;
}
