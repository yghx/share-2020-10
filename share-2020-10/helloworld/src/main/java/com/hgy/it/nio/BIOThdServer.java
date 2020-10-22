package com.hgy.it.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

//异步阻塞-可以比较好工作[强制必须使用多线程]
public class BIOThdServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6379);
        while (true) {
            Socket clientSocket = serverSocket.accept(); // 阻塞
            new Thread(() -> {
                try { // 异步循环处理用户请求
                    while (true) {
                        if (clientSocket != null) {
                            InputStream in = clientSocket.getInputStream();
                            byte[] buff = new byte[in.available()]; // 为了简化程序,此处有些场景下会存在bug
                            if (in.read(buff) > 0) {
                                String datum = new String(buff);
                                System.out.println(datum);
                                if ("-1".equals(datum)) {
                                    clientSocket.close();
                                    System.out.println("server client is closed!");
                                    break;
                                }
                            }
                        } else {
                            System.out.println("clientSocket is null! this client exit");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "my-socket-client").start();
        }
    }
}