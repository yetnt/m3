package com.m3.files;

import com.m3.Pair;
import com.m3.util.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public Pair<String> existingM3Folder() {
        ArrayList<String> lines = Files.readLines(CACHE_TXT);
        if (lines.isEmpty()) return null;
        else return new Pair<>(lines.getFirst(), lines.getLast());
    }

    public void writeM3Folder(File m3, File mods) {
        Files.writeLines(CACHE_TXT,
                (p) -> {
                    p.println(mods.getAbsolutePath());
                    p.println(m3.getAbsolutePath());
                },
                false);
    }

    public void backupModsFolder() {
        // Zip everything in the mods folder, label the zip the current date and time + mods backup and save it to the backups folder
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        String zipFileName = "mods_backup_" + timestamp + ".zip";
        File zipFile = new File(BACKUP_FOLDER, zipFileName);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            File modsFolder = Folders.getInstance().getModsFolder();
            if (modsFolder != null && modsFolder.exists() && modsFolder.isDirectory()) {
                for (File file : modsFolder.listFiles()) {
                    addFileToZip(file, file.getName(), zos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFileToZip(File file, String entryName, ZipOutputStream zos) throws IOException {
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
}