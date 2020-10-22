package com.hgy.it.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//循环阻塞->在不引入异步线程情况工作困难
public class BIOCycServer {
    static byte[] bs = new byte[1024];
    static List<Socket> socketList = new ArrayList<>();

    //
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6379);
        // serverSocket.setBlock(false);
        while (true) {
            for (Socket socket : socketList) {
                if (socket.getInputStream().read(bs) > 0) { // 可以换成异步线程执行,但未必性能更好
                    System.out.println(new String(bs));
                }
            }
            Socket clientSocket = serverSocket.accept();// 阻塞 -> 非阻塞?
            if (clientSocket != null) {
                // serverSocket.setBlock(false);
                socketList.add(clientSocket);
            }
        }
    }
}