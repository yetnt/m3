package com.m3.files;

import java.io.File;
import java.util.UUID;

public class ModFolder {

    private final File file;
    private String name;
    private final UUID id;
    private int amtMods = 0;

    private boolean updated;

    public ModFolder(File file, String name, UUID id, int modsAmt) {
        this.file = file;
        this.name = name;
        this.id = id;
        this.amtMods = modsAmt;
    }

    public File getFile() {
        return file;
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
        updated = true;
    }

    public boolean isUpdated() {
        return updated;
    }
}
