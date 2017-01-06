package org.jdc.template;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class TestFilesystem {
    public static final String FILESYSTEM_DIR_PATH = "build/test-filesystem";
    public static final File FILESYSTEM_DIR = new File(FILESYSTEM_DIR_PATH);
    public static final String INTERNAL_DIR_PATH = FILESYSTEM_DIR_PATH + "/internal";
    public static final File INTERNAL_DIR = new File(INTERNAL_DIR_PATH);
    public static final String EXTERNAL_DIR_PATH = FILESYSTEM_DIR_PATH + "/external";
    public static final File EXTERNAL_DIR = new File(EXTERNAL_DIR_PATH);

    public static final String INTERNAL_FILES_DIR_PATH = INTERNAL_DIR_PATH + "/files";
    public static final File INTERNAL_FILES_DIR = new File(INTERNAL_FILES_DIR_PATH);
    public static final String INTERNAL_DATABASES_DIR_PATH = INTERNAL_DIR_PATH + "/databases";

    public static final String EXTERNAL_FILES_DIR_PATH = EXTERNAL_DIR_PATH + "/files";
    public static final File EXTERNAL_FILES_DIR = new File(EXTERNAL_FILES_DIR_PATH);

    public static void deleteFilesystem() {
        FileUtils.deleteQuietly(FILESYSTEM_DIR);
    }

    public static void copyDatabase(String sourcePath, String targetPath) {
        File targetDBFile = new File(targetPath);
        File dbDirectory = targetDBFile.getParentFile();

        try {
            FileUtils.forceMkdir(dbDirectory);

            if (targetDBFile.exists()) {
                FileUtils.deleteQuietly(targetDBFile);
            }

            FileUtils.copyFile(new File(sourcePath), targetDBFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
