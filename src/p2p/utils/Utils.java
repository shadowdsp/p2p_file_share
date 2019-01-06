package p2p.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class Utils {
    public final static int UDP_PORT = 8001;
    public final static int TCP_PORT = 8002;
    public static final int THREAD_NUMBER = 5;
    public static final int PATH_SIZE = 1024;
    public static final int UDP_SOCKET_TIMEOUT = 2000;
    public static final int TCP_SOCKET_TIMEOUT = 2000;

    public static String getFileNameFromPath(String path) {
        return new File(path).getName();
    }

    public static Boolean fileIsExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void copyFileData(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = in.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
    }
}
