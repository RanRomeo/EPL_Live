package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.MainActivity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_fav_tem_settings;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_notification_setting;
import table.football.soccer.livescore.epl.epllive.model.notification_team_id;

/**
 * Created by Ran on 12-Nov-18.
 */

public class fave_team_setting_adapter extends  RecyclerView.Adapter<fave_team_setting_adapter.Viewholder> {

    //notificaationTeamId is teamlist model
    List<notification_team_id> TeamList;
    private OnItemClickListener listener;
    Context context;


    public interface OnItemClickListener {
        void onItemClicked();
    }

    public fave_team_setting_adapter(List<notification_team_id> teamList, OnItemClickListener listener, Context context) {
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
                Toast.makeText(context, "Club Selected", Toast.LENGTH_SHORT).show();
                Pref_fav_tem_settings favTemSettings = new Pref_fav_tem_settings(context);
                Pref_notification_setting notificationSetting = new Pref_notification_setting(context);

                favTemSettings.SaveFavTeam(TeamModel.getTeamId());
                notificationSetting.enableNotifcation(String.valueOf(TeamModel.getTeamId()));
                listener.onItemClicked();
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
