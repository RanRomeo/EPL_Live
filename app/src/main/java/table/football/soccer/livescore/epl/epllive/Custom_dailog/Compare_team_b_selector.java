package table.football.soccer.livescore.epl.epllive.Custom_dailog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


import table.football.soccer.livescore.epl.epllive.Adapters.compareTeam_B_selector_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_fav_tem_settings;
import table.football.soccer.livescore.epl.epllive.model.notification_team_id;

/**
 * Created by Ran on 24-Nov-18.
 */


public class Compare_team_b_selector  extends DialogFragment implements compareTeam_B_selector_adapter.OnItemClickListener {

    View v;
    ImageButton CloseButton;
    RecyclerView TeamRecyclerView;
    List<notification_team_id> Team_list;
    compareTeam_B_selector_adapter faveTeamSettingAdapter;


    @Override
    public void onItemClicked(View v) {
        getDialog().dismiss();
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fav_team_selector,container,false);
        //  v.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        Team_list = new ArrayList<>();
        CloseButton = v.findViewById(R.id.close_button);
        TeamRecyclerView = v.findViewById(R.id.team_rec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        faveTeamSettingAdapter = new compareTeam_B_selector_adapter(Team_list,this,getContext());

        TeamRecyclerView.setLayoutManager(linearLayoutManager);
        TeamRecyclerView.setAdapter(faveTeamSettingAdapter);
        TeamRecyclerView.hasFixedSize();
        UpdateTeamList();



        if(new Pref_fav_tem_settings(getContext()).getFavTeam()!=0){
            CloseButton.setVisibility(View.VISIBLE);
        }




        CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });



        return  v;
    }

    public void UpdateTeamList(){
        int TeamID[]={1,127,131,43,46,4,6,7,34,159,26,10,11,12,23,20,21,33,25,38};

        String TeamNameArray[] = {"Arsenal","AFC Bournemouth","Brington and Hove Albion",
                "Burnley","Cardiff City","Chelsea","Crystal Palace","Everton","Fulham","Huddersfield Town","Leicester city",
                "Liverpool","Manchester city","Manchester United","Newcastle United","Southampton","Tottenham Hotspur","Watford",
                "West Ham United","Wolverhampton Wanderers"};


        ArrayList<notification_team_id> data = new ArrayList<>();
        notification_team_id item;
        for (int i=0;i<TeamID.length;i++){
            item = new notification_team_id();
            item.setTeamId(TeamID[i]);
            item.setTeamName(TeamNameArray[i]);
            data.add(item);
        }
        Team_list.addAll(data);
        faveTeamSettingAdapter.notifyDataSetChanged();
    }

}
