package in.codepredators.delta.RecyclerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.codepredators.delta.Activities.NewGroupFormation;
import in.codepredators.delta.Classes.User;
import in.codepredators.delta.R;


public class RecyclerAdapterNewGroup extends RecyclerView.Adapter<RecyclerAdapterNewGroup.ViewHolderGroup> {
    private List<User> groupmembersList;


    public class ViewHolderGroup extends RecyclerView.ViewHolder {
        public TextView newGroupMemberName;
        public View newGroupMemberProfilePic;


        public ViewHolderGroup(@NonNull View itemView) {
            super(itemView);

            newGroupMemberName = itemView.findViewById(R.id.newGroupName);
            newGroupMemberProfilePic = itemView.findViewById(R.id.newGroupProfilePic);

        }
    }
    public RecyclerAdapterNewGroup(List<User> groupmembersList) {
        this.groupmembersList = groupmembersList;
    }

    public void updateList(List<User> updatedList)
    {
        groupmembersList = updatedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderGroup onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newgrouprecyclerview,viewGroup,false);
        return new ViewHolderGroup(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGroup viewHolder, final int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewGroupFormation.numberValue(groupmembersList.get(i));
                Log.i("item clicked","yes");
            }
        });

        User groupmembers = groupmembersList.get(i);
        viewHolder.newGroupMemberName.setText(groupmembers.getUserName());


    }

    @Override
    public int getItemCount() {

        return groupmembersList.size();
    }

}

