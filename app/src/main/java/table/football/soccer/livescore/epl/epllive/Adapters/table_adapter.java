package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Team_Detail;
import table.football.soccer.livescore.epl.epllive.model.table_model;

/**
 * Created by Ran on 28-Aug-18.
 */

public class table_adapter extends RecyclerView.Adapter<table_adapter.Viewholder>  {

    List<table_model> tableModelList;
    Context mcontext;

    public table_adapter(List<table_model> tableModelList, Context mcontext) {
        this.tableModelList = tableModelList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_table_view,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final table_model  tableModel = tableModelList.get(position);
        final String TeamID =tableModel.getTid();
        final String TeamNaem = tableModel.getTname();



        holder.TName.setText(tableModel.getTname());
        holder.T_played.setText(tableModel.getP());
        holder.T_won.setText(tableModel.getW());
        holder.T_D.setText(tableModel.getD());
        holder.T_loss.setText(tableModel.getL());
        holder.T_points.setText(tableModel.getPts());
        holder.Tno.setText(tableModel.getPosition());
        holder.T_GD.setText(tableModel.getGD());

        setImage(TeamNaem,holder);

        if (position<=3){
            //colorTable
            holder.item_table.setBackgroundColor(mcontext.getResources().getColor(R.color.colorTable));
        }else {
            holder.item_table.setBackgroundColor(mcontext.getResources().getColor(R.color.colorPrimary));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext, Team_Detail.class);
                i.putExtra("TeamID",TeamID);
                i.putExtra("TeamName",TeamNaem);


                // AddData(int score,String Animename,String Watch_Ep_no,String Total_ep)
                v.getContext().startActivity(i);

            }
        });

    }

    public void setImage(String HomeTeam, Viewholder holder ){
        //   Log.d("ItemAdapterTeam"," " + HomeTeam);
        switch (HomeTeam){
            case "Liverpool":
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                holder.team_image.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Chelsea":
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "AFC Bournemouth":
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "Crystal Palace":
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "Man City":
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "Watford":
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "Man Utd":
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "Spurs":
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "Everton":
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "Wolves":
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case "Burnley":
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "Southampton":
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "Leicester":
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Newcastle":
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "Arsenal":
                holder.team_image.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "Brighton":
                holder.team_image.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "Cardiff":
                holder.team_image.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "Fulham":
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "Huddersfield":
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "West Ham":
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.team_image.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:

        }






    }




    @Override
    public int getItemCount() {
        return tableModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView Tno;
        TextView TName ;
        TextView T_played;
        TextView T_won;
        TextView T_loss;
        TextView T_GD;
        TextView T_points;
        TextView T_D;
        ImageView team_image;
        LinearLayout item_table;
        public Viewholder(View itemView) {
            super(itemView);
            item_table = itemView.findViewById(R.id.item_table);
            team_image = itemView.findViewById(R.id.team_image);
            Tno = itemView.findViewById(R.id.group_team_no);
            TName = itemView.findViewById(R.id.group_team_name);
            T_played = itemView.findViewById(R.id.group_team_P);
            T_won = itemView.findViewById(R.id.group_team_W);
            T_loss = itemView.findViewById(R.id.group_team_L);
            T_GD = itemView.findViewById(R.id.group_team_GD);
            T_points  = itemView.findViewById(R.id.group_team_pts);
            T_D = itemView.findViewById(R.id.group_team_D);

        }
    }

}
