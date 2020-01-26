package in.codepredators.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import in.codepredators.delta.Classes.ListGroup;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterSelectContacts;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;

/**
 * {@link #displayContacts()}                                     Displays the list of contacts from the phone's contact list
 * {@link #callChatActivity(User)}                                Passes the current user object and calls the chat activity
 * {@link #selectedMessage(User, int)}                            Handles the clicking and selecting of any message
 */

public class NewChatButton extends AppCompatActivity {
    private static long[] i;
    private ImageView searchIcon;
    private ImageView optionsIcon;
    private ImageView tickIcon;
    static List<User> phoneDetails;
    static List<User> selectedContacts = new ArrayList<>();
    static List<User> finalList;
    private RecyclerView recyclerView;
    static RecyclerAdapterSelectContacts Object;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iotalkactivity_select_contacts);





        searchIcon = findViewById(R.id.viewSearchSelectContacts);
        optionsIcon = findViewById(R.id.viewSettingsSelectContacts);
        tickIcon = findViewById(R.id.viewSelectContacts);
        recyclerView = findViewById(R.id.recyclerViewSelectContacts);


        phoneDetails = new ArrayList<>();

        tickIcon.setOnClickListener(new View.OnClickListener() {
            //send the contacts which are selected to newgroupformation activity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewChatButton.this, NewGroupFormation.class);
                startActivity(intent);
                finish();
            }
        });

        displayContacts();

//        HashMap<String, String> map = new HashMap<>();

        // put every value of list to Map
//        for (User user : phoneDetails) {
//            map.put(user.getUID(), user.getUserName());
//        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        Object = new RecyclerAdapterSelectContacts(this , phoneDetails);
        //recyclerViewNewGroup.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(Object);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference firebaseDatabaseUserRef = rootRef.child("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                finalList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String firebaseDatabasePhoneNumber = ds.child("userNumber").getValue(String.class);
//                    Log.i("databasephonenumber", firebaseDatabasePhoneNumber);

                    for (User user : phoneDetails) {

                        if (user.getUserNumber() != null && user.getUserNumber().equals(firebaseDatabasePhoneNumber)) {
//                            Log.i("datasnapshotuserphone", user.getUserNumber());
                            user.setUID(ds.child("uid").getValue(String.class));
//                            Log.i("useruid", user.getUID());
                            user.setUserProfilePic(ds.child("userProfilePic").getValue(String.class));
//                            Log.i("userPofilepic", user.getUserProfilePic());
                            finalList.add(user);
                        }
                    }

                }

                Object.updateList(finalList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        firebaseDatabaseUserRef.addListenerForSingleValueEvent(valueEventListener);


    }

    public void displayContacts()
    {

        ListGroup lg = new ListGroup();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                User user = new User();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                Log.i("nameincontact",name);
                user.setUserName(name);
//                Log.i("nameincontactlist",phonenumbersName.get(s));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNo = phoneNo.replaceAll("[-+ ]", "");
                        int r = phoneNo.length();
                        if (r > 10) {
                            phoneNo = phoneNo.substring(r - 10, r);
                        }
                        user.setUserNumber(phoneNo);
                        Toast.makeText(NewChatButton.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    }
                    pCur.close();
                }
                phoneDetails.add(user);
            }
        }


//        return phoneDetails;


    }
    public void callChatActivity(User user)
    {
        Intent intent = new Intent(NewChatButton.this , Chat.class);
        intent.putExtra("USER_OBJECT", (Parcelable) user);
        startActivity(intent);
        finish();
    }
    public static void selectedMessage(User user,int a)
    {
//        if(a==1)
//        {
//            selectedChatActionBar.setVisibility(View.VISIBLE);
//            chatActionBar.setVisibility(View.GONE);
//        }
//        if(a==0)
//        {
//            selectedChatActionBar.setVisibility(View.GONE);
//            chatActionBar.setVisibility(View.VISIBLE);
//        }

        if(selectedContacts.size()>a)
        {
            selectedContacts.remove(user);
        }
        else
        {
            selectedContacts.add(user);
        }
    }



}

