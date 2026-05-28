package com.m3.methods;

import com.m3.files.ModFolder;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents the configuration for a movement operation, specifying the type of movement
 * and any associated data like the old mod folder or new mods.
 * @implNote Implementors aren't forced to use this, however this does provide important context that implementors
 * may require.
 */
public class MovementConfig {
    private ModFolder oldFolder = null;
    private final MovementConfig.Type type;
    private ArrayList<File> newMods = new ArrayList<>();

    /**
     * Creates a new {@link MovementConfig} with the type {@link Type#DEFAULT_REPLACE} and
     * sets the old folder that is being replaced.
     *
     * @param oldFolder The {@link ModFolder} that is being replaced.
     */
    public MovementConfig(ModFolder oldFolder) {
        this.oldFolder = oldFolder;
        this.type = Type.DEFAULT_REPLACE;
    }

    /**
     * Creates a new {@link MovementConfig} with the type {@link Type#UPDATE} and
     * sets the list of new mods that were added.
     *
     * @param newMods The list of new {@link File} objects representing the added mods.
     */
    public MovementConfig(ArrayList<File> newMods) {
        this.newMods = newMods;
        this.type = Type.UPDATE;
    }

    /**
     * Creates a new {@link MovementConfig} with the specified {@link Type}.
     *
     * @param type The {@link Type} of movement configuration.
     */
    public MovementConfig(MovementConfig.Type type) {
        this.type = type;
    }

    /**
     * Returns the old {@link ModFolder} that is being replaced. This only applies when
     * {@link MovementConfig.Type#DEFAULT_REPLACE} is used.
     *
     * @return The old {@link ModFolder}, or {@code null} if not set.
     */
    public ModFolder getOldFolder() {
        return oldFolder;
    }

    /**
     * Returns the list of new {@link File} objects representing the added mods. This only applies when
     * {@link MovementConfig.Type#UPDATE} is used.
     *
     * @return The list of new {@link File} objects, or {@code null} if not set.
     */
    public ArrayList<File> getNewMods() {
        return newMods;
    }

    /**
     * Returns the {@link Type} of this movement configuration.
     *
     * @return The {@link Type} of this movement configuration.
     */
    public MovementConfig.Type getType() {
        return type;
    }

    /**
     * The type of movement configuration.
     */
    public enum Type {
        /**
         * The default operation the method should execute, being a replace of the old {@link ModFolder} with
         * a new one. This provides access to the {@link MovementConfig#getOldFolder()} holding the old mod folder.
         */
        DEFAULT_REPLACE,
        /**
         * This is when the user updates their mod folder with new mods, making the M3 mod folder out of sync
         * with the actual mods' folder. (The only exception would be {@link MSymlink} which avoids this.
         * This provides access to the {@link MovementConfig#getNewMods()} holding the new mods.
         */
        UPDATE,
        /**
         * This is when the user updates the method used, and the new method is called to apply the new method
         * to the currently selected folder.
         */
        METHOD_CHANGE
    }
}
