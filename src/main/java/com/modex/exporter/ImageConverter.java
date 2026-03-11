package com.example.converter;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ImageConverter {

    /**
     * Converts an image file into a DOCX file by embedding the image.
     *
     * @param imagePath  Path to the source image
     * @param outputPath Path where the DOCX will be saved
     * @throws Exception if file processing fails
     */
    public static void convertImageToDocx(String imagePath, String outputPath) throws Exception {

        try (XWPFDocument document = new XWPFDocument();
             FileInputStream imageStream = new FileInputStream(imagePath);
             FileOutputStream out = new FileOutputStream(outputPath)) {

            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();

            int pictureType = getPictureType(imagePath);

            run.addPicture(
                    imageStream,
                    pictureType,
                    imagePath,
                    Units.toEMU(500), // width
                    Units.toEMU(400)  // height
            );

            document.write(out);
        }
    }

    /**
     * Converts an image file into an XLSX file by embedding the image.
     *
     * @param imagePath  Path to the source image
     * @param outputPath Path where the XLSX will be saved
     * @throws Exception if file processing fails
     */
    public static void convertImageToXlsx(String imagePath, String outputPath) throws Exception {

        try (Workbook workbook = new XSSFWorkbook();
             FileInputStream imageStream = new FileInputStream(imagePath);
             FileOutputStream out = new FileOutputStream(outputPath)) {

            Sheet sheet = workbook.createSheet("Image Sheet");

            byte[] bytes = imageStream.readAllBytes();
            int pictureType = getPictureType(imagePath);

            int pictureIdx = workbook.addPicture(bytes, pictureType);
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            CreationHelper helper = workbook.getCreationHelper();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(1);
            anchor.setRow1(1);

            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();

            workbook.write(out);
        }
    }

    /**
     * Determines Apache POI picture type based on file extension.
     */
    private static int getPictureType(String imagePath) {

        String lower = imagePath.toLowerCase();

        if (lower.endsWith(".png")) {
            return Workbook.PICTURE_TYPE_PNG;
        } else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
            return Workbook.PICTURE_TYPE_JPEG;
        } else {
            throw new IllegalArgumentException("Unsupported image format");
        }
    }
}
