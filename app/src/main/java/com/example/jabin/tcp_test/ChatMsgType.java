package com.example.jabin.tcp_test;

/**
 * Created by Jabin on 2017/12/24.
 */
public enum  ChatMsgType {
    REGISTE(0, "注册用户ID"),
    SEND_MSG(1, "发送消息"),
    PUBLISH_MSG(1, "广播消息"),

    ;

    public int msgType;
    public String msgDesc;

    ChatMsgType(int msgType, String msgDesc) {
        this.msgType = msgType;
        this.msgDesc = msgDesc;
    }

    public static ChatMsgType valueOfId(int msgType) {
        ChatMsgType[] chatMsgTypes = ChatMsgType.values();
        for (ChatMsgType chatMsgType : chatMsgTypes) {
            if (msgType == chatMsgType.msgType) {
                return chatMsgType;
            }
        }
        return null;
    }
}
