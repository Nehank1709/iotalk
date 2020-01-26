package in.codepredators.delta.RecyclerAdapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;


public class RecyclerAdapterGroupParticipants extends RecyclerView.Adapter<RecyclerAdapterGroupParticipants.ViewHolderGroupParticipants> {

    List<User> users;
    int limit = 10;
    Context context;
    public RecyclerAdapterGroupParticipants(Context context,List<User> users){
        this.users = users;
        this.context = context;
    }
    //
    public class ViewHolderGroupParticipants extends RecyclerView.ViewHolder {

        public TextView selectContactsName;
        public TextView selectContactsPhoneNo;
        public ImageView chatListProfilePic;
        public TextView selectContactsStatus;
        public TextView textViewMore;

        List<User> userList;
        public ViewHolderGroupParticipants(@NonNull View itemView) {
            super(itemView);
            selectContactsName=itemView.findViewById(R.id.selectContactsName);
            selectContactsPhoneNo=itemView.findViewById(R.id.selectContactsPhoneNo);
            chatListProfilePic=itemView.findViewById(R.id.chatListProfilePic);
            selectContactsStatus=itemView.findViewById(R.id.selectContactsStatus);
            textViewMore=itemView.findViewById(R.id.textViewMore);
        }
        // view holder


        //
    }
        @NonNull
        @Override
        public ViewHolderGroupParticipants onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectcontactsrecyclerview,parent,false);
            return new ViewHolderGroupParticipants(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderGroupParticipants holder, int position) {

            holder.textViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("more clicked","Entered in click");
                    if(holder.textViewMore.getText().equals("15 more")){
                        limit=users.size();

                        Log.i("more clicked","Entered in if click");
                        holder.textViewMore.setText("less");
                    }else{


                        Log.i("more clicked","Entered in else click");
                        holder.textViewMore.setText("more...");
                    }

                }
            });





        }

        @Override
        public int getItemCount() {
        if(users.size()>=limit){
            return limit;
        }else{
            return users.size();
        }
            //TODO implement recycler size limit -> if size < limit => return size else => return limit
        }

}
