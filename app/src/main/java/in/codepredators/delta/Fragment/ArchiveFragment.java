package in.codepredators.delta.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import in.codepredators.delta.Activities.ChatList;
import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterArchiveList;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;

public class ArchiveFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    List<ChatList.ChatListItem> chatListItemList;
    RecyclerAdapterArchiveList recyclerAdapterArchiveList ;
    @Override
    public void onStart() {
        super.onStart();
        chatListItemList = new ArrayList<>();
        ChatList.ChatListItem chatListItem = new ChatList.ChatListItem();
        Log.i("on","start");
        recyclerView = getView().findViewById(R.id.viewpagerChatRecycler);

        User user = new User();
        user.setUserName("");
        Message message=new Message();
        message.setMessageTime("12 : 00 pm");
        message.setMessageText("Hello");
        chatListItem.setMessage(message);
        chatListItem.setUser(user);
        chatListItem.setNoOfUnseenMessage("50000");
        chatListItem.setPinStatus(true);


        chatListItemList.add(chatListItem);
        chatListItem.setPinStatus(true);


        chatListItemList.add(chatListItem);
        chatListItem.getMessage().setMessageTime("1 : 00 pm");
        chatListItemList.add(chatListItem);
        chatListItemList.add(chatListItem);
        chatListItemList.add(chatListItem);
        chatListItemList.add(chatListItem);
        chatListItem.getMessage().setMessageTime("3 : 00 pm");
        chatListItemList.add(chatListItem);

        chatListItemList.add(chatListItem);
        chatListItemList.add(chatListItem);
        Log.i("checkingsize", String.valueOf(chatListItemList.size()));
        Toast.makeText(getActivity().getApplicationContext(),"archive fragment",Toast.LENGTH_SHORT).show();
        //TODO first you have to assign variables here and than you can use setonclicklistener in onstart
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerAdapterArchiveList = new RecyclerAdapterArchiveList(getActivity().getApplicationContext() ,chatListItemList);
        recyclerView.setAdapter(recyclerAdapterArchiveList);
        Log.i("value of recycler", String.valueOf(recyclerAdapterArchiveList.getItemCount()));
    }
    public void searchList(String searchText){
        Log.i("searchList archieve"," " + searchText);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chatlist,container,false);
        Log.i("on","create");
        return rootView;
    }
}
