package org.otomotive.pcb.manufacturer;

import org.apache.commons.lang3.tuple.Pair;
import org.otomotive.pcb.dto.BomComponent;
import org.otomotive.pcb.dto.OutputFile;
import org.otomotive.pcb.dto.PnpComponent;

import java.util.Map;

/**
 * PCB manufacturer converter interface.
 */
public interface IConverter {

    /**
     * Convert a bill of materials to a target manufacturer file.
     *
     * @param file       Bill of materials file
     * @return Target manufacturer file
     */
    Pair<String, byte[]> convertBom(OutputFile<BomComponent> file);

    /**
     * Convert a pick and place file to a target manufacturer file.
     *
     * @param file       Pick and place file
     * @param bomComponents Bill of materials components for reference
     * @return Target manufacturer file
     */
    Pair<String, byte[]> convertPnp(OutputFile<PnpComponent> file, Map<String, BomComponent> bomComponents);
}
