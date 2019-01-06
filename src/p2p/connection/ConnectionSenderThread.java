package p2p.connection;

import p2p.Local;
import p2p.entity.FilePathPkg;
import p2p.utils.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class ConnectionSenderThread implements Runnable {

    /**
     * 发送广播告诉局域网内所有用户自己的情况，超时就跳出
     */
    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress inetAddress = InetAddress.getByName("255.255.255.255");
            int port = Utils.UDP_PORT;
            byte[] localFileInfos = FilePathPkg.encode(Local.localNode.getFilePathArray()).getBytes();
            DatagramPacket packet = new DatagramPacket(localFileInfos, localFileInfos.length, inetAddress, port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
