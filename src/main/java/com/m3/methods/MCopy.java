package com.m3.methods;

import com.m3.files.Folders;
import com.m3.files.ModFolder;
import com.m3.util.Files;

import java.io.File;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Files.copy;

public class MCopy implements MovementMethod {
    @Override
    public void apply(Folders folders, ModFolder oldFolder, ModFolder newFolder) {
        // we dont care about the oldFolder

        // 1. Clean
        clean(folders, oldFolder);
        clearModsFolder(folders);

        // 2. Copy
        File[] file = newFolder.getFolder().listFiles();
        if (file != null) {
            for (File f : file) {
                try {
                    copy(f.toPath(), new File(folders.getModsFolder(), f.getName()).toPath(),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void clean(Folders folders, ModFolder folder) {
        // backup whatever is in the mods folder
        Files.backupModsFolder(Folders.m3Files.getBackupsFolder());
        Files.backupModsFolder(Folders.homeFiles.BACKUP_FOLDER);
    }
}
