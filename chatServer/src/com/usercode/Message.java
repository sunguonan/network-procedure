package com.usercode;

import java.io.Serializable;

/**
 * @author sunGuoNan
 * @version 1.0
 * @date 2022/7/17 18:29
 */
public class Message implements Serializable {
    private static final long serialVersionUID = -6329133565119509128L;

    // 消息类型
    private Integer type;
    // 消息内容
    private String content;
    // 用户名
    private String userName;
    // 密码
    private String passWord;
    // 朋友的用户名
    private String friendUserName;

    public Message() {
    }


    public Message(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Message(Integer type, String content, String userName) {
        this.type = type;
        this.content = content;
        this.userName = userName;
    }

    public Message(Integer type, String content, String userName, String friendUserName) {
        this.type = type;
        this.content = content;
        this.userName = userName;
        this.friendUserName = friendUserName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", friendUserName='" + friendUserName + '\'' +
                '}';
    }
}
