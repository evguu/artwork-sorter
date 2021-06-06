package org.litnine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSorter {
    private static final Map<String, List<File>> categorizedFiles = new HashMap<>();

    public static String str(){
        StringBuilder sb = new StringBuilder("<html>");

        for(String dirName: categorizedFiles.keySet()){
            sb.append("<br/>").append(dirName).append(":<br/><br/>");
            for(File file : categorizedFiles.get(dirName)){
                Path path = file.toPath();
                sb.append(path.toString()).append("<br/>");
            }
        }
        sb.append("</html>");
        return sb.toString();
    }

    public static void addFiles(List<File> files) {
        for (File file : files) {
            addFile(file);
        }
    }

    public static void addFile(File file) {
        String[] filenameParts = file.toString().toLowerCase().split("\\.");
        String fileExtension = filenameParts[filenameParts.length - 1];
        if (!categorizedFiles.containsKey(fileExtension)) {
            categorizedFiles.put(fileExtension, new ArrayList<>());
        }
        List<File> dir = categorizedFiles.get(fileExtension);
        try {
            if (!doesDirContainFile(dir, file)) {
                dir.add(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean doesDirContainFile(List<File> dir, File file) throws IOException {
        String canonicalPath = file.getCanonicalPath();
        for (File i : dir) {
            if (i.getCanonicalPath().equals(canonicalPath)) {
                return true;
            }
        }
        return false;
    }

}
