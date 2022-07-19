package com.usercode;

import com.usercode.immutable.ConstantType;
import com.usercode.immutable.MessageType;
import com.usercode.util.MsgUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;

/**
 * @author sunGuoNan
 * @version 1.0
 * @date 2022/7/19 11:34
 */
public class ServerThread implements Runnable {

    // 客户端连接
    private Socket socket;

    public ServerThread() {
    }

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                // 创建输入输出流
                // 相同也是用之前创建的socket,拿到输入输出流
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream()
        ) {

            // 死循环等待客户端发送消息
            while (true) {
                // 读取从客户端发送过来的数据
                Optional<Message> message = MsgUtils.readMsg(inputStream);
                if (message.isPresent()) {
                    Message msg = message.get();
                    switch (msg.getType()) {
                        case MessageType.TO_LOGIN:
                            // 登入
                            loginHandler(inputStream, outputStream, message, socket);
                            break;
                        case MessageType.TO_SERVER:
                            sendToClient(inputStream, outputStream, msg);
                            break;
                        case MessageType.TO_FRIEND:
                            sendToFriend(msg);
                            break;
                        case MessageType.TO_ALL:
                            sendToAll(inputStream, outputStream, msg);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToAll(InputStream inputStream, OutputStream outputStream, Message message) {
        // 遍历在线的用户
        for (Map.Entry<String, Socket> SocketEntry : ConstantType.CURRENT_USER_DATA.entrySet()) {
            try {
                MsgUtils.writeMsg(SocketEntry.getValue().getOutputStream(),message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给用户发送消息
     * @return void
     * @param message
     * @author sunGuoNan
     * @date 2022/7/19 16:23
     */
    private void sendToFriend(Message message) {

        // 找到需要连接好友对于的socket
        Socket socket = (ConstantType.CURRENT_USER_DATA).get(message.getFriendUserName());

        // 进行转发
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            MsgUtils.writeMsg(outputStream, new Message(MessageType.TO_FRIEND, message.getContent(),message.getFriendUserName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收客户端发送过来的消息,并处理
     *
     * @param inputStream
     * @param outputStream
     * @param message
     * @return void
     * @author sunGuoNan
     * @date 2022/7/19 11:52
     */
    private void sendToClient(InputStream inputStream, OutputStream outputStream, Message message) {

        // 接收到客户端发送过来的消息
        System.out.println(message.getUserName() + " : " + message.getContent());
        // 回复客户端消息
        MsgUtils.writeMsg(outputStream, new Message(MessageType.FROM_SERVER, ConstantType.SERVER_ANSWER, ConstantType.SERVER_NAME));

    }

    /**
     * 在服务端进行对登入的处理
     *
     * @param inputStream
     * @param outputStream
     * @return void
     * @author sunGuoNan
     * @date 2022/7/18 21:08
     */
    private void loginHandler(InputStream inputStream, OutputStream outputStream, Optional<Message> message, Socket socket) {
        if (!message.isPresent() || message.get().getUserName() == null ||
                !(ConstantType.DEFAULT_PASS_WORD).equals(message.get().getPassWord())) {
            // 给客户端回传消息
            MsgUtils.writeMsg(outputStream,
                    new Message(MessageType.FROM_SERVER, ConstantType.FAIL));
        } else {
            // 给客户端回传消息
            MsgUtils.writeMsg(outputStream,
                    new Message(MessageType.FROM_SERVER, ConstantType.SUCCEED));
            // 满足条件则将数据存放在map中
            (ConstantType.CURRENT_USER_DATA).put(message.get().getUserName(), socket);
            System.out.println(message.get().getUserName() + "登入成功");
        }
    }


}
