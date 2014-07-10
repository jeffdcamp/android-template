package org.company.project.adapter;

import dagger.Module;

@Module(
        injects = {
                DirectoryAdapter.class,
        },
        complete = false
)
public class AdapterModule {
}
