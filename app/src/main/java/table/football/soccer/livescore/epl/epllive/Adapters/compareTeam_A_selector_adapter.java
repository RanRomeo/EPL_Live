package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Team_comparision_activity;
import table.football.soccer.livescore.epl.epllive.model.notification_team_id;

/**
 * Created by Ran on 24-Nov-18.
 */

public class compareTeam_A_selector_adapter extends  RecyclerView.Adapter<compareTeam_A_selector_adapter.Viewholder> {


    //notificaationTeamId is teamlist model
    List<notification_team_id> TeamList;
    private OnItemClickListener listener;
    Context context;


    public interface OnItemClickListener {
        void onItemClicked(View v);
    }

    public compareTeam_A_selector_adapter(List<notification_team_id> teamList, OnItemClickListener listener, Context context) {
        TeamList = teamList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_team_fav_select,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final notification_team_id  TeamModel = TeamList.get(position);

        holder.textView.setText(TeamModel.getTeamName());
        setImage(TeamModel.getTeamId(),holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClicked(v);
                ((Team_comparision_activity) context).team_a_image_button.setVisibility(View.GONE);
                ((Team_comparision_activity) context).getTeamFixtureFirst("https://footballapi.pulselive.com/football/stats/team/"+TeamModel.getTeamId()+"?comps=1",new WebView(context).getSettings().getUserAgentString());
                ((Team_comparision_activity) context).setMyTeam_A((String.valueOf(TeamModel.getTeamId())));
                ((Team_comparision_activity) context).SetSnipper1ToDefault();
                TeamASetImage(TeamModel.getTeamId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return TeamList.size();
    }


    public void setImage(int  TeamID, Viewholder holder ){
        //   Log.d("ItemAdapterTeam"," " + HomeTeam);
        switch (TeamID){
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

    public void TeamASetImage(int  TeamID){
        //   Log.d("ItemAdapterTeam"," " + HomeTeam);
        switch (TeamID){
            case 10:
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_liverpool);
                ((Team_comparision_activity) context).team_a_name.setText("Liverpool");
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case 4:
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_chelsea);
                ((Team_comparision_activity) context).team_a_name.setText("Chelsea");
                break;
            case 127:
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                ((Team_comparision_activity) context).team_a_name.setText("AFC");
                break;
            case 6:
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_crystal_palace);
                ((Team_comparision_activity) context).team_a_name.setText("Crystal Palace");
                break;
            case 11:
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_manchester_city);
                ((Team_comparision_activity) context).team_a_name.setText("Man City");
                break;
            case 33:
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_watford);
                ((Team_comparision_activity) context).team_a_name.setText("Watford");
                break;
            case 12:
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_manchester_united);
                ((Team_comparision_activity) context).team_a_name.setText("Man. United");
                break;
            case 21:
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                ((Team_comparision_activity) context).team_a_name.setText("Tottenham");
                break;
            case 7:
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_everton);
                ((Team_comparision_activity) context).team_a_name.setText("Everton");
                break;
            case 38:
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_wolverhampton);
                ((Team_comparision_activity) context).team_a_name.setText("Wolves");
                break;
            case 43:
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_burnley);
                ((Team_comparision_activity) context).team_a_name.setText("Burnley");
                break;
            case 20:
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_southampton);
                ((Team_comparision_activity) context).team_a_name.setText("Southampton");
                break;
            case 26:
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_leicester_city);
                ((Team_comparision_activity) context).team_a_name.setText("Leicester City F.C.");
                break;
            case 23:
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_newcastle_united);
                ((Team_comparision_activity) context).team_a_name.setText("Newcastle United");
                break;
            case 1:
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_arsenal_club);
                ((Team_comparision_activity) context).team_a_name.setText("Arsenal");
                break;
            case 131:
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                ((Team_comparision_activity) context).team_a_name.setText("Brighton & Hove Albion");
                break;
            case 46:
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                ((Team_comparision_activity) context).team_a_name.setText("Cardiff City");
                break;
            case 34:
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_fulham);
                ((Team_comparision_activity) context).team_a_name.setText("Fulham");
                break;
            case 159:
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.logo_huddersfield);
                ((Team_comparision_activity) context).team_a_name.setText("Huddersfield Town");
                break;
            case 25:
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((Team_comparision_activity) context).ImagefirstTeam.setImageResource(R.drawable.ic_logo_west_ham_united);
                ((Team_comparision_activity) context).team_a_name.setText("West Ham United");
                break;
            default:

        }






    }

    public class Viewholder extends RecyclerView.ViewHolder{

        ImageView team_image;
        TextView textView;
        public Viewholder(View itemView) {
            super(itemView);
            team_image = itemView.findViewById(R.id.team_image);
            textView  = itemView.findViewById(R.id.team_name);
        }
    }
}
