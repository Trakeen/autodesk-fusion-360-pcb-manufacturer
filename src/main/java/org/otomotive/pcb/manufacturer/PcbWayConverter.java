package org.otomotive.pcb.manufacturer;

import io.quarkus.logging.Log;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.otomotive.pcb.dto.BomComponent;
import org.otomotive.pcb.dto.OutputFile;
import org.otomotive.pcb.dto.PnpComponent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.otomotive.pcb.Constants.*;
import static org.otomotive.pcb.dto.Manufacturer.JLCPCB;

/**
 * JLCPCB converter.
 */
public class PcbWayConverter implements IConverter {

    private static final List<String> BOM_HEADER = List.of("Item #", "*Designator", "*Qty", "Manufacturer", "*Mfg Part #", "Description / Value", "*Package/Footprint ", "Type", "Your Instructions / Notes");
    private static final List<String> PNP_HEADER = List.of("Designator", "Footprint", "Mid X", "Mid Y", "Ref X", "Ref Y", "Pad X", "Pad Y", "TB", "Rotation", "Comment");

    @Override
    public Pair<String, byte[]> convertBom(final OutputFile<BomComponent> file) {

        final List<BomComponent> components = file.getComponents();

        Log.infof("convertBom input=%s components=%d", file.getInputName(), components.size());

        try (final Workbook workbook = new XSSFWorkbook()) {

            final Sheet sheet = workbook.createSheet("BOM");
            int row = addHeader(sheet, BOM_HEADER);

            for (final BomComponent component : components) {

                row = addBom(sheet, component, row);
            }

            return stream("BOM.xlsx", workbook);
        }
        catch (final IOException e) {

            Log.errorf(e, "convertBom input=%s components=%d", file.getInputName(), components.size());
            return null;
        }
    }

    @Override
    public Pair<String, byte[]> convertPnp(final OutputFile<PnpComponent> file, final Map<String, BomComponent> bomComponents) {

        final List<PnpComponent> components = file.getComponents();

        Log.infof("convertPnp=%s components=%d", file.getInputName(), components.size());

        try (final Workbook workbook = new XSSFWorkbook()) {

            final Sheet sheet = workbook.createSheet("CPL");
            int row = addHeader(sheet, PNP_HEADER);

            for (final PnpComponent component : components) {

                row = addPnp(sheet, component, row, bomComponents);
            }

            return stream("Centroid.xlsx", workbook);
        }
        catch (final IOException e) {

            Log.errorf(e, "convertPnp input=%s components=%d", file.getInputName(), components.size());
            return null;
        }
    }

    private Pair<String, byte[]> stream(final String name, final Workbook workbook) throws IOException {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        workbook.write(out);

        return Pair.of(name, out.toByteArray());
    }

    private int addHeader(final Sheet sheet, final List<String> headers) {

        final Row row = sheet.createRow(0);
        int c = 0;

        for (final String header : headers) {

            row.createCell(c++).setCellValue(header);
        }

        return 1;
    }

    private int addBom(final Sheet sheet, final BomComponent component, final int rowNumber) {

        final Row row = sheet.createRow(rowNumber);
        final Map<String, String> properties = component.getProperties();
        final String packageSize = properties.get("PACKAGE_SIZE");
        final String value = properties.get("VALUE");
        final List<String> parts = component.getParts();
        int c = 0;

        row.createCell(c++).setCellValue(rowNumber); // Item
        row.createCell(c++).setCellValue(String.join(COMMA, parts)); // Designator
        row.createCell(c++).setCellValue(component.getQuantity()); // Qty
        row.createCell(c++).setCellValue(component.getManufacturer()); // Manufacturer
        row.createCell(c++).setCellValue(component.getPackageName()); // Mfg Part
        row.createCell(c++).setCellValue(value == null || value.isBlank() ? component.getDescription() : value); // Description / Value
        row.createCell(c++).setCellValue(packageSize == null || packageSize.isBlank() ? component.getPackageName() : packageSize); // Package/Footprint
        row.createCell(c++).setCellValue(getType(component)); // Type
        row.createCell(c).setCellValue(EMPTY); // Your Instructions / Notes

        return rowNumber + 1;
    }

    private String getType(final BomComponent component) {

        final String mount = component.getProperties().get("MOUNT");

        if (mount != null) {

            switch (mount.trim()) {
                case "THT":
                    return "thru-hole";
                case "SMD":
                case "SMT":
                    return "SMD";
            }
        }

        final String gender = component.getProperties().get("GENDER");

        if ("Pin Header".equals(gender)) {

            return "thru-hole";
        }

        final String packageType = component.getProperties().get("PACKAGE_TYPE");

        if (packageType != null) {

            return switch (packageType.trim()) {
                case "Surface Mount" -> "SMD";
                case "Through Hole" -> "thru-hole";
                default -> packageType.isBlank() ? "SMD" : packageType;
            };
        }

        return "SMD";
    }

    private int addPnp(
            final Sheet sheet,
            final PnpComponent component,
            final int rowNumber,
            final Map<String, BomComponent> bomComponents
    ) {
        final Row row = sheet.createRow(rowNumber);
        final BomComponent bomComponent = bomComponents.get(component.getName());
        final double angle = component.getCorrectionAngle(bomComponent, JLCPCB);
        final String x = String.format("%f%s", component.getX(), MILLIMETERS);
        final String y = String.format("%f%s", component.getY(), MILLIMETERS);
        int c = 0;

        row.createCell(c++).setCellValue(component.getName()); // Designator
        row.createCell(c++).setCellValue(bomComponent.getPackageName()); // Footprint
        row.createCell(c++).setCellValue(x); // Mid X
        row.createCell(c++).setCellValue(y); // Mid Y
        row.createCell(c++).setCellValue(x); // Ref X
        row.createCell(c++).setCellValue(y); // Ref Y
        row.createCell(c++).setCellValue(x); // Pad X
        row.createCell(c++).setCellValue(y); // Pad Y

        // Layer
        switch (component.getPnpType()) {
            case BACK -> row.createCell(c++).setCellValue("B"); // Bottom
            case FRONT -> row.createCell(c++).setCellValue("T"); // Top
        }

        row.createCell(c++).setCellValue(Double.valueOf(angle).intValue()); // Rotation
        row.createCell(c).setCellValue(bomComponent.getDescription()); // Comment

        return rowNumber + 1;
    }
}
