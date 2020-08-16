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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import table.football.soccer.livescore.epl.epllive.Match_Activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.match_model;

public class today_match_adapter extends RecyclerView.Adapter<today_match_adapter.Viewholder> {

    private List<match_model> mList;
    public Context context;
    Handler handlerForloop = new Handler();
    public today_match_adapter(List<match_model> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_live_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
        final match_model itemModel = mList.get(position);
     //   String city = itemModel.getStadiumName()+","+itemModel.getCityName();
        final String matchStats = itemModel.getMatchStatus();

        holder.homeTeam_Text.setText(itemModel.getHomeTeam());
        holder.awayTeam_Text.setText(itemModel.getAwayTeam());

//        holder.homeTeam_text_score.setText(itemModel.getHomeTeamScore());
//        holder.awayTeam_Text_score.setText(itemModel.getAwayTeamScore());
        holder.homeTeam_text_score.setText("2");
        holder.awayTeam_Text_score.setText("2");
       // holder.matchTime.setText(itemModel.getTime());
        holder.matchTime.setText("89'");

        final String UserAgnet = new WebView(context).getSettings().getUserAgentString();
        setImage(itemModel.getHomeTeam(),itemModel.getAwayTeam(),holder);
        Log.d("LiveMatch", " "+  matchStats );
        if (matchStats.equals("L")){
            handlerForloop.post(new Runnable() {
                @Override
                public void run() {
                    updateMatchData("https://footballapi.pulselive.com/football/fixtures/"+itemModel.getMathcId(),holder,UserAgnet,itemModel.getMathcId());
                    handlerForloop.postDelayed(this,20000);
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Match_Activity.class);
                 i.putExtra("matchid",itemModel.getMathcId());
                i.putExtra("HomeTeam",itemModel.getHomeTeam());
                i.putExtra("AwayTeam",itemModel.getAwayTeam());
                i.putExtra("homeTeamScore",itemModel.getHomeTeamScore());
                i.putExtra("awayTeamScore",itemModel.getAwayTeamScore());
                i.putExtra("MatchId",itemModel.getMathcId());
                i.putExtra("MatchStatus",matchStats);
                i.putExtra("HomeTeamID",itemModel.getHomeTeamID());
                i.putExtra("AwayTeamID",itemModel.getAwayTeamID());
                v.getContext().startActivity(i);
            }
        });

    }


    public void setImage(String HomeTeam,String AwayTeam,Viewholder holder){
       //  Log.d("ItemAdapterTeam"," " + HomeTeam + " AwayTeam " + AwayTeam);
        switch (HomeTeam){
            case "Liverpool":
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Chelsea":
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "AFC Bournemouth":
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "Crystal Palace":
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "Man City":
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "Watford":
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "Man Utd":
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "Spurs":
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "Everton":
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "Wolves":
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case "Burnley":
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "Southampton":
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "Leicester":
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Newcastle":
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "Arsenal":
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "Brighton":
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "Cardiff":
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "Fulham":
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "Huddersfield":
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "West Ham":
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.home_Team_image.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:

        }

        switch (AwayTeam){
            case "Liverpool":
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Chelsea":
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "AFC Bournemouth":
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "Crystal Palace":
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "Man City":
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "Watford":
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "Man Utd":
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "Spurs":
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "Everton":
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "Wolves":
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case "Burnley":
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "Southampton":
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "Leicester":
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Newcastle":
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "Arsenal":
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "Brighton":
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "Cardiff":
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "Fulham":
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "Huddersfield":
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "West Ham":
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                holder.awayTeam_image.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:

        }



    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView matchTime,homeTeam_Text,awayTeam_Text,homeTeam_text_score,awayTeam_Text_score;
        ImageView awayTeam_image,home_Team_image;
        public Viewholder(View itemView) {
            super(itemView);
            //today_
            matchTime = itemView.findViewById(R.id.live_match_time);


            homeTeam_Text = itemView.findViewById(R.id.homeTeam_name_live);
            awayTeam_Text = itemView.findViewById(R.id.awayTeam_name_live);


            homeTeam_text_score = itemView.findViewById(R.id.homeTeamScoreLive);
            awayTeam_Text_score = itemView.findViewById(R.id.away_team_score_live);

          //  awayTeam_image = itemView.findViewById(R.id.homeTeam_image_live);
         //   home_Team_image = itemView.findViewById(R.id.awayTeam_image_live);
            awayTeam_image = itemView.findViewById(R.id.awayTeam_image_live);
            home_Team_image = itemView.findViewById(R.id.homeTeam_image_live);

        }
    }


    public void updateMatchData(String url, @NonNull final Viewholder holder, final String UserAgent, final String MatchID){
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject obj = new JSONObject(response);
                //    Log.d("LiveAdapterTetra"," obhj  " + obj);

                    JSONObject Clock = obj.getJSONObject("clock");
                    Integer time = Clock.getInt("secs");
                    long minutes = TimeUnit.SECONDS
                            .toMinutes(time);
                    String times =String.valueOf(minutes);
                    if (minutes>=95)
                    {
                        times = "FT";
                    }

                    JSONArray Team = obj.getJSONArray("teams");
                    JSONObject HomeTeam = Team.getJSONObject(0);
                   // String HomeTeamScore = String.valueOf(HomeTeam.getInt("score"));
                    //Away Team
                    JSONObject AwayTeam = Team.getJSONObject(1);
                   // String AwayTeamScore = String.valueOf(AwayTeam.getInt("score"));

                    holder.homeTeam_text_score.setText(String.valueOf(HomeTeam.getInt("score")));
                    holder.awayTeam_Text_score.setText(String.valueOf(AwayTeam.getInt("score")));
                    holder.matchTime.setText(times);
               //  Log.d("LiveAdapterTetra"," gameDAta " + time+" "+minutes+" " +times + " " + HomeTeamScore + " "  + AwayTeamScore);
                }catch (Exception e){
                   // Log.d("LiveAdapterTetra", " " +e );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyRequestAdapter",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", UserAgent);
                params.put("Referer", "https://www.premierleague.com/"+MatchID);
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }




}
