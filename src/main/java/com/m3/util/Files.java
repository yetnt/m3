/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.m3.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;
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

    public static void writeLines(File file, Consumer<PrintWriter> writer, boolean append) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file), append)) {
            writer.accept(pw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
