package com.m3;

import java.io.File;

public class Mod extends File {

    public Mod(String pathname) {
        super(pathname);
    }

    public boolean modExists() {
        // Check if the file or directory exists
        return this.exists();
    }
}
