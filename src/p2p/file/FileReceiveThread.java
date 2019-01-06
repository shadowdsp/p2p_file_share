package p2p.file;

import p2p.utils.Utils;

import java.io.*;
import java.net.Socket;

/**
 * Created by shadowdsp on 2019/1/6.
 */

/**
 * 接收到下载文件请求后，会在线程池执行这条线程
 */
public class FileReceiveThread implements Runnable {

    private Socket socket;

    public FileReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream dataInputStream = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            String filePath = dataInputStream.readUTF();
            in = new FileInputStream(filePath);
            out = socket.getOutputStream();
            long start = System.currentTimeMillis();
            Utils.copyFileData(in, out);
            System.out.println("Upload " + filePath + " took " + (System.currentTimeMillis() - start) + " ms.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
