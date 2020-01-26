package in.codepredators.delta.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.codepredators.delta.RecyclerAdapter.RecyclerAdapterCodeLanguages;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;

/**
 * {@link #usercodelistfunc(String)}                    Adds the argument into the code list of the user
 */

public class UserDetail extends AppCompatActivity {

    private static int a=0;
    private StorageReference mStorageRef;

    ImageView profilePic;
    EditText userName;
    EditText userBio;
    TextView proceedUserAccount;
    String downloadUrl;
    static HashMap<String,String> codeHashmap;
    RecyclerView coderecycler;
    List<String> codeList;
    TextView openrecyclerview;
    static List<String> userCodeList;
    static String codename[] = {
            "java",
            "xml",
            "C",
            "C++",
            "python"
    };

    User user;
    FirebaseAuth  mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    String UID;
    Uri imageUri;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    int RESULT_LOAD_IMAGE =1;

    public static void usercodelistfunc(String i)
    {

        userCodeList.add(i);
        codeHashmap.put(userCodeList.get(a),"Raj");
        a=a+1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iotalkactivity_useraccountinfo);

        codeList = new ArrayList<>();
        userCodeList = new ArrayList<>();

        imageView = (ImageView) findViewById(R.id.profilePicUserAccountInfo2);

        userBio = findViewById(R.id.editTextBioUserAccountInfo2);
        userName = findViewById(R.id.editTextUserName2);


        proceedUserAccount = findViewById(R.id.textViewProceedUserAccountInfo2);

        coderecycler = findViewById(R.id.recyclerViewUserAccountInfo);
        openrecyclerview  = findViewById(R.id.textViewSelectCodeLanguageUserProfile);
        codeHashmap = new HashMap<>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
//hash map value taking
        for(int i=0;i<a;i++)
        {
            Log.i("hashmap values",codeHashmap.get(i));
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });


        mAuth = FirebaseAuth.getInstance();
//        UID = mAuth.getCurrentUser().getUid();
        UID ="MeINhuTy1oYhjiM81QIbzZFhqup1";
        user = new User();


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("users").child(UID);

//

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        coderecycler.setLayoutManager(linearLayoutManager);

        openrecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coderecycler.setVisibility(View.VISIBLE);
            }
        });

        for(int i=0;i<=4;i++)
        {
            String cl = codename[i];
            codeList.add(cl);
        }
        RecyclerAdapterCodeLanguages adapter = new RecyclerAdapterCodeLanguages(codeList);
        coderecycler.setAdapter(adapter);


        sharedPreferences = getSharedPreferences("IOTalk",0);
        editor = sharedPreferences.edit();
        user.setUID(sharedPreferences.getString("UID","NOTFOUND"));
        user.setUserName(sharedPreferences.getString("UserName","NOTFOUND"));
        user.setUserBio(sharedPreferences.getString("UserBio","NOTFOUND"));
        user.setUserProfilePic(sharedPreferences.getString("UserProfilePic","NOTFOUND"));
        user.setUserNumber(sharedPreferences.getString("UserNumber","NOTFOUND"));
//hash map shared preferences is remaining

        proceedUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StorageReference riversRef = mStorageRef.child("images.jpg");

                riversRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Got the download URL for 'users/me/profile.png'
                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful());
                                Uri Url = urlTask.getResult();
                                downloadUrl = Url.toString();
                                Toast.makeText(UserDetail.this, downloadUrl, Toast.LENGTH_SHORT).show();
                                Log.i("image url",downloadUrl);
                                user.setUserProfilePic(downloadUrl);
                                Log.i("image url by user",user.getUserProfilePic());
//                            editor.putString("UserProfilePic", downloadUrl);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(UserDetail.this, "image not selected", Toast.LENGTH_SHORT).show();
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });


                //   Log.i("image url by user o",user.getUserProfilePic());
                user.setUserBio(userBio.getText().toString());
                user.setUserName(userName.getText().toString());
//            user.setUserProfilePic(downloadUrl);
                //next single is needed
                //           user.setUserNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                user.setUserNumber("9998887776");
                user.setUID(UID);
                user.setUserLanguages(codeHashmap);
                editor.putString("UserName", user.getUserName());
                editor.putString("UserBio", user.getUserBio());
                editor.putString("UID", UID);
                editor.putString("UserProfilePic", user.getUserProfilePic());
                editor.putString("UserCodeList","code name");
                editor.putString("UserPhoneNumber",user.getUserNumber());
//            editor.putString("UserProfilePic", downloadUrl);
//            editor.putStringSet("UserCodeList",  codeHashmap);
//           mReference.setValue(codeHashmap);

                editor.commit();


                FirebaseDatabase.getInstance().getReference().child("users").child(UID).setValue(user);

                //  FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("userCodeList").setValue(codeHashmap);
                //  FirebaseDatabase.getInstance().getReference().child("users").child(UID).child("userCodeList").setValue(userCodeList);



                coderecycler.setVisibility(View.GONE);
                for(int i=0;i<a;i++)
                {
                    Log.i("usercodeList",userCodeList.get(i));
                }
                Toast.makeText(UserDetail.this, "Work done", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserDetail.this , ChatList.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

