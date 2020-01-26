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
    private List<Message> lastMessages;


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
    @Override
    public void onStart() {
        super.onStart();
        chatListItemList = new ArrayList<>();
        lastMessages = new ArrayList<>();
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
        user.setUserName("Anushka");
        PersonalMessage pc = new PersonalMessage();
        pc.setPID("ioio");
        message.setMessageTime("12:00");
        chatListItem.setPersonalMessage(pc);
        chatListItem.setUser(user);
        chatListItem.setMessage(message);
        chatListItemList.add(chatListItem);
        recyclerView = getView().findViewById(R.id.viewpagerChatRecycler);


        Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.swing_up_left);
        recyclerView.startAnimation(animation);

        LinearLayoutManager mlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerAdapterChatList = new RecyclerAdapterChatList(getActivity().getApplicationContext() , chatListItemList);
        recyclerView.setAdapter(recyclerAdapterChatList);
        Log.i("value of recycler", String.valueOf(recyclerAdapterChatList.getItemCount()));
        chatListItem = new ChatList.ChatListItem();
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users").child(UID).child("connectedIds");
        chatListItem = new ChatList.ChatListItem();
        personalMessage = new PersonalMessage();
        ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    personalMessage.setPID(dataSnapshot.getKey());
                    if(dataSnapshot.getChildrenCount() > 0) {
                        final Iterator<DataSnapshot> activatedMessages = dataSnapshot.getChildren().iterator();
                        FirebaseDatabase.getInstance().getReference().child("personalMessage").child(personalMessage.getPID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                user = new User();
                                personalMessage.setPersonalUserOne(dataSnapshot.child("personalUserOne").getValue(String.class));
                                personalMessage.setPersonalUserTwo(dataSnapshot.child("personalUserTwo").getValue(String.class));
                                chatListItem.setPersonalMessage(personalMessage);
                                chatListItem.setArchive(false);
                                chatListItem.setPinStatus(false);
                                DatabaseReference userRef;
                                if(personalMessage.getPersonalUserOne().equals(UID)){
                                    userRef = FirebaseDatabase.getInstance().getReference().child("users").child(personalMessage.getPersonalUserTwo());
                                }else{
                                    userRef = FirebaseDatabase.getInstance().getReference().child("users").child(personalMessage.getPersonalUserOne());
                                }
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        user.setUserProfilePic(dataSnapshot.child("userProfilePic").getValue(String.class));
                                        user.setUserName(dataSnapshot.child("userName").getValue(String.class));
                                        user.setUserNumber(dataSnapshot.child("userNumber").getValue(String.class));
                                        user.setUserBio(dataSnapshot.child("userBio").getValue(String.class));
                                        user.setUID(dataSnapshot.child("uid").getValue(String.class));
                                        Message m = new Message();
                                        m.setMessageText("raj kothari");
                                        m.setMessageType("10000");
                                        m.setMessageTime("12/12/2020");
                                        int i =0;
                                        while(activatedMessages.hasNext()){
                                            i++;
                                            DataSnapshot activeMessage = activatedMessages.next();
                                            String activeMID =  activeMessage.getKey();
                                            Log.i("activatedmessageId",activeMID);
                                            String activeMessageTime = activeMessage.getValue(String.class);
                                            m.setMessageTime(activeMessageTime);
                                            if(!newMessages.contains(m))
                                                newMessages.add(m);
//                                            FirebaseDatabase.getInstance().getReference().child("personalMessage").child(activatedPID).child("messages").child(activeMID).addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            }
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                            }
//                                            });
                                        }
                                        try{
                                            Log.i("trycheck",personalMessage.getPID());
                                        }catch (Exception e){
                                            Log.i("trycheck","error");
                                        }
                                        chatListItem.setNoOfUnseenMessage(String.valueOf(i));
                                        chatListItem.setMessage(m);
                                        chatListItem.setUser(user);
                                        personalMessage.setPID(m.getMID().substring(3,26));
                                        chatListItem.setPersonalMessage(personalMessage);
                                        //chatListItemList.add(chatListItem);
                                        for(ChatList.ChatListItem c : chatListItemList) {
                                                Log.i("checking",c.getPersonalMessage().getPID());
                                                if (c.getPersonalMessage().getPID() != null && c.getPersonalMessage().getPID().equals(chatListItem.getPersonalMessage().getPID())) {
                                                    chatListItemList.set(chatListItemList.indexOf(c), chatListItem);
                                                    databaseHelperPersonDetail.updatePersonalChatDetails(chatListItem.getUser(),chatListItem.getPersonalMessage(),
                                                            chatListItem.getMessage().getMID(),chatListItem.getNoOfUnseenMessage());
                                                    break;
                                                }
                                                if (chatListItemList.indexOf(c) == chatListItemList.size() - 1) {
                                                    chatListItemList.add(chatListItem);
                                                    databaseHelperPersonDetail.insertPersonalChatDetail(chatListItem.getPersonalMessage(),
                                                            chatListItem.getMessage().getMessageTime(),chatListItem.getMessage().getMID(),chatListItem.getNoOfUnseenMessage(),
                                                            chatListItem.getUser());
                                                    databaseHelperMessage.insertMessage(m,"false","complete",
                                                            null,m.getMID().substring(3,26));

                                                }
                                        }
                                        recyclerAdapterChatList.notifyDataSetChanged();
                                        chatListItem = new ChatList.ChatListItem();
                                        personalMessage = new PersonalMessage();
                                        user = new User();
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
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.i("valuechanged","ok");

                    for(ChatList.ChatListItem c : chatListItemList){
                        if(c.getPersonalMessage().getPID().equals(dataSnapshot.getKey())){
                            final int index = chatListItemList.indexOf(c);
                            chatListItemList.get(index).setNoOfUnseenMessage(String.valueOf(dataSnapshot.getChildrenCount()));

                            Iterator<DataSnapshot> activatedMessages = dataSnapshot.getChildren().iterator();
                            int i=0;
                            while (activatedMessages.hasNext()){
                                i++;
                                Message m = new Message();
                                DataSnapshot activeMessage = activatedMessages.next();
                                String activeMID =  activeMessage.getKey();
                                m.setMID(activeMID);
                                Log.i("activatedmessageId",activeMID);
                                String activeMessageTime = activeMessage.getValue(String.class);
                                m.setMessageTime(activeMessageTime);
                                Log.i("value","inside while");
                                if(!newMessages.contains(m)){
                                    databaseHelperMessage.insertMessage(m,"false","complete",
                                            null,m.getMID().substring(3,26));
                                }
                                if(i==Integer.parseInt(chatListItemList.get(index).getNoOfUnseenMessage())){
                                    chatListItemList.get(index).getMessage().setMessageTime(activeMessage.getValue(String.class));
                                    Log.i("value",activeMessage.getValue(String.class));
                                    Log.i("value","inside if");
                                    break;
                                }
                            }

//                            Query lastNode = FirebaseDatabase.getInstance().getReference().child("users").child(UID)
//                                    .child("connectedIds").child(c.getPersonalMessage().getPID()).orderByKey().limitToLast(1);
//                            lastNode.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    chatListItemList.get(index).getMessage().setMessageTime(dataSnapshot.getValue(String.class));
//                                    //recyclerAdapterChatList.notifyDataSetChanged();
//                                    Log.i("Messageforchange",dataSnapshot.getValue(String.class));
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
                            recyclerAdapterChatList.notifyDataSetChanged();
                            break;

                        }
                    }
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
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            final DatabaseReference ref = database.getReference().child("personalMessage");
//            Log.i("ref attached", "now we can hear it");
//            ref.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    chatListItem = new ChatList.ChatListItem();
//                    DataSnapshot rr = dataSnapshot.child("pid");
//                    String value = dataSnapshot.child("pid").getValue(String.class);
//                    long msgNo = dataSnapshot.child("messages").getChildrenCount();
////              for(DataSnapshot child : dataSnapshot.getChildren()) {
//                    String pID[] = new String[2];
//                    pID[0] = value.substring(3, 13);
//                    pID[1] = value.substring(13, 23);
//                    Log.i("checkingUserNumber",authUser[0].getUserNumber());
//                    Log.i("checkingUserNumber",pID[0]);
//                    Log.i("checkingUserNumber",pID[1]);
//                    if (pID[0].equals(authUser[0].getUserNumber()) || pID[1].equals(authUser[0].getUserNumber())) {
//                        final User[] user = {new User()};
//                        PersonalMessage personalMessage = new PersonalMessage();
//                        chatListItem.setArchive(false);
//                        chatListItem.setPinStatus(false);
//                        personalMessage.setPID(value);
//                        String userOneID = dataSnapshot.child("personalUserOne").getValue(String.class);
//                        String userTwoID = dataSnapshot.child("personalUserTwo").getValue(String.class);
//                        personalMessage.setPersonalUserOne(userOneID);
//                        personalMessage.setPersonalUserTwo(userTwoID);
////                         chatListItem.personalMessage.setPersonalUserOneStatus();
////                         chatListItem.personalMessage.setPersonalUserTwoStatus();
//                        chatListItem.setPersonalMessage(personalMessage);
//                        String otherUid;
//                        if (userOneID.equals(UID)) {
//                            otherUid = userTwoID;
//                        } else {
//                            otherUid = userOneID;
//                        }
//                        Log.i("tttt",userOneID);
//                        Log.i("tttt",userTwoID);
//
//                        FirebaseDatabase.getInstance().getReference().child("users").child(otherUid)
//                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                User user = new User();
//                                user.setUserName(dataSnapshot.child("userName").getValue(String.class));
//                                user.setUserNumber(dataSnapshot.child("userNumber").getValue(String.class));
//                                user.setUID(dataSnapshot.child("uid").getValue(String.class));
//                                user.setUserBio(dataSnapshot.child("userBio").getValue(String.class));
//                                user.setUserProfilePic(dataSnapshot.child("userProfilePic").getValue(String.class));
//                                Log.i("inside","kk" +user.getUserNumber());
//                                chatListItem.setUser(user);
//                                Message m = new Message();
//                                m.setMessageText("rak kothari");
//                                m.setMessageType("10000");
//                                m.setMessageTime("12/12/2020");
//                                chatListItem.setMessage(m);
//                                chatListItemList.add(chatListItem);
//                                recyclerAdapterChatList.notifyDataSetChanged();
//                                chatListItem = new ChatList.ChatListItem();
////                                user[0].setUserName(dataSnapshot.child("userName").getValue(String.class));
////                                user[0].setUserNumber(dataSnapshot.child("userNumber").getValue(String.class));
////                                user[0].setUserBio(dataSnapshot.child("userBio").getValue(String.class));
////                                user[0].setUserCountry("India");
////                                user[0].setUserProfilePic(dataSnapshot.child("userProfilePic").getValue(String.class));
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
////                    final Query lastChild = FirebaseDatabase.getInstance().getReference().child("personalMessage").child(value).child("messages").orderByKey().limitToLast(1);
////                    lastChild.addListenerForSingleValueEvent(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
////                            Message message = new Message();
////                            message.setMessageTime(dataSnapshot1.child("messageTime").getKey());
////                            chatListItem[0].setMessage(message);
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError1) {
////
////                        }
////                    });
//                    }
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
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
