package in.codepredators.delta.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.codepredators.delta.Activities.ChatList;
import in.codepredators.delta.DatabaseHelper.DatabaseHelperMessage;
import in.codepredators.delta.DatabaseHelper.DatabaseHelperPersonDetail;
import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.Classes.PersonalMessage;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterChatList;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private List<ChatList.ChatListItem> chatListItemList;
    private List<ChatList.ChatListItem> searchList;
    private RecyclerAdapterChatList recyclerAdapterChatList ;
    private ChatList.ChatListItem chatListItem;
    private PersonalMessage personalMessage;
    private User user;
    private DatabaseHelperPersonDetail databaseHelperPersonDetail;
    private DatabaseHelperMessage databaseHelperMessage;
    private List<Message> newMessages;
    private DatabaseReference otherUserRef;
    private int sizevalue;
    private List<ChatList.ChatListItem> secondList;
    private FloatingActionButton use;


    public void searchList(String searchText){
        Log.i("searchList chat"," " + searchText);
        if(searchText.length()>0) {
            searchList = new ArrayList<>();
            for (ChatList.ChatListItem c : chatListItemList) {
                if (c.getUser().getUserName().toLowerCase().contains(searchText.toLowerCase())) {
                    Log.i("search List",c.getUser().getUserName());
                    searchList.add(c);
                }
            }
            RecyclerAdapterChatList n = new RecyclerAdapterChatList(getContext(), searchList);
            recyclerView.setAdapter(n);
        }else{
            recyclerView.setAdapter(recyclerAdapterChatList);
        }
    }
    public void refreshRecycler(){
        recyclerAdapterChatList.refreshRecycer();
    }
    @Override
    public void onStart() {
        super.onStart();
        chatListItemList = new ArrayList<>();
        secondList = new ArrayList<>();
        newMessages = new ArrayList<>();

        Log.i("on","start");
        databaseHelperPersonDetail = new DatabaseHelperPersonDetail(getActivity().getApplicationContext());
        databaseHelperMessage = new DatabaseHelperMessage(getActivity().getApplicationContext());
        chatListItem = new ChatList.ChatListItem();
        //chatListItemList = databaseHelperPersonDetail.getAllChatList();
        for(ChatList.ChatListItem c : chatListItemList){
            databaseHelperMessage.getMessageByMID(c.getMessage().getMID());
        }
        user = new User();
        Message message  = new Message();
        message.setMID("hyu");
        user.setUserName("Anushka");
        PersonalMessage pc = new PersonalMessage();
        pc.setPID("ioio");
        message.setMessageTime("12:00");
        chatListItem.setPersonalMessage(pc);
        chatListItem.setUser(user);
        chatListItem.setMessage(message);
        chatListItemList.add(chatListItem);
        use = getView().findViewById(R.id.use);
        recyclerView = getView().findViewById(R.id.viewpagerChatRecycler);
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(ChatList.ChatListItem c : chatListItemList){
                    Log.i("clckresult",c.getPersonalMessage().getPID());
                }
            }
        });

        Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.swing_up_left);
        recyclerView.startAnimation(animation);

        LinearLayoutManager mlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerAdapterChatList = new RecyclerAdapterChatList(getActivity().getApplicationContext() , chatListItemList);
        recyclerView.setAdapter(recyclerAdapterChatList);
        Log.i("value of recycler", String.valueOf(recyclerAdapterChatList.getItemCount()));
        mAuth = FirebaseAuth.getInstance();
