package org.litnine;

import java.io.File;
import java.net.URISyntaxException;

public class Config {
    public static final String VERSION = "0.0.1";
    public static final String SORT_ROOT = "SortedFiles";
    public static final String[] ALLOWED_EXTENSIONS = {"psd", "sai2", "png", "jpg", "jpeg", "fla", "gif"};

    public static String getExecRoot(){
        try {
            return new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }
    }
}
