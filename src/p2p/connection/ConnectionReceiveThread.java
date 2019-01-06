package p2p.connection;

import p2p.Local;
import p2p.entity.FilePathPkg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by shadowdsp on 2019/1/6.
 */

/**
 * 监听广播的线程得到包之后会在线程池中执行这条线程对得到的包进行处理
 */
public class ConnectionReceiveThread implements Runnable {

    private DatagramPacket receivePacket;

    public ConnectionReceiveThread(DatagramPacket packet) {
        this.receivePacket = packet;
    }

    @Override
    public void run() {
        DatagramSocket sendSocket = null;
        try {
            String info = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
            InetAddress inetAddress = receivePacket.getAddress();
            String address = inetAddress.getHostAddress();
            System.out.println("ConnectionReceiver receive from " + address +  ": " + info);
            int port = receivePacket.getPort();
            Local.addNode(address, info);

            // 回传发送本机的数据
            sendSocket = new DatagramSocket();
            byte[] localFileInfos = FilePathPkg.encode(Local.localNode.getFilePathArray()).getBytes();
            DatagramPacket sendPacket = new DatagramPacket(localFileInfos, localFileInfos.length,
                    inetAddress,
                    port);
            sendSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sendSocket != null) {
                sendSocket.close();
            }
        }
    }
}
