package org.litnine;

import java.io.File;
import java.net.URISyntaxException;

public class Config {
    public static final String VERSION = "0.0.1";
    public static final String SORT_ROOT = "SortedFiles";

    public static String getExecRoot(){
        try {
            return new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }
    }
}
