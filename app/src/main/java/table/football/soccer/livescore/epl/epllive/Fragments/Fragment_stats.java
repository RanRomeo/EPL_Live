package table.football.soccer.livescore.epl.epllive.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import table.football.soccer.livescore.epl.epllive.Adapters.LineUpRecyclerHeaderItemAdapter;
import table.football.soccer.livescore.epl.epllive.CopyRight_Activity;
import table.football.soccer.livescore.epl.epllive.Custom_dailog.fav_team_setting;
import table.football.soccer.livescore.epl.epllive.Player_activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Team_Detail;
import table.football.soccer.livescore.epl.epllive.Team_comparision_activity;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.activity_player_stats;
import table.football.soccer.livescore.epl.epllive.activity_team_stats;
import table.football.soccer.livescore.epl.epllive.model.table_model;
import table.football.soccer.livescore.epl.epllive.notification_setting_activity;


public class Fragment_stats extends Fragment {


    View v;


    LinearLayout GoalLayout,AssitLayout,
            team_top_gaole_cantinaer,
            clean_sheets_container;

    TextView team_clean_sheets_number,team_goal_nubmer,player_assist_number,player_goal_number;
    ImageView top_goal_player_image,top_assist_player_image,top_team_image_goal,team_clean_sheets_image;

    Button PlayerStats,
            culb_stats,
            notification_setting_button,
            fav_team_button,
            team_compare_button,
            share_App_button,
            Rate_app_button,
            privacy_button,
            copy_right_button;


