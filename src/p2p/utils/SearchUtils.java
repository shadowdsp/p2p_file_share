package p2p.utils;

import p2p.Local;
import p2p.entity.Node;

import java.util.Map;

/**
 * Created by shadowdsp on 2019/1/6.
 */

public class SearchUtils {
    public static void searchHost() {
        System.out.println("=== Search All Host ===");
        for (String host: Local.localNodeMap.keySet()) {
            System.out.println("--- " + host);
        }
        System.out.println();
    }

    public static void searchAll() {
        System.out.println("=== Search All File From All Host ===");
        for (Map.Entry<String, Node> entry: Local.localNodeMap.entrySet()) {
            System.out.println("--- " + entry.getKey());
            for (String fileName: entry.getValue().getFileInfos().keySet()) {
                System.out.println("-------- " + fileName);
            }
        }
        System.out.println();
    }

    public static void searchFileFromHost(String host) {
        System.out.printf("=== Search Host %s ===", host);
        Node node = Local.localNodeMap.get(host);
        if (node == null) {
            System.out.println("seachFile failed: host is not exist");
        } else {
            for (String fileName: node.getFileInfos().keySet()) {
                System.out.println("-------- " + fileName);
            }
        }
        System.out.println();
    }
}
