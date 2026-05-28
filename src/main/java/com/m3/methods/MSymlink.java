package com.m3.methods;

import com.m3.files.Folders;
import com.m3.files.ModFolder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An implementation of {@link MovementMethod} which creates a symbolic link to the selected {@link ModFolder}.
 * <p>
 *     How it works is, when a {@link ModFolder} is selected, it creates a symbolic link to the folder, renames
 *     the symlink to "mods" and replaces the mods folder, with the newly created mods symlink.
 * </p>
 * @implNote Unlike other implementations, which cannot have a user add a mod manually (it has to be through the UI).
 * Symlink naturally handles this meaning the user can add new mods to either the link or the actual {@link ModFolder}
 * outside of the app.
 */
public class MSymlink implements MovementMethod {

    @Override
    public void apply(Folders folders, ModFolder newFolder, MovementConfig config) {

        // Clean
        cleanSymlink(folders, false);

        // Delete the mods folder
        File parent = folders.getModsFolder().getParentFile();
        folders.getModsFolder().delete();

        // create a symlink of the modfolder, name it "mods" and move it to the parent.
        try {
            Files.createSymbolicLink(
                    Path.of(parent.getAbsolutePath(), "mods"),
                    newFolder.getFolder().toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Cleans up the mods folder by deleting any existing symlink and optionally creating a new mods folder.
     * It also backs up the current mods folder before performing any cleanup.
     * @param folders The Folders object containing paths to various game folders.
     * @param createFolder If true, a new mods folder will be created if it doesn't exist or if a symlink was deleted.
     */
    private void cleanSymlink(Folders folders, boolean createFolder) {
        // backup whatever is in the mods folder
        backup();

        // Delete the sym link if any and if it is a symlink in the first place,
        // and otherwise if the mods folder doesn't exist, create it.
        if (Files.isSymbolicLink(folders.getModsFolder().toPath())) {
            try {
                Files.delete(folders.getModsFolder().toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (createFolder) folders.getModsFolder().mkdirs();
        } else if (!folders.getModsFolder().exists() && createFolder) {
            folders.getModsFolder().mkdirs();
        } else {
            // prolly just a folder, delete contents
            clearModsFolder(folders);
        }
    }

    @Override
    public void clean(Folders folders, ModFolder folderInMods) {
        cleanSymlink(folders, true);
    }
}
