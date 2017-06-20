package com.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 堵塞式IO
 * Created by wangyong on 2017/6/12.
 */
public class BIOTest {

    public static void main(String[] args) throws IOException {

        int port = 9090;
        if (args != null && args.length > 0) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                port = 8080;
            }
        }


        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            System.out.println("server is start in port:" + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();

            }
        } finally {
            if (server != null) {
                server.close();
                server = null;
            }
        }


    }
}
