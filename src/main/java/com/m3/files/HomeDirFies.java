package com.m3.files;

import com.m3.util.Pair;
import com.m3.util.Files;
import com.m3.util.TextFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipOutputStream;

/**
 * This class manages files and folders within the application's home directory,
 * including cache files, backup folders, and operations related to M3 and mods folders.
 */
public class HomeDirFies {

    public final File CACHE_TXT = new TextFile(Folders.HOME_DIR_M3, "cache");

    public final File BACKUP_FOLDER = new File(Path.of(Folders.HOME_DIR_M3.getAbsolutePath(), "backup").toUri());

    /**
     * Constructs a new HomeDirFies object.
     * Initialises the cache file and backup folder, creating them if they don't exist.
     */
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

    /**
     * Retrieves the paths of the existing M3 and mods folders from the cache file.
     *
     * @return A {@link Pair} containing the paths of the mods folder (first) and M3 folder (last), or {@code null} if the cache is empty.
     */
    public Pair<String> existingM3Folder() {
        ArrayList<String> lines = Files.readLines(CACHE_TXT);
        if (lines.isEmpty()) return null;
        else return new Pair<>(lines.getFirst(), lines.getLast());
    }

    /**
     * Writes the absolute paths of the M3 and mods folders to the cache file.
     * @param m3 The M3 folder.
     * @param mods The mods folder.
     */
    public void writeM3Folder(File m3, File mods) {
        Files.writeLines(CACHE_TXT,
                (p) -> {
                    p.println(mods.getAbsolutePath());
                    p.println(m3.getAbsolutePath());
                },
                false);
    }

    /**
     * Creates a zip backup of the mods folder, naming it with a timestamp and saving it to the backup folder.
     */
    public void backupModsFolder() {
        // Zip everything in the mods folder, label the zip the current date and time + mods backup and save it to the backups folder
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