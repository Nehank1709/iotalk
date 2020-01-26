package in.codepredators.delta.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.codepredators.delta.DatabaseHelper.DatabaseHelperMessage;
import in.codepredators.delta.Classes.Group;
import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.Classes.PersonalMessage;
import in.codepredators.delta.Classes.RealPathUtil;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterChatRecycler;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * {@link #selectedMessage(Message, int)}                                     Handles the selecting and de-selecting of any message
 * {@link #createShorcut()}                                                   Creating chat shortcut
 * {@link #removeShorcuts()}                                                  Removing chat shortcut
 * {@link #reverseSelectedMessage()}                                          Recalling the selected message
 * {@link #setReplyMessage(int)}                                              Sets up the reply to any image, contact or text message
 * {@link #cameraImageBitmap(Bitmap)}
 * {@link #getMID(String)}                                                    Creates and returns message Id
 * {@link #getPID(String, String)}                                            Creates and returns pid
 * {@link #getTime()}                                                         Returns message time
 * {@link #setRecyclerAndFirebase(String, String, String, String, String)}    Sets up the recyler according to the message type
 * {@link #setTextInTextView(String)}
 * {@link #settingFirebase(String, String, String, String, String, Message)}  Commits message on the firebase according to the message type
 * {@link #forwardMessages(List)}
 * {@link #generateChatFile(Context, String, String, String)}
 * {@link #onMenuSearchTap()}                                                 Handles the click on the search icon
 */

public class Chat extends AppCompatActivity {

    String chatType="personal";



    HashMap<String, String> hashmap = new HashMap<>();
    List<Integer> searchedPosition;
    EditText searchText;
    ImageView uparrow;
    ImageView downarrow;
    String RealPath;
    ImageView menuOptionCaller;
    ImageView chatBackground;
    ImageView chatCode;
    ImageView chatSettings;
    ImageView testImage;
    StorageReference riversRef;
    ImageView chatU2ProfilePic;
    TextView chatU2Name;
    TextView chatU2Status;
    LinearLayout chatU2Detail;
    ImageView memeImage;
    ImageView selectContent;
    LinearLayout pinIconLayout;
    HashMap<String, String> messageContactList;
    ImageView phoneGallery;
    ImageView phoneCamera;
    ImageView phoneDocument;
    ImageView phoneContact;
    ImageView sendMessage;
    CheckBox codecheckBox;
    EditText messageContent;
    RecyclerView messagesRecycler;
    RecyclerView codeRecycler;
    ConstraintLayout codeListLinearLayout;
    static ConstraintLayout chatActionBar;
    static LinearLayout selectedChatActionBar;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
    String UID;
    FirebaseAuth mAuth;
    RecyclerAdapterChatRecycler recyclerAdapterGroupText1;
    String typeOfMessage;
    String TypeDay = "N";
    String TEXT = "0";
    static String IMAGE = "0";
    String CONTACT = "0";
    String FILE = "0";
    String REPLIED = "0";
    PersonalMessage personalMessage;
    Group group;
    RelativeLayout showReplyMessage;
    View replyMessageView;
    TextView replyMessageSenderName;
    TextView replyMessageType;
    ImageView replyMessageCancel;
    ImageView replyMessagePic;
    RecyclerView.LayoutManager mLayoutManager;
    Message m;
    Message n;
    DatabaseReference messageReference;
    ChildEventListener childEventListener;
    int a = 0;
    int g = 0;
    int RESULT_LOAD_IMAGE = 0;
    int RESULT_LOAD_DOCUMENT = 0;
    int REQUEST_GET_SINGLE_FILE = 1;
    Uri imageUri;
    String timeOfDataSaving;
    TextView selectedMessageNumber;
    ImageView selectedChatStar;
    ImageView deleteSelectedMessage;
    ImageView copySelectedMessage;
    ImageView forwardSelectedMessage;
    int index = 0;//searching purpose
    //ImageView sendingmessageImage;
    List<Message> messageList = new ArrayList<>();
    private StorageReference mStorageRef;
    Bitmap bitmap;

    DatabaseHelperMessage databaseMessage;
    ItemTouchHelper itemTouchHelper;

    static List<Message> selectedMessageList = new ArrayList<>();
    static int selectedMessageCount = 0;

    public static void selectedMessage(Message m, int a) {
        selectedMessageCount = a;
        if (selectedMessageCount == 1) {
            selectedChatActionBar.setVisibility(View.VISIBLE);
            chatActionBar.setVisibility(View.GONE);
        }
        if (selectedMessageCount == 0) {
            selectedChatActionBar.setVisibility(View.GONE);
            chatActionBar.setVisibility(View.VISIBLE);
        }

        if (selectedMessageList.size() > selectedMessageCount) {
            selectedMessageList.remove(m);
        } else {
            selectedMessageList.add(m);
        }
        if (selectedMessageCount > 1) {
            replySelectedMessage.setVisibility(View.GONE);
        } else {
            replySelectedMessage.setVisibility(View.VISIBLE);
        }
    }


//    public void showPdf(String filePath){
//            Log.i("showingPdf", "strat");
//            Uri fileURI = Uri.fromFile(new File(filePath));
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Log.i("showingPdf", "intentcreated");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setDataAndType(fileURI, "application/pdf");
//            Log.i("showingPdf", "intentcreatedforpdf");
//            startActivity(intent);
//    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("abcdefg", "ChatLine144");
        int wallpaper = 0;
        if(RESULT_LOAD_DOCUMENT == 1 || RESULT_LOAD_IMAGE == 1) {
            wallpaper = 1;
            if (RESULT_LOAD_IMAGE == 1) {
                if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
                    //check for api
                    imageUri = data.getData();
                    RealPath = RealPathUtil.getRealPathFromURI_API19(this, imageUri);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        Log.i("unabletogetbitmap", "GoneWrong");
                        e.printStackTrace();
                    }
                    IMAGE = "1";
                    timeOfDataSaving = getTime();
                    riversRef = mStorageRef.child(timeOfDataSaving + ".jpg");

                }
                Log.i("abcdefg", "ChatLine162");
            }

            if (RESULT_LOAD_DOCUMENT == 1) {
                if (requestCode == RESULT_LOAD_DOCUMENT && resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    //check for api
                    RealPath = RealPathUtil.getRealPathFromURI_API19(this, imageUri);
                    FILE = "1";
                    Log.i("pdfselected", "pdfinside");
                    timeOfDataSaving = getTime();
                    riversRef = mStorageRef.child(timeOfDataSaving + ".pdf");
                    m.setMessageFile(timeOfDataSaving + ".pdf");
                }
            }
            Log.i("pdfselected", "pdfoutside");

            Log.i("pdfselected", "callingsetRecyclerAndFirebase");
            setRecyclerAndFirebase(TEXT, IMAGE, CONTACT, FILE, REPLIED);
        }
        if (requestCode == REQUEST_GET_SINGLE_FILE && resultCode == RESULT_OK && wallpaper == 0)
        {
            Log.i("wallpaper","yesinsideif");
            try {
                chatBackground.setVisibility(View.VISIBLE);
                Log.i("wallpaper","yes");
                chatBackground.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
            }
            catch (Exception e)
            {
                Log.i("wallpaper","no");
                Toast.makeText(this,"Wallpaper cancel",Toast.LENGTH_LONG).show();
            }
        }
        wallpaper = 0;
    }
    static ImageView replySelectedMessage;
    @Override
    public void onBackPressed()
    {
        if(searchText.getVisibility() == View.VISIBLE)
        {
            chatU2ProfilePic.setVisibility(View.VISIBLE);
            chatU2Detail.setVisibility(View.VISIBLE);
            menuOptionCaller.setVisibility(View.VISIBLE);
            searchText.setVisibility(View.GONE);
            uparrow.setVisibility(View.GONE);
            downarrow.setVisibility(View.GONE);
            chatCode.setVisibility(View.VISIBLE);
            index =0;
            searchText.setText("");
            messagesRecycler.smoothScrollToPosition(messageList.size() - 1);
            searchedPosition.clear();
            recyclerAdapterGroupText1.cancelSelectedIndex();
            recyclerAdapterGroupText1.notifyDataSetChanged();
        }
        else {
            if(chatTextView.getVisibility() == View.VISIBLE)
            {
                chatTextView.setText("");
                chatTextView.setVisibility(View.GONE);
            }
            super.onBackPressed();
        }
    }
    static TextView chatTextView;
    public static void setTextInTextView(String Text)
    {
        chatTextView.setVisibility(View.VISIBLE);
        chatTextView.setText(Text);
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iotalkactivity_personalchat);
        Log.i("abcdefg","ChatLine190");
        chatTextView = findViewById(R.id.chatTextView);
        chatU2ProfilePic = findViewById(R.id.profilePicChatActivity);
        chatU2Name = findViewById(R.id.textViewContactNameChatActivity);
        chatU2Status = findViewById(R.id.textViewLastSeenGroupMembersChatActivity);
        chatU2Detail = findViewById(R.id.linearLayout3ChatActivity);
        memeImage = findViewById(R.id.imageViewMemesChatActivity);
        selectContent = findViewById(R.id.imageViewPinIconChatActivity);
        messageContent = findViewById(R.id.editTextEnterMessageChatActivity);
        sendMessage = findViewById(R.id.imageViewSendIconChatActivity);
        codecheckBox = findViewById(R.id.checkBoxCodeFileChatActivity);
        messagesRecycler = findViewById(R.id.recyclerViewMessagesChatActivity);
        codeRecycler = findViewById(R.id.recyclerViewChatCodeList);
        codeListLinearLayout = findViewById(R.id.linearLayoutChatCodeList);
        phoneGallery = findViewById(R.id.selectImageGallery);
        phoneCamera = findViewById(R.id.selectCamera);
        phoneDocument = findViewById(R.id.selectDocuments);
        phoneContact = findViewById(R.id.selectContact);
        pinIconLayout = findViewById(R.id.phoneDataSelection);
        chatActionBar = findViewById(R.id.chatActionBar);
        selectedChatActionBar = findViewById(R.id.selectedChatActionBar);
        selectedMessageNumber = findViewById(R.id.selectedMessageNumber);
        selectedChatStar = findViewById(R.id.selectedChatStarred);
        deleteSelectedMessage = findViewById(R.id.deleteSelectedMessage);
        copySelectedMessage = findViewById(R.id.copySelectedMessage);
        replySelectedMessage = findViewById(R.id.backSelectedMessage);
        forwardSelectedMessage = findViewById(R.id.forwardSelectedMessage);
        chatBackground = findViewById(R.id.imageChatBackground);

        showReplyMessage = findViewById(R.id.showReply);
        replyMessageView = findViewById(R.id.replyMessageSenderView);
        replyMessageSenderName = findViewById(R.id.replyMessageSenderName);
        replyMessageType = findViewById(R.id.replyMessageType);
        replyMessageCancel = findViewById(R.id.replyMessageCancel);
        replyMessagePic = findViewById(R.id.replyMessagePic);

        chatCode = findViewById(R.id.imageCodeChatActivity);
        menuOptionCaller = findViewById(R.id.imageSettingsChatActivity);

        searchText = findViewById(R.id.editTextSearchChatScreen);
        uparrow = findViewById(R.id.uparrow);
        downarrow = findViewById(R.id.downarrow);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        sendMessage.setClickable(false);

        m = new Message();
        databaseMessage = new DatabaseHelperMessage(this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesRecycler.setLayoutManager(mLayoutManager);
        List<Message> databaseMessageList = databaseMessage.getAllMessages("all");
        Log.i("databaseMovement","outsideIFcondition");
        hashmap.put("C","Raj");
        hashmap.put("d","Raj");
        hashmap.put("w","Raj");
        hashmap.put("r","Raj");
        hashmap.put("n","Raj");
        // final User user =new User("UIDabcd","Nameabcd","Numberabcd","Bioabcd","India","Profileabcd",hashmap,hashmap,hashmap,hashmap,"userLastseen",hashmap,hashmap,hashmap);
//user2 is opposite partner

        final User user2 = new User();
        user2.setUID("8FM1u4ZmRoXwlnOcdBDnATbd8Fw1");
        user2.setUserLastSeen("Online");
//user2.setUserNumber(FirebaseDatabase.getInstance().getReference().child("users").child(user2.getUID()).child("userNumber").getKey());
        user2.setUserName("User1");
        user2.setUserProfilePic("https://firebasestorage.googleapis.com/v0/b/iotalk-552e3.appspot.com/o/images.jpg?alt=media&token=7d23a8ac-8684-47be-9a79-5d9ebeb4346c");
        user2.setUserNumber("8318711855");
        user2.setUserLanguages(hashmap);
        mAuth = FirebaseAuth.getInstance();
//        UID = mAuth.getCurrentUser().getUid();
        UID ="MeINhuTy1oYhjiM81QIbzZFhqup1";

        chatU2Name.setText(user2.getUserName());
        chatU2Status.setText(user2.getUserLastSeen());
        //she will give uid or gid
        if(databaseMessageList.size() > 0)
        {
            if(!databaseMessageList.get(0).getMID().equals("clear is done"))
            {
                messageList = databaseMessageList;
            }
            Log.i("databaseMovement", "insideIFcondition");

            recyclerAdapterGroupText1 = new RecyclerAdapterChatRecycler(messageList, Chat.this);
            messagesRecycler.setAdapter(recyclerAdapterGroupText1);
            //check that given id is uid or gid
            //check it is present in database or not
            //if it is present than take necesary data from sql and fill in personal message object or gid
            //personal Message object should be saved from sql database

            if(chatType.equals("personal")){
                //this detail is given chat list activity
                personalMessage = new PersonalMessage();
                personalMessage.setPID(getPID("6205572993", user2.getUserNumber()));
                personalMessage.setPersonalUserOne(UID);
                personalMessage.setPersonalUserTwo(user2.getUID());
            }else{
                group = new Group();
                HashMap<String,String> hashmap = new HashMap<>();
                hashmap.put("admin","MeINhuTy1oYhjiM81QIbzZFhqup1");
                hashmap.put("not admin","E9UjEKO8YjNE0geM8PaVUl0XyYa2");
                hashmap.put("not admin","8FM1u4ZmRoXwlnOcdBDnATbd8Fw1");
                group.setGID("GID9998887776Thu May 30 11:11:13 GMT+05:30 2019");
                group.setGroupCreater("MeINhuTy1oYhjiM81QIbzZFhqup1");
                group.setGroupDescription("tytyty");
                group.setGroupFormationTime("Thu May 30 11:11:13 GMT+05:30 2019");
                group.setGroupName("uiui");
                group.setGroupParticipant(hashmap);
                group.setGroupPic("https://firebasestorage.googleapis.com/v0/b/iotalk-552e3.appspot.com/o/images.jpg?alt=media&token=f425c0d9-c522-4178-8f1d-e59003cacd28");

            }
            a = 1;
        }

        Log.i("databaseMovement","outsideIFcondition");
        //sharedPreferences = getSharedPreferences("IOTalk",0);
        //reply message cancel onclick listener
        replyMessageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                replyMessagePic.setVisibility(View.GONE);
                showReplyMessage.setVisibility(View.GONE);
                replyMessageSenderName.setText(null);
                replyMessageType.setText(null);
                replyMessageView.setBackgroundColor(Color.parseColor("#00000000"));
                REPLIED = "0";
            }
        });

        memeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        selectContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("abcdefg","ChatLine253");
                if(g==0)
                {
                    pinIconLayout.setVisibility(View.VISIBLE);
                    g=1;
                }
                else
                {
                    pinIconLayout.setVisibility(View.GONE);
                    g=0;
                }
            }
        });

        phoneGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESULT_LOAD_IMAGE =1;
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });
        phoneDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESULT_LOAD_DOCUMENT = 1;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                Log.i("pdfselected","callingpdf");
                startActivityForResult(intent,RESULT_LOAD_DOCUMENT);
            }
        });
        phoneCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chat.this, IOCamera.class);
                startActivity(intent);
                if(IMAGE.equals("1"))
                {
                    setRecyclerAndFirebase(TEXT,IMAGE,CONTACT,FILE,REPLIED);
                }
            }
        });
        phoneContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageContactList = new HashMap<>();
                messageContactList.put("9876543210","delta");
                messageContactList.put("0123456789","delta2");
                messageContactList.put("1357902468","delta3");
                CONTACT ="1";
                setRecyclerAndFirebase(TEXT, IMAGE, CONTACT, FILE, REPLIED);
            }
        });

        codecheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codecheckBox.isChecked())
                {
                    //make Visible
                }
                else
                {
                    //make Gone
                }
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("searchFromList", "text is changing");
                searchedPosition = new ArrayList<>();
                String searchString = searchText.getText().toString();
                if(searchString.length() > 0) {
                    for (Message searchMessage : messageList) {
                        if (searchMessage.getMessageType().charAt(0) == '1') {
                            if (searchMessage.getMessageText().contains(searchString)) {
                                Log.i("searchFromList", "word is in list");
                                searchedPosition.add(messageList.indexOf(searchMessage));
                            }
                        }
                    }
                    Log.i("searchFromList", "before selected index");

                    recyclerAdapterGroupText1.selectedIndex(searchedPosition, searchText.getText().toString());
                    recyclerAdapterGroupText1.notifyDataSetChanged();
                    Log.i("searchFromList", "after selected index");


                    index = searchedPosition.size() - 1;
                    Log.i("searchFromList", "smooth scroll completed");
                    //color of complete message item
                    //up and down arrow function
                }
                if(searchString.length() == 0)
                    try {
                        searchedPosition.clear();
                        index = 0;
                        recyclerAdapterGroupText1.cancelSelectedIndex();
                        recyclerAdapterGroupText1.notifyDataSetChanged();
                        messagesRecycler.smoothScrollToPosition(messageList.size() - 1);
                    }
                    catch (Exception e)
                    {
                        Log.i("searchFromList", "under catch text = 0");
                    }

            }
        });

        downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("searchFromList","downarrowclicked");
                index = index + 1;
                try
                {
                    messagesRecycler.smoothScrollToPosition(searchedPosition.get(index));
                    Log.i("searchFromList","scroll is completed");
                }
                catch(Exception e)
                {
                    index = index - 1;
                }

            }
        });
        uparrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("searchFromList","up arrow clicked");
                index = index - 1;
                try
                {
                    messagesRecycler.smoothScrollToPosition(searchedPosition.get(index));
                }
                catch (Exception e)
                {
                    index = index + 1;
                }
            }
        });
