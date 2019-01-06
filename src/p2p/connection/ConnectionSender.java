package p2p.connection;

import p2p.utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shadowdsp on 2019/1/6.
 */

public class ConnectionSender {
    private static ExecutorService threadPoll = Executors.newFixedThreadPool(Utils.THREAD_NUMBER);

    public static void sendConnection(Integer tag) {
        threadPoll.execute(new ConnectionSenderThread(tag));
    }
}
