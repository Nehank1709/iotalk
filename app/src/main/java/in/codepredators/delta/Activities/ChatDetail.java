package in.codepredators.delta.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterDocumentList;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterGroupParticipants;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;

public class ChatDetail extends AppCompatActivity {

    RecyclerAdapterDocumentList recyclerAdapterDocumentList;
    RecyclerAdapterDocumentList recyclerAdapterCodeList;
    RecyclerAdapterDocumentList recyclerAdapterMediaList;
    RecyclerAdapterDocumentList recyclerAdapterLinksList;
    List<Message> messageList;

    //nidhi
    LinearLayout groupDescription;
    LinearLayout groupParticipants;
    LinearLayout reportContact;
    LinearLayout block;
    LinearLayout exitGroup;
    LinearLayout bioAndPhoneNumber;
    LinearLayout linearLayoutMedia;
    LinearLayout linearLayoutCode;
    LinearLayout linearLayoutDocuments;
    LinearLayout linearLayoutLinks;
    RecyclerView recyclerViewMedia;
    RecyclerView recyclerViewCode;
    RecyclerView recyclerViewDocuments;
    RecyclerView recyclerViewLinks;
    RecyclerView recyclerViewGroupMembers;
    RecyclerAdapterGroupParticipants recyclerAdapterGroupParticipants;
    ImageView imageViewArrow0;
    ImageView imageViewArrow1;
    ImageView imageViewArrow2;
    ImageView imageViewArrow3;
    ImageView imageViewDown;
    List<User> userList;
    //nidhi

        @Override
        protected void onCreate (@Nullable Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.iotalkactivity_chatdetails);
            recyclerViewDocuments = findViewById(R.id.recyclerDocuments);
            recyclerViewCode = findViewById(R.id.recyclerCode);
            recyclerViewMedia = findViewById(R.id.recyclerViewMedia);
            recyclerViewLinks = findViewById(R.id.recyclerLinks);

            messageList=new ArrayList<>();

            Message message1=new Message();
            message1.setMessageText("Hello");
            messageList.add(message1);

            Message message2= new Message();
            message2.setMessageTime("4:00");
            messageList.add(message2);

            Message message3=new Message();
            message3.setMessageText("Hi");
            messageList.add(message3);

            Message message4=new Message();
            message4.setMessageTime("5:00");
            messageList.add(message4);

            Message message5=new Message();
            message5.setMessageText("Name");
            messageList.add(message5);

            Message message6=new Message();
            message6.setMessageTime("6:00");
            messageList.add(message6);

            Message message7=new Message();
            message7.setMessageText("roll");
            messageList.add(message7);

            Message message8=new Message();
            message8.setMessageTime("7:00");
            messageList.add(message8);

            Message message9=new Message();
            message9.setMessageText("class");
            messageList.add(message9);

            Message message10=new Message();
            message10.setMessageTime("8:00");
            messageList.add(message10);






            LinearLayoutManager mlayoutManager1 = new LinearLayoutManager(getApplicationContext());
            LinearLayoutManager mlayoutManager2 = new LinearLayoutManager(getApplicationContext());
            LinearLayoutManager mlayoutManager3 = new LinearLayoutManager(getApplicationContext());
            GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

            recyclerViewDocuments.setLayoutManager(mlayoutManager1);
            recyclerViewLinks.setLayoutManager(mlayoutManager2);
            recyclerViewCode.setLayoutManager(mlayoutManager3);
            recyclerViewMedia.setLayoutManager(manager);
            recyclerAdapterDocumentList = new RecyclerAdapterDocumentList(getApplicationContext(), "document", messageList);
Log.i("sizeoflist", String.valueOf(messageList.size()));
            recyclerAdapterMediaList = new RecyclerAdapterDocumentList(getApplicationContext(), "media",messageList);
            recyclerAdapterCodeList = new RecyclerAdapterDocumentList(getApplicationContext(), "code",messageList);
            recyclerAdapterLinksList = new RecyclerAdapterDocumentList(getApplicationContext(), "links",messageList);
            recyclerViewCode.setAdapter(recyclerAdapterCodeList);
            recyclerViewMedia.setAdapter(recyclerAdapterMediaList);
            recyclerViewLinks.setAdapter(recyclerAdapterLinksList);
            recyclerViewDocuments.setAdapter(recyclerAdapterDocumentList);

//
            //nidhi
            groupDescription = findViewById(R.id.linearLayoutGroupDescription);
            groupParticipants = findViewById(R.id.linearLayoutGroupParticipants);
            reportContact = findViewById(R.id.linearLayoutReportContact);
            block = findViewById(R.id.linearLayoutBlock);
            exitGroup = findViewById(R.id.linearLayoutExitGroup);
            bioAndPhoneNumber = findViewById(R.id.linearLayoutBioAndPhoneNumber);
            linearLayoutMedia = findViewById(R.id.linearLayoutMedia);
            linearLayoutCode = findViewById(R.id.linearLayoutCode);
            linearLayoutDocuments = findViewById(R.id.linearLayoutDocuments);
            linearLayoutLinks = findViewById(R.id.linearLayoutLinks);

