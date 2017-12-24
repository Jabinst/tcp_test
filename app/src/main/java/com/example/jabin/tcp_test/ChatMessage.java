package com.example.jabin.tcp_test;

import java.util.List;

/**
 * Created by Jabin on 2017/12/24.
 */
public class ChatMessage {
    public int msgType;
    public Integer fromId;
    public List<Integer> toIds;
    public String content;
}
