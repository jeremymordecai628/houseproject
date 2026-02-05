import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

public class PdfToExcel {

    public static void main(String[] args) throws Exception {

        File pdfFile = new File("input.pdf");
        PDDocument document = PDDocument.load(pdfFile);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("PDF");
        int rowNum = 0;

        ObjectExtractor extractor = new ObjectExtractor(document);
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

        PageIterator pages = extractor.extract();
        while (pages.hasNext()) {
            Page page = pages.next();
            List<Table> tables = sea.extract(page);
            for (Table table : tables) {
                for (List<RectangularTextContainer> row : table.getRows()) {
                    Row excelRow = sheet.createRow(rowNum++);
                    int colNum = 0;
                    for (RectangularTextContainer cell : row) {
                        excelRow.createCell(colNum++).setCellValue(cell.getText());
                    }
                }
            }
        }

        document.close();

        FileOutputStream out = new FileOutputStream("output.xlsx");
        workbook.write(out);
        out.close();
        workbook.close();

        System.out.println("Conversion completed.");
    }
}