            recyclerViewMedia = findViewById(R.id.recyclerViewMedia);
            recyclerViewCode = findViewById(R.id.recyclerCode);
            recyclerViewDocuments = findViewById(R.id.recyclerDocuments);
            recyclerViewLinks = findViewById(R.id.recyclerLinks);
            imageViewArrow0 = findViewById(R.id.imageViewArrow0);
            imageViewArrow1 = findViewById(R.id.imageViewArrow1);
            imageViewArrow2 = findViewById(R.id.imageViewArrow2);
            imageViewArrow3 = findViewById(R.id.imageViewArrow3);
            imageViewDown = findViewById(R.id.imageViewDown);
            recyclerViewGroupMembers = findViewById(R.id.recyclerViewGroupParticipants);


            userList = new ArrayList<>();


            User user1 = new User();
            user1.setUserName("Abcgfxghd");
            userList.add(user1);

            User user2 = new User();
            user2.setUserName("Abhgygfvtycd");
            userList.add(user2);

            User user3 = new User();
            user3.setUserName("Abcdftyfrhg");
            userList.add(user3);

            User user4 = new User();
            user4.setUserName("Abcygujjhyd");
            userList.add(user4);

            User user5 = new User();
            user5.setUserName("Abcd");
            userList.add(user5);

            User user6 = new User();
            user6.setUserName("Abcfvhtythd");
            userList.add(user6);

            User user7 = new User();
            user7.setUserName("Abcgbjugyud");
            userList.add(user7);

            User user8 = new User();
            user8.setUserName("Abcdtfyt");
            userList.add(user8);

            User user9 = new User();
            user9.setUserName("Abcdcyjcy");
            userList.add(user9);

            User user10 = new User();
            user10.setUserName("ytftbcd");
            userList.add(user10);

            User user11 = new User();
            user11.setUserName("Abcdef");
            userList.add(user11);

            LinearLayoutManager layoutManager = new LinearLayoutManager((getApplicationContext()));

            recyclerViewGroupMembers.setLayoutManager(layoutManager);
            recyclerAdapterGroupParticipants = new RecyclerAdapterGroupParticipants(getApplicationContext(), userList);
            recyclerViewGroupMembers.setAdapter(recyclerAdapterGroupParticipants);


            //arrowImage=findViewById(R.id.imageViewArrow3);
            //arrowImage.setRotationX(180);
            //changeState(1);
            linearLayoutMedia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewMedia.getVisibility()==View.GONE){
                        changeAppearance(R.id.recyclerViewMedia,imageViewArrow0);
                    }
                }});

            linearLayoutCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewCode.getVisibility()==View.GONE){
                        changeAppearance(R.id.recyclerCode,imageViewArrow1);
                    }
                }
            });

            linearLayoutDocuments.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(recyclerViewDocuments.getVisibility()==View.GONE){
                        changeAppearance(R.id.recyclerDocuments,imageViewArrow2);
                    }
                }});

            linearLayoutLinks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewLinks.getVisibility()==View.GONE){
                        changeAppearance(R.id.recyclerLinks,imageViewArrow3);
                    }
                }
            });

            imageViewArrow0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeAppearance(R.id.recyclerViewMedia,imageViewArrow0);
                }
            });

            imageViewArrow1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeAppearance(R.id.recyclerCode,imageViewArrow1);
                }
            });

            imageViewArrow2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeAppearance(R.id.recyclerDocuments,imageViewArrow2);
                }
            });

            imageViewArrow3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeAppearance(R.id.recyclerLinks,imageViewArrow3);
                }
            });

            imageViewDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            //nidhi



        }

        //nidhi
        public void changeAppearance(int recycler,ImageView arrow){
            if(findViewById(recycler).getVisibility()==View.VISIBLE){
                findViewById(recycler).setVisibility(View.GONE);
            }else{
                findViewById(recycler).setVisibility(View.VISIBLE);
            }
            Log.i("arrow","rotating");
            arrow.animate().rotationXBy(180).setDuration(100);

        }
        public void changeState(int a){
            if(a==1){
                //visible for personal detail
                //gone for group detail

                groupDescription.setVisibility(View.GONE);
                groupParticipants.setVisibility(View.GONE);
                reportContact.setVisibility(View.GONE);
                block.setVisibility(View.GONE);
                reportContact.setVisibility(View.GONE);
            }else{
                bioAndPhoneNumber.setVisibility(View.GONE);
                //visible for group detail
                //gone for personal detail
            }
        }
    //nidhi

    }