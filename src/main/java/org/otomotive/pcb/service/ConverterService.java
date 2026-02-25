package org.otomotive.pcb.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.RequestScoped;
import org.apache.commons.lang3.tuple.Pair;
import org.otomotive.pcb.dto.BomComponent;
import org.otomotive.pcb.dto.Manufacturer;
import org.otomotive.pcb.dto.PnpComponent;
import org.otomotive.pcb.dto.PnpType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.otomotive.pcb.Constants.COMMA;
import static org.otomotive.pcb.Constants.NEWLINE_PATTERN;

/**
 * Convert service.
 */
@RequestScoped
public class ConverterService {

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

        try (final ZipOutputStream out = new ZipOutputStream(mainZip)) {

            try (final ZipInputStream in = new ZipInputStream(new ByteArrayInputStream(zip))) {

                for (ZipEntry zipEntry = in.getNextEntry(); zipEntry != null; zipEntry = in.getNextEntry()) {

                    final String name = zipEntry.getName();

                    Log.infof("convert=%s input=%s", manufacturer, name);

                    // Gerber file
                    if (name.contains("/GerberFiles/")) {

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
                            final Pair<String, byte[]> file = manufacturer.getConverter().convertPnp(name, type, components);

                            addFile(out, file.getKey(), file.getValue());
                        }
                        // Bill of materials file
                        else {

                            final String[] lines = input.split(NEWLINE_PATTERN);
                            final List<String> headers = Arrays.asList(lines[0].split(COMMA));
                            final List<BomComponent> components = Arrays.stream(lines)
                                                                        .map(line -> BomComponent.fromLine(headers, line))
                                                                        .filter(Objects::nonNull)
                                                                        .toList();
                            final Pair<String, byte[]> file = manufacturer.getConverter().convertBom(name, components);

                            addFile(out, file.getKey(), file.getValue());
                        }
                    }
                }
            }
            finally {

                gerberOut.close();
                addFile(out, "Gerber.zip", gerberZip.toByteArray());
            }
        }

        return mainZip.toByteArray();
    }

    private void addFile(final ZipOutputStream out, final String name, final byte[] content) throws IOException {

        final ZipEntry entry = new ZipEntry(name);

        Log.infof("addFile=%s", name);
        out.putNextEntry(entry);
        out.write(content);
    }
}
