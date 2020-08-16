package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_notification_setting;
import table.football.soccer.livescore.epl.epllive.model.notification_team_id;

/**
 * Created by Ran on 29-Sep-18.
 */

public class notification_setting_adapter extends  RecyclerView.Adapter<notification_setting_adapter.Viewholder> {


    List<notification_team_id> TeamList;
    Context context;

//
    public notification_setting_adapter(List<notification_team_id> teamList, Context context) {
        TeamList = teamList;
        this.context = context;
        this.setHasStableIds(true);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_layout_notification_team,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final notification_team_id  TeamModel = TeamList.get(position);

        holder.textView.setText(TeamModel.getTeamName());
        setImage(TeamModel.getTeamId(),holder);



        if (new Pref_notification_setting(context).isNotificationEnable(String.valueOf(TeamModel.getTeamId()))){
            holder.switchCompat.setChecked(true);
        }
        else {
         holder.switchCompat.setChecked(false);
    }

        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    new Pref_notification_setting(context).enableNotifcation(String.valueOf(TeamModel.getTeamId()));
                }else {
                    new Pref_notification_setting(context).disabeNotification(String.valueOf(TeamModel.getTeamId()));
                }
            }
        });

    }


    @Override
    public long getItemId(int position) {
        return position;
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


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return TeamList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        ImageView team_image;
        TextView textView;
        SwitchCompat switchCompat;
        public Viewholder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            team_image = itemView.findViewById(R.id.notif_image);
            textView  = itemView.findViewById(R.id.notif_text);
            switchCompat = itemView.findViewById(R.id.notif_switch);






        }
    }



}
