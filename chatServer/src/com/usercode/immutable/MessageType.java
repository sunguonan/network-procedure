package com.usercode.immutable;

/**
 * @author sunGuoNan
 * @version 1.0
 * @date 2022/7/17 18:34
 */
public class MessageType {
    // 登入
    public static final int TO_LOGIN = 1;
    // 发送给服务器
    public static final int TO_SERVER = 2;
    // 发送给朋友
    public static final int TO_FRIEND = 3;
    // 发送给全部人
    public static final int TO_ALL = 4;
    // 从服务器发送
    public static final int FROM_SERVER = 5;
    // 读取消息
    public static final int TO_RECEIVER = 6;
    // 退出
    public static final int EXIT = -1;
    
}
