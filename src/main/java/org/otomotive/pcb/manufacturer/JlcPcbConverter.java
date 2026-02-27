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
import org.otomotive.pcb.dto.PnpType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.otomotive.pcb.Constants.*;

/**
 * JLCPCB converter.
 */
public class JlcPcbConverter implements IConverter {

    private static final List<String> BOM_HEADER = List.of("Comment" ,"Designator","Footprint", "JLCPCB Part #（optional）");
    private static final List<String> PNP_HEADER = List.of("Designator","Mid X","Mid Y","Layer","Rotation");

    @Override
    public Pair<String, byte[]> convertBom(final OutputFile<BomComponent> file) {

        final List<BomComponent> components = file.getComponents();

        Log.infof("convertBom input=%s components=%d", file.getInputName(), components.size());

        try (final Workbook workbook = new XSSFWorkbook()) {

            final Sheet sheet = workbook.createSheet("BOM");
            int row = addHeader(sheet, BOM_HEADER);

            for (final BomComponent component : components) {

               row= addBom(sheet, component, row);
            }

            return stream("BOM.xlsx", workbook);
        } catch (final IOException e) {

            Log.errorf(e, "convertBom input=%s components=%d", file.getInputName(), components.size());
            return null;
        }
    }

    @Override
    public Pair<String, byte[]> convertPnp(final OutputFile<PnpComponent> file, final Map<String, BomComponent> bomComponents) {

        final List<PnpComponent> components = file.getComponents();
        final PnpType type = file.getPnpType();

        Log.infof("convertPnp=%s input=%s components=%d", type, file.getInputName(), components.size());

        try (final Workbook workbook = new XSSFWorkbook()) {

            final Sheet sheet = workbook.createSheet("CPL");
            int row = addHeader(sheet, PNP_HEADER);

            for (final PnpComponent component : components) {

                row = addPnp(sheet, component, type, row, bomComponents);
            }

            return stream("CPL.xlsx", workbook);
        } catch (final IOException e) {

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
        final String mpn = component.getProperties().get("MPN");
        final String packageSize = component.getProperties().get("PACKAGE_SIZE");
        final String ref = component.getProperties().get("JLCPCB");
        int c = 0;

        row.createCell(c++).setCellValue(mpn == null || mpn.isBlank() ? component.getValue() : mpn);
        row.createCell(c++).setCellValue(String.join(COMMA, component.getParts()));
        row.createCell(c++).setCellValue(packageSize == null || packageSize.isBlank() ? component.getPackageName() : packageSize);
        row.createCell(c).setCellValue(ref == null || ref.isBlank() ? EMPTY : ref);

        return rowNumber+1;
    }

    private int addPnp(
            final Sheet sheet,
            final PnpComponent component,
            final PnpType type,
            final int rowNumber,
            final Map<String, BomComponent> bomComponents
    ) {
        final Row row = sheet.createRow(rowNumber);
        final BomComponent bomComponent = bomComponents.get(component.getName());
        final String angleCorrection = bomComponent.getProperties().get("CORRECTION_ANGLE");
        double angle = component.getAngle();
        int c = 0;

        row.createCell(c++).setCellValue(component.getName());
        row.createCell(c++).setCellValue(String.format("%f%s", component.getX(), MILLIMETERS));
        row.createCell(c++).setCellValue(String.format("%f%s", component.getY(), MILLIMETERS));

        switch (type) {
            case BACK -> row.createCell(c++).setCellValue("Bottom"); // Bottom
            case FRONT -> row.createCell(c++).setCellValue("Top"); // Top
        }

        if (angleCorrection != null && !angleCorrection.isBlank()) {

            try {

                angle += Double.parseDouble(angleCorrection);
            } catch (final NumberFormatException e) {

                Log.errorf(e, "addPnp=%s msg=Invalid angle correction %s", component.getName(), angleCorrection);
            }
        }

        row.createCell(c).setCellValue(Double.valueOf(angle).intValue());

        return rowNumber+1;
    }
}
