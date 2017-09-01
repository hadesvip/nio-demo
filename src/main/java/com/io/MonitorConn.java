package com.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 监听客户端的连接并进行相应的处理
 */
public class MonitorConn implements Runnable {


  private Socket socket;

  public MonitorConn(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    BufferedReader reader = null;
    PrintStream out = null;

    try {

      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      System.out.println("客户端发送过来的消息:" + reader.readLine());

      out = new PrintStream(socket.getOutputStream());
      out.println("服务端已经接收到消息....");


    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      if (out != null) {
        out.close();
      }

      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }
}
