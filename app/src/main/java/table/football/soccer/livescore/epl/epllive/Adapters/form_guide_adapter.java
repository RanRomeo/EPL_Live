package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.form_mode;

/**
 * Created by Ran on 23-Aug-18.
 */

public class form_guide_adapter extends  RecyclerView.Adapter<form_guide_adapter.Viewholder>  {



    List<form_mode> formModeList;
    Context context;

    public form_guide_adapter(List<form_mode> formModeList, Context context) {
        this.formModeList = formModeList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_form_guide,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // final commentry_model  commentryModel = commentryModelList.get(position);
        form_mode formMode = formModeList.get(position);

        holder.place_text.setText(formMode.getPlace());
        holder.Result_text.setText(formMode.getResult());
        holder.team_and_score_text.setText(formMode.getTeam_and_score());



    }

    @Override
    public int getItemCount() {
        return formModeList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        ImageView TeamImage;
        TextView Result_text,place_text,team_and_score_text;
        public Viewholder(View itemView) {
            super(itemView);
        }
    }


}
