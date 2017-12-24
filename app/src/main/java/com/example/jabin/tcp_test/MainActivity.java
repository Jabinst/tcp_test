package com.example.jabin.tcp_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvMyId;
    TextView tvToId;

    EditText etSendMsg = null;
    Button btSend = null;
    RecyclerView rvMsgView;
    Integer myId;
    final List<ChatMessage> chatMessages = new ArrayList<>();

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        tvMyId = findViewById(R.id.my_id);
        tvToId = findViewById(R.id.to_id);

        etSendMsg = findViewById(R.id.send_msg);
        btSend = findViewById(R.id.send);
        rvMsgView = findViewById(R.id.msg_view);

        myId = (int)(Math.random()*10);
        Log.i("生成客户端id", myId + "");
        tvMyId.setText(myId + "");
        final MsgViewAdapter msgViewAdapter = new MsgViewAdapter(this, R.layout.msg_view_item, myId, chatMessages);
        rvMsgView.setAdapter(msgViewAdapter);
        rvMsgView.setLayoutManager(new LinearLayoutManager(this));

        final ChatClient chatClient = new ChatClient("192.168.1.104", 1234, myId, new ChatClient.ChatListener() {
            @Override
            public void onReceive(ChatMessage chatMessage) {
                chatMessages.add(chatMessage);
                handler.sendEmptyMessage(0);
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        msgViewAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tvToId.getText())) {
                    Toast.makeText(MainActivity.this, "请输入要发送的客户端id", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Integer> toIds = new ArrayList<>();
                toIds.add(Integer.valueOf(tvToId.getText().toString()));

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.msgType = ChatMsgType.SEND_MSG.msgType;
                chatMessage.content = etSendMsg.getText().toString();
                chatMessage.fromId = myId;
                chatMessage.toIds = toIds;
                chatClient.send(chatMessage);

                etSendMsg.setText("");
            }
        });
    }
}
