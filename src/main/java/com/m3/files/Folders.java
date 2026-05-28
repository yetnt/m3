package com.m3.files;

import com.m3.methods.MovementMethod;
import com.m3.util.Pair;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class Folders {

    public static final File HOME_DIR_M3 = new File(
            Path.of(
                    System.getProperty("user.home"),
                    "M3"
            ).toUri()
    );
    public static final HomeDirFies homeFiles = new HomeDirFies();
    public static M3Files m3Files;


    static {
        if (!HOME_DIR_M3.exists())
            HOME_DIR_M3.mkdirs();
    }

    private final boolean isEmpty;

    private File DIR_M3;
    private File DIR_MODS;

    private Folders() {
        isEmpty = true;
    }

    private Folders(File modsFolder, File m3Folder) {
        setModsFolder(modsFolder);
        setM3Folder(m3Folder, false);
        isEmpty = false;
    }

    public Folders(File modsFolder, boolean write) {
        setModsFolder(modsFolder);
        if (write) homeFiles.writeM3Folder(DIR_M3, DIR_MODS);
        ModFolder empty = ModFolder.create("Empty Preset", ModFolder.EMPTY_PRESET);
        m3Files.addModFolder(empty);
        isEmpty = false;
    }

    public static Folders getInstance() {
        Pair<String> path = homeFiles.existingM3Folder();
        if (path == null || !Path.of(path.getFirst()).toFile().exists() || !Path.of(path.getSecond()).toFile().exists()) return new Folders();
        else return new Folders(new File(path.getFirst()), new File(path.getSecond()));
    }

    public File getModsFolder() {
        return DIR_MODS;
    }

    public File getM3Folder() {
        return DIR_M3;
    }

    public void setModsFolder(File modsFolder) {
        DIR_MODS = modsFolder;
        DIR_M3 = new File(Path.of(modsFolder.getParentFile().getAbsolutePath(), "M3").toUri());
        m3Files = new M3Files(DIR_M3);

        if (!DIR_M3.exists())
            DIR_M3.mkdirs();
    }

    public void setM3Folder(File m3Folder, boolean addEmptyPreset) {
        DIR_M3 = m3Folder;
        m3Files = new M3Files(DIR_M3);
        homeFiles.writeM3Folder(m3Folder, DIR_MODS);
        if (!DIR_M3.exists())
            DIR_M3.mkdirs();
        HashMap<String, String> config = m3Files.readConfig();
        if (config == null || config.isEmpty()) {
            config = new HashMap<>();
            config.put(M3Files.METHOD_KEY, MovementMethod.Type.COPY.getName());
            config.put(M3Files.SELECTED_KEY, ModFolder.EMPTY_PRESET.toString());
            m3Files.writeConfig(config);
        }
        if (addEmptyPreset) {
            ModFolder empty = ModFolder.create("Empty Preset", ModFolder.EMPTY_PRESET);
            m3Files.addModFolder(empty);
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
