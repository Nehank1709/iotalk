package in.codepredators.delta.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.codepredators.delta.DatabaseHelper.DatabaseHelperPersonDetail;
import in.codepredators.delta.Classes.Group;
import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.Classes.PersonalMessage;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterChatList;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.Fragment.ArchiveFragment;
import in.codepredators.delta.Fragment.ChatFragment;
import in.codepredators.delta.Fragment.CodeFragment;
import in.codepredators.delta.R;

/**
 * {@link #chatListObject()}                                       Sets and returns an object of class ChatListItem
 * {@link #selectedChat(ChatListItem, int)}                        Handles the clicking and selecting of any chat
 * {@link #sendNotificationToUser(String, String)}                 Sends notification to user that a message has been received
 */

public class ChatList extends AppCompatActivity {


    ImageView backArrow;
    static  TextView noOfChatsSelected;
    ImageView pinChat;
    ImageView muteChat;
    ImageView archiveChat;
    ImageView searchIcon;
    EditText searchEditText ;
    ImageView menuOptionCaller;
    ImageView menuSelectedOptionCaller;
    TextView iotalkText;
    ViewPager viewPager;
    TabLayout tabs;
    public static String UID = "abcd";
    RecyclerView chatListRecycler;
    LinearLayoutManager layoutManager;
    RecyclerAdapterChatList recyclerAdapterChatList;
    List<ChatListItem> chatList;
    DatabaseHelperPersonDetail databaseHelperPersonDetail;
    FloatingActionButton floatingActionButton;
    public static LinearLayout linearLayout;
    public static ConstraintLayout linearLayout2 ;
    ChatListPagerAdapter chatListPagerAdapter;

    String testNames[] = {
            "Raj Kothari",
            "Kumar Harsh",
            "Parth Sharma",
            "Garima Singh",
            "Anushka Chandel",
            "Rakshita Jain",
            "Raj Kothari 2",
            "Kumar Harsh 2",
            "Parth Sharma 2",
            "Garima Singh 2"
    };
    @TargetApi(25)
    private void createShorcut() {
        ShortcutManager sM = getSystemService(ShortcutManager.class);
        Log.i("shortcut","work in progress");
        Intent intent1 = new Intent(getApplicationContext(), Chat.class);
        intent1.setAction(Intent.ACTION_VIEW);

        ShortcutInfo shortcut1 = new ShortcutInfo.Builder(this, "shortcut1")
                .setIntent(intent1)
                .setShortLabel("Raj")
                .setLongLabel("Shortcut 1")
                .setShortLabel("This is the shortcut 1")
                .setDisabledMessage("Login to open this")
                .setIcon(Icon.createWithResource(this, R.drawable.starstarredmessagesicon))
                .build();

        sM.setDynamicShortcuts(Arrays.asList(shortcut1));
        Log.i("shortcut","work completed" );

    }
    @TargetApi(25)
    private void removeShorcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.disableShortcuts(Arrays.asList("shortcut1"));
        shortcutManager.removeAllDynamicShortcuts();
    }
    @Override
    public void onBackPressed()
    {
        if(searchEditText.getVisibility()==View.VISIBLE){
            searchEditText.setText("");
            createSearchBox(1);
        }else{
            super.onBackPressed();
        }
    }

    FragmentManager fm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iotalkactivity_chatscreen);

//      showChatList();

        floatingActionButton = findViewById(R.id.floatingActionNewChat);
        backArrow = findViewById(R.id.imageViewBackButton);

        noOfChatsSelected= findViewById(R.id.noOfItemsSelected);
        pinChat= findViewById(R.id.pushPinIconImageView);
        muteChat= findViewById(R.id.muteIconImageView);
        archiveChat= findViewById(R.id.archiveIconImageView);
        searchIcon = findViewById(R.id.viewSearchChatScreen);
        searchEditText = findViewById(R.id.editTextSearchChatScreen);
        iotalkText = findViewById(R.id.textViewIOTalkChatScreen);
        viewPager = findViewById(R.id.viewpagerChatList);
        linearLayout = findViewById(R.id.selectedChatScreenActionBar);
        linearLayout2 = findViewById(R.id.linearLayout3ChatScreen);//android tto androidx in layout to solve error
        tabs = findViewById(R.id.tabs);
        menuSelectedOptionCaller = findViewById(R.id.settingsIconImageView);
        menuOptionCaller = findViewById(R.id.viewSettingsChatScreen);
        fm  = getSupportFragmentManager();
        chatListPagerAdapter = new ChatListPagerAdapter(fm);
        viewPager.setAdapter(chatListPagerAdapter);
