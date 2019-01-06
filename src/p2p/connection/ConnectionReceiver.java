package p2p.connection;

import p2p.Local;
import p2p.utils.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class ConnectionReceiver implements Runnable {

    private final ExecutorService threadPoll = Executors.newFixedThreadPool(Utils.THREAD_NUMBER);

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(Utils.UDP_PORT)) {
            while (true) {
                // 接收别人的数据
                byte[] data = new byte[Utils.PATH_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                socket.receive(receivePacket);
                if (!Local.localNodeMap.containsKey(receivePacket.getAddress().getHostAddress())) {
                    threadPoll.execute(new ConnectionReceiveThread(receivePacket));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
