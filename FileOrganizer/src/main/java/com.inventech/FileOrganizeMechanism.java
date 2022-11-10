package com.inventech;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FileOrganizeMechanism {

    final static String USER = "Dharm";
    final static String LOG_FILE_URL = "C:\\Users\\" + USER + "\\Documents\\output.txt";

    static void startFileProcessing(File fromPath, File toPath) throws IOException {
        int total;
        if (fromPath == null) {
            return;
        }
        total = FileOrganizer(fromPath, toPath);
        createLogs(fromPath, toPath, total);
    }

    private static void createLogs(File fromPath, File toPath, int total) {
        try {
            OutputStream outputStream = new FileOutputStream(LOG_FILE_URL, true);
            Writer outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write("Log: " + Date.from(Instant.now()) + Time.from(Instant.now()) + "\n"
                    + total + " total files moved from " + fromPath + " to location " + toPath + "\n");
            outputStreamWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int FileOrganizer(File path, File toPath) throws IOException {
        int fileCount = 0;
        File[] fileList = path.listFiles();
        if (fileList == null) {
            return 0;
        }
        for (File file : fileList) {
            if (file != null) {
                BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                LocalDateTime time = attr.creationTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                int year = time.getYear();
                fileMover(file, toPath, year);
                fileCount++;
            }
        }
        return fileCount;
    }

    private static void fileMover(File file, File toPath, int year) throws IOException {
        String fileName = file.getName();
        File directory = new File(toPath.getPath() + "\\" + year);
        if (!directory.exists()) {
            boolean res = directory.mkdir();
        }
        String to = directory + "\\" + fileName;
        Files.move(file.toPath(), Path.of(to));
    }
}