//        tabs.setupWithViewPager(viewPager);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sendmessageClicked","done") ;
                Log.i("abcdefg","ChatLine312");
                PersonalMessage  personalMessage = new PersonalMessage();
                // personalMessage.setPID(getPID(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),user2.getUserNumber()));
                personalMessage.setPID("PID"+"8318711858"+"6205572993");
                personalMessage.setPersonalUserOne("MeINhuTy1oYhjiM81QIbzZFhqup1");
                personalMessage.setPersonalUserTwo("8FM1u4ZmRoXwlnOcdBDnATbd8Fw1");
                FirebaseDatabase.getInstance().getReference().child("personalMessage").child(personalMessage.getPID()).setValue(personalMessage);
                FirebaseDatabase.getInstance().getReference().child("users").child("MeINhuTy1oYhjiM81QIbzZFhqup1").
                        child("connectedIds").child(personalMessage.getPID()).child("MIDPID620557299383187118552019071807:15:00 PM").setValue("07:15:00 PM");
            }
        });
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
             chatListPagerAdapter.sendSearchText(0,editable.toString());
            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                createSearchBox(1);
            }
        });
        pinChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        muteChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        archiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSearchBox(0);
            }
        });
        menuOptionCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpMenuHandler(view, MenuStatus.UNSELECTED);
            }
        });
        menuSelectedOptionCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpMenuHandler(view,MenuStatus.SELECTED);
            }
        });
        chatList = new ArrayList<>();
        databaseHelperPersonDetail = new DatabaseHelperPersonDetail(this);
//        for(int i=0;i<10;i++)
//        {
//            chatList.add(chatListObject());
//            databaseHelperPersonDetail.insertPersonalChatDetail(chatList.get(i).getPersonalMessage(),"1:20pm","last message Id");
//            databaseHelperPersonDetail.insertUserDetail(chatList.get(i).getUser(),"string of image converted from bitmap of image","java/c/c++/python");
//        }
//        chatListRecycler = findViewById(R.id.chatlistchatrecycler);
//        layoutManager = new LinearLayoutManager(getApplicationContext());
//        chatListRecycler.setLayoutManager(layoutManager);

        //start

