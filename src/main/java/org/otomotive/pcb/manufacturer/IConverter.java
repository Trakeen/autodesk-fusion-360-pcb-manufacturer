package org.otomotive.pcb.manufacturer;

import org.apache.commons.lang3.tuple.Pair;
import org.otomotive.pcb.dto.BomComponent;
import org.otomotive.pcb.dto.PnpComponent;
import org.otomotive.pcb.dto.PnpType;

import java.io.IOException;
import java.util.List;

/**
 * PCB manufacturer converter interface.
 */
public interface IConverter {

    /**
     * Convert a bill of materials to a target manufacturer file.
     *
     * @param name       Bill of materials file name
     * @param components Components from the bill of materials
     * @return Target manufacturer file
     * @throws IOException If an I/O error occurs
     */
    Pair<String, byte[]> convertBom(String name, List<BomComponent> components) throws IOException;

    /**
     * Convert a pick and place file to a target manufacturer file.
     *
     * @param name       Pick and place file name
     * @param type       Pick and place file type
     * @param components Components from the pick and place file
     * @return Target manufacturer file
     * @throws IOException If an I/O error occurs
     */
    Pair<String, byte[]> convertPnp(String name, PnpType type, List<PnpComponent> components) throws IOException;
}
