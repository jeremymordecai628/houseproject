package com.modex.exporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Iterator;


/**
 * Handles document preview rendering.
 */
public class DocumentPreviewer {

    /**
     * Render PDF preview using PDFBox
     */
    public static void renderPDF(FileInputStream fis, JPanel panel) {

        try {

            panel.removeAll();

            PDDocument document = PDDocument.load(fis);

            PDFRenderer renderer = new PDFRenderer(document);

            BufferedImage image = renderer.renderImageWithDPI(0, 150);

            JLabel label = new JLabel(new ImageIcon(image));

            panel.setLayout(new java.awt.BorderLayout());
            panel.add(new JScrollPane(label), java.awt.BorderLayout.CENTER);

            document.close();

            panel.revalidate();
            panel.repaint();

        } catch (Exception e) {

            panel.removeAll();
            panel.add(new JLabel("Error previewing PDF: " + e.getMessage()));

        }
    }


    /**
     * Render DOCX preview using Apache POI
     */
    public static void renderDOCX(FileInputStream fis, JPanel panel) {

        try {

            panel.removeAll();

            XWPFDocument document = new XWPFDocument(fis);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            for (XWPFParagraph para : document.getParagraphs()) {

                textArea.append(para.getText());
                textArea.append("\n");

            }

            panel.setLayout(new java.awt.BorderLayout());
            panel.add(new JScrollPane(textArea), java.awt.BorderLayout.CENTER);

            document.close();

            panel.revalidate();
            panel.repaint();

        } catch (Exception e) {

            panel.removeAll();
            panel.add(new JLabel("Error previewing DOCX: " + e.getMessage()));

        }
    }


    /**
     * Render XLSX preview using Apache POI
     */
    public static void renderXLSX(FileInputStream fis, JPanel panel) {

        try {

            panel.removeAll();

            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            Iterator<Row> rowIterator = workbook.getSheetAt(0).iterator();

            DefaultTableModel model = new DefaultTableModel();

            int columnCount = 0;

            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();

                if (columnCount == 0) {

                    columnCount = row.getLastCellNum();

                    for (int i = 0; i < columnCount; i++) {
                        model.addColumn("Col " + (i + 1));
                    }
                }

                Object[] data = new Object[columnCount];

                for (int i = 0; i < columnCount; i++) {

                    Cell cell = row.getCell(i);

                    if (cell != null)
                        data[i] = cell.toString();
                    else
                        data[i] = "";
                }

                model.addRow(data);
            }

            JTable table = new JTable(model);

            panel.setLayout(new java.awt.BorderLayout());
            panel.add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

            workbook.close();

            panel.revalidate();
            panel.repaint();

        } catch (Exception e) {

            panel.removeAll();
            panel.add(new JLabel("Error previewing XLSX: " + e.getMessage()));

        }
    }
}
