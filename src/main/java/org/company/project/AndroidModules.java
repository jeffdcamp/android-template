package org.company.project;

import dagger.Module;


@Module(
        library = true // library=false will fail the build if any dependency is unused. Use this to make sure you don't have too many @Provides methods.﻿
)
public class AndroidModules {
    private final MyApplication application;

    public AndroidModules(MyApplication application) {
        this.application = application;
    }

}
