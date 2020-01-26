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

import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.R;

public class RecyclerAdapterDocumentList extends RecyclerView.Adapter<RecyclerAdapterDocumentList.ViewHolderDocumentList>{
    private Context context;
    private  String recyclerType;
    private List<Message> messageList;

  public  class ViewHolderDocumentList extends RecyclerView.ViewHolder {
        private List<Message> messageList;
        private Context context;
        public TextView DocumentName;
        public TextView DocumentSize;
        public TextView DocumentDate;
        public ImageView pdfIcon;


        public ViewHolderDocumentList(@NonNull View itemView) {
            super(itemView);
            DocumentName = itemView.findViewById(R.id.DocumentName);
            DocumentDate = itemView.findViewById(R.id.DocumentDate);
            DocumentSize = itemView.findViewById(R.id.DocumentSize);
            pdfIcon = itemView.findViewById(R.id.pdfIcon);


        }
    }
    public RecyclerAdapterDocumentList(Context context ,String recyclerType, List<Message> messageList) {

         this.recyclerType = recyclerType;
            this.context = context;
            this.messageList = messageList;

        Log.i("sizeoflist", String.valueOf(this.messageList.size()));
        }



    @NonNull
    @Override
            public ViewHolderDocumentList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.documentrecycleritem,viewGroup,false);
                return new ViewHolderDocumentList(view);
            }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDocumentList holder, int position) {
      if(recyclerType=="media"){




      }




    }

    @Override
    public int getItemCount() {
      return messageList.size();
    }
}