/// changes needed for group transformation in menu optionCaller
        menuOptionCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Chat.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final Dialog dialogExportDelete = new Dialog(Chat.this);
                        dialogExportDelete.setContentView(R.layout.clearchat_popup);
                        TextView workOfPopup = dialogExportDelete.findViewById(R.id.textViewClearChat);
                        TextView no = dialogExportDelete.findViewById(R.id.textViewCancelClearChat);
                        TextView yes = dialogExportDelete.findViewById(R.id.textViewClearMessages);
                        LinearLayout mediaLayout = dialogExportDelete.findViewById(R.id.linearLayoutMediaChatDelete);
                        switch (item.getItemId()) {
                            case R.id.Search:
                                onMenuSearchTap();
                                return false;
                            case R.id.Export:
                                mediaLayout.setVisibility(View.GONE);
                                workOfPopup.setText("Do you want to export the chat???");
                                yes.setText("Export Chat");
                                dialogExportDelete.show();
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String exportBody = "IOtalk Chat between " + chatU2Name.getText().toString() + " and " + "Raj Kothari";//instead of raj kothari name of user exporting
                                        //in case of gid u have to just give name of group
                                        //String exportBody = "IOtalk Chat in (group name)
                                        for (Message exportMessage : messageList) {
                                            if (exportMessage.getMessageType().charAt(0) == '1') {
                                                if (exportMessage.getMessagesenderUID().equals(UID)) {
                                                    exportBody = exportBody + "\n" + "Raj Kothari : " + exportMessage.getMessageText();
                                                } else {
                                                    exportBody = exportBody + "\n" + chatU2Name.getText().toString() + " : " + exportMessage.getMessageText();
                                                }
                                            } else {
                                                if (exportMessage.getMessagesenderUID().equals(UID)) {
                                                    exportBody = exportBody + "\n" + "Raj Kothari : " + " <Message media is omitted>";
                                                } else {
                                                    exportBody = exportBody + "\n" + chatU2Name.getText().toString() + " : " + " <Message media is omitted>";
                                                }
                                            }
                                        }
                                        generateChatFile(Chat.this, "IOTalk" + chatU2Name.getText().toString() + " and " + "RajKothari", exportBody, "TXT");
                                        FILE = "1";
                                        dialogExportDelete.cancel();
                                        setRecyclerAndFirebase(TEXT, IMAGE, CONTACT, FILE, REPLIED);
                                        //create a file and send it to firebase
                                        //work completed
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogExportDelete.cancel();
                                    }
                                });
                                return false;
                            case R.id.Clear_Chat:
                                final int[] a = {0};
                                mediaLayout.setVisibility(View.VISIBLE);
                                final CheckBox deleteMedia = dialogExportDelete.findViewById(R.id.checkBox1ClearChat);
                                dialogExportDelete.show();
                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (messageList.size() > 0) {
                                            if (deleteMedia.isChecked())
                                                a[0] = 1;
                                            else
                                                a[0] = 0;
                                            dialogExportDelete.cancel();
                                            for (Message message : messageList) {
                                                databaseMessage.deleteDownload(message, a[0]);
                                            }
                                            messageList.clear();
                                            Message o = new Message();
                                            o.setMID("clear is done");
                                            databaseMessage.insertMessage(o, null, null, null, null);
                                            recyclerAdapterGroupText1.notifyDataSetChanged();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "already done", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogExportDelete.cancel();
                                    }
                                });

                                return false;
                            case R.id.Wallpaper:
                                final Dialog dialog = new Dialog(Chat.this);
                                dialog.setContentView(R.layout.iotalkactivity_wallpaper_popup);
                                LinearLayout defaultBackground = dialog.findViewById(R.id.defaultBackground);
                                LinearLayout galleryBackground = dialog.findViewById(R.id.galleryBackground);
                                dialog.show();
                                defaultBackground.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        chatBackground.setVisibility(View.GONE);
                                        dialog.cancel();
                                    }
                                });
                                galleryBackground.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/*");
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
                                        dialog.cancel();
                                    }
                                });
                                return false;
                            case R.id.Add_Shortcut:
                                if (Build.VERSION.SDK_INT >= 25) {
                                    Log.i("shortcut", "createShorcut called");
                                    createShorcut();
                                } else {
                                    removeShorcuts();
                                }
                                Toast.makeText(getApplicationContext(), "shortcut created", Toast.LENGTH_LONG).show();
                                return false;
                            case R.id.Details:
                                return false;
                        }
                        return false;
                    }
                });
                popup.inflate(R.menu.chatmenu);
                popup.show();
            }
        });

        m = new Message();
//        editor = sharedPreferences.edit();
//        m.setMessageImage(sharedPreferences.getString("MessageImage","NOTFOUND"));
        Log.i("databaseMovement","outsideIFcondition");
        if(messageList.size() == 0)
        {
            Log.i("databaseMovement","FirstMessage");
            m.setMessageType(TypeDay);
            messageList.add(m);
            recyclerAdapterGroupText1 = new RecyclerAdapterChatRecycler(messageList,Chat.this);
            messagesRecycler.setAdapter(recyclerAdapterGroupText1);
        }
        Log.i("databaseMovement","outsideIFcondition");
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                //here to set message type

                if (direction == ItemTouchHelper.RIGHT) {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    else
                    {
                        v.vibrate(50);
                    }
                    recyclerAdapterGroupText1.notifyItemChanged(position);
                    Toast.makeText(Chat.this, "inside onSwipe", Toast.LENGTH_SHORT).show();
                    setReplyMessage(position);

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, float dY, int actionState, boolean isCurrentlyActive) {
                try {
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        viewHolder.itemView.setTranslationX(dX /3);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.itemView.setTranslationX(0);
                            }
                        }, 100);

                    }
                    else {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(messagesRecycler);
        m = new Message();
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sendmessageClicked","done") ;
                Log.i("abcdefg","ChatLine312");
                if(a==0)
                {
                    if(chatType.equals("personal")) {
                        personalMessage = new PersonalMessage();
//                        personalMessage.setPersonalUserOne(sharedPreferences.getString("UserOne", "NOTFOUND"));
//                        personalMessage.setPID(sharedPreferences.getString("PID", "NOTFOUND"));
//                        personalMessage.setPersonalUserTwo(sharedPreferences.getString("UserTwo", "NOTFOUND"));

                        // personalMessage.setPID(getPID(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),user2.getUserNumber()));
                        personalMessage.setPID(getPID("6205572993", user2.getUserNumber()));
                        personalMessage.setPersonalUserOne(UID);
                        personalMessage.setPersonalUserTwo(user2.getUID());

//                        editor.putString("UserOne", personalMessage.getPersonalUserOne());
//                        editor.putString("UserTwo", personalMessage.getPersonalUserTwo());
//                        editor.putString("Messages", "messageblock");
//                        editor.commit();
                        FirebaseDatabase.getInstance().getReference().child("personalMessage").child(personalMessage.getPID()).setValue(personalMessage);
//
//
                        //    setRecyclerAndFirebase(TEXT,IMAGE,CONTACT,FILE,REPLIED);
                        Log.i("abcdefg", "ChatLine334");
                    }else{
                        //nothing to do all things are already there

                    }
                    a=1;
                }
                else
                {
                    setRecyclerAndFirebase(TEXT,IMAGE,CONTACT,FILE,REPLIED);
                }

            }
        });

        if(chatType.equals("personal")) {
            messageReference = FirebaseDatabase.getInstance().getReference().child("personalMessage").child(getPID("6205572993", user2.getUserNumber())).child("messages");
        }else{
            messageReference = FirebaseDatabase.getInstance().getReference().child("groups").child(group.getGID()).child("messages");

        }

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                String starredMessage = null,StatusMessage = "NotSend",PGID = "raj",ContactMessage = null;
                Log.i("message is coming", "Yes");
                if(dataSnapshot.child("messagesenderUID").getValue(String.class).equals(user2.getUID()))
                {
                    Log.i("MessageComing","My message");
                }
                else
                {
                    // databaseMessage.deleteDownload("clear is done");for now it is not in use but it is useful
                    final Message z = new Message();
                    z.setMessageType(dataSnapshot.child("messageType").getValue(String.class));
                    z.setMessagesenderUID(dataSnapshot.child("messagesenderUID").getValue(String.class));
                    z.setMID(dataSnapshot.child("mid").getValue(String.class));
                    z.setMessageTime(dataSnapshot.child("messageTime").getValue(String.class));
                    if(z.getMessageType().charAt(1) == '1')
                    {
                        z.setMessageImageBitmap(null);
                        z.setMessageImage(dataSnapshot.child("messageImage").getValue(String.class));
                    }
                    if(z.getMessageType().charAt(0) == '1') {
                        z.setMessageText(dataSnapshot.child("messageText").getValue(String.class));
                    }
                    if(z.getMessageType().charAt(3)=='1')
                    {
                        z.setMessageFileLocalAddress(null);
                        z.setMessageFile(dataSnapshot.child("messageFile").getValue(String.class));
                    }
                    if(z.getMessageType().charAt(2) == '1')
                    {
                        HashMap<String, String> databaseHashmap = new HashMap<>();
                        Iterator<DataSnapshot> items = dataSnapshot.child("messageContact").getChildren().iterator();
                        while(items.hasNext())
                        {
                            DataSnapshot item = items.next();
                            ContactMessage = ContactMessage  + item.getKey() + "~" + (String) item.getValue() + "~";
                            databaseHashmap.put(item.getKey(), (String) item.getValue());
                        }
                        z.setMessageContact(databaseHashmap);
                    }
                    messageList.add(z);
                    Log.i("database","formed");
                    databaseMessage.insertMessage(z,starredMessage,StatusMessage,ContactMessage,PGID);
                    recyclerAdapterGroupText1.notifyDataSetChanged();

                    //  messagesRecycler.smoothScrollToPosition(messagesRecycler.getAdapter().getItemCount());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
        };
        messageReference.addChildEventListener(childEventListener);

//
//        if(a==1) {
//            messageReference.addChildEventListener(childEventListener);
//        }
//        else
//        {
//            messageReference.removeEventListener(childEventListener);
//        }

        messageContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(messageContent.getText().length()>0)
                {
                    sendMessage.setClickable(true);
                    TEXT = "1";
                }
                else
                {
                    TEXT = "0";
                }
            }
        });

        // On click listner of chatActionBar
        selectedChatStar = findViewById(R.id.selectedChatStarred);
        deleteSelectedMessage = findViewById(R.id.deleteSelectedMessage);





        selectedChatStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //database change
                for(Message message : selectedMessageList) {
                    databaseMessage.updateMessageStarredstatus(message.getMID(), "true");
                }
                //recycler changes - change in current list
                for (Message message : selectedMessageList) {
                    messageList.get(messageList.indexOf(message)).setMessageStarredStatus("true");
                }
                reverseSelectedMessage();
            }
        });
        deleteSelectedMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Chat.this);
                dialog.setContentView(R.layout.clearchat_popup);
                TextView no = dialog.findViewById(R.id.textViewCancelClearChat);
                TextView yes = dialog.findViewById(R.id.textViewClearChat);
                final LinearLayout checkBoxLayout = dialog.findViewById(R.id.linearLayoutMediaChatDelete);
                final CheckBox checkBox = dialog.findViewById(R.id.checkBox1ClearChat);
                dialog.show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int a = 0;
                        for(Message message : selectedMessageList)
                        {
                            if(message.getMessageType().charAt(0) != '0')
                            {
                                checkBoxLayout.setVisibility(View.VISIBLE);
                                break;
                            }
                            else
                                checkBoxLayout.setVisibility(View.GONE);
                        }
                        if(checkBox.isChecked())
                            a = 1;
                        else
                            a = 0;
                        for(Message message : selectedMessageList)
                        {
                            databaseMessage.deleteDownload(message,a);
                            messageList.remove(message);
                        }
                        recyclerAdapterGroupText1.notifyDataSetChanged();
                        dialog.cancel();
                        reverseSelectedMessage();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        reverseSelectedMessage();
                    }
                });

            }
        });
        copySelectedMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String copyText = "";
                for(Message message : selectedMessageList)
                {
                    if(message.getMessageType().charAt(0) == '1')
                        copyText = copyText + message.getMessageText() + "\n";
                }
                if(copyText.length()>0) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(copyText, copyText);
                    clipboard.setPrimaryClip(clip);
                }
                reverseSelectedMessage();
            }
        });
        replySelectedMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReplyMessage(messageList.indexOf(selectedMessageList.get(0)));
                reverseSelectedMessage();
            }
        });

        forwardSelectedMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this will change for now we are doing this for checking purpose
                forwardMessages(selectedMessageList);
                reverseSelectedMessage();
            }
        });
    }
    public void reverseSelectedMessage()
    {
        selectedMessageCount = 0;
        recyclerAdapterGroupText1.changeSelectedMessageCount();
        recyclerAdapterGroupText1.notifyDataSetChanged();
        selectedChatActionBar.setVisibility(View.GONE);
        chatActionBar.setVisibility(View.VISIBLE);
        selectedMessageList.clear();
    }
    public void setReplyMessage(int i)
    {
        REPLIED = "R";
        replyMessageSenderName.setText(chatU2Name.getText());
        replyMessageSenderName.setTextColor(Color.parseColor("#1AF0FF"));
        replyMessageView.setBackgroundColor(Color.parseColor("#1AF0FF"));
        if(messageList.get(i).getMessageType().charAt(1) == '1')
        {
            replyMessagePic.setVisibility(View.VISIBLE);
            replyMessagePic.setImageResource(R.drawable.starstarredmessagesicon);
            replyMessageType.setText("Photo");
            REPLIED = REPLIED + "I";
        }
        else
        {
            if( messageList.get(i).getMessageType().charAt(2) == '1')
            {
                replyMessagePic.setVisibility(View.VISIBLE);
                replyMessagePic.setImageResource(R.drawable.starstarredmessagesicon);
                replyMessageType.setText("Contact");
                REPLIED = REPLIED + "C";
            }
            else
            {
                if(messageList.get(i).getMessageType().charAt(3) == '1')
                {
                    int dotPosition = messageList.get(i).getMessageFile().indexOf(".");
                    replyMessagePic.setVisibility(View.VISIBLE);
                    replyMessagePic.setImageResource(R.drawable.starstarredmessagesicon);
                    replyMessageType.setText(messageList.get(i).getMessageFile().substring(dotPosition + 1));
                    REPLIED = REPLIED + "F";
                    if(replyMessageType.getText().length() < 10)
                    {
                        REPLIED = REPLIED + replyMessageType.getText().toString();
                        for(int j = 1; j<=10-replyMessageType.getText().length();j++)
                        {
                            REPLIED = REPLIED + " ";
                        }
                    }
                    else {
                        REPLIED = REPLIED + replyMessageType.getText().toString();
                    }
                }
                else
                {
                    replyMessagePic.setVisibility(View.GONE);
                    REPLIED = REPLIED + "T";
                    if(messageList.get(i).getMessageText().length() > 10) {
                        replyMessageType.setText(messageList.get(i).getMessageText().substring(0, 10));
                        REPLIED = REPLIED + messageList.get(i).getMessageText().substring(0, 10);
                    }
                    else
                    {
                        replyMessageType.setText(messageList.get(i).getMessageText());
                        REPLIED = REPLIED + replyMessageType.getText().toString();
                        for(int j =1; j<=10-messageList.get(i).getMessageText().length();j++)
                        {
                            REPLIED = REPLIED + " ";
                        }
                    }
                }
            }
        }
        showReplyMessage.setVisibility(View.VISIBLE);
        REPLIED = REPLIED + messageList.get(i).getMID() + String.valueOf(i);
        Toast.makeText(this,REPLIED,Toast.LENGTH_SHORT).show();
    }

    public static Bitmap bitmap2;
    public static void cameraImageBitmap(Bitmap b)
    {
        IMAGE = "1";
        bitmap2 = b;
        Log.i("cameraImageBitmap", String.valueOf(b));
    }
    public String getMID(String PID)
    {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        date = date.replaceAll("[-]","");
        String MID;
        MID = "MID" + PID + date + getTime();
        return MID;
    }
    public String getTime()
    {
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
        String  time = simpleDateFormat.format(calander.getTime());
        return time ;
    }
    public String getPID(String U1Number,String U2Number)
    {
        String PID;
        PID = "PID" + U1Number + U2Number;
        return PID;
    }
    public void setRecyclerAndFirebase(String T,String I,String C,String F,String R)
    {
        if(messageList.size()==0) {
            Message clearMessage = new Message();
            clearMessage.setMID("clear is done");
            databaseMessage.deleteDownload(clearMessage, 0);
        }

        String starredMessage = null, StatusMessage = "NotSend", PGID, ContactMessage = null;
        if (chatType.equals("personal")){
            PGID = personalMessage.getPID();
        }else{
            PGID = group.getGID();
        }
        final int a=0;

        if(codecheckBox.isChecked())
        {
            T = "0";
            F = "1";
            generateChatFile(this,"Taken from edit text",messageContent.getText().toString(),"java");//extension from text view of linear layout above edittext
        }

        Log.i("setRecyclerAndFirebase","called") ;
        String Type;
        Type = T + I + C + F + R;

        m.setMessagesenderUID(UID);
        if(T.equals("1"))
        {
            m.setMessageText(messageContent.getText().toString());
        }
        m.setMessageTime(getTime());
        if(chatType.equals("personal")){
            m.setMID(getMID(personalMessage.getPID()));}
        else{
            m.setMID(getMID(group.getGID()));
        }
        if(I.equals("1"))
        {
            m.setMessageImage(timeOfDataSaving + ".jpg");
            m.setMessageImageLocalAddress(RealPath);
            Log.i("findthepath",imageUri.getPath());
            m.setMessageImageBitmap(bitmap);
        }
        if(F.equals("1"))
        {
            Log.i("pdfselected","F.equals(1) downloadUrl");
            m.setMessageFileLocalAddress(RealPath);
            // m.setMessageFile(timeOfDataSaving + ".pdf");this part is done in respectice type of file
        }
        if(C.equals("1"))
        {
            m.setMessageContact(messageContactList);

            for(HashMap.Entry<String,String> entry : messageContactList.entrySet())
            {
                ContactMessage = ContactMessage + entry.getKey() + "~" + entry.getValue() + "~";
            }
        }

        m.setMessageType(Type);
        messageList.add(m);
        Log.i("numberofobjectsinList", String.valueOf(messageList.size()));
        // recyclerAdapterGroupText1.updateMessageListItems(messageList);
        recyclerAdapterGroupText1.notifyDataSetChanged();


        // messagesRecycler.smoothScrollToPosition(messagesRecycler.getAdapter().getItemCount());

        messageContent.setText("");
        sendMessage.setClickable(false);
        IMAGE = "0";
        TEXT = "0";
        CONTACT = "0";
        FILE = "0";

        replyMessagePic.setVisibility(View.GONE);
        showReplyMessage.setVisibility(View.GONE);
        replyMessageSenderName.setText(null);
        replyMessageType.setText(null);
        replyMessageView.setBackgroundColor(Color.parseColor("#00000000"));
        REPLIED = "0";
        TypeDay="N";
        RESULT_LOAD_IMAGE = 0;
        RESULT_LOAD_DOCUMENT = 0;
        Log.i("numberofitems", String.valueOf(recyclerAdapterGroupText1.getItemCount()));
        settingFirebase(T,I,C,F,R,m);
    }
    public void settingFirebase(String T,String I,String C,String F,String R,Message m)
    {
        String starredMessage = null,StatusMessage = "NotSend",PGID,ContactMessage = null;
        if (chatType.equals("personal")){
            PGID = personalMessage.getPID();
        }else{
            PGID = group.getGID();
        }
//        editor.putString("MID",m.getMID());
//        if(T.equals("1")) {
//            editor.putString("MessageText", m.getMessageText());
//        }
//        if(C.equals("1"))
//        {
//            editor.putString("MessageContact","ContactList");
//        }
//        editor.putString("MessageTime",m.getMessageTime());
//        editor.putString("MessagesenderUID",m.getMessagesenderUID());
//        editor.putString("MessageType",m.getMessageType());
//        if(I.equals("1"))
//        {
//            editor.putString("MessageImage",m.getMessageImage());
//        }
//        if(F.equals("1"))
//        {
//            Log.i("pdfselected","F.equals(1)editor");
//            editor.putString("MessageFile",m.getMessageFile());
//        }
//        editor.commit();
        m.setMessageImageBitmap(null);
        m.setMessageFileLocalAddress(null);
        m.setMessageImageLocalAddress(null);

        n = new Message();
        n = m;
        if(I.equals("1") || F.equals("1"))
        {
            Toast.makeText(this,"Uploadingprocessstarted",Toast.LENGTH_SHORT).show();

            riversRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getApplicationContext(),"Image or file is uploaded",Toast.LENGTH_SHORT).show();
                            n.setMessageImageBitmap(null);
                            n.setMessageFileLocalAddress(null);
                            n.setMessageImageLocalAddress(null);
                            if(chatType.equals("personal")) {
                                FirebaseDatabase.getInstance().getReference().child("personalMessage").child(personalMessage.getPID()).child("messages").child(n.getMID()).setValue(n);
                            }else{
                                FirebaseDatabase.getInstance().getReference().child("groups").child(group.getGID()).child("messages").child(n.getMID()).setValue(n);
                            }
                            Toast.makeText(getApplicationContext(),"FirebaseWorkCompleted",Toast.LENGTH_LONG).show();
                            n = new Message();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Chat.this, "image not selected", Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }


        //   FirebaseDatabase.getInstance().getReference().child("personalMessage").child(personalMessage.getPID()).child("messages").setValue(m);
        if(!I.equals("1") && !F.equals("1") && !C.equals("1")) {
            if(chatType.equals("personal")) {
                FirebaseDatabase.getInstance().getReference().child("personalMessage").child(personalMessage.getPID()).child("messages").child(m.getMID()).setValue(m);
            }else{
                FirebaseDatabase.getInstance().getReference().child("groups").child(group.getGID()).child("messages").child(m.getMID()).setValue(m);
            }
        }
        if(C.equals("1"))
        {
            if (chatType.equals("personal")){
                FirebaseDatabase.getInstance().getReference().child("personalMessage").child(personalMessage.getPID()).child("messages").child(m.getMID()).child("messageContact").setValue(messageContactList);
            }else{
                FirebaseDatabase.getInstance().getReference().child("groups").child(group.getGID()).child("messages").child(m.getMID()).child("messageContact").setValue(messageContactList);

            }
        }
        if(I.equals("1"))
        {
            m.setMessageImageLocalAddress( RealPath);
            Log.i("Imageaddress",m.getMessageImageLocalAddress());
            m.setMessageImageBitmap(bitmap);
        }
        if(F.equals("1"))
            m.setMessageFileLocalAddress(RealPath);
        //Database
        databaseMessage.insertMessage(m,starredMessage,StatusMessage,ContactMessage,PGID);
        m = new Message();
    }
    public void informRecieverForMessage(String recieverID,String PID, String MID,String time){
        FirebaseDatabase.getInstance().getReference().child("users").child(recieverID).child("connectedIds").child(PID).child(MID).setValue(time);
    }
    public void forwardMessages(List<Message> forwardList)
    {
        for(Message m : forwardList)
        {
            m.setMessageTime(getTime());
            if(chatType.equals("personal")){
                m.setMID(personalMessage.getPID());
            }
            else{
                m.setMID(group.getGID());
            }
            m.setMessagesenderUID(UID);
            m.setMessageStarredStatus(null);
            if(m.getMessageType().charAt(4) != '0')
            {
                m.setMessageType(m.getMessageType().substring(0,3) + "0");
            }
            String T = String.valueOf(m.getMessageType().charAt(0));
            String I = String.valueOf(m.getMessageType().charAt(1));
            String C = String.valueOf(m.getMessageType().charAt(2));
            String F = String.valueOf(m.getMessageType().charAt(3));
            messageList.add(m);
            recyclerAdapterGroupText1.notifyDataSetChanged();
            settingFirebase(T,I,C,F,"0",m);
        }
    }
    public void generateChatFile(Context context, String sFileName, String sBody, String sExtension) {
        try {
            File root = new File(context.getExternalFilesDir(null), "/IOTalk" + "/PDF");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            imageUri = Uri.fromFile(gpxfile);
            timeOfDataSaving = getTime();
            riversRef = mStorageRef.child(timeOfDataSaving + "." + sExtension);
            m.setMessageFile(timeOfDataSaving + "." + sExtension);
            RealPath = context.getExternalFilesDir(null) + "/IOTalk" + "/PDF" + "/" + m.getMessageFile();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onMenuSearchTap()
    {
        chatU2ProfilePic.setVisibility(View.GONE);
        chatU2Detail.setVisibility(View.GONE);
        menuOptionCaller.setVisibility(View.GONE);
        searchText.setVisibility(View.VISIBLE);
        uparrow.setVisibility(View.VISIBLE);
        downarrow.setVisibility(View.VISIBLE);
        chatCode.setVisibility(View.GONE);
    }
}
// Got the download URL for 'users/me/profile.png'
//                         Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
//                         while (!urlTask.isSuccessful()) ;
//                         Uri Url = urlTask.getResult();
//                         downloadUrl = Url.toString();
//
//                         Toast.makeText(Chat.this, downloadUrl, Toast.LENGTH_SHORT).show();
//                         Log.i("image url", downloadUrl);
//                         m.setMessageImage(downloadUrl);
//                         Log.i("image url by user", m.getMessageImage());
//      editor.putString("GroupPic",newgroup.getGroupPic());
///                          editor.putString("UserProfilePic", downloadUrl);
