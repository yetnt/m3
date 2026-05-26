package com.m3.files;

import com.m3.util.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class HomeDirFies {

    public final File CACHE_TXT = new File(Path.of(Folders.HOME_DIR_M3.getAbsolutePath(), "cache.txt").toUri());

    public final File BACKUP_FOLDER = new File(Path.of(Folders.HOME_DIR_M3.getAbsolutePath(), "backup").toUri());

    public HomeDirFies() {
        if (!CACHE_TXT.exists()) {
            try {
                CACHE_TXT.getParentFile().mkdirs();
                CACHE_TXT.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!BACKUP_FOLDER.exists())
            BACKUP_FOLDER.mkdirs();
    }

    public String existingM3Folder() {
        ArrayList<String> lines = Files.readLines(CACHE_TXT);
        if (lines.isEmpty()) return null;
        else return lines.getFirst();
    }

    public void writeM3Folder(File m3) {
        Files.writeLines(CACHE_TXT,
                (p) -> {
                    p.println(m3.getAbsolutePath());
                },
                false);
    }
}