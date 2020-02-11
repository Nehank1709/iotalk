package in.codepredators.delta.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import in.codepredators.delta.Activities.Chat;
import in.codepredators.delta.Activities.ChatList;
import in.codepredators.delta.R;


public class RecyclerAdapterChatList extends RecyclerView.Adapter<RecyclerAdapterChatList.ViewHolderChatScreen> {
    private List<ChatList.ChatListItem> chatListItemList;
    private  Context context;
    int a = 0;
    int selectedStatus = 0;

    public class ViewHolderChatScreen extends RecyclerView.ViewHolder {
        public TextView messageTime;
        public TextView chatListName;
        public ImageView imageViewAttachIcon;
        public TextView noOfUnseenMessage;
        public ConstraintLayout backgroundColor;
        public ImageView selectedStatus;



        public ViewHolderChatScreen(@NonNull View itemView) {
            super(itemView);
            messageTime = itemView.findViewById(R.id.textViewTimeOfMessage);
            chatListName = itemView.findViewById(R.id.chatListName);
            imageViewAttachIcon = itemView.findViewById(R.id.imageViewAttachIcon);
            noOfUnseenMessage = itemView.findViewById(R.id.textViewNoOfUnseenMessages);
            backgroundColor = itemView.findViewById(R.id.chatListLinearLayout1);
            selectedStatus = itemView.findViewById(R.id.chatListTickImageView);
        }
    }
    public RecyclerAdapterChatList(Context context ,List<ChatList.ChatListItem> userList)
    {
        this.context = context;
        this.chatListItemList = userList;
    }

    public ViewGroup viewGroup1;
    @NonNull
    @Override
    public ViewHolderChatScreen onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        viewGroup1 = viewGroup;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatrecyclerview,viewGroup,false);
        return new ViewHolderChatScreen(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderChatScreen viewHolder, final int i) {
        ChatList.ChatListItem chatListItem = chatListItemList.get(i);
        viewHolder.chatListName.setText(chatListItem.getUser().getUserName());
        viewHolder.messageTime.setText(chatListItem.getMessage().getMessageTime());
        Log.i("recyclerunseenmessage"," " + chatListItem.getNoOfUnseenMessage());
        viewHolder.noOfUnseenMessage.setText(chatListItem.getNoOfUnseenMessage());
        Log.i("user name","gyg" + chatListItem.getUser().getUserName());
        if(selectedStatus == 0){
            viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#00000000"));
        }
        if ( chatListItem.getPinStatus() != null && chatListItem.getPinStatus()){
            viewHolder.imageViewAttachIcon.setVisibility(View.VISIBLE);
        }else{
            viewHolder.imageViewAttachIcon.setVisibility(View.GONE);
        }
        if( chatListItem.getArchive() != null && chatListItem.getArchive()){
            viewHolder.backgroundColor.setVisibility(View.GONE);
        }else{
            viewHolder.backgroundColor.setVisibility(View.VISIBLE);
        }
       // Log.i("messagetime" +
       //         "",chatListItem.getMessage().getMessageTime() + " ");

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectedStatus = 1;
                Toast.makeText(context,"  " + viewHolder.selectedStatus.getVisibility(),Toast.LENGTH_LONG).show();
                if (viewHolder.selectedStatus.getVisibility() == View.GONE) {
                    a++;

                    ChatList.selectedChat(chatListItemList.get(i),a);
                    viewHolder.selectedStatus.setVisibility(View.VISIBLE);
                }
                //Toast.makeText(getContext(), "long Click" + position+"  ", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a > 0) {
                    if (viewHolder.selectedStatus.getVisibility() == View.VISIBLE) {
                        a--;
                        if(a==0){
                            selectedStatus = 0;
                        }
                        viewHolder.selectedStatus.setVisibility(View.GONE);
                    } else {
                        a++;
                        viewHolder.selectedStatus.setVisibility(View.VISIBLE);
                    }
                    ChatList.selectedChat(chatListItemList.get(i), a);
                }else{
                    Intent intent = new Intent(context, Chat.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }
        });


    }
    public void refreshRecycer(){
        selectedStatus = 0;
        a=0;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return chatListItemList.size();
    }
}