package com.m3.files;

import com.m3.util.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class M3Files {

    private final File VERSIONS_FOLDER;
    private final File MODSFOLDER_TXT;
    private final File BACKUPS_FOLDER;

    public M3Files(File m3Folder) {
        VERSIONS_FOLDER = new File(m3Folder, "mod-versions");
        MODSFOLDER_TXT = new File(m3Folder, "mods.txt");
        BACKUPS_FOLDER = new File(m3Folder, "backups");

        if (!VERSIONS_FOLDER.exists())
            VERSIONS_FOLDER.mkdirs();
        if (!MODSFOLDER_TXT.exists()) {
            try {
                MODSFOLDER_TXT.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!BACKUPS_FOLDER.exists())
            BACKUPS_FOLDER.mkdirs();
    }

    public ArrayList<ModFolder> readModFolders() {
        ArrayList<ModFolder> folders = new ArrayList<>();
        ArrayList<String> lines = Files.readLines(MODSFOLDER_TXT);

        for (String line : lines) {
            String[] parts = line.split("=");
            if (parts.length != 2) throw new RuntimeException("Invalid mod folder line");
            UUID uuid = UUID.fromString(parts[0]);
            String name = parts[1];
            File file = new File(VERSIONS_FOLDER, uuid.toString());
            folders.add(new ModFolder(file, name, uuid, Objects.requireNonNull(file.listFiles()).length));
        }

        return folders;
    }

    private void writeModFolder(ModFolder folder, boolean append) {
        Files.writeLines(MODSFOLDER_TXT, (pw) -> {
                pw.println(folder.getId().toString() + "=" + folder.getName());
        }, append);
    }

    public void writeModFolder(ModFolder folder) {
        writeModFolder(folder, true);
    }

    public void writeModFolders(ArrayList<ModFolder> folder) {
        for (ModFolder modFolder : folder) {
            writeModFolder(modFolder);
        }
    }

    public void removeModFolder(ModFolder victim) {
        ArrayList<ModFolder> modFolders = readModFolders();
        modFolders.removeIf(modFolder -> modFolder.getId().equals(victim.getId()));
        Files.writeLines(MODSFOLDER_TXT, (pw) -> {
            for (ModFolder modFolder : modFolders) {
                pw.println(modFolder.getId().toString() + "=" + modFolder.getName());
            }
        }, false);
        // Also delete the actual folder and its contents
        if (victim.getFile().exists()) {
            deleteDirectory(victim.getFile());
        }
    }

    private void deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }

    public void editModFolder(ModFolder victim) {
        ArrayList<ModFolder> modFolders = readModFolders();
        for (int i = 0; i < modFolders.size(); i++) {
            if (modFolders.get(i).getId().equals(victim.getId())) {
                modFolders.set(i, victim);
                break;
            }
        }
        Files.writeLines(MODSFOLDER_TXT, (pw) -> {
            for (ModFolder modFolder : modFolders) {
                pw.println(modFolder.getId().toString() + "=" + modFolder.getName());
            }
        }, false);
    }

    public File getBackupsFolder() {
        return BACKUPS_FOLDER;
    }
}
