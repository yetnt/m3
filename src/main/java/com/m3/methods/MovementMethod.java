package com.m3.methods;

import com.m3.files.ModFolder;

public interface MovementMethod {
    void apply(ModFolder folder);
    void clean(ModFolder folder);
}
