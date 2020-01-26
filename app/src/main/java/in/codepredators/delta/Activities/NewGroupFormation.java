package in.codepredators.delta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.codepredators.delta.Classes.Group;
import in.codepredators.delta.Classes.ListGroup;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterNewGroup;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;

/**
 * {@link #filter(String)}                                         Filters the contact list according to the text entered by the user in the search box
 * {@link #createGID(String, String, String)}                      Creates the group id for the group formation
 * {@link #requestLocationPermission()}                            Requests to access the contacts of the user
 * {@link #onRequestPermissionsResult(int, String[], int[])}       Handles the result of request
 * {@link #displayContacts()}                                      Displays the list of contacts from the phone's contact list
 */
public class NewGroupFormation extends AppCompatActivity {

    private StorageReference mStorageRef;
    View formgroupButton;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView newgroupTextView;

    String UID;
    String downloadUrl;
    ImageView groupImage;
    EditText groupName;
    EditText groupDescription;
    static  HashMap<String,String> groupParticipantHashMap = new HashMap<>();

    static List<User> finalList = new ArrayList<>();
    List<User> phoneDetail;
    private List<User> groupmembersList = new ArrayList<>();
    Group newgroup;
    int RESULT_LOAD_IMAGE = 1;
    Uri imageUri;
    private RecyclerView recyclerViewNewGroup;

    int noOfContact = 0;
    private RecyclerAdapterNewGroup RecyclerAdapterNewGroup;


    static int d=0;
    public static void numberValue(User user)
    {
        // selectedContactNumber.add(finalList.get(i));
        groupParticipantHashMap.put(user.getUID(),"NoAdmin");
        d+=1;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            groupImage.setImageURI(imageUri);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iotalkactivity_newgroup);

        formgroupButton = findViewById(R.id.formGroupButton);
        groupName = findViewById(R.id.editTextGroupSubjectNewGroup);
        groupDescription = findViewById(R.id.editTextGroupDescriptionNewGroup);
        recyclerViewNewGroup = (RecyclerView) findViewById(R.id.recyclerViewNewGroup);
        groupImage = findViewById(R.id.imageViewCameraIconNewGroup);


        newgroupTextView = findViewById(R.id.textViewNewGroup);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        newgroup = new Group();
        ListGroup lg = new ListGroup();
        mAuth = FirebaseAuth.getInstance();
//        UID = mAuth.getCurrentUser().getUid();
        UID = "MeINhuTy1oYhjiM81QIbzZFhqup1";
        requestLocationPermission();

        Log.i("displayiscalled","Called");
        groupmembersList = displayContacts();
        //   finalList = groupmembersList;
        groupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });


        final int[] i = {0};
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference firebaseDatabaseUserRef = rootRef.child("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                finalList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String firebaseDatabasePhoneNumber = ds.child("userNumber").getValue(String.class);
                    Log.i("databasephonenumber",firebaseDatabasePhoneNumber);

                    for(User user : groupmembersList)
                    {
                        i[0]++;
                        Log.i("insidesnapfor", String.valueOf(i[0]));
//                        if(user.getUserNumber()!=null)
//                        {
//                            Log.i("username",user.getUserName());
//                            Log.i("usernumberbuuser", user.getUserNumber());
//                            Log.i("usernumberbyfirebase", firebaseDatabasePhoneNumber);
//                        }
                        if (user.getUserNumber() != null && user.getUserNumber().equals(firebaseDatabasePhoneNumber))
                        {
                            Log.i("datasnapshotuserphone",user.getUserNumber());
                            user.setUID(ds.child("uid").getValue(String.class));
                            Log.i("useruid",user.getUID());
                            user.setUserProfilePic(ds.child("userProfilePic").getValue(String.class));
                            Log.i("userPofilepic",user.getUserProfilePic());
                            finalList.add(user);
                        }
                    }

                }
                RecyclerAdapterNewGroup.updateList(finalList);
