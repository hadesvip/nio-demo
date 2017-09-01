package com.nio;

import com.io.MonitorConn;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;

public class BIOTest {


  @Test
  public void bioTest() throws IOException {

    //固定5个线程池
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    ServerSocket serverSocket = new ServerSocket();

    InetSocketAddress address = new InetSocketAddress(9999);
    serverSocket.bind(address);

    while (true) {
      Socket scoket = serverSocket.accept();

      executorService.execute(new MonitorConn(scoket));

    }

  }


}
