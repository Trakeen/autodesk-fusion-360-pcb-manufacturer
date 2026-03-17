package org.otomotive.pcb.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.otomotive.pcb.manufacturer.IConverter;
import org.otomotive.pcb.manufacturer.JlcPcbConverter;
import org.otomotive.pcb.manufacturer.PcbWayConverter;

/**
 * PCB manufacturer.
 */
@Getter
@AllArgsConstructor
@RegisterForReflection
public enum Manufacturer {

    /**
     * JLCPCB.
     */
    JLCPCB(new JlcPcbConverter()),

    /**
     * PCB Way.
     */
    PCBWAY(new PcbWayConverter());

    /**
     * FAO converter.
     */
    private final IConverter converter;
}
