package com.usercode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author sunGuoNan
 * @version 1.0
 * @date 2022/7/17 17:54
 */
// 压制全部警告
@SuppressWarnings("all")
public class Server {
    public static void main(String[] args){
        try (ServerSocket serverSocket = new ServerSocket()){
            // 绑定服务端端口号
            serverSocket.bind(new InetSocketAddress(8888));
            System.out.println("服务器启动完成，等待客户端连接");
            // 每一个客户端连上服务器,就单独把这个socket传给线程处理
            while (true){
                // 开始监听 方法阻塞 等待客户端回传消息
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
