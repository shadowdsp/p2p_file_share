package p2p.file;

import p2p.utils.Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class FileReceiver implements Runnable {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(Utils.THREAD_NUMBER);

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(Utils.TCP_PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new FileReceiveThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
