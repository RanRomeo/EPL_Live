package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.stat_player_model;

/**
 * Created by Ran on 20-Sep-18.
 */

public class Stat_player_adapter extends  RecyclerView.Adapter<Stat_player_adapter.Viewholder> {


    List<stat_player_model> StatsList;
    Context context;

    public Stat_player_adapter(List<stat_player_model> statsList, Context context) {
        StatsList = statsList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_player_stats_view,parent,false);
         return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final stat_player_model  Stats = StatsList.get(position);
        int no = position+1;
        holder.stat_count.setText(String.valueOf(Stats.getValue()));
        holder.team_name.setText(Stats.getClubname());
        holder.posistion_no.setText(String.valueOf(no));
        holder.name_player.setText(Stats.getPlayername());

        Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/"+Stats.getImageId()+".png").into(holder.player_image);

    }

    @Override
    public int getItemCount() {
        return StatsList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView stat_count,team_name,posistion_no,name_player;
        ImageView player_image;
        public Viewholder(View itemView) {
            super(itemView);
            stat_count = itemView.findViewById(R.id.text_stat_count);
            team_name = itemView.findViewById(R.id.text_team_name);
            posistion_no = itemView.findViewById(R.id.text_possition_no);
            name_player = itemView.findViewById(R.id.text_name_player);
            player_image = itemView.findViewById(R.id.player_image);

        }
    }


}
