package com.hgy.it.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class NIOServer {
    static ArrayList<SocketChannel> socketList = new ArrayList<>();
    static ByteBuffer byteBuffer = ByteBuffer.allocate(512);

    //NIO设计就是利用单线程来解决并发的问题
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        /*Selector s = Selector.open();
        s.select();*/
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6379);
        serverSocket.bind(socketAddress);
        serverSocket.configureBlocking(false);//accept()变成非阻塞

        //单线程
        while (true) {
            for (SocketChannel socketChannel : socketList) {
                int read = socketChannel.read(byteBuffer); // 这个虽然是阻塞的,但是绝大多数情况下是短暂的
                if (read > 0) {
                    System.out.println("read--------" + read);
                    byteBuffer.flip();

                    byte[] bs = new byte[read];
                    byteBuffer.get(bs);
                    String content = new String(bs);
                    System.out.println(content);
                    byteBuffer.flip();
                }
            }

            SocketChannel accept = serverSocket.accept();
            if (accept != null) {
                System.out.println("conn success-------");
                accept.configureBlocking(false);//去除阻塞
                socketList.add(accept);
                System.out.println("socketlist  Size ==" + socketList.size());
            }
        }
    }
}
