package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import table.football.soccer.livescore.epl.epllive.Match_Activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.match_model;

/**
 * Created by Ran on 17-Aug-18.
 */


public class RecyclerHeaderItemAdapter extends RecyclerItemAdapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {


    private List<match_model> mList ;
    public Context context;


    public RecyclerHeaderItemAdapter(Context context, List<match_model> list){
        this.mList = list;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_result_fixture, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final match_model itemModel = mList.get(position);
        final String city = itemModel.getStadiumName()+","+itemModel.getCityName();
        final String matchStatus = itemModel.getMatchStatus();


        if(matchStatus.equals("L")){
            ((ItemViewHolder)holder).gameTimePast.setText("Live");
        }else if(matchStatus.equals("C")){
            ((ItemViewHolder)holder).gameTimePast.setText("FT");
        }else {
            ((ItemViewHolder)holder).gameTimePast.setText("");
        }




        ((ItemViewHolder)holder).homeTeam.setText(itemModel.getHomeTeam());
        ((ItemViewHolder)holder).awayTeam.setText(itemModel.getAwayTeam());
        ((ItemViewHolder)holder).gameTime.setText(itemModel.getTime());

        ((ItemViewHolder)holder).match_venue.setText(city);
        ((ItemViewHolder)holder).homeTeamScore.setText(itemModel.getHomeTeamScore());
        ((ItemViewHolder)holder).awayTeamScore.setText(itemModel.getAwayTeamScore());


        setImage(itemModel.getHomeTeam(),itemModel.getAwayTeam(),((ItemViewHolder)holder));
        ((ItemViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Match_Activity.class);
                   i.putExtra("HomeTeam",itemModel.getHomeTeam());
                   i.putExtra("AwayTeam",itemModel.getAwayTeam());
                   i.putExtra("homeTeamScore",itemModel.getHomeTeamScore());
                   i.putExtra("awayTeamScore",itemModel.getAwayTeamScore());
                   i.putExtra("MatchId",itemModel.getMathcId());
                   i.putExtra("MatchStatus",matchStatus);
                   i.putExtra("HomeTeamID",itemModel.getHomeTeamID());
                   i.putExtra("AwayTeamID",itemModel.getAwayTeamID());


                // AddData(int score,String Animename,String Watch_Ep_no,String Total_ep)
                  v.getContext().startActivity(i);
                //Toast.makeText(mContext,ep,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public long getHeaderId(int position) {
        // ItemModel itemModel = mList.get(position);
        // return itemModel.getMillies().hashCode();
       /*
        if (position == 0) {
            return -1;
        } else {
        }
        */
        return getItemId(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header, parent, false);
        return new ItemHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
       /*
       Event event = events.get(position);
  holder.bindHeader(DateService.getEventStatus(event));
  */
        if (holder instanceof ItemHeaderViewHolder) {
            if (getItem(position).getDate() != null) {
                match_model itemModel = mList.get(position);
                String header = itemModel.getDate();
                ((ItemHeaderViewHolder) holder).header.setText(header);
            }
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public match_model getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
      //  Log.d("HasCode"," " + getItem(position).hashCode());
        return getItem(position).getMillies();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void setImage(String HomeTeam,String AwayTeam,RecyclerView.ViewHolder holder){
     //   Log.d("ItemAdapterTeam"," " + HomeTeam);
        switch (HomeTeam){
            case "Liverpool":
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Chelsea":
               // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "AFC Bournemouth":
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "Crystal Palace":
               // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "Man City":
               // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "Watford":
               // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "Man Utd":
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "Spurs":
              //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "Everton":
              //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "Wolves":
              //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case "Burnley":
               // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "Southampton":
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "Leicester":
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Newcastle":
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "Arsenal":
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "Brighton":
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "Cardiff":
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "Fulham":
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "Huddersfield":
               // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "West Ham":
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:

        }

        switch (AwayTeam){
            case "Liverpool":
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Chelsea":
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "AFC Bournemouth":
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "Crystal Palace":
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "Man City":
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "Watford":
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "Man Utd":
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "Spurs":
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "Everton":
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "Wolves":
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case "Burnley":
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "Southampton":
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "Leicester":
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Newcastle":
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "Arsenal":
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "Brighton":
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "Cardiff":
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "Fulham":
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "Huddersfield":
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "West Ham":
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                ((ItemViewHolder)holder).awayTeamImage.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:

        }



    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView homeTeam,homeTeamScore,awayTeam,awayTeamScore,gameTime,match_venue,gameTimePast;
        ImageView homeTeamImage,awayTeamImage;
        public ItemViewHolder(View itemView) {
            super(itemView);

            homeTeam = itemView.findViewById(R.id.text_home_team_name);
            homeTeamScore = itemView.findViewById(R.id.text_home_team_score);
            homeTeamImage = itemView.findViewById(R.id.home_team_image);

            awayTeam = itemView.findViewById(R.id.text_away_team_name);
            awayTeamScore = itemView.findViewById(R.id.text_away_team_score);
            awayTeamImage = itemView.findViewById(R.id.away_team_image);


            gameTime = itemView.findViewById(R.id.text_time);
            gameTimePast = itemView.findViewById(R.id.text_game_time);
            match_venue = itemView.findViewById(R.id.text_venue);


            // homeTeam = itemView.findViewById(R.id.HomeTeam);
           // AwayTeam = itemView.findViewById(R.id.AwayTeam);
        }
    }

    public static class ItemHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        public ItemHeaderViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
        }
    }



}