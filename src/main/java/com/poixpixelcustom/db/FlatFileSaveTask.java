package com.poixpixelcustom.db;

import com.poixpixelcustom.PoixpixelCustomMessaging;
import com.poixpixelcustom.util.FileMgmt;

import java.util.List;

public class FlatFileSaveTask implements Runnable {

    private final List<String> list;
    private final String path;

    /**
     * Constructor to save a list
     * @param list - list to save.
     * @param path - path on filesystem.
     */
    public FlatFileSaveTask(List<String> list, String path) {
        this.list = list;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            FileMgmt.listToFile(list, path);
        } catch (NullPointerException ex) {
            PoixpixelCustomMessaging.sendErrorMsg("Null Error saving to file - " + path);
        }
    }
}
