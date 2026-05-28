package com.m3.util;

import java.io.File;

public class TextFile extends File {
    /**
     * Constructs a new TextFile instance.
     * If the file does not exist, it attempts to create it.
     * @param parent The parent directory.
     * @param fileName The name of the file (without the .txt extension).
     */
    public TextFile(File parent, String fileName) {
        super(parent, fileName + ".txt");
        if (!this.exists())
            try {
                this.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
