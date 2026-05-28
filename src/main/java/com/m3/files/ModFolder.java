package com.m3.files;

import com.m3.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ModFolder {

    /**
     * The UUID for the preset with no mods in it. This preset is special as its only made when you first
     * run the app and no mods can ever be added to it, nor can it be deleted.
     */
    public static UUID EMPTY_PRESET = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private final File folder;
    private String name;
    private UUID id;
    private int amtMods = 0;
    private JRadioButton jRadioButton1 = null;

    public ModFolder(File file, String name, UUID id, int modsAmt) {
        this.folder = file;
        this.name = name;
        this.id = id;
        this.amtMods = modsAmt;
    }

    public static ModFolder create(String name) {
        UUID id = UUID.randomUUID();
        return create(name, id);
    }

    public static ModFolder create(String name, UUID id) {
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

    public ArrayList<String> getModNames() {
        if (amtMods == 0) return new ArrayList<>();
        ArrayList<String> mods = new ArrayList<>();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            mods.add(file.getName());
        }
        return mods;
    }

    public void addMods(File ...file) {
        // Move these files, which the user picked, into the ModFolder's folder.
        for (File modFile : file) {
            try {
                Files.copy(modFile.toPath(), new File(folder, modFile.getName()).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                amtMods++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JRadioButton getBtn() {
        if (jRadioButton1 == null) {
            jRadioButton1 = new JRadioButton();
            jRadioButton1.setFont(new java.awt.Font("SansSerif", Font.BOLD, 14)); // NOI18N
            jRadioButton1.setText(name);
        }
        return jRadioButton1;
    }
}
