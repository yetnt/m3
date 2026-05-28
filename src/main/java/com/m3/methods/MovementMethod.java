package com.m3.methods;

import com.m3.files.Folders;
import com.m3.files.ModFolder;

import java.io.File;

/**
 * Defines the core contract for a movement strategy, being how exactly M3 should move the mods from it's own local
 * repository into the user's mod folder.
 */
public interface MovementMethod {
    /**
     * Method that implementors need to override for application of a mod folder. So that be the actual {@link File}
     * I/O of moving or copying the mods or otherwise into the user's mod folder
     * @param folders The folders instance.
     * @param oldFolder The old mod folder that is currently within the mods
     * @param newFolder The new mod folder to replace it with.
     * @implSpec If, the {@code oldFolder} and {@code newFolder} are identical then that means the user has added a new
     * mod .jar file to M3's copy but since this same folder is selected and in the mods folder, it needs to be recopied.
     */
    void apply(Folders folders, ModFolder oldFolder, ModFolder newFolder);

    /**
     * Method that implementors need to override such as to clean the mods folder for fresh new state.
     * @param folders The folders instance.
     * @param folder The mod folder to clean.
     */
    void clean(Folders folders, ModFolder folder);

    /**
     * Helper method to clear the mod folder.
     * @param folders The folders instance.
     */
    default void clearModsFolder(Folders folders) {
        File[] files = folders.getModsFolder().listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    /**
     * An enumeration of the different movement methods available in M3.
     */
    enum Type {
        MOVE(new MMove(), "MOVE"),
        SYMLINK(new MSymlink(), "SYMLINK"),
        COPY(new MCopy(), "COPY");

        private final MovementMethod method;
        private final String name;

        /**
         * Constructs a new Type constant.
         *
         * @param method The {@link MovementMethod} implementation associated with this enum constant.
         * @param name The display name of the movement method.
         */
        Type(MovementMethod method, String name) {
            this.method = method;
            this.name = name;
        }

        /**
         * Gets the display name of the movement method.
         *
         * @return The name of the movement method.
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the {@link MovementMethod} implementation associated with this enum constant.
         *
         * @return The movement method implementation.
         */
        public MovementMethod getMethod() {
            return method;
        }

        /**
         * Retrieves a {@code MovementMethod.Type} constant by its name.
         *
         * @param name The name of the movement method to search for.
         * @return The {@code MovementMethod.Type} constant with the given name, or {@code null} if not found.
         */
        public static Type getByName(String name) {
            for (Type method : Type.values())
                if (method.getName().equals(name))
                    return method;
            return null;
        }
    }
}
