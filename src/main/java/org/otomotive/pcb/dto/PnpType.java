package org.otomotive.pcb.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Pick and place file type.
 */
@Getter
@AllArgsConstructor
@RegisterForReflection
public enum PnpType {

    /**
     * Back pick and place.
     */
    BACK("_back.csv"),

    /**
     * Front pick and place.
     */
    FRONT("_front.csv");

    /**
     * File suffix.
     */
    private final String suffix;

    /**
     * Get pick and place type from file name.
     *
     * @param fileName File name
     * @return Pick and place type, or null if not found
     */
    public static PnpType fromFileName(final String fileName) {

        for (final PnpType pnpType : values()) {

            if (fileName.endsWith(pnpType.getSuffix())) {

                return pnpType;
            }
        }

        return null;
    }
}
