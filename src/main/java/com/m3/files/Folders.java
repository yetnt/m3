package com.m3.files;

import java.io.File;
import java.nio.file.Path;

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

    private Folders(File modsFolder) {
        setModsFolder(modsFolder);
        isEmpty = false;
    }

    public Folders(File modsFolder, boolean write) {
        setModsFolder(modsFolder);
        if (write) homeFiles.writeM3Folder(DIR_M3);
        isEmpty = false;
    }

    public static Folders getInstance() {
        String path = homeFiles.existingM3Folder();
        if (path == null) return new Folders();
        else return new Folders(new File(path));
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

    public void setM3Folder(File m3Folder) {
        DIR_M3 = m3Folder;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