//      final String UID = mAuth.getCurrentUser().getUid();
        final String UID = "MeINhuTy1oYhjiM81QIbzZFhqup1";
        final User[] authUser = {new User()};



        FirebaseDatabase.getInstance().getReference().child("users").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    authUser[0] = dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        sizevalue = chatListItemList.size();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users").child(UID).child("connectedIds");
        ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    secondList.add(new ChatList.ChatListItem());
                    secondList.get(sizevalue-1).setPersonalMessage(new PersonalMessage());
                    final Iterator<DataSnapshot> activeMessages = dataSnapshot.getChildren().iterator();
                    FirebaseDatabase.getInstance().getReference().child("personalMessage")
                            .child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String one = dataSnapshot.child("personalUserOne").getValue(String.class);
                            final String two = dataSnapshot.child("personalUserTwo").getValue(String.class);
                            final String three = dataSnapshot.child("pid").getValue(String.class);
                            secondList.get(sizevalue-1).getPersonalMessage().setPersonalUserOne(dataSnapshot.child("personalUserOne").getValue(String.class));
                            secondList.get(sizevalue-1).getPersonalMessage().setPersonalUserTwo(dataSnapshot.child("personalUserTwo").getValue(String.class));
                            Log.i("opop",dataSnapshot.child("personalUserOne").getValue(String.class)+ "  " + dataSnapshot.child("personalUserTwo").getValue(String.class));
                            secondList.get(sizevalue-1).getPersonalMessage().setPID(dataSnapshot.child("pid").getValue(String.class));
                            //this datasnapshot can also give messages
                            //pulling messages

                            //end

                            if(!secondList.get(sizevalue-1).getPersonalMessage().getPersonalUserTwo().equals(UID)){
                                otherUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(secondList.get(sizevalue-1).getPersonalMessage().getPersonalUserTwo());
                            }else{
                                otherUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(secondList.get(sizevalue-1).getPersonalMessage().getPersonalUserOne());
                            }
                            otherUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    secondList.get(sizevalue-1).setUser(new User());
                                    secondList.get(sizevalue-1).getUser().setUID(dataSnapshot.child("uid").getValue(String.class));
                                    secondList.get(sizevalue-1).getUser().setUserProfilePic(dataSnapshot.child("userProfilePic").getValue(String.class));
                                    Log.i("opop",secondList.get(sizevalue-1).getUser().getUID());
                                    secondList.get(sizevalue-1).getUser().setUserName(dataSnapshot.child("userName").getValue(String.class));
                                    secondList.get(sizevalue-1).getUser().setUserNumber(dataSnapshot.child("userNumber").getValue(String.class));
                                    secondList.get(sizevalue-1).getUser().setUserBio(dataSnapshot.child("userBio").getValue(String.class));
                                    secondList.get(sizevalue-1).setNoOfUnseenMessage(String.valueOf(0));
                                    secondList.get(sizevalue-1).setMessage(new Message());
                                    secondList.get(sizevalue-1).getMessage().setMessageTime("kkk");
                                    secondList.get(sizevalue-1).getMessage().setMID("knjn");


                                    while(activeMessages.hasNext()){
                                        Log.i("running","running");
                                        secondList.get(sizevalue - 1).setNoOfUnseenMessage(String.valueOf(Integer.parseInt(secondList.get(sizevalue - 1)
                                                .getNoOfUnseenMessage()) + 1));
                                        DataSnapshot activeMessage = activeMessages.next();
                                        String activeMID =  activeMessage.getKey();
                                        Log.i("activemessageId",activeMID);
                                        String activeMessageTime = activeMessage.getValue(String.class);
                                        secondList.get(sizevalue-1).getMessage().setMessageTime(activeMessageTime);
                                        secondList.get(sizevalue-1).getMessage().setMID(activeMID);
                                    }
                                    Log.i("clck","ok");
                                    secondList.get(sizevalue - 1).setPersonalMessage(new PersonalMessage());
                                    secondList.get(sizevalue - 1).getPersonalMessage().setPID(three);
                                    secondList.get(sizevalue - 1).getPersonalMessage().setPersonalUserOne(one);
                                    secondList.get(sizevalue - 1).getPersonalMessage().setPersonalUserTwo(two);
                                    int flag = -1;
                                    for(ChatList.ChatListItem c : chatListItemList){
                                        if(c.getPersonalMessage().getPID().equals(secondList.get(sizevalue-1).getPersonalMessage().getPID())){
                                            flag=chatListItemList.indexOf(c);
                                            break;
                                        }
                                    }
                                    if(flag!=-1){
                                        chatListItemList.set(flag,secondList.get(sizevalue-1));
                                        Log.i("updatechat","updated");
                                        databaseHelperPersonDetail.updatePersonalChatDetails(secondList.get(sizevalue-1));
                                    }else{
                                        chatListItemList.add(secondList.get(sizevalue-1));
                                        databaseHelperPersonDetail.insertPersonalChatDetail(secondList.get(sizevalue-1));
                                        sizevalue++;
                                    }
