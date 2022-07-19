package com.usercode.immutable;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 * @date 2022/7/18 16:00
 */
public class ConstantType {
    // 表示成功
    public static final String SUCCEED = "SUCCEED";
    // 表示失败
    public static final String FAIL = "FAIL";
    // 服务器名字
    public static final String SERVER_NAME = "SERVER";
    // 服务器应答
    public static final String SERVER_ANSWER = "okk";
    // 默认密码
    public static final String DEFAULT_PASS_WORD = "123";

    // 保存连接服务器端的用户数据
    public static final Map<String, Socket> CURRENT_USER_DATA = new HashMap<>(16);
}
