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
import table.football.soccer.livescore.epl.epllive.model.team_stat_model;

/**
 * Created by Ran on 22-Sep-18.
 */

public class team_stat_rec_adapter extends RecyclerView.Adapter<team_stat_rec_adapter.Viewholder> {


    List<team_stat_model> teamStatModelList;
    Context context;

    public team_stat_rec_adapter(List<team_stat_model> teamStatModelList, Context context) {
        this.teamStatModelList = teamStatModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_team_stat, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final team_stat_model itemModel = teamStatModelList.get(position);
        int no = position+1;
        int clubIc = itemModel.getClubId();
        holder.text_poss.setText(String.valueOf(no));
        holder.value_text.setText(String.valueOf(itemModel.getValue()));
        holder.text_team_name.setText(itemModel.getClubName());
        setImage(clubIc,holder);

    }


    public void setImage(int  HomeTeamID, Viewholder holder ){
        //   Log.d("ItemAdapterTeam"," " + HomeTeam);
        switch (HomeTeamID){
            case 10:
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                holder.team_image.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case 4:
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case 127:
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case 6:
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case 11:
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case 33:
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_watford);
                break;
            case 12:
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case 21:
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case 7:
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_everton);
                break;
            case 38:
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case 43:
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case 20:
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case 26:
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case 23:
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case 1:
                holder.team_image.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case 131:
                holder.team_image.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case 46:
                holder.team_image.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case 34:
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case 159:
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.logo_huddersfield);
                break;
            case 25:
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:

        }






    }
    @Override
    public int getItemCount() {
        return teamStatModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        ImageView  team_image;
        TextView value_text,text_team_name,text_poss;
        public Viewholder(View itemView) {
            super(itemView);

            team_image = itemView.findViewById(R.id.clubImage);

            value_text = itemView.findViewById(R.id.value_text);

            text_team_name = itemView.findViewById(R.id.text_team_name);
            text_poss = itemView.findViewById(R.id.text_poss);

        }



    }



}
