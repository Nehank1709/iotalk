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

import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.R;


public class RecyclerAdapterStarredMessages extends RecyclerView.Adapter<RecyclerAdapterStarredMessages.ViewHolderStarredMessages> {
    private List<Message> messageList;
    private Context context;



    public class ViewHolderStarredMessages extends RecyclerView.ViewHolder {
        public TextView StarredMessagesMessageSender;
        public ImageView imageViewArrowStarredMessages;
        public View StarredMessagesProfilePic;
        public TextView StarredMessagesMessageReceiver;
        public ImageView StarredMessagesForwardimageView;
        public TextView StarredMessagesMessagetextView;
        public ImageView StarredMessagesStarimageView;
        public TextView StarredMessagesTimetextView;
        public TextView textView9;


        public ViewHolderStarredMessages(@NonNull View itemView) {
            super(itemView);

            StarredMessagesMessageSender = itemView.findViewById(R.id.StarredMessagesMessageSender);
            StarredMessagesProfilePic = itemView.findViewById(R.id.StarredMessagesProfilePic);
            imageViewArrowStarredMessages = itemView.findViewById(R.id.imageViewArrowStarredMessages);
            StarredMessagesMessageReceiver = itemView.findViewById(R.id.StarredMessagesMessageReceiver);
            StarredMessagesForwardimageView = itemView.findViewById(R.id.StarredMessagesForwardimageView);

            StarredMessagesMessagetextView = itemView.findViewById(R.id.StarredMessagesMessagetextView);
            StarredMessagesStarimageView = itemView.findViewById(R.id.StarredMessagesStarimageView);
            StarredMessagesTimetextView = itemView.findViewById(R.id.StarredMessagesTimetextView);
            textView9 = itemView.findViewById(R.id.textView9);
        }
    }

    public RecyclerAdapterStarredMessages(Context context, List<Message> messageList) {

        this.context = context;
        this.messageList = messageList;

        Log.i("entered", String.valueOf(this.messageList.size()));
    }

    @NonNull
    @Override
    public ViewHolderStarredMessages onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Log.i("entered","entered in oncreate");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.starredmessagesrecyclerview, viewGroup, false);
        return new ViewHolderStarredMessages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderStarredMessages viewHolder, int i)
    {
        Log.i("entered", "entered");
        final Message message = messageList.get(i);
        //if (message.getMessageStarredStatus().equals(true))
        viewHolder.StarredMessagesStarimageView.setImageDrawable(context.getResources().getDrawable(R.drawable.star));
        viewHolder.StarredMessagesMessageSender.setText(message.getMessagesenderUID());
        if (message.getMessageType().charAt(0) == '1') {
            viewHolder.StarredMessagesMessagetextView.setText(message.getMessageText());
        }
        if (message.getMessageType().charAt(1) == '1') {
            viewHolder.StarredMessagesMessagetextView.setText(message.getMessageImage());
        }
        if (message.getMessageType().charAt(2) == '1') {
            viewHolder.StarredMessagesMessagetextView.setText(message.getMessageFile());
        }


        viewHolder.StarredMessagesTimetextView.setText(message.getMessageTime());
        viewHolder.StarredMessagesStarimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageList.remove(message);
                notifyDataSetChanged();
            }
        });
        viewHolder.textView9.post(new Runnable(){
            @Override
            public  void run () {
                int a = viewHolder.StarredMessagesMessagetextView.getLineCount();
                if (a < 5) {
                    viewHolder.textView9.setVisibility(View.INVISIBLE);
                    Log.i("visible","click");
                } else {
                    viewHolder.StarredMessagesMessagetextView.setMaxLines(5);
                    viewHolder.textView9.setVisibility(View.VISIBLE);
                }
            }

        });


        viewHolder.textView9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("more clicked", "Entered in click");
                if (viewHolder.textView9.getText().equals("more...")) {
                    viewHolder.StarredMessagesMessagetextView.setMaxLines(200);

                    Log.i("more clicked", "Entered in if click");
                    viewHolder.textView9.setText("less");
                } else {
                    viewHolder.StarredMessagesMessagetextView.setMaxLines(5);

                    Log.i("more clicked", "Entered in else click");
                    viewHolder.textView9.setText("more...");

                }
            }
        });
//@Override
//                public int getItemCount () {
//                return messageList.size();
//                }

//        public void updateList (List < Message > messageList)
//        {
//            messageList = messageList;
//            notifyDataSetChanged();
//        }
    }

    @Override
    public int getItemCount() {
        return this.messageList.size();
    }
}
