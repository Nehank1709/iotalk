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


public class RecyclerAdapterCode extends RecyclerView.Adapter<RecyclerAdapterCode.ViewHolderCode> {

    private List<Message> messageList;
    private Context context;
    int selectedStatus = 0;


    public class ViewHolderCode extends RecyclerView.ViewHolder {
        public TextView codeMessageSender;
        public View codeProfilePic;
        public TextView codeMessageReceiver;
        public ImageView codeArrowimageView;
        public TextView codeMessagetextView;
        public TextView codeTimeOfMessagetextView;
        public TextView textView9;



        public ViewHolderCode(@NonNull View itemView) {
            super(itemView);

            codeMessageSender = itemView.findViewById(R.id.codeMessageSender);
            codeArrowimageView = itemView.findViewById(R.id.codeArrowimageView);
            codeProfilePic = itemView.findViewById(R.id.codeProfilePic);
            codeMessageReceiver = itemView.findViewById(R.id.StarredMessagesMessageReceiver);
            codeMessagetextView = itemView.findViewById(R.id.codeMessagetextView);
            codeTimeOfMessagetextView = itemView.findViewById(R.id.codeTimeOfMessagetextView);
            textView9=itemView.findViewById(R.id.textView9);

        }
    }
    public RecyclerAdapterCode(Context context  ,  List<Message> messageList) {

        this.context = context;
        this.messageList= messageList;
    }
    @NonNull
    @Override
    public ViewHolderCode onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coderecyclerview,viewGroup,false);
        return new ViewHolderCode(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCode viewHolder, int i) { ;
        Message message = messageList.get(i);

        viewHolder.codeMessageSender.setText(message.getMessagesenderUID());
        if(message.getMessageType().charAt(0)=='1') {
            viewHolder.codeMessagetextView.setText(message.getMessageText());
        }
        if(message.getMessageType().charAt(1)=='1'){
            viewHolder.codeMessagetextView.setText(message.getMessageImage());
        }
        if(message.getMessageType().charAt(2)=='1'){
            viewHolder.codeMessagetextView.setText(message.getMessageFile());
        }

        viewHolder.codeTimeOfMessagetextView.setText(message.getMessageTime());


        viewHolder.textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("more clicked","Entered in click");
                if(viewHolder.textView9.getText().equals("more...")){
                    viewHolder.codeMessagetextView.setMaxLines(200);

                    Log.i("more clicked","Entered in if click");
                    viewHolder.textView9.setText("less");
                }else{
                    viewHolder.codeMessagetextView.setMaxLines(5);

                    Log.i("more clicked","Entered in else click");
                    viewHolder.textView9.setText("more...");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
    public void updateList(List<Message> updatedList)
    {
        messageList = updatedList;
        notifyDataSetChanged();
    }
    int a =0;
    public void refreshRecycler(){
        selectedStatus = 0;
        a=0;
        notifyDataSetChanged();
    }
}

