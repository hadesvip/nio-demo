package com.io.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by wangyong on 2017/6/20.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        startClient();
    }

    private static void startClient() {
        try {
            Socket socket = new Socket("127.0.0.1", 9090);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintStream ps = new PrintStream(socket.getOutputStream());
            new AcceptData(socket);
            while (true) {
                String str = br.readLine();
                if ("exit".equals(str)) {
                    break;
                }
                ps.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


/**
 * 随时接受从服务器发送过来的数据，可能是群聊的内容也可能是私聊的内容
 */
class AcceptData implements Runnable {

    private Socket socket;
    private BufferedReader bufferedReader;

    public AcceptData(Socket socket) {
        this.socket = socket;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    @Override
    public void run() {

        while (true) {
            try {
                String str = bufferedReader.readLine();

                if ("exit".equals(str)) {
                    System.out.println("有客户端退出了...");
                }
                if ("".equals(str) || str == null) {
                    continue;
                }
                //规定:私聊-->ip/内容，群聊没有/
                int index = str.lastIndexOf("/");

                //私聊
                if (index > 0) {
                    String ipAddress = str.split("/")[0];
                    String content = str.split("/")[1];
                    System.out.println(ipAddress + "对我说:" + content);
                } else {
                    //群聊
                    System.out.println("群聊内容是:" + str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
