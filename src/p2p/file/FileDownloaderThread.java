package p2p.file;

import p2p.Local;
import p2p.entity.Node;
import p2p.utils.Utils;

import java.io.*;
import java.net.Socket;

/**
 * Created by shadowdsp on 2019/1/6.
 */

public class FileDownloaderThread implements Runnable {

    private String host;
    private String fileName;
    private String localDir;

    public FileDownloaderThread(String host, String fileName, String localDir) {
        this.host = host;
        this.fileName = fileName;
        this.localDir = localDir;
    }

    /**
     * 下载 host 的 path 的文件并保存到本地 localDir
     */
    @Override
    public void run() {
        Node node = Local.localNodeMap.get(host);
        if (node == null) {
            System.out.println("download failed: cannot find host");
        } else {
            String path = node.getFileInfos().get(fileName);
            if (path == null) {
                System.out.println("download failed: cannot find " + fileName + " in " + host);
            } else {
                try (Socket socket = new Socket(host, Utils.TCP_PORT)) {
                    // 发送文件路径
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(path);

                    // 接收文件
                    InputStream in = socket.getInputStream();
                    File f = new File(localDir);
                    boolean created = false;
                    if (!f.exists()) {
                        try {
                            created = f.mkdir();
                        } catch (Exception e) {
                            System.out.println("Couldn't create the folder, the file will be saved in the current " +
                                    "directory!");
                        }
                    } else {
                        created = true;
                    }
                    OutputStream out = (created) ? new FileOutputStream(localDir + File.separator + fileName) :
                            new FileOutputStream(fileName);
                    Utils.copyFileData(in, out);
                    System.out.println("File " + fileName + " download from " + host);
                    dataOutputStream.close();
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
