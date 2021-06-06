package org.litnine;

import com.sun.jdi.ObjectCollectedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FileSorter {
    private static final Map<String, List<File>> categorizedFiles = new HashMap<>();

    @Deprecated
    public static String str() {
        StringBuilder sb = new StringBuilder("<html>");

        for (String dirName : categorizedFiles.keySet()) {
            sb.append("<br/>").append(dirName).append(":<br/><br/>");
            for (File file : categorizedFiles.get(dirName)) {
                Path path = file.toPath();
                sb.append(path.toString()).append("<br/>");
            }
        }
        sb.append("</html>");
        return sb.toString();
    }

    public static Map<String, List<File>> getCategorizedFiles() {
        return categorizedFiles;
    }

    public static void sortFiles() {
        Path root = Paths.get(Config.getExecRoot(), Config.SORT_ROOT);
        for (String dirName : FileSorter.getCategorizedFiles().keySet()) {
            Path to = Paths.get(root.toString(), dirName);

            String[] existingFiles = to.toFile().list();

            int maxName = 0;
            if (existingFiles != null) {
                for (String name : existingFiles) {
                    String potentialNumber = name.split("\\.")[0];
                    if (isNumeric(potentialNumber)) {
                        int num = Integer.parseInt(potentialNumber);
                        if (num > maxName) {
                            maxName = num;
                        }
                    }
                }
            }
            maxName++;

            List<File> sorted = new ArrayList<>(FileSorter.getCategorizedFiles().get(dirName));
            Collections.sort(sorted, (o1, o2) -> {
                BasicFileAttributes attr1;
                BasicFileAttributes attr2;
                try {
                    attr1 = Files.readAttributes(o1.toPath(), BasicFileAttributes.class);
                    attr2 = Files.readAttributes(o2.toPath(), BasicFileAttributes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
                return attr1.creationTime().compareTo(attr2.creationTime());
            });

            for (File file : sorted) {
                Path dest = Paths.get(to.toString(), "" + maxName++ + "." + dirName);
                System.out.println(file + " перемещен в " + dest);
                try {
                    Files.move(file.toPath(), dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void addFiles(List<File> files) {
        for (File file : files) {
            addFile(file);
        }
    }

    public static void addFile(File file) {
        String[] filenameParts = file.toString().toLowerCase().split("\\.");

        if (filenameParts.length == 1) {
            // Если это папка или нет расширения, не добавляем.
            return;
        }

        String fileExtension = filenameParts[filenameParts.length - 1];

        if (!Arrays.asList(Config.ALLOWED_EXTENSIONS).contains(fileExtension)) {
            // Если расширения нет в белом списке, игнорируем файл.
            return;
        }

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
