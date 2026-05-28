package com.m3.files;

import com.m3.methods.MovementMethod;
import com.m3.util.Files;
import com.m3.util.TextFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Manages all file operations for M3, including mod versions, selected mods, and backups.
 */
public class M3Files {

    public final File VERSIONS_FOLDER;
    private final TextFile MODSFOLDER_TXT;
    private final TextFile CONFIG_TXT;
    private final File BACKUPS_FOLDER;

    public static String SELECTED_KEY = "selected";
    public static String METHOD_KEY = "method";

    /**
     * Constructs an M3Files object, initializing paths and creating necessary directories.
     * @param m3Folder The root directory for M3 files.
     */
    public M3Files(File m3Folder) {
        VERSIONS_FOLDER = new File(m3Folder, "mod-versions");
        MODSFOLDER_TXT = new TextFile(m3Folder, "mods");
        CONFIG_TXT = new TextFile(m3Folder, "config");
        BACKUPS_FOLDER = new File(m3Folder, "backups");

        if (!VERSIONS_FOLDER.exists())
            VERSIONS_FOLDER.mkdirs();
        if (!BACKUPS_FOLDER.exists())
            BACKUPS_FOLDER.mkdirs();
    }

    public HashMap<String, String> readConfig() {
        HashMap<String, String> config = new HashMap<>();
        ArrayList<String> lines = Files.readLines(CONFIG_TXT);
        for (String line : lines) {
            String[] parts = line.split("=");
            if (parts.length != 2) throw new RuntimeException("Invalid config line");
            config.put(parts[0], parts[1]);
        }
        return config;
    }

    void writeConfig(HashMap<String, String> config) {
        Files.writeLines(CONFIG_TXT, (pw) -> {
            for (String key : config.keySet()) {
                pw.println(key + "=" + config.get(key));
            }
        }, false);
    }

    /**
     * Retrieves the UUID of the currently selected mod version.
     * @return The UUID of the selected mod version, or null if none is selected.
     */
    public UUID getSelectedVersion() {
        HashMap<String, String> config = readConfig();
        if (!config.containsKey(SELECTED_KEY)) return null;
        return UUID.fromString(config.get(SELECTED_KEY));
    }

    /**
     * Sets the currently selected mod version.
     * @param modFolder The ModFolder to be set as selected.
     */
    public void setSelectedVersion(ModFolder modFolder) {
        HashMap<String, String> config = readConfig();
        config.put(SELECTED_KEY, modFolder.getId().toString());
        writeConfig(config);
    }

    public MovementMethod.Type getMethodToUse() {
        HashMap<String, String> config = readConfig();
        if (!config.containsKey(METHOD_KEY)) return null;
        return MovementMethod.Type.getByName(config.get(METHOD_KEY));
    }

    public void setMethodToUse(MovementMethod.Type method) {
        HashMap<String, String> config = readConfig();
        config.put(METHOD_KEY, method.getName());
        writeConfig(config);
    }

    /**
     * Reads all stored mod folders from the configuration file.
     * @return An ArrayList of ModFolder objects.
     */
    public ArrayList<ModFolder> readModFolders() {
        ArrayList<ModFolder> folders = new ArrayList<>();
        ArrayList<String> lines = Files.readLines(MODSFOLDER_TXT);

        for (String line : lines) {
            String[] parts = line.split("=");
            if (parts.length != 2) throw new RuntimeException("Invalid mod folder line");
            UUID uuid = UUID.fromString(parts[0]);
            String name = parts[1];
            File file = new File(VERSIONS_FOLDER, uuid.toString());
            folders.add(new ModFolder(file, name, uuid, file.listFiles() == null ? 0 : Objects.requireNonNull(file.listFiles()).length));
        }

        return folders;
    }

    /**
     * Writes a single mod folder to the configuration file.
     * @param folder The ModFolder to write.
     * @param append True to append to the file, false to overwrite.
     */
    private void writeModFolder(ModFolder folder, boolean append) {
        Files.writeLines(MODSFOLDER_TXT, (pw) -> {
                pw.println(folder.getId().toString() + "=" + folder.getName());
        }, append);
    }

    /**
     * Writes a single mod folder to the configuration file, appending it.
     * @param folder The ModFolder to write.
     */
    public void writeModFolder(ModFolder folder) {
        writeModFolder(folder, true);
    }

    /**
     * Writes a list of mod folders to the configuration file.
     * @param folder An ArrayList of ModFolder objects to write.
     */
    public void writeModFolders(ArrayList<ModFolder> folder) {
        for (ModFolder modFolder : folder) {
            writeModFolder(modFolder);
        }
    }

    /**
     * Removes a mod folder from the configuration file.
     * @param victim The ModFolder to remove.
     */
    public void removeModFolder(ModFolder victim, boolean delete) {
        ArrayList<ModFolder> modFolders = readModFolders();
        modFolders.removeIf(modFolder -> modFolder.getId().equals(victim.getId()));
        Files.writeLines(MODSFOLDER_TXT, (pw) -> {
            for (ModFolder modFolder : modFolders) {
                pw.println(modFolder.getId().toString() + "=" + modFolder.getName());
            }
        }, false);

        if (delete && victim.getFolder().exists()) {
            deleteDirectory(victim.getFolder());
        }
    }

    public void replaceModFolder(ModFolder modFolder) {
        removeModFolder(modFolder, false);
        writeModFolder(modFolder);
    }

    /**
     * Recursively deletes a directory and its contents.
     * @param directory The directory to delete.
     */
    private void deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }

    /**
     * Edits an existing mod folder's information in the configuration file.
     * @param victim The ModFolder with updated information.
     */
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

    /**
     * Returns the File object representing the backups folder.
     * @return The backups folder.
     */
    public File getBackupsFolder() {
        return BACKUPS_FOLDER;
    }

    /**
     * Adds a new mod folder to the configuration.
     * @param modFolder The ModFolder to add.
     */
    public void addModFolder(ModFolder modFolder) {
        writeModFolder(modFolder);
    }
}
