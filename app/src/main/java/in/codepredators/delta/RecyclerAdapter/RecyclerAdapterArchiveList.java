package in.codepredators.delta.RecyclerAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.codepredators.delta.Activities.ChatList;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;


public class RecyclerAdapterArchiveList extends RecyclerView.Adapter<RecyclerAdapterArchiveList.ViewHolderArchive> {
    private List<User> userList;
    private List<ChatList.ChatListItem> chatListItemList;

    private Context context;



    public class ViewHolderArchive extends RecyclerView.ViewHolder {
        public TextView chatListName;
        public ImageView chatListProfilePic;
        public TextView textViewTimeOfMessage;
        public ImageView imageViewAttachIcon;
        public TextView textViewNoOfUnseenMessages;
        public ImageView chatListTickImageView;


        public ViewHolderArchive(@NonNull View itemView) {
            super(itemView);

            chatListName = itemView.findViewById(R.id.chatListName);
            chatListProfilePic = itemView.findViewById(R.id.chatListProfilePic);
            textViewTimeOfMessage = itemView.findViewById(R.id.textViewTimeOfMessage);
            imageViewAttachIcon=itemView.findViewById(R.id.imageViewAttachIcon);
            textViewNoOfUnseenMessages=itemView.findViewById(R.id.textViewNoOfUnseenMessages);
            chatListTickImageView=itemView.findViewById(R.id.chatListTickImageView);
            imageViewAttachIcon.setVisibility(View.INVISIBLE);
            textViewNoOfUnseenMessages.setVisibility(View.INVISIBLE);
            chatListTickImageView.setVisibility(View.INVISIBLE);


        }
    }
    public RecyclerAdapterArchiveList(Context context , List<ChatList.ChatListItem> chatListItemList) {

        this.context = context;
        this.chatListItemList = chatListItemList;

        Log.i("checkingsizerecycler", String.valueOf(chatListItemList.size()));
    }
    @NonNull
    @Override
    public ViewHolderArchive onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatrecyclerview,viewGroup,false);
        return new ViewHolderArchive(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderArchive viewHolder, int i) {
        ChatList.ChatListItem c=chatListItemList.get(i);

        Log.i("checkingsizeonbind", String.valueOf(chatListItemList.size()));
        viewHolder.chatListName.setText(c.getUser().getUserName());
        if(Integer.parseInt(c.getNoOfUnseenMessage())>=1) {
            viewHolder.textViewNoOfUnseenMessages.setVisibility(View.VISIBLE);
            viewHolder.textViewNoOfUnseenMessages.setText(c.getNoOfUnseenMessage());
        }else{
            viewHolder.textViewNoOfUnseenMessages.setVisibility(View.INVISIBLE);
        }
        viewHolder.textViewTimeOfMessage.setText(c.getMessage().getMessageTime());
        if (c.getPinStatus())
        {
            viewHolder.imageViewAttachIcon.setVisibility(View.VISIBLE);
        }else{

            viewHolder.imageViewAttachIcon.setVisibility(View.INVISIBLE);
        }

        // viewHolder.chatListName.setText(chatListItemList.);
        /** ChatList c= listname.get(i);
         c.getUser().getUserName();
         c.getNoOfUnseenMessage();**/



        // viewHolder. chatListName.setText(user.getUserName());

    }

    @Override
    public int getItemCount() {

        return chatListItemList.size();
//        return userList.size();
    }

    public void updateList(List<User> updatedList)
    {
        userList = updatedList;
        notifyDataSetChanged();
    }
}

