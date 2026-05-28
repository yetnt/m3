package com.m3.methods;

import com.m3.files.Folders;
import com.m3.files.ModFolder;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;


/**
 * An implementation of {@link MovementMethod} which aims to move the mods around.
 * <p>
 *     How it works is, when a {@link ModFolder} is selected, it simply fetches all the mods within it and moves
 *     it over into the user's Minecraft mods file.
 * </p>
 */
public class MMove implements MovementMethod {
    @Override
    public void apply(Folders folders, ModFolder newFolder, MovementConfig config) {
        backup(); // extremely important, deathly important.

        // and the pain begins.
        if (config.getType() == MovementConfig.Type.DEFAULT_REPLACE) {
            // A new mod folder is requesting it be loaded. but there is an old folder.
            rollbackMods(folders, config.getOldFolder());

            // Move the files.
            for (File mod : Objects.requireNonNull(newFolder.getFolder().listFiles())) {
                moveFile(mod, new File(folders.getModsFolder(), mod.getName()));
            }
        } else {
            // No old folder available here.
            switch (config.getType()) {
                case UPDATE -> {
                    // New mods were added to the ModFolder move them to the actual mod folder.
                    ArrayList<File> newMods = config.getNewMods();
                    newMods.forEach(mod -> moveFile(mod, new File(folders.getModsFolder(), mod.getName())));
                }
                case METHOD_CHANGE -> // When method change, we need to clear the mods folder.
                        rollbackMods(folders, newFolder);
            }
        }
    }

    /**
     * Rolls back the mods in the main mods folder to a specified old mod folder.
     * @param folders The object containing references to various folders, including the main mods folder.
     * @param oldFolder The ModFolder object representing the old mod folder to which mods should be rolled back.
     */
    private void rollbackMods(Folders folders, ModFolder oldFolder) {
        // get all the files within the mod folder.
        File[] mods = folders.getModsFolder().listFiles();
        if (mods == null) return; // this shouldn't happen.

        // Move each file to the mod folder.
        for (File mod : mods) {
            moveFile(mod, new File(oldFolder.getFolder(), mod.getName()));
        }
    }


    /**
     * Moves a file from one location to another, replacing the destination file if it already exists.
     * If an error occurs during the move operation, the stack trace is printed.
     * @param oldFile The source file to be moved.
     * @param newFile The destination file.
     */
    private void moveFile(File oldFile, File newFile) {
        try {
            Files.move(oldFile.toPath(), newFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clean(Folders folders, ModFolder folderInMods) {
        backup();
        rollbackMods(folders, folderInMods);
    }
}
