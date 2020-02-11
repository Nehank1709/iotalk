package in.codepredators.delta.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterCode;
import in.codepredators.delta.R;

public class  CodeFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerAdapterCode recyclerAdapterCode ;
    @Override
    public void onStart() {
        List<Message> messageList = new ArrayList<>();
        Message message= new Message();

        super.onStart();
        recyclerView = getView().findViewById(R.id.viewpagerChatRecycler);

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
        message4.setMessageText("dahggggggggggggggbcd");
        messageList.add(message4);
        Message message5 = new Message();
        message5.setMessageTime("6:46 pm");
        message5.setMessageType("10000");
        message5.setMessageText("abcd");
        messageList.add(message5);

        //TODO first you have to assign variables here and then you can use setonclicklistener in onstart

        LinearLayoutManager mlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerAdapterCode = new RecyclerAdapterCode(getActivity().getApplicationContext() , messageList);
        recyclerView.setAdapter(recyclerAdapterCode);
        Log.i("value of recycler", String.valueOf(recyclerAdapterCode.getItemCount()));
    }
    public void searchList(String searchText){
        Log.i("searchList code"," " + searchText);
    }
    public void refreshRecycler(){
        recyclerAdapterCode.refreshRecycler();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chatlist,container,false);
        Log.i("on","create");
        return rootView;
    }
}
