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
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import in.codepredators.delta.Activities.NewChatButton;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;




public class RecyclerAdapterSelectContacts extends RecyclerView.Adapter<RecyclerAdapterSelectContacts.ViewHolderContacts> {
    private static List<User> contactList;

    private static List<User>  multiselect_list;
    int a=0;
    boolean isMultiSelect = false;
    ActionMode mActionMode;
   private Context context;




    public class ViewHolderContacts extends RecyclerView.ViewHolder {
        public TextView selectContactsName;
        public View selectContactsProfilePic;
        public TextView selectContactsPhoneNo;
        public TextView selectContactsStatus;
        public ConstraintLayout backgroundColor;
        public ImageView tickIcon ;

        public ViewHolderContacts(@NonNull View itemView) {
            super(itemView);
            tickIcon = itemView.findViewById(R.id.chatListTickImageView);
            selectContactsStatus = itemView.findViewById(R.id.selectContactsStatus);
            selectContactsPhoneNo = itemView.findViewById(R.id.selectContactsPhoneNo);
            selectContactsName=itemView.findViewById(R.id.selectContactsName);
            selectContactsProfilePic = itemView.findViewById(R.id.chatListProfilePic);
            backgroundColor = itemView.findViewById(R.id.selectContactsConstraintLayout);
        }
    }
    public RecyclerAdapterSelectContacts(Context context, List<User> contactList)
    {
        this.context=context;
        this.contactList = contactList;
    }
    @NonNull
    @Override
    public ViewHolderContacts onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.selectcontactsrecyclerview,viewGroup,false);
        return new ViewHolderContacts(view);
    }
    public void updateList(List<User> updatedList)
    {
        contactList = updatedList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderContacts viewHolder , final int i) {
        final User contactselect = contactList.get(i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (a > 0) {
                    ColorDrawable viewColor = (ColorDrawable) viewHolder.backgroundColor.getBackground();
                    int colorId = viewColor.getColor();
                    if (colorId == Color.parseColor("#1af0ff")) {
                        a--;
                        viewHolder.tickIcon.setVisibility(View.GONE);
                        viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#00000000"));
                    } else {
                        a++;
                        viewHolder.tickIcon.setVisibility(View.VISIBLE);
                        viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#1af0ff"));
                    }
                    NewChatButton.selectedMessage(contactList.get(i), a);
                }
                else
                ((NewChatButton)context).callChatActivity(contactselect);
//
//                else
//                    {
//                        multi_select(i);
//                }
            }
        });
        viewHolder.selectContactsName.setText(contactselect.getUserName());
        viewHolder.selectContactsPhoneNo.setText(contactselect.getUserNumber());
        viewHolder.selectContactsStatus.setText(contactselect.getUserBio());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v ) {

                Log.i("DEBUG" , "HELLO");
               viewHolder.tickIcon.setVisibility(View.VISIBLE);
                a++;
                ColorDrawable viewColor = (ColorDrawable)viewHolder.backgroundColor.getBackground();
                int colorId = viewColor.getColor();
                if (colorId != Color.parseColor("#1af0ff")) {

                    a++;
                    NewChatButton.selectedMessage(contactList.get(i),a);
                    viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#1af0ff"));
                }
                //Toast.makeText(getContext(), "long Click" + position+"  ", Toast.LENGTH_SHORT).show();
                return false;
//                viewHolder.backgroundColor.setBackgroundColor(Color.parseColor("#1af0ff"));
//                if (!isMultiSelect)
//                {
//                    multiselect_list = new ArrayList();
//                    isMultiSelect = true;
//                }
//                multi_select(i);
//                return false;
            }

            });


    }
    // Add/Remove the item from/to the list

//    public void multi_select(int position) {
//        if (mActionMode != null) {
//            if (multiselect_list.contains(contactList.get(position)))
//                multiselect_list.remove(contactList.get(position));
//            else
//            {
//
//                multiselect_list.add(contactList.get(position));
//            }
//
//
//
//
//            notifyDataSetChanged();
//        }
//    }


    @Override
    public int getItemCount() {

        return contactList.size();
    }




}

