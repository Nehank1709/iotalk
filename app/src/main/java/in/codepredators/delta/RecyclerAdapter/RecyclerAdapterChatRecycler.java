package in.codepredators.delta.RecyclerAdapter;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import in.codepredators.delta.Activities.Chat;
import in.codepredators.delta.DatabaseHelper.DatabaseHelperMessage;
import in.codepredators.delta.Classes.GlideApp;
import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.R;


public class RecyclerAdapterChatRecycler extends RecyclerView.Adapter<RecyclerAdapterChatRecycler.ViewHolderText> {

    private List<Message> messageTextList;
    private List<ViewHolderText> viewholderList= new ArrayList<>();
    private Context mContext;
    private ImageView image;
    int v =0;
    int searchHelper = 0;//use to find that search is going on text
       // private int imagesCount = 0;
DatabaseHelperMessage databaseMessage;
    public class ViewHolderText extends RecyclerView.ViewHolder {
         private  TextView backgroundDecider;
        private ConstraintLayout backgroundColor;
        private LinearLayout adjustingMessageGravity;
        private TextView dayText;
        private LinearLayout messageBackground;
        private View senderView;
        private TextView senderName;
        private RelativeLayout replyMessage;//shortform rM
        private View rMSenderView;
        private TextView rMSenderName;
        private TextView rMType;
        private ImageView rMPic;
        private LinearLayout contactMessage; //cM
        private ImageView cMPic;
        private TextView cMText;
        private ImageView messageImage;
        private TextView messageTextContent;
        private TextView messageType;
        private ImageView starredMessageStatusPic;
        private TextView messageTime;
        private ImageView messageStatus;
        private View cMView;
        private TextView addContact;

        //public View selectMessageView;
        public ViewHolderText(@NonNull View itemView) {
            super(itemView);


            adjustingMessageGravity = itemView.findViewById(R.id.messageContainer);
            dayText = itemView.findViewById(R.id.messageDay);
            messageBackground = itemView.findViewById(R.id.messageLinearLayoutContainer);
            senderView = itemView.findViewById(R.id.senderView);
            senderName = itemView.findViewById(R.id.senderName);
            replyMessage = itemView.findViewById(R.id.replyMessage);
            rMSenderView = itemView.findViewById(R.id.replyMessageSenderView);
            rMSenderName = itemView.findViewById(R.id.replyMessageSenderName);
            rMType = itemView.findViewById(R.id.replyMessageType);
            rMPic = itemView.findViewById(R.id.replyMessagePic);
            contactMessage = itemView.findViewById(R.id.contactMessage);
            cMPic = itemView.findViewById(R.id.contactMessagePic);
            cMText = itemView.findViewById(R.id.contactMessageText);
            messageImage = itemView.findViewById(R.id.imageMessage);
            messageTextContent = itemView.findViewById(R.id.messageTextContent);
            messageType = itemView.findViewById(R.id.fileTypeTextView);
            starredMessageStatusPic = itemView.findViewById(R.id.starredMessageStatusPic);
            messageTime = itemView.findViewById(R.id.messageTimeTextView);
            messageStatus = itemView.findViewById(R.id.messageStatusImageView);
            cMView = itemView.findViewById(R.id.contactMessageView);
            addContact = itemView.findViewById(R.id.addContactMessage);
            backgroundColor = itemView.findViewById(R.id.backgroundColor);
            backgroundDecider = itemView.findViewById(R.id.backgroundDecider);
//selectMessageView = itemView.findViewById(R.id.selectedMessageView);
        }
    }

    public RecyclerAdapterChatRecycler(List<Message> messageTextList, Context mContext) {
        this.messageTextList = messageTextList;
        this.mContext = mContext;
    }


//    public void updateMessageListItems(List<Message> employees) {
//        Log.i("numberofitemsinListE", String.valueOf(employees.size()));
//        final MessageDiffCallBack diffCallback = new MessageDiffCallBack(this.messageTextList, employees);
//        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        Log.i("numberofitemsinList", String.valueOf(this.messageTextList.size()));
//        Log.i("numberofitemsinListE", String.valueOf(employees.size()));
//        this.messageTextList.clear();
//        Log.i("numberofitemsinListE", String.valueOf(employees.size()));
//        this.messageTextList.addAll(employees);
//
//        Log.i("numberofitemsinListE", String.valueOf(employees.size()));
//        Log.i("numberofItem", String.valueOf(getItemCount()));
//        diffResult.dispatchUpdatesTo(this);
//    }


