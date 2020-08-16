package table.football.soccer.livescore.epl.epllive.Fragments_match;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
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

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.commentry_adpater;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Team_Detail;
import table.football.soccer.livescore.epl.epllive.model.commentry_model;
import table.football.soccer.livescore.epl.epllive.model.match_model;


public class Fragment_commentry extends Fragment {


    View v;
    commentry_adpater commentryAdpater;
    RecyclerView cRecyclerView;
    List<commentry_model> commentryModelList;
    Handler handlerForloop = new Handler();
    Handler handlerForCommentry = new Handler();
    RequestQueue requestQueue;


    //Actviity compaonent
    AVLoadingIndicatorView TimeLoadingProgress;
    TextView HomeTeam_name,AwayTeam_name,HomeTeamScore,AwayTeamScore,Match_time;
    ImageView HomeTeamImage,AwayTeamImage;

    ImageButton match_sub_scribed_button;

    //text widget
    void init(){
        TimeLoadingProgress = getActivity().findViewById(R.id.loading_progress);

        HomeTeam_name = getActivity().findViewById(R.id.homeTeam_name);
        AwayTeam_name = getActivity().findViewById(R.id.awayTeam_name);
        HomeTeamScore = getActivity().findViewById(R.id.homeTeamScore);
        AwayTeamScore = getActivity().findViewById(R.id.awayTeamScore);
        Match_time =   getActivity().findViewById(R.id.match_time_past);
        //"ð" U+26BD

        HomeTeamImage = getActivity().findViewById(R.id.homeTeam_image);
        AwayTeamImage = getActivity().findViewById(R.id.awayTeam_image);

        match_sub_scribed_button = getActivity().findViewById(R.id.notification_button);

    }


    TextView CommentryText;
    SwipeRefreshLayout swipeRefreshLayout;


    String MatchStatus = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_commentry, container, false);

        //Activity componet
        init();

        final String MatchId     = getActivity().getIntent().getExtras().getString("MatchId");

        //Team
        String   HomeTeam =  getActivity().getIntent().getExtras().getString("HomeTeam");
        String   AwayTeam =  getActivity().getIntent().getExtras().getString("AwayTeam");
        //TeamID
        String   HomeTeamID =  getActivity().getIntent().getExtras().getString("HomeTeamID");
        String   AwayTeamID =  getActivity().getIntent().getExtras().getString("AwayTeamID");

       // Log.d("pendinIntwne", "onCreateView: "+MatchId +" HomeTeam::"+HomeTeam+" AwayTeam::"+AwayTeam);

        CommentryText = v.findViewById(R.id.CommentryStatus_Text);
        swipeRefreshLayout = v.findViewById(R.id.Swipe);

        cRecyclerView = v.findViewById(R.id.recycler_commentry);
        commentryModelList = new ArrayList<>();
        cRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        commentryAdpater = new commentry_adpater(commentryModelList,getContext(),MatchId);
        cRecyclerView.setAdapter(commentryAdpater);
        cRecyclerView.setHasFixedSize(true);
