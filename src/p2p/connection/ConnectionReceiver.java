package p2p.connection;

import p2p.Local;
import p2p.utils.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class ConnectionReceiver implements Runnable {

    private final ExecutorService threadPoll = Executors.newFixedThreadPool(Utils.THREAD_NUMBER);

    @Override
    public void run() {
        try (MulticastSocket socket = new MulticastSocket(Utils.UDP_PORT)) {
            socket.joinGroup(InetAddress.getByName(Utils.BROADCAST_IP));
            while (true) {
                // 接收别人的数据
                byte[] data = new byte[Utils.PATH_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                socket.receive(receivePacket);
                String remoteAddress = receivePacket.getAddress().getHostAddress();
                String localAddress = InetAddress.getLocalHost().getHostAddress();
//                System.out.println(localAddress + " - " + remoteAddress + " : " + new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength()));
                if (!remoteAddress.equals(localAddress)) {
                    threadPoll.execute(new ConnectionReceiveThread(receivePacket));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
