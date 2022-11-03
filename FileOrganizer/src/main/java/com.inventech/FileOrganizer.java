package com.inventech;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FileOrganizer {
    final static String LOG_FILE_URL = "\"C:\\\\Users\\\\Dharm\\\\Documents\\\\output.txt\"";

    static void startFileProcessing(File fromPath, File toPath) throws IOException {
        int duplicate = 0;
        int total = 0;

        int[] ar = new int[2];
        String[] stringArr = {"*.jpg", "*png", "*.3gp", "*.jpeg", "*.mp4"};
        FileOrganizer obj = new FileOrganizer();
        if (fromPath == null) {
            return;
        }
        for (String s : stringArr) {
            ar = obj.FileOrganizer(fromPath, toPath, s);
            duplicate += ar[0];
            total += ar[1];
        }
        System.out.println(total + " total files" + duplicate + " duplicate files."); //will use for debugging purpose
        createLogs(total, duplicate);
    }

    int[] FileOrganizer(File path, File toPath, String extension) throws IOException {
        int fileCount = 0;
        int isExist = 0;
        int[] arr = new int[2];

        File[] fileList = path.listFiles();
        for (File file : fileList) {
            if (file != null) {
                BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                LocalDateTime time = attr.creationTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                int year = time.getYear();

                switch (year) {
                    case 2022:
                        isExist = fileMover(file, toPath, isExist, fileCount, year);
                        fileCount++;
                        break;
                    case 2020:
                        isExist = fileMover(file, toPath, isExist, fileCount, year);
                        fileCount++;
                        break;
                    default:
                        break;
                }
            }
            arr[0] = isExist;
            arr[1] = fileCount;
        }
        return arr;
    }

    private int fileMover(File file, File toPath, int isExist, int fileCount, int year) throws IOException {
        String from = file.getName();
        //checks year folder is exist, if not create a new folder with year name and then move.
        String to = toPath.getPath() + "'\'" + year + "'\'" + file.getName();
        if (file.exists()) {
            file.delete();
            isExist++;
        }
        Files.move(file.toPath(), Path.of(to));
        return isExist;
    }

    private static void createLogs(int total, int duplicate) {
        try {
            OutputStream outputStream = new FileOutputStream(LOG_FILE_URL, true);
            Writer outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write("Log: " + Date.from(Instant.now()) + Time.from(Instant.now()) + "\n" + total
                    + " , Duplicate files : " + duplicate);

            outputStreamWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String... args) throws IOException {
        FileOrganizerUIPannel panel = new FileOrganizerUIPannel();
        JFrame frame = panel.showPanel();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}