    @NonNull
    @Override
    public ViewHolderText onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messagerecyleritem, viewGroup, false);
        return new ViewHolderText(view);
    }

    int a = 0;

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderText viewHolder, final int i) {
        final Message messageInfo = messageTextList.get(i);

        databaseMessage = new DatabaseHelperMessage(mContext);

//                    ColorDrawable viewColor = (ColorDrawable) viewHolder.adjustingMessageGravity.getBackground();
//                    int colorId = viewColor.getColor();
//                    if(colorId == Color.parseColor("#1af0ff"))
//                    {
//                        Log.i("longclicked", String.valueOf(i));
//                        viewHolder.adjustingMessageGravity.setBackgroundColor(Color.parseColor("#00000000"));
//                     }

viewHolder.contactMessage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v)
    {
        if(messageInfo.getMessageType().charAt(3) == '1') {
            Log.i("insideContactOnClick", String.valueOf(i));
//           Chat c = new Chat();
//           c.showPdf(messageTextList.get(i).getMessageFileLocalAdress());
            if(messageInfo.getMessageFile().substring(messageInfo.getMessageFile().indexOf(".") + 1).equals("TXT") || messageInfo.getMessageFile().substring(messageInfo.getMessageFile().indexOf(".") + 1).equals("java"))
            {
                File root = new File(mContext.getExternalFilesDir(null), "/IOTalk" + "/PDF");
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root,messageInfo.getMessageFile());
//                FileWriter writer = null;
//                try {
//                    writer = new FileWriter(gpxfile);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                FileInputStream textData = null;
                try {
                    textData = new FileInputStream(gpxfile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                DataInputStream in = new DataInputStream(textData);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String TextofFile = "";
                String strLine = null;
                try {
                    strLine = br.readLine();
                    while (strLine != null) {
                        TextofFile = TextofFile + strLine + "\n";
                            strLine = br.readLine();
                        }
                    br.close();
                    in.close();
                    textData.close();
                    }
                 catch (IOException e) {
                    e.printStackTrace();
                }
               Chat.setTextInTextView(TextofFile);
                Log.i("textFileFromFirebase",TextofFile);
            }
        }
    }
});
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ColorDrawable viewColor = (ColorDrawable) viewHolder.backgroundColor.getBackground();
                int colorId = viewColor.getColor();
                if (colorId != Color.parseColor("#1af0ff")) {
                    a++;
                  Chat.selectedMessage(messageTextList.get(i),a);
                    viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#1af0ff"));
                }
                //Toast.makeText(getContext(), "long Click" + position+"  ", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a > 0) {
                    ColorDrawable viewColor = (ColorDrawable) viewHolder.backgroundColor.getBackground();
                    int colorId = viewColor.getColor();
                    if (colorId == Color.parseColor("#1af0ff")) {
                        a--;
                        viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#00000000"));
                    } else {
                        a++;
                        viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#1af0ff"));
                    }
                       Chat.selectedMessage(messageTextList.get(i), a);
                }
            }
        });

        viewHolder.addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    for(HashMap.Entry<String,String> entry : messageInfo.getMessageContact().entrySet())
                    {
                        addContact(entry.getKey(),entry.getValue());
                    }

            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT); // or set height to any fixed value you want

        viewHolder.backgroundColor.setLayoutParams(lp);

        Log.i("drawable",String.valueOf(viewHolder.messageBackground.getBackground()));//16842910 - sent msg
        Log.i("drawable",String.valueOf(mContext.getResources().getDrawable(R.drawable.shape_sent_msg)));

        if (a == 0) {
            viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#00000000"));
        }
        if (messageInfo.getMessageType().charAt(0) == 'N') {
            //set message container grivity as center
            Log.i("layoutworking","TypeN");
            viewHolder.adjustingMessageGravity.setGravity(Gravity.CENTER);
            viewHolder.messageBackground.setVisibility(View.GONE);
            viewHolder.messageTextContent.setVisibility(View.GONE);
            viewHolder.messageImage.setVisibility(View.GONE);
            viewHolder.contactMessage.setVisibility(View.GONE);
            viewHolder.dayText.setVisibility(View.VISIBLE);
        } else {
                        if(messageInfo.getMessageStarredStatus() != null &&messageInfo.getMessageStarredStatus().equals("true"))
                        {
                            viewHolder.messageStatus.setImageResource(R.drawable.starstarredmessagesicon);
                        }
                        else {
                            viewHolder.messageStatus.setImageBitmap(null);
                        }

                       if(v == 0)//.equals to user 1 id
                           {
                               //changes for u1 message
                               viewHolder.messageTextContent.setTextColor(Color.parseColor("#ffffff"));
                               viewHolder.messageTime.setTextColor(Color.parseColor("#ffffff"));
                               viewHolder.adjustingMessageGravity.setGravity(Gravity.END);
                               viewHolder.replyMessage.setBackgroundColor(Color.parseColor("#ffffff"));
                               viewHolder.rMSenderView.setBackgroundColor(Color.parseColor("#FFF800"));
                               viewHolder.rMSenderName.setTextColor(Color.parseColor("#FFF900"));
                               viewHolder.cMText.setTextColor(Color.parseColor("#ffffff"));
                               viewHolder.contactMessage.setBackgroundColor(Color.parseColor("#00000000"));
                               if(messageInfo.getMessageType().charAt(3) == '1')
                               {
                                   viewHolder.cMText.setTextColor(Color.parseColor("#1976D2"));
                                   viewHolder.contactMessage.setBackgroundColor(Color.parseColor("#ffffff"));
                                   viewHolder.messageType.setTextColor(Color.parseColor("#ffffff"));
                               }
                               //end
                               if(viewHolder.backgroundDecider.getText().toString().equals("shape_sent_msg") || viewHolder.backgroundDecider.getText().toString().equals("shape_middle_sentmsg"))
                               {
                                   Log.i("layoutworking","middlesent");
                                   viewHolder.messageBackground.setBackgroundResource(R.drawable.shape_middle_sentmsg);
                                   viewHolder.backgroundDecider.setText("shape_middle_sentmsg");
                               }
                               if(viewHolder.backgroundDecider.getText().toString().equals("shape_middle_receivedmsg") || viewHolder.backgroundDecider.getText().toString().equals("shape_received_msg"))
                               {
                                   Log.i("layoutworking","sent");
                                   viewHolder.messageBackground.setBackgroundResource(R.drawable.shape_sent_msg);
                                   viewHolder.backgroundDecider.setText("shape_sent_msg");
                               }
                               v=1;
                           }

                           else
                           {
                               v=0;
                            //Changes for essage
                               viewHolder.messageTextContent.setTextColor(Color.parseColor("#000000"));
                               viewHolder.messageTime.setTextColor(Color.parseColor("#000000"));
                               viewHolder.adjustingMessageGravity.setGravity(Gravity.START);
                               viewHolder.replyMessage.setBackgroundColor(Color.parseColor("#E1F5FE"));
                               viewHolder.rMSenderView.setBackgroundColor(Color.parseColor("#75F5F5"));
                               viewHolder.rMSenderName.setTextColor(Color.parseColor("#75F5F5"));
                               viewHolder.cMText.setTextColor(Color.parseColor("#0E0E0E"));
                               viewHolder.cMView.setBackgroundColor(Color.parseColor("#000000"));
                               viewHolder.addContact.setTextColor(Color.parseColor("#000000"));
                               if(messageInfo.getMessageType().charAt(3) == '1')
                               {
                                   viewHolder.cMText.setTextColor(Color.parseColor("#1976D2"));
                                   viewHolder.contactMessage.setBackgroundColor(Color.parseColor("#E1F5FE"));
                                   viewHolder.messageType.setTextColor(Color.parseColor("#000000"));
                               }
                               //end
                               if(viewHolder.backgroundDecider.getText().toString().equals("shape_middle_receivedmsg") || viewHolder.backgroundDecider.getText().toString().equals("shape_received_msg"))
                               {
                                   Log.i("layoutworking","middlerecieve");
                                   viewHolder.messageBackground.setBackgroundResource(R.drawable.shape_middle_receivedmsg);
                                   viewHolder.backgroundDecider.setText("shape_middle_receivedmsg");
                                   viewHolder.messageTextContent.setTextColor(Color.parseColor("#000000"));
                              }
                               if(viewHolder.backgroundDecider.getText().toString().equals("shape_sent_msg") || viewHolder.backgroundDecider.getText().toString().equals("shape_middle_sentmsg"))
                               {
                                   Log.i("layoutworking","recieve");
                                   viewHolder.backgroundDecider.setText("shape_received_msg");
                                   viewHolder.messageBackground.setBackgroundResource(R.drawable.shape_received_msg);
                               }
                           }
            viewHolder.dayText.setVisibility(View.GONE);
            viewHolder.messageBackground.setVisibility(View.VISIBLE);
            if (messageInfo.getMessageType().charAt(0) == '1')
            {
                viewHolder.messageTextContent.setVisibility(View.VISIBLE);
                if(searchHelper == 1 && searchedString.length()>0 && searchMessageIndexList.size()>0)
                {
                    if(searchMessageIndexList.contains(i))
                    {


                        Log.i("searched",searchedString);
                        SpannableString ss = new SpannableString(messageInfo.getMessageText());
                        BackgroundColorSpan yellow = new BackgroundColorSpan(Color.YELLOW);
                        BackgroundColorSpan transparent = new BackgroundColorSpan(Color.TRANSPARENT);
                        Log.i("searched",searchedString + "1");
                        ss.setSpan(transparent,0,messageInfo.getMessageText().indexOf(searchedString),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        ss.setSpan(yellow,messageInfo.getMessageText().indexOf(searchedString),messageInfo.getMessageText().indexOf(searchedString) + searchedString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ss.setSpan(transparent,messageInfo.getMessageText().indexOf(searchedString) + searchedString.length(),messageInfo.getMessageText().length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        Log.i("searched",searchedString + "2");
                        viewHolder.messageTextContent.setText(ss);
                    }
                    else
                    {
                        viewHolder.messageTextContent.setText(messageInfo.getMessageText());
                    }
                }
                else {
                    viewHolder.messageTextContent.setText(messageInfo.getMessageText());
                }

                if (messageInfo.getMessageType().charAt(1) == '0')
                {
                        viewHolder.messageImage.setVisibility(View.GONE);
                }
                if (messageInfo.getMessageType().charAt(3) == '0')
                {
                    viewHolder.contactMessage.setVisibility(View.GONE);
                    viewHolder.messageType.setVisibility(View.GONE);
                }
                if(messageInfo.getMessageType().charAt(2) == '0')
                {
                    viewHolder.cMView.setVisibility(View.GONE);
                    viewHolder.addContact.setVisibility(View.GONE);
                }
                //charAt(2)
                // is remaining
            }
            else
            {
                viewHolder.messageTextContent.setVisibility(View.GONE);
            }

            if (messageInfo.getMessageType().charAt(1) == '1') {

                viewHolder.messageImage.setVisibility(View.VISIBLE);
//                if (messageInfo.getMessageImageBitmap() == null) //instead of this u have to check in phone storage
//                {
                if (messageInfo.getMessageImageBitmap() == null)
                {
                                     if(messageInfo.getMessageImageLocalAddress() == null) {

                                             StorageReference mStorageRef;
                                             mStorageRef = FirebaseStorage.getInstance().getReference();
                                             StorageReference storageReference = mStorageRef.child(messageInfo.getMessageImage());

                                             Log.i("GlideAppWorking", "Start");
                                             GlideApp.with(mContext)
                                                     .asBitmap()
                                                     .load(storageReference)
                                                     .into(new SimpleTarget<Bitmap>() {
                                                         @Override
                                                         public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                             viewHolder.messageImage.setImageBitmap(resource);
                                                             messageInfo.setMessageImageBitmap(resource);
                                                             messageInfo.setMessageImageLocalAddress(saveImage(resource, messageInfo.getMessageImage()));
                                                             //add the address to sqldatabase
                                                             Log.i("underupdate","goingfordatabaseupdate");
                                                             databaseMessage.updateMessageImageAddress(messageInfo.getMID(),messageInfo.getMessageImageLocalAddress());
                                                         }
                                                     });
                                             //.into(viewHolder.messageImage);
                                             Log.i("GlideAppWorking", "End");
                                     }
                                  else
                                     {
                                         //we have image address so get bitmap from address and put them in view Holder.messageImage.setImageBitmap
                                         String root = messageInfo.getMessageImageLocalAddress();
                                         File file = new File(root,messageInfo.getMessageImage());
                                         Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                         Log.i("insideimageaddress", String.valueOf(bitmap));
                                         messageInfo.setMessageImageBitmap(bitmap);
                                         viewHolder.messageImage.setImageBitmap(bitmap);
                                         Log.i("insideimageaddress","done");

                                     }

                } else {
//                assert  messageInfo.getMessageImageBitmap()!=null : "Null";
//                 Bitmap bitmap = Bitmap.createScaledBitmap(messageInfo.getMessageImageBitmap(),600,600,true);
//                //viewHolder.messageStatus.setImageBitmap(messageInfo.getMessageImageBitmap());
                    viewHolder.messageImage.setImageBitmap(messageInfo.getMessageImageBitmap());
                }
                if (messageInfo.getMessageType().charAt(0) == '0') {
                    viewHolder.messageTextContent.setVisibility(View.GONE);
                }
                if(messageInfo.getMessageType().charAt(2) == '0')
                {
                    viewHolder.cMView.setVisibility(View.GONE);
                    viewHolder.addContact.setVisibility(View.GONE);
                }
                if (messageInfo.getMessageType().charAt(3) == '0') {
                    viewHolder.contactMessage.setVisibility(View.VISIBLE);
                    viewHolder.messageType.setVisibility(View.GONE);
                }
            }
            else
            {
                viewHolder.messageImage.setVisibility(View.GONE);
            }

            if (messageInfo.getMessageType().charAt(3) == '1' || messageInfo.getMessageType().charAt(2) == '1')
            { viewHolder.contactMessage.setVisibility(View.VISIBLE);
                if(messageInfo.getMessageType().charAt(3) == '1')
                {
                    viewHolder.messageType.setVisibility(View.VISIBLE);
                    Log.i("FileDownloading", "insideBefore[null]condition");
                    //check that messageinfo has file address if null than download if contain than get from storage
                    if (messageInfo.getMessageFileLocalAddress() == null) {
                        Log.i("FileDownloading", "inside[null]condition");
                        File myDir = new File(mContext.getExternalFilesDir(null) + "/IOTalk" + "/PDF");
                        myDir.mkdirs();
                        File localfile = new File(myDir, messageInfo.getMessageFile());
                        if(localfile.exists())
                        {
                          Toast.makeText(mContext,"file already exist so it will not be downloaded from firebase",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference storageReference = mStorageRef.child(messageInfo.getMessageFile());
                            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(mContext, "File downloaded", Toast.LENGTH_SHORT).show();
                                    Log.i("task", "complete");
                                    messageInfo.setMessageFileLocalAddress(mContext.getExternalFilesDir(null) + "/IOTalk" + "/PDF" + "/" + messageInfo.getMessageFile());
                                    //set address to sql database
                                    databaseMessage.updateMessageFileAddress(messageInfo.getMID(),messageInfo.getMessageFileLocalAddress());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(mContext,"File Not Downloaded",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } else {
                        Log.i("FileDownloading", "outside[null]condition");
                    }

                    viewHolder.cMPic.setImageResource(R.drawable.starstarredmessagesicon);
                    viewHolder.cMText.setText("Raj Kothari");
                    int dotPosition = messageInfo.getMessageFile().indexOf(".");
                    viewHolder.messageType.setText(messageInfo.getMessageFile().substring(dotPosition + 1));
                    if (messageInfo.getMessageType().charAt(0) == '0') {
                        viewHolder.messageTextContent.setVisibility(View.GONE);
                    }
                }
                if(messageInfo.getMessageType().charAt(2) == '1')
                {
                    viewHolder.messageType.setVisibility(View.GONE);
                    viewHolder.cMPic.setImageResource(R.drawable.starstarredmessagesicon);
                    viewHolder.cMText.setText("Contacts");
                   if( true)
                    {//for now we will show add contact here
                        viewHolder.cMView.setVisibility(View.VISIBLE);
                        viewHolder.addContact.setVisibility(View.VISIBLE);
                    }
                    else
                   {
                       Toast.makeText(mContext,"insideContactElse",Toast.LENGTH_SHORT).show();
                   }
                }

            }
            else
                {
                viewHolder.contactMessage.setVisibility(View.GONE);
                }

            viewHolder.senderName.setText("Raj kothari");
            viewHolder.messageTime.setText(messageInfo.getMessageTime());
                //reply
              if(messageInfo.getMessageType().charAt(4) == 'R')
              {
                  viewHolder.rMSenderName.setText(viewHolder.senderName.getText());
                  viewHolder.rMSenderView.setVisibility(View.VISIBLE);
                   if(messageInfo.getMessageType().charAt(3) == '0')
                   {
                       viewHolder.messageType.setVisibility(View.GONE);
                   }
                  if(messageInfo.getMessageType().charAt(5) == 'I')
                  {
                      viewHolder.rMPic.setVisibility(View.VISIBLE);
                      viewHolder.rMPic.setImageResource(R.drawable.starstarredmessagesicon);
                      viewHolder.rMType.setText("Photo");

                  }
                  else
                  {
                      if(messageInfo.getMessageType().charAt(5) == 'C')
                      {
                          viewHolder.rMPic.setVisibility(View.VISIBLE);
                          viewHolder.rMPic.setImageResource(R.drawable.starstarredmessagesicon);
                          viewHolder.rMType.setText("Contact");
                      }
                      else
                      {
                          if(messageInfo.getMessageType().charAt(5) == 'F')
                          {
                             String type = messageInfo.getMessageType().substring(6,16);
                             Log.i("type",type);
                             int j = 0;
                             for(;j<10;j++) {
                                 if (type.charAt(9 - j) == ' ')
                                 {
                                     if(type.charAt(9 - j -1) != ' ')
                                     {
                                         break;
                                     }
                                 }

                             }

                              viewHolder.rMPic.setVisibility(View.VISIBLE);
                              viewHolder.rMPic.setImageResource(R.drawable.starstarredmessagesicon);
                              viewHolder.rMType.setText(type.substring(0,9 - j));
                          }
                          else
                          {
                              viewHolder.rMPic.setVisibility(View.GONE);
                              viewHolder.rMType.setText(messageInfo.getMessageType().substring(6,15));

                          }
                      }
                  }
                  viewHolder.replyMessage.setVisibility(View.VISIBLE);
              }
              else
              {
                  viewHolder.replyMessage.setVisibility(View.GONE);
              }
                //reply over

        }
            viewholderList.add(viewHolder);

    }

    @SuppressLint("StaticFieldLeak")
    private void addContact(final String number, final String name)
    {
                ArrayList<ContentProviderOperation> ops = new ArrayList<>();
                int rawContactID = ops.size();
                // Adding insert operation to operations list
                // to insert a new raw contact in the table ContactsContract.RawContacts
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());
                // Adding insert operation to operations list
                // to insert display name in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,name)
                        .build());
                // Adding insert operation to operations list
                // to insert Mobile Number in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
                     try
                     {
                         // Executing all the insert operations as a single database transaction
                         mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                         Toast.makeText(mContext,"contact added",Toast.LENGTH_SHORT).show();
                     }
                     catch (RemoteException e)
                     {
                        e.printStackTrace();
                     }
                     catch (OperationApplicationException e)
                     {
                        e.printStackTrace();
                     }

    }
    private String saveImage(Bitmap finalBitmap, String image_name)
    {
        Log.i("savingimage","started");
        String root =  mContext.getExternalFilesDir(null) + "/IOTalk" + "/Images";
        File myDir = new File(root);
        myDir.mkdirs();
        String fname =image_name;
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.i("savingimage","undercatch");
            e.printStackTrace();
        }
        Log.i("savingimage","done");
return root;
    }
     @Override
    public int getItemCount() {
        return messageTextList.size();
    }
    public void changeSelectedMessageCount()
    {
        a = 0;
    }
List<Integer> searchMessageIndexList = new ArrayList<>();
    String searchedString;
    public void selectedIndex(List<Integer> messageListIndex , String searchedString )
    {
        searchHelper = 1;
        searchMessageIndexList = messageListIndex;
        this.searchedString = searchedString;

    }
    public void cancelSelectedIndex()
    {
        searchHelper = 0;
        searchMessageIndexList.clear();
        searchedString = null;
    }
}

