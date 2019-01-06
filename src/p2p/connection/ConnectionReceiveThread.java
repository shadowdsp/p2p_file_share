package p2p.connection;

import p2p.Local;
import p2p.entity.FilePathPkg;
import p2p.utils.Utils;

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
            System.out.println("ConnectionReceiver receive from " + address + ": " + info);
            int port = receivePacket.getPort();
            FilePathPkg pkg = FilePathPkg.decode(info);
            Local.addNode(address, pkg.getFilePathList());

            // 如果是一开始加入广播，就需要回传发送本机的数据
            if (pkg.getTag().equals(Utils.BROADCAST_JOIN)) {
                sendSocket = new DatagramSocket();
                byte[] localFileInfos =
                        FilePathPkg.encode(new FilePathPkg(Utils.BROADCAST_UPDATE,
                                Local.localNode.getFilePathArray())).getBytes();
                System.out.println("receive broadcast and send back to " +
                        inetAddress.getHostAddress() + ": " + new String(localFileInfos));
                DatagramPacket sendPacket = new DatagramPacket(localFileInfos, localFileInfos.length,
                        inetAddress, port);
                sendSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sendSocket != null) {
                sendSocket.close();
            }
        }
    }
}