    AVLoadingIndicatorView stats_loading;
    Boolean goalLoaded = false,assitLoaded= false,
            teamgoalloaded = false,teamcleansheets = false,
            goalPlayerLoaded = false,goalAssitLoaded = false;
    LinearLayout adContainer;
    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_stats, container, false);

        stats_loading = v.findViewById(R.id.stats_loading);



        player_assist_number = v.findViewById(R.id.player_assist_number);
        player_goal_number = v.findViewById(R.id.player_goal_number);
        top_goal_player_image = v.findViewById(R.id.top_goal_player_image);
        top_assist_player_image = v.findViewById(R.id.top_assist_player_image);


        // team_clean_sheets_number
        team_clean_sheets_number = v.findViewById(R.id.team_clean_sheets_number);
        team_clean_sheets_image = v.findViewById(R.id.team_clean_sheets_image);

        // team_gaol_number
        team_goal_nubmer = v.findViewById(R.id.team_goal_nubmer);
        top_team_image_goal = v.findViewById(R.id.top_team_image_goal);

        GoalLayout = v.findViewById(R.id.goal_layout);
        AssitLayout = v.findViewById(R.id.assist_layout);

        stats_loading.show();


        team_top_gaole_cantinaer = v.findViewById(R.id.team_top_gaole_cantinaer);
        clean_sheets_container = v.findViewById(R.id.clean_sheets_container);



    String UserAgent = new WebView(getContext()).getSettings().getUserAgentString();

        initButton();
        getTopGoal(UserAgent);
        getTopAssit(UserAgent);
        getTopTeamCleanSheets();
        getTopTeamGoal();

        mAdView = v.findViewById(R.id.adView);
        mAdView = v.findViewById(R.id.adView);
        adContainer = v.findViewById(R.id.banner_container);

        showAds();

        return v;
    }



    private void showAds(){
        if(new PrefManager_googleAdSetting(getContext()).CheckAdCanShow()){
            showgoogleAds();
            adContainer.setVisibility(View.VISIBLE);
        }else if (new PrefManager_Facebook_Adsetting(getContext()).CheckAdCanShow()){
            mAdView.setVisibility(View.GONE);
            showFacebookAds();
            adContainer.setVisibility(View.VISIBLE);
        }else {
            adContainer.setVisibility(View.GONE);
        }

    }

    private void showgoogleAds(){

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // AdRequest.Builder.addTestDevice("01DE48E77EEFC7F7C6E3A641708BC457")
        mAdView.setAdListener(new com.google.android.gms.ads.AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (mAdView!=null){
                    mAdView.destroy();
                    mAdView.setVisibility(View.GONE);
                }
            //    Log.d("homeAtivity", "onAdFailedToLoad: " + i);
                //showAds();
                showfacebookAdCan();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            //this method is perform when ads is clicked
            @Override
            public void onAdOpened() {
                super.onAdOpened();
                new PrefManager_googleAdSetting(getContext()).SaveClickedTime();
                // Toast.makeText(getContext(), "Adopen", Toast.LENGTH_SHORT).show();
                if (mAdView!=null){
                    mAdView.destroy();
                }
                mAdView.setVisibility(View.GONE);
                // showFacebookAdsCan();
                showAds();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                //  Toast.makeText(getContext(), "Adloaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                //   new PrefManager_googleAdSetting(getContext()).SaveClickedTime();
                //   mAdView.setVisibility(View.GONE);
                //   showFacebookAdsCan();
                //  Toast.makeText(getContext(), "Adclicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
    }

    private void showFacebookAds(){
        adView = new com.facebook.ads.AdView(getContext(), getContext().getString(R.string.Facebook_bannerAd), AdSize.BANNER_HEIGHT_50);
        // AdSettings.addTestDevice("903898e4-a7b9-422f-b811-47646f070bef");



        // Add the ad view to your activity layout
        adContainer.addView(adView);
        //
        // Request an ad
        //AdSettings.addTestDevice("56d51070-f693-46aa-9379-38cc81df9ae5");
        adView.loadAd();

        adView.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                if (adView != null) {
                    adView.destroy();
                }

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

                //  LasTimeClicked = System.currentTimeMillis();
                //Saves current time and clicke trie
                new PrefManager_Facebook_Adsetting(getContext()).SaveClickedTime();
                if (adView != null) {
                    adView.destroy();
                }
                showAds();
                //  showGoogleAdsChanvce();
                /*
                    it will check if user clicked value is not equal to 0 if clicked it will return true
                    if not it if will return false

                    if it return true than we will check time differnce betwern last clicked time and current time

                    if it  return false than we will save the time;



                if ( new PrefManager_Adsetting(getContext()).UserClickedTime()){
                    new PrefManager_Adsetting(getContext()).getTimeDiff();
                    Log.d("PrefManager_Adsetting"," -------------------- ");
                    new PrefManager_Adsetting(getContext()).SaveLastClickedTime();
                }else {

                }
                  */
                // new PrefManager_Adsetting(getContext()).SaveLastClickedTime();
                //SharedPreferences sp = getSharedPreferences(PREFS_AdSettings ,getContext().MODE_PRIVATE);
                // sp.edit().putInt(GAME_SCORE,100).commit();
                /*

                 */

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });


    }

    private void  showfacebookAdCan(){
        if (new PrefManager_Facebook_Adsetting(getContext()).CheckAdCanShow()){
            mAdView.setVisibility(View.GONE);
            showFacebookAds();
            adContainer.setVisibility(View.VISIBLE);
        }
    }






    void initButton(){

        //PLayersStats
        PlayerStats = v.findViewById(R.id.butto_player_stats);
        PlayerStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), activity_player_stats.class);
                startActivity(i);

            }
        });


        //TeamStats
        culb_stats = v.findViewById(R.id.culb_stats);
        culb_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), activity_team_stats.class);
                startActivity(i);
            }
        });


        //Team compate button
        team_compare_button =v.findViewById(R.id.team_compare_button);
        team_compare_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Team_comparision_activity.class);
                startActivity(i);
            }
        });


        //notification button
        notification_setting_button = v.findViewById(R.id.notification_button);
        notification_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), notification_setting_activity.class);
                startActivity(i);
            }
        });

        //fav button
        fav_team_button = v.findViewById(R.id.fav_team_button);
        fav_team_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav_team_setting favTeamSetting_dailog = new fav_team_setting();
                favTeamSetting_dailog.setCancelable(false);
                favTeamSetting_dailog.show(getChildFragmentManager(),"FavTeamSetting");
            }
        });


        share_App_button = v.findViewById(R.id.share_app_button);
        share_App_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });

        Rate_app_button = v.findViewById(R.id.Rate_app_button);
        Rate_app_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateme(getContext());
            }
        });
        //privacy_button,
         ///       copy_right_button;
        copy_right_button = v.findViewById(R.id.copy_right_button);
        copy_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),CopyRight_Activity.class);
                startActivity(i);
            }
        });

        privacy_button = v.findViewById(R.id.privacy_button);
        privacy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPrivacPolicy();
            }
        });

    }


    void shareApp(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Premier League  Live Score,Fixture,Result,News   https://play.google.com/store/apps/details?id="+getActivity().getPackageName();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "EPL Videos,score,News");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    void  openPrivacPolicy(){
        String url = "https://sites.google.com/view/epllive/home";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void rateme(Context context){
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }


    void getTopGoal(final String  UserAgent){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/stats/ranked/players/goals?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // ArrayList<table_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                  //  JSONArray Table = obj.getJSONArray("tables");
                   /// JSONObject MatchData = Table.getJSONObject(0);
                   // JSONArray TeamTable = MatchData.getJSONArray("entries");
                    JSONObject getStat = obj.getJSONObject("stats");
                    JSONArray getDataArray = getStat.getJSONArray("content");


                    JSONObject getTopGoalePlayer = getDataArray.getJSONObject(0);
                    JSONObject getPlayerData = getTopGoalePlayer.getJSONObject("owner");
                    int goal = getTopGoalePlayer.getInt("value");
                    int playerId = getPlayerData.getInt("id");

                    JSONObject nameObje = getPlayerData.getJSONObject("name");
                    String Name = nameObje.getString("display");

                    player_goal_number.setText(String.valueOf(goal));

                    new getImage(String.valueOf(playerId),UserAgent,Name).execute();
                 // Log.d("Fragment_statGoal"," " + goal  + " playerId " +playerId );

                }catch (Exception e){

                  //  Log.d("Fragment_seat_season_data"," " +  e);
                }
         //       Boolean goalLoaded = false,assitLoaded= false,teamgoalloaded = false,teamcleansheets = false;
//                Boolean goalLoaded = false,assitLoaded= false,
//                        teamgoalloaded = false,teamcleansheets = false,
//                        goalPlayerLoaded = false,goalAssitLoaded = false;


                goalLoaded = true;
                hideLoader();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Swipe.setRefreshing(false);
              //  Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
               // Nested_table_view.setVisibility(View.GONE);
               // Log.d("Fragment_seat_season_data"," " +  error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/stats");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;}
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

    void getTopAssit(final String UserAgent){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/stats/ranked/players/goal_assist?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  ArrayList<table_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    //  JSONArray Table = obj.getJSONArray("tables");
                    /// JSONObject MatchData = Table.getJSONObject(0);
                    // JSONArray TeamTable = MatchData.getJSONArray("entries");
                    JSONObject getStat = obj.getJSONObject("stats");
                    JSONArray getDataArray = getStat.getJSONArray("content");


                    JSONObject getTopGoalePlayer = getDataArray.getJSONObject(0);
                    JSONObject getPlayerData = getTopGoalePlayer.getJSONObject("owner");
                    int goalAssit = getTopGoalePlayer.getInt("value");
                    int playerId = getPlayerData.getInt("id");
                    player_assist_number.setText(String.valueOf(goalAssit));
                    JSONObject nameObje = getPlayerData.getJSONObject("name");
                    String Name = nameObje.getString("display");

                    new getImage2(String.valueOf(playerId),UserAgent,Name).execute();
                  //  Log.d("Fragment_statGoal","goalAssit " + goalAssit  + " playerId " +playerId );

                }catch (Exception e){

                   // Log.d("Fragment_seat_season_data"," " +  e);
                }
                assitLoaded =true;
                hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Swipe.setRefreshing(false);
                //  Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                // Nested_table_view.setVisibility(View.GONE);
                // Log.d("Fragment_seat_season_data"," " +  error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/stats");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;}
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

    void getTopTeamGoal(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/stats/ranked/teams/goals?page=0&pageSize=20&compSeasons=210&comps=1&altIds=true", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ArrayList<table_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    //  JSONArray Table = obj.getJSONArray("tables");
                    /// JSONObject MatchData = Table.getJSONObject(0);
                    // JSONArray TeamTable = MatchData.getJSONArray("entries");
                    JSONObject getStat = obj.getJSONObject("stats");
                    JSONArray getDataArray = getStat.getJSONArray("content");


                    JSONObject getTopGoalePlayer = getDataArray.getJSONObject(0);
                    JSONObject getPlayerData = getTopGoalePlayer.getJSONObject("owner");
                    int goal = getTopGoalePlayer.getInt("value");
                    final int playerId = getPlayerData.getInt("id");

                    team_goal_nubmer.setText(String.valueOf(goal));
                    TeamData(String.valueOf(playerId),top_team_image_goal);
                  //  Log.d("Fragment_statGoal"," " + goal  + " playerId " +playerId );
                    //team_top_gaole_cantinaer,
                    // clean_sheets_container;
                    team_top_gaole_cantinaer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getContext(), Team_Detail.class);
                            i.putExtra("TeamName",TeamName(String.valueOf(playerId)));
                            i.putExtra("TeamID",String.valueOf(playerId));
                            getContext().startActivity(i);
                        }
                    });

                }catch (Exception e){

                  //  Log.d("Fragment_seat_season_data"," " +  e);
                }
                teamgoalloaded = true;
                hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Swipe.setRefreshing(false);
                //  Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                // Nested_table_view.setVisibility(View.GONE);
                // Log.d("Fragment_seat_season_data"," " +  error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/stats");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;}
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

    void getTopTeamCleanSheets(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/stats/ranked/teams/clean_sheet?page=0&pageSize=20&compSeasons=210&comps=1&altIds=true", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<table_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    //  JSONArray Table = obj.getJSONArray("tables");
                    /// JSONObject MatchData = Table.getJSONObject(0);
                    // JSONArray TeamTable = MatchData.getJSONArray("entries");
                    JSONObject getStat = obj.getJSONObject("stats");
                    JSONArray getDataArray = getStat.getJSONArray("content");


                    JSONObject getTopGoalePlayer = getDataArray.getJSONObject(0);
                    JSONObject getPlayerData = getTopGoalePlayer.getJSONObject("owner");
                    int goal = getTopGoalePlayer.getInt("value");
                    final int playerId = getPlayerData.getInt("id");

                    team_clean_sheets_number.setText(String.valueOf(goal));
                    TeamData(String.valueOf(playerId),team_clean_sheets_image);
                   // Log.d("Fragment_statGoal"," " + goal  + " playerId " +playerId );
                    clean_sheets_container.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getContext(), Team_Detail.class);
                            i.putExtra("TeamName",TeamName(String.valueOf(playerId)));
                            i.putExtra("TeamID",String.valueOf(playerId));
                            getContext().startActivity(i);
                        }
                    });

                }catch (Exception e){

                  //  Log.d("Fragment_seat_season_data"," " +  e);
                }
                teamcleansheets = true;
                hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Swipe.setRefreshing(false);
                //  Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                // Nested_table_view.setVisibility(View.GONE);
                // Log.d("Fragment_seat_season_data"," " +  error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/stats");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;}
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

    public class getImage extends AsyncTask<String,Void,String> {


        String id;
        String USER_AGENT;
        String PlayerName;

        public getImage(String id, String USER_AGENT, String playerName) {
            this.id = id;
            this.USER_AGENT = USER_AGENT;
            PlayerName = playerName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // holder.goal_player_view.setVisibility(View.INVISIBLE);
            // holder.goal_player_text.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
           // String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
            Document document,document1;
            String Link = null;
            try{
                document = Jsoup.connect("https://www.premierleague.com/players/"+id).userAgent(USER_AGENT).get();
                Elements LastPage = document.select(".imgContainer");
                String dataId = LastPage.select("img").attr("data-player");
                Link = "https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+dataId+".png";

                final String finalLink = Link;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {//top_goal_player_image top_assist_player_image
                        Picasso.with(getContext()).load(finalLink).into(top_goal_player_image);
                    }
                });






            }catch(Exception e ){

                //   https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/p37572.png
            }

            return Link;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            GoalLayout.setVisibility(View.VISIBLE);
            goalPlayerLoaded =true;
            hideLoader();
            GoalLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(),Player_activity.class);
                    i.putExtra("PlayerID",id);
                    i.putExtra("PlayerName","PlayerName");
                    getContext().startActivity(i);
                }
            });
        }
    }

    public class getImage2 extends AsyncTask<String,Void,String> {


        String id;
        String USER_AGENT;
        String PlayerName;

        public getImage2(String id, String USER_AGENT, String playerName) {
            this.id = id;
            this.USER_AGENT = USER_AGENT;
            PlayerName = playerName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // holder.goal_player_view.setVisibility(View.INVISIBLE);
            // holder.goal_player_text.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
           // String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
            Document document;
            String Link = null;
            try{
                document = Jsoup.connect("https://www.premierleague.com/players/"+id).userAgent(USER_AGENT).get();
                Elements LastPage = document.select(".imgContainer");
                String dataId = LastPage.select("img").attr("data-player");
                Link = "https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+dataId+".png";

                final String finalLink = Link;


                if (getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {//top_goal_player_image top_assist_player_image
                           Picasso.with(getContext()).load(finalLink).into(top_assist_player_image);
                        }
                    });
                }







            }catch(Exception e ){

                //   https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/p37572.png
            }

            return Link;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AssitLayout.setVisibility(View.VISIBLE);
            goalAssitLoaded = true;
            hideLoader();
            AssitLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // final String PlayerID = Objects.requireNonNull(getIntent().getExtras()).getString("PlayerID");
                   // final String PlayerName = Objects.requireNonNull(getIntent().getExtras().getString("PlayerName")).replace(" ","-");
                    Intent i = new Intent(getContext(),Player_activity.class);
                    i.putExtra("PlayerID",id);
                    i.putExtra("PlayerName","PlayerName");
                    getContext().startActivity(i);
                }
            });
        }
    }


    private void TeamData(String TeamID,ImageView Team_image){
        //ButtonEvents("Hello");
        switch (TeamID){
            case "1":

                Team_image.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "127"://bour
                Team_image.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "131":
                Team_image.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "43":

                Team_image.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "46":
                Team_image.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "4"://chelease

                Team_image.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "6":

                Team_image.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "7"://everton

                Team_image.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "34"://fulham
                Team_image.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "159"://huddersfield
                Team_image.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "26"://Leincester
                Team_image.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "10"://Llevirpool
                Team_image.setImageResource(R.drawable.ic_logo_liverpool);
                break;
            case "11"://Manchester city
                Team_image.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "12"://Manchester United
                Team_image.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "23"://New Castle
                Team_image.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "20"://Southampton
                Team_image.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "21"://tottenhamhotspur
                Team_image.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "33"://watford
                Team_image.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "25"://west ham
                Team_image.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            case "38"://Leincester
                Team_image.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            default:
                Team_image.setImageResource(R.drawable.missing_thumb);
                break;

        }

    }

    void hideLoader(){
     //   Log.d("loading", "hideLoader: " + "goalLoaded " + goalLoaded + " assitLoaded " +assitLoaded + " teamgoalloaded  "
         //       +teamgoalloaded + " teamcleansheets "+teamcleansheets + " goalPlayerLoaded" + goalPlayerLoaded + " goalAssitLoaded:"+goalAssitLoaded);
        if (goalLoaded && assitLoaded&& teamgoalloaded&& teamcleansheets&&goalPlayerLoaded&&goalAssitLoaded){
            stats_loading.hide();
        }
    }

    private String TeamName(String TeamID){
        //ButtonEvents("Hello");
        switch (TeamID){
            case "1":
               return  "Arsenal";
            case "127"://bour
               return   "AFC Bournemouth";
            case "131":
                return   "Bringhton and Hove Albion";
            case "43":
                return   "Burnley";
            case "46":
                return   "Cardiff City";
            case "4"://chelease
                return   "Chelsea";
            case "6":
                return   "Crystal Palace";
            case "7"://everton
                return   "Everton";
            case "34"://fulham
                return   "Fulham";
            case "159"://huddersfiel
                return   "Huddersfield Town";
            case "26"://Leinceste
                return   "Leicester City";
            case "10"://Llevirpool
                return   "Liverpool";
            case "11"://Manchester city
                return   "Manchester City";
            case "12"://Manchester United
                return   "Manchester United";
            case "23"://New Castle
                return   "Newcastle United";
            case "20"://Southampton
                return   "Southampton";
            case "21"://tottenhamhotspur
                return   "Tottenham Hotspur";
            case "33"://watford
                return   "Watford";
            case "25"://west ham
                return   "West Ham United";
            case "38"://Leincester
                return   "Wolverhampton Wanderers";
            default:
                return  null;

        }

    }



}