//                                    chatListItemList.add(secondList.get(sizevalue-1));
                                    //sizevalue++;
                                    recyclerAdapterChatList.notifyDataSetChanged();
//                                    if(flag!=-1)
//                                        sizevalue++;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.i("valuechanged","ok");
                    String pid = dataSnapshot.getKey();
                    for(ChatList.ChatListItem c : chatListItemList){
                        if(c.getPersonalMessage().getPID().equals(pid)){
                            c.setNoOfUnseenMessage(String.valueOf(dataSnapshot.getChildrenCount()));
                            recyclerAdapterChatList.notifyDataSetChanged();
                            Log.i("update","updated");
                            databaseHelperPersonDetail.updatePersonalChatDetails(c);
                            break;
                        }
                    }
//                    for(ChatList.ChatListItem c : chatListItemList){
//                        if(c.getPersonalMessage().getPID().equals(dataSnapshot.getKey())){
//                            final int index = chatListItemList.indexOf(c);
//                            chatListItemList.get(index).setNoOfUnseenMessage(String.valueOf(dataSnapshot.getChildrenCount()));
//
//                            Iterator<DataSnapshot> activatedMessages = dataSnapshot.getChildren().iterator();
//                            int i=0;
//                            while (activatedMessages.hasNext()){
//                                i++;
//                                Message m = new Message();
//                                DataSnapshot activeMessage = activatedMessages.next();
//                                String activeMID =  activeMessage.getKey();
//                                m.setMID(activeMID);
//                                Log.i("activatedmessageId",activeMID);
//                                String activeMessageTime = activeMessage.getValue(String.class);
//                                m.setMessageTime(activeMessageTime);
//                                Log.i("value","inside while");
//                                if(!newMessages.contains(m)){
//                                    databaseHelperMessage.insertMessage(m,"false","complete",
//                                            null,m.getMID().substring(3,26));
//                                }
//                                if(i==Integer.parseInt(chatListItemList.get(index).getNoOfUnseenMessage())){
//                                    chatListItemList.get(index).getMessage().setMessageTime(activeMessage.getValue(String.class));
//                                    Log.i("value",activeMessage.getValue(String.class));
//                                    Log.i("value","inside if");
//                                    break;
//                                }
//                            }
//
////                            Query lastNode = FirebaseDatabase.getInstance().getReference().child("users").child(UID)
////                                    .child("connectedIds").child(c.getPersonalMessage().getPID()).orderByKey().limitToLast(1);
////                            lastNode.addListenerForSingleValueEvent(new ValueEventListener() {
////                                @Override
////                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                                    chatListItemList.get(index).getMessage().setMessageTime(dataSnapshot.getValue(String.class));
////                                    //recyclerAdapterChatList.notifyDataSetChanged();
////                                    Log.i("Messageforchange",dataSnapshot.getValue(String.class));
////                                }
////                                @Override
////                                public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                }
////                            });
//                            recyclerAdapterChatList.notifyDataSetChanged();
//                            break;
//
//                        }
//                    }

                    Log.i("onchildchanged",dataSnapshot.getKey());

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        /////
        DatabaseReference ref2 = database.getReference().child("users").child(UID);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("change123","raj" + dataSnapshot.child("age").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ////
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chatlist,container,false);
        Log.i("on","create");
        return rootView;


    }
}
//                    Iterator<DataSnapshot> activatedMessages = dataSnapshot.getChildren().iterator();
//                    while(activatedMessages.hasNext()){
//
//                        DataSnapshot activeMessage = activatedMessages.next();
//                        String activeMID =  activeMessage.getKey();
//                        String activeMessageTime = activeMessage.getValue(String.class);
//                        FirebaseDatabase.getInstance().getReference().child("personalMessage").child(activatedPID).child("messages").child(activeMID).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    }