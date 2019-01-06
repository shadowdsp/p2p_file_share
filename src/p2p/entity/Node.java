package p2p.entity;

import p2p.utils.Utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class Node {
    private String address;
    /**
     * <fileName, path>
     */
    private HashMap<String, String> fileInfos;

    public Node() {
        try {
            this.address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.fileInfos = new HashMap<>();
    }

    public Node(String address) {
        fileInfos = new HashMap<>();
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashMap<String, String> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(HashMap<String, String> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public void insertFileInfos(List<String> paths) {
        for (String path: paths) {
            String fileName = Utils.getFileNameFromPath(path);
            if (!fileInfos.containsKey(fileName)) {
                fileInfos.put(fileName, path);
            }
        }
    }

    public List<String> getFilePathArray() {
        return new ArrayList<>(fileInfos.values());
    }
}
