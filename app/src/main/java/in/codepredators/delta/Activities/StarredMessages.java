package in.codepredators.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterStarredMessages;
import in.codepredators.delta.R;

public class StarredMessages extends AppCompatActivity {

    RecyclerAdapterStarredMessages recyclerAdapterStarredMessages ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iotalkactivity_starred_messages);

        RecyclerView recyclerView;
        List<Message> messageList = new ArrayList<>();
        Message message= new Message();
        recyclerView = findViewById(R.id.recyclerViewStarredMessages);

        message.setMessageTime("2:46 pm");
        message.setMessageType("10000");
        message.setMessageText("abcd");
        messageList.add(message);
        Message message2 = new Message();
        message2.setMessageTime("3:46 pm");
        message2.setMessageType("10000");
        message2.setMessageText("abcd");
        messageList.add(message2);
        Message message3 = new Message();
        message3.setMessageTime("4:46 pm");
        message3.setMessageType("10000");
        message3.setMessageText("abuguhud");
        messageList.add(message3);
        Message message4 = new Message();
        message4.setMessageTime("5:46 pm");
        message4.setMessageType("10000");
        message4.setMessageText("dahggggggggggggggbkhyuvhjniuhny7ghnjiojujjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjlpkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkcd");
        messageList.add(message4);
        Message message5 = new Message();
        message5.setMessageTime("6:46 pm");
        message5.setMessageType("10000");
        message5.setMessageText("abcd");
        messageList.add(message5);

        //TODO first you have to assign variables here and then you can use setonclicklistener in onstart

        LinearLayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        Log.i("entered", String.valueOf(messageList.size()));
        recyclerAdapterStarredMessages = new RecyclerAdapterStarredMessages(getApplicationContext() , messageList);
        recyclerView.setAdapter(recyclerAdapterStarredMessages);
        Log.i("value of recycler", String.valueOf(recyclerAdapterStarredMessages.getItemCount()));

    }
}
