package in.codepredators.delta.RecyclerAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import in.codepredators.delta.Activities.ChatList;
import in.codepredators.delta.R;


public class RecyclerAdapterChatList extends RecyclerView.Adapter<RecyclerAdapterChatList.ViewHolderChatScreen> {
    private List<ChatList.ChatListItem> userList;
    private  Context context;
    int a = 0;

    public class ViewHolderChatScreen extends RecyclerView.ViewHolder {
        public TextView messageTime;
        public TextView chatListName;
        public ImageView imageViewAttachIcon;
        public TextView noOfUnseenMessage;
        public ConstraintLayout backgroundColor;


        public ViewHolderChatScreen(@NonNull View itemView) {
            super(itemView);
            messageTime = itemView.findViewById(R.id.textViewTimeOfMessage);
            chatListName = itemView.findViewById(R.id.chatListName);
            imageViewAttachIcon = itemView.findViewById(R.id.imageViewAttachIcon);
            noOfUnseenMessage = itemView.findViewById(R.id.textViewNoOfUnseenMessages);
            backgroundColor = itemView.findViewById(R.id.chatListLinearLayout1);
        }
    }
    public RecyclerAdapterChatList(Context context ,List<ChatList.ChatListItem> userList)
    {
        this.context = context;
        this.userList = userList;
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
        ChatList.ChatListItem chatListItem = userList.get(i);
        viewHolder.chatListName.setText(chatListItem.getUser().getUserName());
        viewHolder.messageTime.setText(chatListItem.getMessage().getMessageTime());
        viewHolder.noOfUnseenMessage.setText(chatListItem.getNoOfUnseenMessage());
        Log.i("user name","gyg" + chatListItem.getUser().getUserName());
        Log.i("message time" +
                "",chatListItem.getMessage().getMessageTime());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ColorDrawable viewColor = (ColorDrawable) viewHolder.backgroundColor.getBackground();
                int colorId = viewColor.getColor();
                if (colorId != Color.parseColor("#1af0ff")) {
                    a++;
                    ChatList.selectedChat(userList.get(i),a);
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
                    ChatList.selectedChat(userList.get(i), a);
            }
        }
    });
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
}