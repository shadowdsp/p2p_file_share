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
    private Integer tag;

    public ConnectionSenderThread(Integer tag) {
        this.tag = tag;
    }

    /**
     * 发送广播告诉局域网内所有用户自己的情况，超时就跳出
     */
    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress inetAddress = InetAddress.getByName(Utils.BROADCAST_IP);
            byte[] localFileInfos =
                    FilePathPkg.encode(new FilePathPkg(tag, Local.localNode.getFilePathArray())).getBytes();
            DatagramPacket packet = new DatagramPacket(localFileInfos, localFileInfos.length, inetAddress, Utils.UDP_PORT);
            socket.send(packet);
            System.out.println("ConnectionSendThread broadcast: " + new String(localFileInfos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
