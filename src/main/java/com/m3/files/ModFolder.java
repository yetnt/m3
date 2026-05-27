package com.m3.files;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

public class ModFolder {

    private final File folder;
    private String name;
    private final UUID id;
    private int amtMods = 0;

    public ModFolder(File file, String name, UUID id, int modsAmt) {
        this.folder = file;
        this.name = name;
        this.id = id;
        this.amtMods = modsAmt;
    }

    public static ModFolder create(String name) {
        UUID id = UUID.randomUUID();
        File folder = new File(Folders.m3Files.VERSIONS_FOLDER, String.valueOf(id));
        folder.mkdirs();
        return new ModFolder(folder, name, id, 0);
    }

    public File getFolder() {
        return folder;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public int getAmtMods() {
        return amtMods;
    }

    public void setAmtMods(int amtMods) {
        this.amtMods = amtMods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMods(File ...file) {
        // Move these files, which the user picked, into the ModFolder's folder.
        for (File modFile : file) {
            try {
                Files.copy(modFile.toPath(), new File(folder, modFile.getName()).toPath());
                amtMods++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
