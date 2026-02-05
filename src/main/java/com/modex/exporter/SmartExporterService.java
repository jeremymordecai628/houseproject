package com.modex.exporter;

import java.io.File;
import java.io.IOException;

public class SmartExporterService {

    /**
     * Export a file using LibreOffice or Microsoft Office.
     * targetFormat: pdf, docx, xlsx
     */
    public static File export(File input, String targetFormat) throws Exception {

        if (!input.exists()) {
            throw new IllegalArgumentException("Input file does not exist.");
        }

        String libreCmd = detectLibreOffice();

        if (libreCmd != null) {
            return exportWithLibreOffice(libreCmd, input, targetFormat);
        }

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return exportWithMicrosoftOffice(input, targetFormat);
        }

        throw new RuntimeException("No LibreOffice or Microsoft Office found.");
    }

    // -------------------- LIBREOFFICE --------------------

    private static File exportWithLibreOffice(String cmd, File input, String fmt) throws Exception {

        String outDir = input.getParent();

        ProcessBuilder pb = new ProcessBuilder(
                cmd,
                "--headless",
                "--nologo",
                "--nofirststartwizard",
                "--convert-to", fmt,
                input.getAbsolutePath(),
                "--outdir", outDir
        );

        pb.redirectErrorStream(true);
        Process p = pb.start();
        p.waitFor();

        return buildOutputFile(input, fmt);
    }

    private static String detectLibreOffice() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("mac")) {
                String mac = "/Applications/LibreOffice.app/Contents/MacOS/soffice";
                if (new File(mac).exists()) return mac;
            }

            if (commandExists("libreoffice")) return "libreoffice";
            if (commandExists("soffice")) return "soffice";

        } catch (Exception ignored) {}

        return null;
    }

    // -------------------- MICROSOFT OFFICE --------------------

    private static File exportWithMicrosoftOffice(File input, String fmt) throws Exception {

        String out = buildOutputFile(input, fmt).getAbsolutePath();
        String in = input.getAbsolutePath().replace("\\", "\\\\");

        String ps;

        if (input.getName().endsWith(".xlsx") || input.getName().endsWith(".xls")) {
            ps = "$e=New-Object -ComObject Excel.Application;"
               + "$w=$e.Workbooks.Open('" + in + "');"
               + "$w.ExportAsFixedFormat(0,'" + out + "');"
               + "$w.Close($false);$e.Quit();";
        }
        else if (input.getName().endsWith(".docx") || input.getName().endsWith(".doc")) {
            ps = "$w=New-Object -ComObject Word.Application;"
               + "$d=$w.Documents.Open('" + in + "');"
               + "$d.ExportAsFixedFormat('" + out + "',17);"
               + "$d.Close();$w.Quit();";
        }
        else if (input.getName().endsWith(".pptx") || input.getName().endsWith(".ppt")) {
            ps = "$p=New-Object -ComObject PowerPoint.Application;"
               + "$pr=$p.Presentations.Open('" + in + "');"
               + "$pr.SaveAs('" + out + "',32);"
               + "$pr.Close();$p.Quit();";
        }
        else {
            throw new RuntimeException("Microsoft Office cannot handle this file type.");
        }

        ProcessBuilder pb = new ProcessBuilder(
                "powershell",
                "-Command",
                ps
        );

        pb.redirectErrorStream(true);
        Process p = pb.start();
        p.waitFor();

        File result = new File(out);
        if (!result.exists()) throw new IOException("MS Office export failed.");

        return result;
    }

    // -------------------- UTIL --------------------

    private static boolean commandExists(String cmd) throws Exception {
        Process p = new ProcessBuilder("which", cmd).start();
        return p.waitFor() == 0;
    }

    private static File buildOutputFile(File input, String fmt) {
        String base = input.getName().replaceFirst("[.][^.]+$", "");
        return new File(input.getParent(), base + "." + fmt);
    }
}

