package com.usercode.util;

import com.usercode.Message;

import java.io.*;
import java.util.Optional;

/**
 * @author sunGuoNan
 * @version 1.0
 * @date 2022/7/17 19:15
 */
public class MsgUtils {
   
    /**
     * 从流中读取message
     * @return java.util.Optional<com.usercode.Message>
     * @param inputStream
     * @author sunGuoNan
     * @date 2022/7/17 21:09
     */
    public static Optional<Message> readMsg(InputStream inputStream){
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(inputStream);
            // 封装成一个Optional,将来外界进行使用的时候可以避免空指针
            return Optional.ofNullable((Message) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
        // 在工具类中不主动关闭流
    }
    
    /**
     * 从流中写出数据
     * @return void
     * @param outputStream
     * @param message
     * @author sunGuoNan
     * @date 2022/7/17 21:18
     */
    public static void writeMsg(OutputStream outputStream,Message message){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 在工具类中不主动关闭流
    }
}