//                for(String phoneBookNumber : phoneNumbers) {
//                    if (firebaseDatabaseList.contains(phoneBookNumber)) {
//                        finalList.add(phoneBookNumber);
//                    }
//                }
                //Do what you need to do with the finalList
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        firebaseDatabaseUserRef.addListenerForSingleValueEvent(valueEventListener);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewNewGroup.setLayoutManager(mLayoutManager);
        RecyclerAdapterNewGroup = new RecyclerAdapterNewGroup(groupmembersList);
        //recyclerViewNewGroup.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNewGroup.setAdapter(RecyclerAdapterNewGroup);

        Date currentTime = Calendar.getInstance().getTime();
        final String dateandtime = currentTime.toString();
        Log.i("dateandtime",dateandtime);

        sharedPreferences = getSharedPreferences("IOTalk",0);
        editor = sharedPreferences.edit();
        newgroup.setGID(sharedPreferences.getString("GID","NOTFOUND"));
        newgroup.setGroupFormationTime(sharedPreferences.getString("GroupFormationTime","NOTFOUND"));
        newgroup.setGroupCreater(sharedPreferences.getString("GroupCreater","NOTFOUND"));
        newgroup.setGroupName(sharedPreferences.getString("GroupName","NOTFOUND"));
        newgroup.setGroupDescription(sharedPreferences.getString("GroupDescription","NOTFOUND"));
        newgroup.setGroupPic(sharedPreferences.getString("GroupPic","NOTFOUND"));

//        for (User s : selectedContactDetail)
//        {
//            String u=  FirebaseDatabase.getInstance().getReference().child("users").orderByChild("phoneNumber").equalTo(s.getUserNumber()).getRef().getKey();
//            selectedUid.add(u);
//        }

        formgroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StorageReference riversRef = mStorageRef.child("images.jpg");

                riversRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Got the download URL for 'users/me/profile.png'
                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful()) ;
                                Uri Url = urlTask.getResult();
                                downloadUrl = Url.toString();

                                Toast.makeText(NewGroupFormation.this, downloadUrl, Toast.LENGTH_SHORT).show();
                                Log.i("image url", downloadUrl);
                                newgroup.setGroupPic(downloadUrl);
                                Log.i("image url by user", newgroup.getGroupPic());
                                //      editor.putString("GroupPic",newgroup.getGroupPic());
///                          editor.putString("UserProfilePic", downloadUrl);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(NewGroupFormation.this, "image not selected", Toast.LENGTH_SHORT).show();
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });

                // selectedContactNumber.add(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

                groupParticipantHashMap.put(UID,"Admin");

                //selectedUid.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                newgroup.setGID(createGID("GID","9998887776",dateandtime));
                newgroup.setGroupName(groupName.getText().toString());
                newgroup.setGroupDescription(groupDescription.getText().toString());
                newgroup.setGroupCreater(UID);
                newgroup.setGroupFormationTime(dateandtime);

                //  newgroup.setGroupParticipant((HashMap<String, String>) selectedUid);
                editor.putString("GID",newgroup.getGID());
                editor.putString("GroupFormationTime",newgroup.getGroupFormationTime());
                editor.putString("GroupCreater",newgroup.getGroupCreater());
                editor.putString("GroupName",newgroup.getGroupName());
                editor.putString("GroupDescription",newgroup.getGroupDescription());
                editor.putString("GroupPic",newgroup.getGroupPic());
                editor.putString("GroupParticipant","UID");
                Log.i("urlputting","done"+newgroup.getGroupPic());
                editor.commit();

                FirebaseDatabase.getInstance().getReference().child("groups").child(newgroup.getGID()).setValue(newgroup);
                FirebaseDatabase.getInstance().getReference().child("groups").child(newgroup.getGID()).child("groupParticipant").setValue(groupParticipantHashMap);
            }
        });


    }
    public void filter(String text)
    {
        Log.i("searchboxtext",text);
        List<User> filteredList = new ArrayList<>();
        for(User user : finalList)
        {
            if(user.getUserName().toLowerCase().contains(text.toLowerCase()))
            {
                Log.i("ifblocklist",user.getUserName());
                filteredList.add(user);
            }
        }
        RecyclerAdapterNewGroup.updateList(filteredList);
    }



    public static String createGID(String gidInitial,String gidMid,String gidEnd )
    {String GID = gidInitial + gidMid +gidEnd;
        return GID;
    }



    public static final int REQUEST_READ_CONTACTS = 79;

    protected void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!

        } else {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }

    }
    int a=0;
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    a=1;

                } else {

                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }


      public List<User> displayContacts() {
       phoneDetail= new ArrayList<>();

        ListGroup lg = new ListGroup();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                User user = new User();
                String id = cur.getString(cur.getColumnIndex(Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(Contacts.DISPLAY_NAME));
//                Log.i("nameincontact",name);
                user.setUserName(name);
//                Log.i("nameincontactlist",phonenumbersName.get(s));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNo = phoneNo.replaceAll("[-+ ]","");
                        int r =phoneNo.length();
                        if(r>10) {
                            phoneNo = phoneNo.substring(r - 10, r);
                        } user.setUserNumber(phoneNo);
                        Toast.makeText(NewGroupFormation.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    }
                    pCur.close();
                }
                phoneDetail.add(user);
            }
        }


        return phoneDetail;
    }

}
