package in.codepredators.delta.RecyclerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.codepredators.delta.Activities.UserDetail;
import in.codepredators.delta.R;


public class RecyclerAdapterCodeLanguages extends RecyclerView.Adapter<RecyclerAdapterCodeLanguages.ViewHolderCodeLanguages> {
    private List<String> languageList;


    public class ViewHolderCodeLanguages extends RecyclerView.ViewHolder {
        public TextView codeLanguageEnter;
        public ImageView codeLanguageCheckboximageView;


        public ViewHolderCodeLanguages(@NonNull View itemView) {
            super(itemView);

            codeLanguageEnter = itemView.findViewById(R.id.codeLanguageEnter);
            codeLanguageCheckboximageView = itemView.findViewById(R.id.codeLanguageCheckboximageView);

        }
    }
    public RecyclerAdapterCodeLanguages(List<String> languageList) {
        this.languageList = languageList;
    }
    @NonNull
    @Override
    public ViewHolderCodeLanguages onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.codelanguagesrecyclerview,viewGroup,false);
        return new ViewHolderCodeLanguages(view);
    }

    public void onBindViewHolder(ViewHolderCodeLanguages holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetail.usercodelistfunc(languageList.get(position));
                Log.i("item clicked","yes");
            }
        });
    }

    @Override
    public int getItemCount() {

        return languageList.size();
    }

}

