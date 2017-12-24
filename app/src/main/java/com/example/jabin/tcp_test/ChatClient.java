package com.example.jabin.tcp_test;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Jabin on 2017/12/24.
 */
public class ChatClient {
    Socket socket = null;

    ChatClient(final String ip, final int port, final Integer myId, final ChatClient.ChatListener listener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.msgType = ChatMsgType.REGISTE.msgType;
                    chatMessage.fromId = myId;
                    send(chatMessage);
                    startup(listener);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void send(ChatMessage chatMessage) {
        try {
            socket.getOutputStream().write((JsonUtil.toJsonString(chatMessage)).getBytes("UTF-8"));
            socket.getOutputStream().write("\n".getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startup(final ChatClient.ChatListener listener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream(), "UTF-8"));//当前服务器会不断读取当前客户端的数据
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        try {
                            System.out.println(line);
                            ChatMessage chatMessage = JsonUtil.fromJson(line, ChatMessage.class);
                            listener.onReceive(chatMessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    br.close();
                    System.out.println("断开了一个客户端链接");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public interface ChatListener {
        void onReceive (ChatMessage chatMessage);
    }
}
