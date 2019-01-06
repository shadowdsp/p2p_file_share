package p2p.utils;

/**
 * Created by shadowdsp on 2019/1/6.
 */

public class FilePathUtils {
    private static boolean isOsWindows() {
        String osname = System.getProperty("os.name").toLowerCase();
        boolean rt = osname.startsWith("windows");
        return rt;
    }

    public static String fixPath(String path) {
        if (null == path) return path;
        if (path.length() >= 1 && ('/' == path.charAt(0) || '\\' == path.charAt(0))) {
            // 根目录, Windows下需补上盘符.
            if (isOsWindows()) {
                String userdir = System.getProperty("user.dir");
                if (null != userdir && userdir.length() >= 2) {
                    return userdir.substring(0, 2) + path;
                }
            }
        }
        return path;
    }
}