//
//    recyclerAdapterChatList = new RecyclerAdapterChatList(this,chatList);
//        sendNotificationToUser("puf", "Hi there puf!");
//        chatListRecycler.setAdapter(recyclerAdapterChatList);
       //end
        //database codex in above comments
        Toast.makeText(this,"List is completed, now we will update it",Toast.LENGTH_LONG).show();
    }

    public void createSearchBox(int a){
        if(a==0) {
            menuOptionCaller.setVisibility(View.GONE);
            searchIcon.setVisibility(View.GONE);
            iotalkText.setVisibility(View.GONE);
            backArrow.setVisibility(View.VISIBLE);
            searchEditText.setVisibility(View.VISIBLE);
            tabs.setVisibility(View.GONE);
        }
        else{
            menuOptionCaller.setVisibility(View.VISIBLE);
            searchIcon.setVisibility(View.VISIBLE);
            iotalkText.setVisibility(View.VISIBLE);
            backArrow.setVisibility(View.GONE);
            searchEditText.setVisibility(View.GONE);
            tabs.setVisibility(View.VISIBLE);
        }
    }
    public enum MenuStatus{
        SELECTED,UNSELECTED
    }
    public void popUpMenuHandler(View view, MenuStatus menuStatus){
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.show();
        if(menuStatus == MenuStatus.SELECTED){
            popupMenu.getMenu().getItem(0).setVisible(false);
            popupMenu.getMenu().getItem(1).setVisible(false);
            popupMenu.getMenu().getItem(4).setVisible(false);
        }else{
            popupMenu.getMenu().getItem(3).setVisible(false);
            popupMenu.getMenu().getItem(2).setVisible(false);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.newgroup:
                        Intent intent = new Intent(ChatList.this,NewGroupFormation.class);
                        startActivity(intent);
                        break;
                    case R.id.chatshortcut:
                        if (Build.VERSION.SDK_INT >= 25) {
                            Log.i("shortcut", "createShorcut called");
                            createShorcut();
                        } else {
                            removeShorcuts();
                        }
                        Toast.makeText(getApplicationContext(), "shortcut created", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.markunread:
                        break;
                    case R.id.starredmessage:
                        Intent intent2 = new Intent(ChatList.this,StarredMessages.class);
                        startActivity(intent2);
                        break;
                    case R.id.settings:
                        break;
                }
                return false;
            }
        });
    }
    public ChatListItem chatListObject()
    {
        ChatListItem chatListItem = new ChatListItem();
        PersonalMessage personalMessage = new PersonalMessage();
        User user = new User();
        Message message = new Message();
        user.setUID("1234");
        user.setUserName("Raj kothari");
        user.setUserNumber("9876543210");
        message.setMessageType("10000");
        message.setMessageText("i am here");
        message.setMessageTime("1:20");
        personalMessage.setPersonalUserOne("raj");
        personalMessage.setPersonalUserTwo("Raj");
        personalMessage.setPID("rajandRaj");
        chatListItem.setArchive(false);
        chatListItem.setPinStatus(false);
        chatListItem.setMessage(message);
        chatListItem.setUser(user);
        chatListItem.setPersonalMessage(personalMessage);
        return chatListItem;
    }
    public static void sendNotificationToUser(String user, final String message) {
    }

    public enum ChatListItemType{
        PERSONAL,GROUP
    }
    public static class ChatListItem
    {
        private User user;
        private Message message;
        private PersonalMessage personalMessage;
        private String noOfUnseenMessage;
        private Boolean pinStatus;
        private Boolean archive;
        ChatListItemType chatListItemType;
        private Group group;

        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public ChatListItemType getChatListItemType() {
            return chatListItemType;
        }

        public void setChatListItemType(ChatListItemType chatListItemType) {
            this.chatListItemType = chatListItemType;
        }

        public Boolean getArchive() {
            return archive;
        }

        public void setArchive(Boolean archive) {
            this.archive = archive;
        }

        public PersonalMessage getPersonalMessage() {
            return personalMessage;
        }

        public void setPersonalMessage(PersonalMessage personalMessage) {
            this.personalMessage = personalMessage;
        }
        public String getNoOfUnseenMessage() {
            return noOfUnseenMessage;
        }

        public void setNoOfUnseenMessage(String noOfUnseenMessage) {
            this.noOfUnseenMessage = noOfUnseenMessage;
        }

        public Boolean getPinStatus() {
            return pinStatus;
        }

        public void setPinStatus(Boolean pinStatus) {
            this.pinStatus = pinStatus;
        }

        public ChatListItem() {
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }
    class ChatListPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;
        ChatFragment cf;
        ArchiveFragment af;
        CodeFragment cof;
        public ChatListPagerAdapter(FragmentManager fm) {
            super(fm);
            cf = new ChatFragment();
            af = new ArchiveFragment();
            cof = new CodeFragment();
            fragments = new ArrayList<>();
            fragments.add(0,cf);
            fragments.add(1,af);
            fragments.add(2,cof);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return 3;
        }
        public void sendSearchText(int index,String searchText){
            if(index == 0){
                cf.searchList(searchText);
            }else{
                if(index == 1){
                    af.searchList(searchText);
                }else{
                    cof.searchList(searchText);
                }
            }
        }
    }

    static List<ChatListItem> selectedChatList = new ArrayList<>();
    static int selectedChatCount = 0;

    public static void selectedChat(ChatListItem m, int a) {
        selectedChatCount = a;
        if (selectedChatCount == 1) {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
            noOfChatsSelected.setText(String.valueOf(selectedChatCount));
        }
        if (selectedChatCount == 0) {
            linearLayout.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            noOfChatsSelected.setText(String.valueOf(selectedChatCount));
        }

        if (selectedChatList.size() > selectedChatCount) {
            selectedChatList.remove(m);
        } else {
            selectedChatList.add(m);
        }
            noOfChatsSelected.setText(String.valueOf(selectedChatCount));
        Log.i("selectedresult",m.getPersonalMessage().getPID());

    }
}
//    private void seeMessages()
//    {
//        mChatMessagesDb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if (dataSnapshot.exists()) {
//                    String messageText = "",
//                            messagesenderUID = "";
//                    if (dataSnapshot.child(messages.getMID()).child("messageText").getValue() != null)
//                        messageText = dataSnapshot.child(messages.getMID()).child("messageText").getValue().toString();
//                    if (dataSnapshot.child(messages.getMID()).child("messagesenderUID").getValue() != null)
//                        messagesenderUID = dataSnapshot.child(messages.getMID()).child("messagesenderUID").getValue().toString();
//
//                }
//
//            }

//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @androidx.annotation.Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


