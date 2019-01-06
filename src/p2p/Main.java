package p2p;

import p2p.connection.ConnectionReceiver;
import p2p.connection.ConnectionSender;
import p2p.file.FileDownloader;
import p2p.file.FileReceiver;
import p2p.utils.SearchUtils;
import p2p.utils.Utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * Created by shadowdsp on 2019/1/5.
 */

public class Main {


    private static void printFormat() {
        System.out.println("请按照格式输入");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("进入P2P文件共享系统");

        // TODO: 监听广播 (Thread)
        new Thread(new ConnectionReceiver()).start();
        // TODO: 广播加入局域网 (Thread)
        ConnectionSender.sendConnection();
        // TODO: 接收下载文件请求 (Thread)
        new Thread(new FileReceiver()).start();

        do {
            System.out.println("\n请输入需要的操作及其参数：");
            System.out.println("1. 上传文件命令: [upload path] --- path为文件路径 ; \n" +
                    "2: 下载文件命令: [download host file dir] --- host为主机名, file为文件名, dir为本地保存路径 ; \n" +
                    "3: 查找所有主机: [searchHost] ; \n" +
                    "4: 查找主机对应的文件: [searchFile host] --- host为主机名 ; \n" +
                    "5: 查找所有主机及其文件: [searchAll] ; \n" +
                    "6: 退出系统: [q]");
            System.out.flush();
            String input = scanner.nextLine();

            if (input.length() == 1 && Character.toLowerCase(input.charAt(0)) == 'q') {
                System.out.println("退出P2P文件共享系统");
                // TODO: 添加删除本主机的逻辑
                System.exit(0);
            } else {
                String[] strs = input.split(" ");
                if (strs.length <= 0) {
                    printFormat();
                    continue;
                }
                switch (strs[0]) {
                    case "upload":
                        if (strs.length == 2) {
                            if (Utils.fileIsExists(strs[1])) {
                                List<String> list = new ArrayList<>();
                                list.add(strs[1]);
                                Local.localNode.insertFileInfos(list);
                                try {
                                    Local.localNodeMap.put(InetAddress.getLocalHost().getHostAddress(),
                                                Local.localNode);
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                }
                                // refresh file to other host
                                ConnectionSender.sendConnection();
                                System.out.println("upload " + strs[1] + " succeed");
                            } else {
                                System.out.println("upload failed: path is not exist");
                            }
                        } else {
                            System.out.println("upload failed: format error");
                        }
                        break;
                    case "download":
                        if (strs.length == 4) {
                            FileDownloader.download(strs[1], strs[2], strs[3]);
                        } else {
                            System.out.println("download failed: format error");
                        }
                        break;
                    case "searchHost":
                        SearchUtils.searchHost();
                        break;
                    case "searchFile":
                        if (strs.length == 2) {
                            SearchUtils.searchFileFromHost(strs[1]);
                        } else {
                            System.out.println("searchFile failed: format error");
                        }
                        break;
                    case "searchAll":
                        SearchUtils.searchAll();
                        break;
                    default:
                        System.out.println("command format error");
                }

            }
        } while (scanner.hasNextLine());
    }
}
