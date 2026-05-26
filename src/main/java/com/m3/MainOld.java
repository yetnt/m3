package com.m3;

import java.io.File;

public class MainOld {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("The mods absolute path is required!");
            System.exit(1);
        }

        File modsFolder = new File(args[0]);
        if (!modsFolder.exists()) {
            System.err.println("The mods folder does not exist!");
            System.exit(1);
        }
        if (!modsFolder.isDirectory()) {
            System.err.println("The mods path is not a folder!");
            System.exit(1);
        }

        // in the same folder as the mods folder, look for a "mods-m2" folder
        File m2ModsFolder = new File(modsFolder.getParentFile(), "mods-m2");
        if (!m2ModsFolder.exists()) {
            m2ModsFolder.mkdir();
        }
        if (!m2ModsFolder.isDirectory()) {
            System.err.println("The mods-m2 path is not a folder!");
            System.exit(1);
        }

    }
}