//        cRecyclerView.setItemViewCacheSize(20);
        cRecyclerView.setDrawingCacheEnabled(true);
        cRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        cRecyclerView.setNestedScrollingEnabled(false);
        if (getContext()!=null){
            requestQueue = Volley.newRequestQueue(getContext());
        }

      //  TimeLoadingProgress = v.findViewById(R.id.loading_progress);


        //non refresing component of activity
        InitScoreBoard(HomeTeam,AwayTeam,HomeTeamID,AwayTeamID);

        //MatchData

    //    final String MatchStatus = getActivity().getIntent().getExtras().getString("MatchStatus");

        //InitScoreBoard(HomeTeam,AwayTeam,HomeTeamScore,AwayTeamScore,HomeTeamID,AwayTeamID);


      //  swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CommentryText.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(true);
            }
        });
     //   LoadMatchData(MatchId);

        runLoop(MatchId);
        runLoopForcommentry(MatchId);

        return v;
    }


    //not refresing component of team
    private void InitScoreBoard(final String HomeTeam, final String AwayTeam,final String HomeTeamID, final String AwayTeamID){


         HomeTeam_name.setText(HomeTeam);
         AwayTeam_name.setText(AwayTeam);

         HomeTeamImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(getContext(), Team_Detail.class);
                 i.putExtra("TeamName",HomeTeam);
                 i.putExtra("TeamID",HomeTeamID);
                 getContext().startActivity(i);
             }
         });


        AwayTeamImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Team_Detail.class);
                i.putExtra("TeamName",AwayTeam);
                i.putExtra("TeamID",AwayTeamID);
                getContext().startActivity(i);
            }
        });


        setImage(HomeTeam,AwayTeam);
    }


    private void setImage(String HomeTeam,String AwayTeam){

        switch (HomeTeam){
            case "Liverpool":
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                HomeTeamImage.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Chelsea":
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "AFC Bournemouth":
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "Crystal Palace":
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "Man City":
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "Watford":
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "Man Utd":
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "Spurs":
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "Everton":
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "Wolves":
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case "Burnley":
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "Southampton":
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "Leicester":
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Newcastle":
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "Arsenal":
                HomeTeamImage.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "Brighton":
                HomeTeamImage.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "Cardiff":
                HomeTeamImage.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "Fulham":
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "Huddersfield":
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "West Ham":
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                HomeTeamImage.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:

        }

        switch (AwayTeam){
            case "Liverpool":
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                AwayTeamImage.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Chelsea":
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "AFC Bournemouth":
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "Crystal Palace":
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "Man City":
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "Watford":
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "Man Utd":
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "Spurs":
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "Everton":
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "Wolves":
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case "Burnley":
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "Southampton":
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "Leicester":
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "Newcastle":
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "Arsenal":
                AwayTeamImage.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "Brighton":
                AwayTeamImage.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "Cardiff":
                AwayTeamImage.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "Fulham":
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "Huddersfield":
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "West Ham":
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                AwayTeamImage.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:

        }
    }



    private void loadCommentry(String url, final String UserAgent, final String MatchID){
        swipeRefreshLayout.setRefreshing(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // System.out.println(response);
                //  Log.d("VolleyRequest",response);

                ArrayList<commentry_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    JSONObject Commentry = obj.getJSONObject("events");
                    commentry_model item;
                    JSONArray CommentryList = Commentry.getJSONArray("content");
                   // System.out.print(CommentryList.length());
                 //   Log.d("CommentryFragment"," "  +CommentryList.length());


                    if (CommentryList.length()==0){
                        CommentryText.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    //    Log.d("CommentryFragment","  "+ CommentryList.length());
                    }



                    for(int i=0;i<CommentryList.length();i++){
                        JSONObject CommentryData = CommentryList.getJSONObject(i);
                        String type = CommentryData.getString("type");
                        item = new commentry_model();


                     //   String playerIds = String.valueOf(playerId.get(0));

                        JSONObject TimeData=null;
                        if (!Objects.equals(type, "lineup")){
                             TimeData = CommentryData.getJSONObject("time");
                             item.setTime(TimeData.getString("label")+"'");
                        }

                                                                                                                             //red card
                        if(Objects.equals(type, "goal")||Objects.equals(type, "yellow card")||Objects.equals(type, "own goal")||Objects.equals(type, "red card")){
                            JSONArray playerId = CommentryData.getJSONArray("playerIds");
                           // String playerIds =
                            item.setPlayerID(String.valueOf(playerId.get(0)));
                           // Log.d("CommentryFragment"," "  +playerId.length() + " " +playerIds);
                        }


                        item.setCommentryText(CommentryData.getString("text"));
                        item.setImageType(CommentryData.getString("type"));
                        data.add(item);


                    }
                    if (commentryModelList.size()>0){
                        commentryModelList.clear();
                        commentryModelList.addAll(data);
                        commentryAdpater.notifyDataSetChanged();
                    }else {
                        commentryModelList.addAll(data);
                        commentryAdpater.notifyDataSetChanged();
                    }



                }catch (Exception e){
                  //  Log.d("CommentryFragment"," "  +e);
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                CommentryText.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            //    Log.d("VolleyRequestError",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent",UserAgent);
                params.put("Referer", "https://www.premierleague.com/match/"+MatchID);
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
        };
      //  stringRequest.setShouldCache(false);
        //creating a request queue
        if (getContext()!=null){

            //adding the string request to request queue request.setShouldCache(false);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
          //  requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }

    }

    public void LoadMatchData(final String MatchID, final String UserAgent){
        //https://footballapi.pulselive.com/football/fixtures/38459?altIds=true
        TimeLoadingProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/fixtures/"+ MatchID+"?altIds=true", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject obj = new JSONObject(response);
                    //
                    MatchStatus = obj.getString("status");
                    JSONArray TeamArray = obj.getJSONArray("teams");

                    Log.d("commentlog", "onResponse: " + MatchStatus);
                    if (MatchStatus.equals("C")){
                        match_sub_scribed_button.setVisibility(View.GONE);
                    }



                    //MatchTime
                    JSONObject Clock = obj.getJSONObject("clock");
                    final String Time = Clock.getString("label");


                    //Socre
                    //For Home Team
                    JSONObject HomeTeam = TeamArray.getJSONObject(0);
                    final String HomeScore = String.valueOf(HomeTeam.getInt("score"));

                    //For Away Team
                    JSONObject AwayTeam = TeamArray.getJSONObject(1);
                    final String AwayScore = String.valueOf(AwayTeam.getInt("score"));



                    if (getActivity()!=null){
//                        HomeTeamScore,AwayTeamScore,Match_time
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                HomeTeamScore.setText(HomeScore);
                                AwayTeamScore.setText(AwayScore);
                               if (MatchStatus.equals("C")){
                                   Match_time.setText("FT");
                               }
                               else {
                                   Match_time.setText(Time.substring(0,3));
                               }
                            }
                        });


                    }



                    if (MatchStatus.equals("C")){
                        handlerForloop.removeMessages(0);
                        handlerForCommentry.removeMessages(0);
                    }


                }catch (Exception e){
                 //   System.out.println(e);
                 //   Log.d("CommentryFragment"," " +e);
                }
                swipeRefreshLayout.setRefreshing(false);
                TimeLoadingProgress.hide();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                //ErrorTextView.setVisibility(View.VISIBLE);
            //    Log.d("CommentryFragment",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", UserAgent);
                params.put("Referer", "https://www.premierleague.com/match/"+MatchID);
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }



    public void runLoop(final String MatchId){
        final String UserAgent = new WebView(getContext()).getSettings().getUserAgentString();
        handlerForloop.post(new Runnable() {
          //  int i = 0;
            @Override
            public void run() {


                LoadMatchData(MatchId,UserAgent);


                handlerForloop.postDelayed(this,20000);
            }
        });
    }


    public void runLoopForcommentry(final String MatchId){
        handlerForCommentry.post(new Runnable() {
            @Override
            public void run() {
               if(getContext()!=null){
                   loadCommentry("https://footballapi.pulselive.com/football/fixtures/" +    MatchId + "/textstream/EN?pageSize=300&sort=desc",new WebView(getContext()).getSettings().getUserAgentString(),MatchId);

               }
                handlerForCommentry.postDelayed(this,20000);
            }
        });
    }



    @Override
    public void onStop() {
        super.onStop();
        handlerForloop.removeMessages(0);
        handlerForCommentry.removeMessages(0);
       // new commentry_adpater.getImage().cancel(true);
    }
}
