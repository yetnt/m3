package com.m3.methods;

import com.m3.files.Folders;
import com.m3.files.ModFolder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * An implementation of {@link MovementMethod} which aims to copy the mods around.
 * <p>
 *     How it works is, when a {@link ModFolder} is selected, it simply fetches all the mods within it and copies
 *     it over into the user's Minecraft mods file.
 * </p>
 */
public class MCopy implements MovementMethod {
    @Override
    public void apply(Folders folders, ModFolder newFolder, MovementConfig config) {
        if (config.getType() == MovementConfig.Type.UPDATE) {
            // Fast path, we only need to copy the files over.
            ArrayList<File> newMods = config.getNewMods();
            newMods.forEach(
                    mod -> copy(mod, new File(folders.getModsFolder(), mod.getName()))
            );
            return;
        }

        // 1. Clean
        clean(folders, config.getOldFolder());

        // 2. Copy
        File[] file = newFolder.getFolder().listFiles();
        if (file != null)
            Arrays.stream(file)
                    .forEach(f -> copy(f, new File(folders.getModsFolder(), f.getName())));

    }

    /**
     * Copies a file from one location to another, replacing the destination file if it already exists.
     * @param oldFile The source file to copy.
     * @param newFile The destination file.
     */
    private void copy(File oldFile, File newFile) {
        try {
            Files.copy(oldFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean(Folders folders, ModFolder folderInMods) {
        // backup whatever is in the mods folder
        backup();
        clearModsFolder(folders);
    }
}
