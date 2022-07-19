package com.usercode;

import com.usercode.immutable.ConstantType;
import com.usercode.immutable.MessageType;
import com.usercode.util.MsgUtils;
import com.usercode.util.ScannerUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;


/**
 * @author sunGuoNan
 * @version 1.0
 * @date 2022/7/17 17:55
 */
public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket();

        // 使用connect方法和服务器建立连接
        socket.connect(new InetSocketAddress(InetAddress.getByName("localhost"), 8888));

        System.out.println("成功和服务器建立连接");

        // 创建输入输出流
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        // 表示当前登入的用户
        String userName = null;

        while (true) {
            // 判断用户名是否为空
            if (userName == null) {
                // 进行用户登入
                userName = login(outputStream, inputStream, userName);
            } else {
                // 菜单栏选择
                systemMenus();

                String input = ScannerUtil.input();

                switch (Integer.parseInt(input)) {
                    case MessageType.TO_SERVER:
                        // 给服务器发送消息
                        sendToServer(userName, outputStream, inputStream);
                        break;
                    case MessageType.TO_FRIEND:
                        // 给朋友发送消息
                        sendToFriend(userName, outputStream, inputStream);
                        break;
                    case MessageType.TO_ALL:
                        // 给全部人发送消息
                        sendToAll(userName,outputStream, inputStream);
                        break;
                    case MessageType.TO_RECEIVER:
                        receiverMsg(outputStream, inputStream);
                        break;
                    case MessageType.EXIT:
                        // 退出操作
                        System.exit(-1);
                    default:
                        break;
                }
            }
        }
    }

    private static void receiverMsg(OutputStream outputStream, InputStream inputStream) {
      while (true){
          // 读取消息
          Optional<Message> message = MsgUtils.readMsg(inputStream);
          message.ifPresent(m -> System.out.println(m.getUserName() + "  : " + m.getContent()));
      }
    }

    private static void sendToAll( String userName, OutputStream outputStream, InputStream inputStream) {
        boolean flag = true;
        
        while (flag){
            System.out.print(userName + ": ");
            String seedMessage = ScannerUtil.input();

            if ("bye".equals(seedMessage)){
                flag = false;
            }

            // 发送消息
            MsgUtils.writeMsg(outputStream, new Message(MessageType.TO_ALL, seedMessage, userName));
        }
    }

    private static void sendToFriend(String userName, OutputStream outputStream, InputStream inputStream) {
        System.out.println("请输入好友id: ");
        String friendName = ScannerUtil.input();
        
        boolean flag = true;
        
        while (flag){
            System.out.print(userName + ": ");
            String seedMessage = ScannerUtil.input();
            
            if ("bye".equals(seedMessage)){
                flag = false;
            }
            
            // 发送消息
            MsgUtils.writeMsg(outputStream, new Message(MessageType.TO_FRIEND, seedMessage, userName, friendName));
        }
    }

    /**
     * 客户端给服务器发送消息
     *
     * @param outputStream
     * @param inputStream
     * @return void
     * @author sunGuoNan
     * @date 2022/7/18 17:54
     */
    private static void sendToServer(String userName, OutputStream outputStream, InputStream inputStream) {

        System.out.println(userName + "的消息: ");
        String seedMessage = ScannerUtil.input();
        // 发送消息
        MsgUtils.writeMsg(outputStream, new Message(MessageType.TO_SERVER, seedMessage, userName));

        // 接收返回的消息
        Optional<Message> acceptMessage = MsgUtils.readMsg(inputStream);
        acceptMessage.ifPresent(m -> System.out.println(m.getUserName() + " : " + m.getContent()));
    }

    /**
     * 用户尝试登入
     *
     * @param outputStream
     * @param inputStream
     * @param userName
     * @return java.lang.String
     * @author sunGuoNan
     * @date 2022/7/18 16:18
     */
    private static String login(OutputStream outputStream, InputStream inputStream, String userName) {
        System.out.println("请输入用户名");
        String name = ScannerUtil.input();

        System.out.println("请输入密码");
        String password = ScannerUtil.input();

        // 将用户数据写出到服务器端  尝试登入
        Message userLogin = new Message();
        userLogin.setType(MessageType.TO_LOGIN);
        userLogin.setUserName(name);
        userLogin.setPassWord(password);
        MsgUtils.writeMsg(outputStream, userLogin);

        // 接收来自服务器返回的消息,返回是否成功登入
        Optional<Message> message = MsgUtils.readMsg(inputStream);


        if (message.isPresent() && (ConstantType.SUCCEED).equals(message.get().getContent())) {
            userName = name;
            return userName;
        }
        return null;
    }

    /**
     * 菜单功能选择
     *
     * @param
     * @return void
     * @author sunGuoNan
     * @date 2022/7/18 17:16
     */
    public static void systemMenus() {
        System.out.println(
                "请选择功能 :" + "  " +
                        MessageType.TO_SERVER + "给服务器发消息" + "  " +
                        MessageType.TO_FRIEND + "给朋友发消息" + "  " +
                        MessageType.TO_ALL + "给全部人发消息" + "  " +
                        MessageType.TO_RECEIVER + "读取消息" + "  " +
                        MessageType.EXIT + "退出" + "  "
        );
    }
}
