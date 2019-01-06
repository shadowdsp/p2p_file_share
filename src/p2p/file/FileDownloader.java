package p2p.file;

import p2p.utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shadowdsp on 2019/1/6.
 */

public class FileDownloader {
    private static ExecutorService threadPoll = Executors.newFixedThreadPool(Utils.THREAD_NUMBER);

    public static void download(String host, String fileName, String localDir) {
        threadPoll.execute(new FileDownloaderThread(host, fileName, localDir));
    }
}
