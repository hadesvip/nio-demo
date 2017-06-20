package com.io.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 存储socket并转发数据
 * Created by wangyong on 2017/6/20.
 */
public class Server {

    //存储socket容器:ip地址--->socket
    public final static Map<String, Socket> socketMap = new HashMap<>();

    /**
     * 添加客户端
     *
     * @param address
     * @param socket
     */
    public static void addClient(String address, Socket socket) {
        socketMap.put(address, socket);
    }

    /**
     * 获取所有储存的客户端
     *
     * @return
     */
    public static Map<String, Socket> getUserSocketMap() {
        return socketMap;
    }

    /**
     * 获取某一个客户端
     *
     * @param address
     * @return
     */
    public static Socket findClient(String address) {
        if (socketMap.containsKey(address))
            return socketMap.get(address);
        return null;
    }

    public static void main(String[] args) {
        new Server().startServer();
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("服务已经开启...");
            while (true) {
                Socket socket = serverSocket.accept();
                String address = socket.getInetAddress().getHostAddress()
                        + "-" + UUID.randomUUID().toString().replaceAll("-", "");
                System.out.println("接收" + address + "的请求...");
                addClient(address, socket);
                new Service(socket, address);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class Service implements Runnable {

    private Socket socket;
    private BufferedReader br;
    private PrintStream ps;
    private PrintStream clientToClient;
    private PrintStream toAll;
    private String address;


    public Service(Socket socket, String address) {
        this.address = address;
        this.socket = socket;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ps = new PrintStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    @Override
    public void run() {

        while (true) {
            try {
                String str = br.readLine();
                if ("".equals(str) || str == null) {
                    continue;
                }
                if ("exit".equals(str)) {
                    break;
                }
                int index = str.indexOf("/");

                //私聊
                if (index > 0) {
                    String ip = str.split("/")[0];
                    String content = str.split("/")[1];
                    Socket client = Server.findClient(ip);
                    if (client != null) {
                        clientToClient = new PrintStream(client.getOutputStream());
                        clientToClient.println(address + "/" + content);
                    } else {
                        ps.println("你所要对话的客户端不存在...");
                    }
                } else {
                    //群聊
                    Server.getUserSocketMap().forEach((addr, sk) -> {
                        try {
                            Socket client = Server.getUserSocketMap().get(addr);
                            toAll = new PrintStream(client.getOutputStream());
                            toAll.println(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
