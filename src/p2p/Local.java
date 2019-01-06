package p2p;

import p2p.entity.Node;
import p2p.entity.FilePathPkg;

import java.net.DatagramSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class Local {

    public static Node localNode = new Node();
    public static Map<String, Node> localNodeMap = new ConcurrentHashMap<>();
    public static Queue<DatagramSocket> connectQueue = new LinkedList<>();
    public static Queue<Socket> fileQueue = new LinkedList<>();

    /**
     * 添加结点，如果结点原来不存在就添加，然后根据文件路径刷新该结点的文件信息
     * @param address
     * @param info
     */
    public static void addNode(String address, String info) {
        List<String> paths = FilePathPkg.decode(info);
        Node node = localNodeMap.get(address);
        if (node == null) {
            node = new Node(address);
            localNodeMap.put(address, node);
        }
        node.insertFileInfos(paths);
    }
}
