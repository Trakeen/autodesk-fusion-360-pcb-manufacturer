package org.otomotive.pcb.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.RequestScoped;
import org.apache.commons.lang3.tuple.Pair;
import org.otomotive.pcb.dto.*;
import org.otomotive.pcb.manufacturer.IConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.otomotive.pcb.Constants.COMMA;
import static org.otomotive.pcb.Constants.NEWLINE_PATTERN;
import static org.otomotive.pcb.dto.OutputType.BOM;
import static org.otomotive.pcb.dto.OutputType.PNP;

/**
 * Convert service.
 */
@RequestScoped
public class ConverterService {

    private IConverter converter;

    /**
     * Convert Autodesk Fusion 360 PCB FAO archive to target manufacturer archive.
     *
     * @param manufacturer PCB manufacturer
     * @return PCB manufacturer archive
     */
    public byte[] convert(
            final Manufacturer manufacturer,
            final byte[] zip
    ) throws IOException {

        final ByteArrayOutputStream mainZip = new ByteArrayOutputStream();
        final ByteArrayOutputStream gerberZip = new ByteArrayOutputStream();
        final ZipOutputStream gerberOut = new ZipOutputStream(gerberZip);
        final List<OutputFile<?>> files = new ArrayList<>();
        final Map<String, BomComponent> bomComponents = new HashMap<>();

        converter = manufacturer.getConverter();

        try (final ZipOutputStream out = new ZipOutputStream(mainZip)) {

            try (final ZipInputStream in = new ZipInputStream(new ByteArrayInputStream(zip))) {

                for (ZipEntry zipEntry = in.getNextEntry(); zipEntry != null; zipEntry = in.getNextEntry()) {

                    final String name = zipEntry.getName();

                    Log.infof("convert=%s input=%s", manufacturer, name);

                    // Gerber file
                    if (name.contains("/GerberFiles/") || name.contains("/DrillFiles/")) {

                        final String fileName = name.substring(name.lastIndexOf("/") + 1);

                        addFile(gerberOut, fileName, in.readAllBytes());
                    }
                    else if (name.endsWith(".csv")) {

                        final String input = new String(in.readAllBytes());

                        // Pick and place files
                        if (name.contains("Assembly/PnP")) {

                            final PnpType type = PnpType.fromFileName(name);
                            final List<PnpComponent> components = Arrays.stream(input.split(NEWLINE_PATTERN))
                                                                        .map(PnpComponent::fromLine)
                                                                        .toList();
                            final OutputFile<PnpComponent> file = OutputFile.<PnpComponent>builder()
                                    .type(PNP)
                                    .inputName(name)
                                    .components(components)
                                    .pnpType(type)
                                    .build();

                            files.add(file);
                        }
                        // Bill of materials file
                        else {

                            final String[] lines = input.split(NEWLINE_PATTERN);
                            final List<String> headers = Arrays.asList(lines[0].split(COMMA));
                            final List<BomComponent> components = Arrays.stream(lines)
                                                                        .map(line -> BomComponent.fromLine(headers, line))
                                                                        .filter(Objects::nonNull)
                                                                        .toList();
                            final OutputFile<BomComponent> file = OutputFile.<BomComponent>builder()
                                    .type(BOM)
                                    .inputName(name)
                                    .components(components)
                                    .build();

                            files.add(file);
                            components.forEach(c -> c.getParts().forEach(p -> bomComponents.put(p, c)));
                        }
                    }
                }

                final List<Pair<String, byte[]>> bomFiles = files.stream()
                        .map(this::toBom)
                        .filter(Objects::nonNull)
                        .toList();
                final List<Pair<String, byte[]>> pnpFiles = files.stream()
                        .map(f -> toPnp(f, bomComponents))
                        .filter(Objects::nonNull)
                        .toList();

                for (final Pair<String, byte[]> pair : bomFiles) {

                    addFile(out, pair.getKey(), pair.getValue());
                }

                for (final Pair<String, byte[]> pair : pnpFiles) {

                    addFile(out, pair.getKey(), pair.getValue());
                }
            }
            finally {

                gerberOut.close();
                addFile(out, "Gerber.zip", gerberZip.toByteArray());
            }
        }

        return mainZip.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private Pair<String, byte[]> toBom(final OutputFile<?> file) {

        if (!BOM.equals(file.getType())) {

            return null;
        }

        return converter.convertBom((OutputFile<BomComponent>) file);
    }

    @SuppressWarnings("unchecked")
    private Pair<String, byte[]> toPnp(final OutputFile<?> file, final Map<String, BomComponent> bomComponents) {

        if (!PNP.equals(file.getType())) {

            return null;
        }

        return converter.convertPnp((OutputFile<PnpComponent>) file, bomComponents);
    }

    private void addFile(final ZipOutputStream out, final String name, final byte[] content) throws IOException {

        final ZipEntry entry = new ZipEntry(name);

        Log.infof("addFile=%s", name);
        out.putNextEntry(entry);
        out.write(content);
    }
}
