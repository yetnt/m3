package com.m3.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class SystemCapabilities {

    private static Boolean SYMLINK_SUPPORTED;

    public static boolean canCreateSymlinks() {
        if (SYMLINK_SUPPORTED != null) return SYMLINK_SUPPORTED;

        File tempTarget = null;
        File tempLink = null;

        try {
            tempTarget = File.createTempFile("m3_symlink_target", ".tmp");
            tempLink = new File(tempTarget.getParentFile(), "m3_symlink_test_link");

            Path link = tempLink.toPath();
            Path target = tempTarget.toPath();

            Files.deleteIfExists(link);

            Files.createSymbolicLink(link, target);

            SYMLINK_SUPPORTED = true;
        } catch (Exception e) {
            SYMLINK_SUPPORTED = false;
        } finally {
            try {
                if (tempLink != null) Files.deleteIfExists(tempLink.toPath());
                if (tempTarget != null) Files.deleteIfExists(tempTarget.toPath());
            } catch (Exception ignored) {}
        }

        return SYMLINK_SUPPORTED;
    }
}