package com.m3.util;

import com.m3.files.Folders;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author ACER
 */
public class Files {
       /**
     * Shows a file chooser dialog and returns the selected file's absolute path.
     * @param chooserConfigure A consumer that configures the file chooser.
     * @return The absolute path of the selected file, or null if no file is selected.
     */
    public static File fileChooser(Consumer<JFileChooser> chooserConfigure, JFrame frameParent) {
        JFileChooser chooser = new JFileChooser();
        chooserConfigure.accept(chooser);

        int result = chooser.showOpenDialog(frameParent);
        return result == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile(): null;
    }

    /**
     * Shows a file chooser dialogue that allows multiple file selections.
     * @param chooserConfigure A consumer that configures the file chooser.
     * @param frameParent The parent frame for the dialogue.
     * @return An ArrayList of selected files, or null if no files are selected.
     */
    public static ArrayList<File> multipleFileChooser(Consumer<JFileChooser> chooserConfigure, JFrame frameParent) {
        JFileChooser chooser = new JFileChooser();
        chooserConfigure.accept(chooser);
        chooser.setMultiSelectionEnabled(true);

        int result = chooser.showOpenDialog(frameParent);
        if (result == JFileChooser.APPROVE_OPTION) {
            ArrayList<File> selectedFiles = new ArrayList<>();
            Collections.addAll(selectedFiles, chooser.getSelectedFiles());
            return selectedFiles;
        } else {
            return null;
        }
    }
    
    /**
     * Shows a folder chooser dialog and returns the selected folder's absolute path.
     * @return The absolute path of the selected folder, or null if no folder is selected.
     */
    public static File folderChooser(JFrame frameParent) {
        return fileChooser(chooser -> {
            chooser.setDialogTitle("Select Folder Big Dawgg");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
        }, frameParent);
    }

    public static ArrayList<String> readLines(File file) {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    /**
     * Writes lines to a specified file.
     * @param file The file to write to.
     * @param writer A consumer that accepts a PrintWriter to write content.
     * @param append True to append to the file, false to overwrite.
     */
    public static void writeLines(File file, Consumer<PrintWriter> writer, boolean append) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, append), true)) {
            writer.accept(pw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a file or a directory to a ZipOutputStream.
     * @param file The file or directory to add.
     * @param entryName The name of the entry within the zip file.
     * @param zos The ZipOutputStream to write to.
     * @throws IOException If an I/O error occurs.
     */
    public static void addFileToZip(File file, String entryName, ZipOutputStream zos) throws IOException {
        if (file.isDirectory()) {
            for (File nestedFile : file.listFiles()) {
                addFileToZip(nestedFile, entryName + "/" + nestedFile.getName(), zos);
            }
        } else {
            ZipEntry zipEntry = new ZipEntry(entryName);
            zos.putNextEntry(zipEntry);
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
            }
            zos.closeEntry();
        }
    }

    /**
     * Backs up the mods folder by zipping its contents and saving it to a specified backup folder.
     * The zip file is named with a timestamp.
     * @param BACKUP_FOLDER The folder where the backup zip file will be saved.
     */
    public static void backupModsFolder(File BACKUP_FOLDER) {

        File[] amt = BACKUP_FOLDER.listFiles();
        if (amt == null || amt.length == 0) {
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = sdf.format(new Date());
        String zipFileName = "mods_backup_" + timestamp + ".zip";
        File zipFile = new File(BACKUP_FOLDER, zipFileName);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            File modsFolder = Folders.getInstance().getModsFolder();
            if (modsFolder != null && modsFolder.exists() && modsFolder.isDirectory()) {
                for (File file : modsFolder.listFiles()) {
                    Files.addFileToZip(file, file.getName(), zos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
