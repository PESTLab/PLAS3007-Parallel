package edu.plas.testautoandci.parallel;

import org.apache.commons.io.FileUtils;
import java.io.*;
import java.util.Collection;
import java.util.UUID;

public class ReportMerger {
    private static final String REPORT_FILE_NAME = "report.js";
    private static final String REPORT_IMAGE_EXTENSION = "png";

    public static void main(String[] args) throws Throwable {
        File reportDirectory = new File(args[0]);
        if (reportDirectory.exists()) {
            ReportMerger merger = new ReportMerger();
            merger.mergeReports(reportDirectory);
        }
    }

    public void mergeReports(File reportDirectory) throws Throwable {
        File mergedReport = null;

        // rename images
        Collection<File> existingReports = FileUtils.listFiles(reportDirectory, new String[]{"js"}, true);
        for (File report : existingReports) {
            //only address report files
            if (report.getName().equals(REPORT_FILE_NAME) && report.getPath().split("/").length == 3) {
                //rename all the image files (to give unique names) in report directory and update report
                renameEmbeddedImages(report);

                if (mergedReport == null) {
                    FileUtils.copyDirectory(report.getParentFile(), reportDirectory);
                    mergedReport = new File(reportDirectory, REPORT_FILE_NAME);
                } else {
                    String targetReport = FileUtils.readFileToString(mergedReport);
                    String sourceReport = FileUtils.readFileToString(report);
                    FileUtils.writeStringToFile(mergedReport, targetReport + sourceReport);
                }
            }
        }

        // copy and delete images
        Collection<File> embeddedImages = FileUtils.listFiles(reportDirectory, new String[]{REPORT_IMAGE_EXTENSION}, true);
        for (File image : embeddedImages) {
            if (image.getPath().split("/").length == 3) {
                FileUtils.copyFileToDirectory(image, reportDirectory);
                FileUtils.forceDelete(image);
            }
        }
    }

    public void renameEmbeddedImages(File reportFile) throws Throwable {
        File reportDirectory = reportFile.getParentFile();
        Collection<File> embeddedImages = FileUtils.listFiles(reportDirectory, new String[]{REPORT_IMAGE_EXTENSION}, true);

        String fileAsString = FileUtils.readFileToString(reportFile);

        for (File image : embeddedImages) {
            String curImageName = image.getName();
            String uniqueImageName = UUID.randomUUID().toString() + "." + REPORT_IMAGE_EXTENSION;

            image.renameTo(new File(reportDirectory, uniqueImageName));
            fileAsString = fileAsString.replace(curImageName, uniqueImageName);
        }

        FileUtils.writeStringToFile(reportFile, fileAsString);
    }
}