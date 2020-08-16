package table.football.soccer.livescore.epl.epllive.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.Team_fixture_Adpter;
import table.football.soccer.livescore.epl.epllive.Adapters.my_team_upcoming_Adapter;
import table.football.soccer.livescore.epl.epllive.Adapters.myteam_table_adapter;
import table.football.soccer.livescore.epl.epllive.Custom_dailog.fav_team_setting;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Team_Detail;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_fav_tem_settings;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.match_model;
import table.football.soccer.livescore.epl.epllive.model.table_model;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_favTeam extends Fragment {



    View v;
    ImageView team_image;
    TextView team_name;

    //Team upcoming fixture starts
    RecyclerView recyclerView;
    my_team_upcoming_Adapter teamFixtureAdpter;
    List<match_model> recentModelList;
    String url = "https://footballapi.pulselive.com/football/fixtures?comps=1&teams=1,127,131,43,46,4,6,7,34,159,26,10,11,12,23,20,21,33,25,38&compSeasons=210&page=0&pageSize=380&statuses=U&altIds=true";
    //Team upcoming fixture ends



    //Team upcoming fixture starts
    RecyclerView recyclerView_result;
    my_team_upcoming_Adapter teamFixtureAdpter__result;
    List<match_model> recentModelList_result;
    String url_result = "https://footballapi.pulselive.com/football/fixtures?comps=1&compSeasons=210&teams=1,127,131,43,46,4,6,7,34,159,26,10,11,12,23,20,21,33,25,38&page=0&pageSize=380&sort=desc&statuses=C&altIds=true";
    //Team upcoming fixture ends
    LinearLayout club_container;

   // LinearLayout TweetContainer;

    //progress for fixtures
    ProgressBar upcoming_progress;
    ProgressBar recent_progress;
    ProgressBar Tweet_progress;
    Handler handler=new Handler();

  //  TextView team_selectro_text;
    AppBarLayout appBarLayout;

    //For table
    myteam_table_adapter tableAdapter;
    RecyclerView recyclerView_table;
    List<table_model> tableModelList;
    CardView table_container;
    ProgressBar table_progress;

    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fav_team, container, false);

        upcoming_progress = v.findViewById(R.id.upcoming_progress);
        recent_progress =v.findViewById(R.id.recent_progress);
        Tweet_progress =v.findViewById(R.id.tweet_progress);
        table_progress =v.findViewById(R.id.table_progress);


       // team_selectro_text = v.findViewById(R.id.team_selectro_text);
        appBarLayout = v.findViewById(R.id.app_bar_layout);

        team_image = v.findViewById(R.id.team_image);
        team_name = v.findViewById(R.id.team_name);

       // TweetContainer = v.findViewById(R.id.tweet_container);
        table_container =v.findViewById(R.id.table_container);
        club_container = v.findViewById(R.id.club_container);

      //  TweetProgress = v.findViewById(R.id.tweet_progress);

        final int TeamID = new Pref_fav_tem_settings(getContext()).getFavTeam();

        mAdView = v.findViewById(R.id.adView);
        //0 is no team selected
        if (TeamID!=0){


            SetTeamNameAndImage(TeamID);
            showUpcomingFixture(TeamID);
            showUpcomingResult(TeamID);
            showTable(String.valueOf(TeamID));

            showTweets(TwiiterHandler(TeamID));

            team_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), Team_Detail.class);
                    i.putExtra("TeamName",TeamName(String.valueOf(TeamID)));
                    i.putExtra("TeamID",String.valueOf(TeamID));
                    getContext().startActivity(i);
                }
            });





        }

        showAds();
        return v;
    }


    private void showAds(){
        if(new PrefManager_googleAdSetting(getContext()).CheckAdCanShow()){
            showgoogleAds();
        }else if (new PrefManager_Facebook_Adsetting(getContext()).CheckAdCanShow()){
            mAdView.setVisibility(View.GONE);
            showFacebookAds();
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
                //Toast.makeText(getContext(), "Adopen", Toast.LENGTH_SHORT).show();
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

        // Find the Ad Container
        LinearLayout adContainer = v.findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);
        //
        // Request an ad
        // AdSettings.addTestDevice("56d51070-f693-46aa-9379-38cc81df9ae5");
        adView.loadAd();

        adView.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                if (adView != null) {
                    adView.destroy();
                }
                //  showGoogleAdsChanvce();
               // showAds();
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {
                //Log.d("FacebookAdsSettings","Current time is :  " +  System.currentTimeMillis());
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


    private void showTable(String teamID){
        tableModelList = new ArrayList<>();
        tableAdapter = new myteam_table_adapter(tableModelList,getContext());
        recyclerView_table = v.findViewById(R.id.table_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_table.setLayoutManager(layoutManager);
        recyclerView_table.setHasFixedSize(true);
        recyclerView_table.setNestedScrollingEnabled(false);
        recyclerView_table.setAdapter(tableAdapter);

        getTableData(teamID);
    }


    void getTableData(final String ClubID){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/standings?compSeasons=210", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                ArrayList<table_model> data = new ArrayList<>();
                try{

                    JSONObject obj = new JSONObject(response);
                    JSONArray Table = obj.getJSONArray("tables");
                    JSONObject MatchData = Table.getJSONObject(0);
                    JSONArray TeamTable = MatchData.getJSONArray("entries");
                    table_model item;
                    // Log.d("Fragment_table"," " + TeamTable.length() );
                    for(int i =0;i<TeamTable.length();i++){
                        //team
                        JSONObject TeamObject= TeamTable.getJSONObject(i);
                        JSONObject Team = TeamObject.getJSONObject("team");
                        item = new table_model();
                        String TeamID = String.valueOf(Team.getInt("id"));
                        String TeamName = Team.getString("shortName");
                        String position = TeamObject.getString("position");
                        JSONObject TeamStats = TeamObject.getJSONObject("overall");
                        String Played =  String.valueOf(TeamStats.getInt("played"));
                        String Drawn = String.valueOf(TeamStats.getInt("drawn"));
                        String Won = String.valueOf(TeamStats.getInt("won"));
                        String Lost = String.valueOf(TeamStats.getInt("lost"));
                        String GD = String.valueOf(TeamStats.getInt("goalsDifference"));
                        String points = String.valueOf(TeamStats.getInt("points"));


                        if (Objects.equals(ClubID, TeamID)){
                            item.setP(Played);
                            item.setW(Won);
                            item.setD(Drawn);
                            item.setGD(GD);
                            item.setL(Lost);
                            item.setPosition(position);
                            item.setTname(TeamName);
                            item.setTid(TeamID);
                            item.setPts(points);
                            data.add(item);
                        }



                        //    Log.d("FragmeTableData", "TeamPos: " + position + "TeamName: " + TeamName  + "Played:" + Played +" " + "Won:" + Won + "Drawn:" + Drawn +" GD:" + GD )
                    }

                    tableModelList.addAll(data);
                    tableAdapter.notifyDataSetChanged();
                    table_progress.setVisibility(View.GONE);
                    table_container.setVisibility(View.VISIBLE);
                }catch (Exception e){

                    //   Log.d("Fragment_seat_season_data"," " +  e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if(getContext()!=null){
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/tables");
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


    private void showTweets(final String TwiiterHandler){





        final RecyclerView recyclerViewTweets = (RecyclerView) v.findViewById(R.id.tweets_rec_team);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()),LinearLayoutManager.HORIZONTAL,false);

        recyclerViewTweets.setLayoutManager(new LinearLayoutManager(getContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                //your code

                TwitterConfig config = new TwitterConfig.Builder(getContext())
                        .logger(new DefaultLogger(Log.DEBUG))
                        .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.CONSUMER_KEY), getResources().getString(R.string.CONSUMER_SECRET)))
                        .debug(true)
                        .build();
                Twitter.initialize(config);


                final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                        .query(TwiiterHandler).languageCode("en")
                        .maxItemsPerRequest(50)
                        .build();


                final TweetTimelineRecyclerViewAdapter adapter =
                        new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                                .setTimeline(searchTimeline)
                                .setViewStyle(R.style.tw__TweetDarkWithActionsStyle)
                                .build();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                      //  final int TeamID = new Pref_fav_tem_settings(getContext()).getFavTeam();
                      //  showTweets(TwiiterHandler(TeamID));
                        final Callback<TimelineResult<Tweet>> callback = new Callback<TimelineResult<Tweet>>() {

                            @Override
                            public void success(Result<TimelineResult<Tweet>> result) {
                                // Log.d("TAG", "success");
                                //TweetProgress.setVisibility(View.GONE);
                                Tweet_progress.setVisibility(View.GONE);
                                recyclerViewTweets.setVisibility(View.VISIBLE);
                                //  Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void failure(TwitterException e) {
                                //   Log.d("TAG", "Failure");

                                //   Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                            }
                        };
                        recyclerViewTweets.setAdapter(adapter);
                        searchTimeline.next(null,callback);
                    }
                });
            }
        }).start();




       // Log.d("TwiiterSize"," ");

      //  recyclerViewTweets.setNestedScrollingEnabled(false);

    }


    private void SetTeamNameAndImage(int TeamID){
        switch (TeamID) {
            case 10:
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                team_name.setText("Liverpool F.C");
                team_image.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case 4:
                team_name.setText("Chelsea F.C.");
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
               team_image.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case 127:
                team_name.setText("A.F.C. Bournemouth");
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
                team_image.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case 6:
                team_name.setText("Crystal Palace F.C.");
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
              team_image.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case 11:
                team_name.setText("Manchester City F.C.");
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
                team_image.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case 33:
                team_name.setText("Watford F.C.");
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
                team_image.setImageResource(R.drawable.ic_logo_watford);
                break;
            case 12:
                team_name.setText("Manchester United F.C.");
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
               team_image.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case 21:
                team_name.setText("Tottenham Hotspur F.C.");
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
               team_image.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case 7:
                team_name.setText("Everton F.C.");
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
                team_image.setImageResource(R.drawable.ic_logo_everton);
                break;
            case 38:
                team_name.setText("Wolverhampton Wanderers F.C.");
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
                team_image.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case 43:
                team_name.setText("Burnley F.C.");
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
                team_image.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case 20:
                team_name.setText("Southampton F.C.");
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
                // holder.team_image.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case 26:
                team_name.setText("Leicester City F.C.");
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
              team_image.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case 23:
                team_name.setText("Newcastle United F.C.");
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
               team_image.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case 1:
                team_name.setText("Arsenal F.C.");
                team_image.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case 131:
                team_name.setText("Brighton & Hove Albion F.C.");
                team_image.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case 46:
                team_name.setText("Cardiff City F.C.");
                team_image.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case 34:
                team_name.setText("Fulham F.C.");
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
              team_image.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case 159:
                team_name.setText("Huddersfield Town");
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
                team_image.setImageResource(R.drawable.logo_huddersfield);
                break;
            case 25:
                team_name.setText("West Ham United F.C.");
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
                team_image.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:
                ///TwiiterHandler = "#EPL";


        }

    }


    private String TwiiterHandler(int TeamID) {
        String TwiiterHandler;
        switch (TeamID) {
            case 10:
                //Picasso.get().load(R.drawable.ic_logo_liverpool).into();
                TwiiterHandler = "#LFC";
                // holder.team_image.setImageResource(R.drawable.ic_logo_liverpool);
                //((ItemViewHolder)holder).homeTeamImage.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case 4:
                TwiiterHandler = "@ChelseaFC";
                // Picasso.get().load(R.drawable.ic_logo_chelsea).into(((ItemViewHolder)holder).homeTeamImage);
                // holder.team_image.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case 127:
                TwiiterHandler = "@afcbournemouth";
                //Picasso.get().load(R.drawable.ic_logo_afc_bournemiuth).into(((ItemViewHolder)holder).homeTeamImage);
             //   holder.team_image.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case 6:
                TwiiterHandler = "@CPFC";
                // Picasso.get().load(R.drawable.ic_logo_crystal_palace).into(((ItemViewHolder)holder).homeTeamImage);
              //  holder.team_image.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case 11:
                TwiiterHandler = "@ManCity";
                // Picasso.get().load(R.drawable.ic_logo_manchester_city).into(((ItemViewHolder)holder).homeTeamImage);
               // holder.team_image.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case 33:
                TwiiterHandler = "@WatfordFC";
                // Picasso.get().load(R.drawable.ic_logo_watford).into(((ItemViewHolder)holder).homeTeamImage);
              //  holder.team_image.setImageResource(R.drawable.ic_logo_watford);
                break;
            case 12:
                TwiiterHandler = "@ManUtd";
                //Picasso.get().load(R.drawable.ic_logo_manchester_united).into(((ItemViewHolder)holder).homeTeamImage);
               // holder.team_image.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case 21:
                TwiiterHandler = "@SpursOfficial";
                //  Picasso.get().load(R.drawable.ic_logo_tottenham_hotspur).into(((ItemViewHolder)holder).homeTeamImage);
              //  holder.team_image.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case 7:
                TwiiterHandler = "@Everton";
                //  Picasso.get().load(R.drawable.ic_logo_everton).into(((ItemViewHolder)holder).homeTeamImage);
             //   holder.team_image.setImageResource(R.drawable.ic_logo_everton);
                break;
            case 38:
                TwiiterHandler = "@Wolves";
                //  Picasso.get().load(R.drawable.ic_logo_wolverhampton).into(((ItemViewHolder)holder).homeTeamImage);
             //   holder.team_image.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;
            case 43:
                TwiiterHandler = "@BurnleyOfficial";
                // Picasso.get().load(R.drawable.ic_logo_burnley).into(((ItemViewHolder)holder).homeTeamImage);
               // holder.team_image.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case 20:
                TwiiterHandler = "@SouthamptonFC";
                //Picasso.get().load(R.drawable.ic_logo_southampton).into(((ItemViewHolder)holder).homeTeamImage);
               // holder.team_image.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case 26:
                TwiiterHandler = "@LCFC";
                //Picasso.get().load(R.drawable.ic_logo_leicester_city).into(((ItemViewHolder)holder).homeTeamImage);
               // holder.team_image.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case 23:
                TwiiterHandler = "@NUFC";
                //Picasso.get().load(R.drawable.ic_logo_newcastle_united).into(((ItemViewHolder)holder).homeTeamImage);
              //  holder.team_image.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case 1:
                TwiiterHandler = "@Arsenal";
               // holder.team_image.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case 131:
                TwiiterHandler = "@OfficialBHAFC";
                //holder.team_image.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case 46:
                TwiiterHandler = "@CardiffCityFC";
               // holder.team_image.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case 34:
                TwiiterHandler = "@FulhamFC";
                //Picasso.get().load(R.drawable.ic_logo_fulham).into(((ItemViewHolder)holder).homeTeamImage);
               // holder.team_image.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case 159:
                TwiiterHandler = "@htafcdotcom";
                // Picasso.get().load(R.drawable.ic_logo_huddersfield_town).into(((ItemViewHolder)holder).homeTeamImage);
               // holder.team_image.setImageResource(R.drawable.logo_huddersfield);
                break;
            case 25:
                TwiiterHandler = "@WestHamUtd";
                //Picasso.get().load(R.drawable.ic_logo_west_ham_united).into(((ItemViewHolder)holder).homeTeamImage);
               // holder.team_image.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            default:
                TwiiterHandler = "#EPL";


        }
        return TwiiterHandler;
    }

    private void showUpcomingFixture(int TeamID){
        // Inflate the layout for this fragment
        recentModelList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.Team_fixture_rec);
        teamFixtureAdpter = new my_team_upcoming_Adapter(recentModelList,getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        // DividerItemDecoration itemDecoration = new DividerItemDecoration(v.getContext(),linearLayoutManager.getOrientation());
        //recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(teamFixtureAdpter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
       // recyclerView.addItemDecoration(new SimpleDivider(ContextCompat.getDrawable(getContext(),R.drawable.line_divider)));


      //  String TeamID = getActivity().getIntent().getExtras().getString("TeamID");
        getTeamFixture(String.valueOf(TeamID));
    }

    private void showUpcomingResult(int TeamID){
        //Team_fixture_rec_result
        // Inflate the layout for this fragment
        recentModelList_result = new ArrayList<>();
        recyclerView_result = v.findViewById(R.id.Team_fixture_rec_result);
        teamFixtureAdpter__result = new my_team_upcoming_Adapter(recentModelList_result,getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        // DividerItemDecoration itemDecoration = new DividerItemDecoration(v.getContext(),linearLayoutManager.getOrientation());
        //recyclerView
        recyclerView_result.setHasFixedSize(true);
        recyclerView_result.setAdapter(teamFixtureAdpter__result);
        recyclerView_result.setLayoutManager(linearLayoutManager);
        recyclerView_result.setNestedScrollingEnabled(false);
        // recyclerView.addItemDecoration(new SimpleDivider(ContextCompat.getDrawable(getContext(),R.drawable.line_divider)));


        //  String TeamID = getActivity().getIntent().getExtras().getString("TeamID");
        getTeamResult(String.valueOf(TeamID));
    }


    private void getTeamResult(final String TeamID){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // System.out.println(response);
                //  Log.d("VolleyRequest",response);
                //  ErrorTextView.setVisibility(View.GONE);
                ArrayList<match_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("content");
                  //  System.out.println(heroArray.length());

                    match_model item;
                    for(int i=0;i<heroArray.length();i++){
                        JSONObject gameData = heroArray.getJSONObject(i);
                        String GameID = String.valueOf(gameData.getInt("id"));
                        String matchStatus = gameData.getString("status");
                        String matchPhase = gameData.getString("phase");

                        // System.out.println("Match Data: "+matchStatus + " " + matchPhase);

                        //Toast.makeText(MainActivity.this, " "+GameID, Toast.LENGTH_SHORT).show();
                        //team Array
                        JSONArray Teams = gameData.getJSONArray("teams");
                        //get the first object
                        JSONObject TeamDetail = Teams.getJSONObject(0);
                        //Object team

                        JSONObject AllTeam = TeamDetail.getJSONObject("team");
                        JSONObject TeamClub0 = AllTeam.getJSONObject("club");
                        String HomeTeam = TeamClub0.getString("shortName");
                        String HomeTeamId= String.valueOf(TeamClub0.getInt("id"));


                        //get the first  away team
                        JSONObject TeamDetail1 = Teams.getJSONObject(1);
                        //Object team
                        JSONObject AllTeam1 = TeamDetail1.getJSONObject("team");
                        JSONObject TeamClub = AllTeam1.getJSONObject("club");
                        String AwayTeam = TeamClub.getString("shortName");
                        String AwayTeamID= String.valueOf(TeamClub.getInt("id"));







                        //DAte get from json
                        JSONObject DateDetail = gameData.getJSONObject("kickoff");
                        String DateInMillis = String.valueOf(DateDetail.getLong("millis"));
                        //Collections.sort(DateInMillis);
                        item = new match_model();
                        JSONObject GroundName = gameData.getJSONObject("ground");
                        String cityName = GroundName.getString("city");
                        String StadName= GroundName.getString("name");


                        //item.setHomeTeam(HomeTeam);
                        ///  item.setAwayTeam(AwayTeam);

                        // item.setHomeTeamID(HomeTeamId);
                        // item.setAwayTeamID(AwayTeamID);//score
                        // item.setMatchStatus(matchStatus);

                        if (matchStatus.equals("C")){
                            String HomeTeamSccore = String.valueOf(TeamDetail.getInt("score"));
                            String AwayTeamScore = String.valueOf(TeamDetail1.getInt("score"));
//score
                            //      item.setHomeTeamScore(HomeTeamSccore);
                            //     item.setAwayTeamScore(AwayTeamScore);
                            if (Objects.equals(HomeTeamId, TeamID) || Objects.equals(AwayTeamID, TeamID)){
                                item.setHomeTeam(HomeTeam);
                                item.setAwayTeam(AwayTeam);

                                item.setHomeTeamID(HomeTeamId);
                                item.setAwayTeamID(AwayTeamID);
                                item.setMathcId(GameID);
                                // item.setGameTimePast();
                                item.setDate(getReadDAte(convertDate(DateInMillis,"dd/MM/yyyy")));
                                item.setTime(HomeTeamSccore+" : "+AwayTeamScore);
                                item.setCityName(cityName);
                                item.setStadiumName(StadName);
                                //item.setMillies(DateDetail.getLong("millis"));
                                item.setMillies(MillisDate(convertDate(DateInMillis,"dd/MM/yyyy")));
                                item.setMatchStatus(matchStatus);
                                data.add(item);
                            }

                        }

                        /*

                         */


                        // data.add(item);
                    }
                    recentModelList_result.addAll(data);
                    teamFixtureAdpter__result.notifyDataSetChanged();
                    recent_progress.setVisibility(View.GONE);
                    recyclerView_result.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    System.out.println(e);
                }

                //  swipeRefreshLayout.setRefreshing(false);
//linearLayoutResult


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("VolleyRequestError",error.toString());
                //  swipeRefreshLayout.setRefreshing(false);
                ///.//ErrorTextView.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/result");
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

    private void getTeamFixture(final String TeamID){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // System.out.println(response);
                //  Log.d("VolleyRequest",response);
                //  ErrorTextView.setVisibility(View.GONE);
                ArrayList<match_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("content");
                //    System.out.println(heroArray.length());

                    match_model item;
                    for(int i=0;i<heroArray.length();i++){
                        JSONObject gameData = heroArray.getJSONObject(i);
                        String GameID = String.valueOf(gameData.getInt("id"));
                        String matchStatus = gameData.getString("status");
                        String matchPhase = gameData.getString("phase");

                        // System.out.println("Match Data: "+matchStatus + " " + matchPhase);

                        //Toast.makeText(MainActivity.this, " "+GameID, Toast.LENGTH_SHORT).show();
                        //team Array
                        JSONArray Teams = gameData.getJSONArray("teams");
                        //get the first object
                        JSONObject TeamDetail = Teams.getJSONObject(0);
                        //Object team

                        JSONObject AllTeam = TeamDetail.getJSONObject("team");
                        JSONObject TeamClub0 = AllTeam.getJSONObject("club");
                        String HomeTeam = TeamClub0.getString("shortName");
                        String HomeTeamId= String.valueOf(TeamClub0.getInt("id"));

                        //get the first  away team
                        JSONObject TeamDetail1 = Teams.getJSONObject(1);

                        //Object team
                        JSONObject AllTeam1 = TeamDetail1.getJSONObject("team");
                        JSONObject TeamClub = AllTeam1.getJSONObject("club");
                        String AwayTeam = TeamClub.getString("shortName");
                        String AwayTeamID= String.valueOf(TeamClub.getInt("id"));


                        //DAte get from json
                        JSONObject DateDetail = gameData.getJSONObject("kickoff");
                        String DateInMillis = String.valueOf(DateDetail.getLong("millis"));
                        //Collections.sort(DateInMillis);
                        // Log.d("jsonResult"," "+ GameID+" " + HomeTeam + " " + HomeTeamId + " " + " VS " + AwayTeam + " " + AwayTeamID+"  " +convertDate(DateInMillis,"hh:mm a"));
                        // System.out.println(GameID+" " + HomeTeam + " " + HomeTeamId + " " + " VS " + AwayTeam + " " + AwayTeamID+"  " +convertDate(DateInMillis,"hh:mm a"));
                        item = new match_model();
                        JSONObject GroundName = gameData.getJSONObject("ground");
                        String cityName = GroundName.getString("city");
                        String StadName= GroundName.getString("name");


                        if (Objects.equals(HomeTeamId, TeamID) || Objects.equals(AwayTeamID, TeamID)){
                            item.setHomeTeam(HomeTeam);
                            item.setAwayTeam(AwayTeam);

                            item.setHomeTeamID(HomeTeamId);
                            item.setAwayTeamID(AwayTeamID);
                            item.setMathcId(GameID);
                            // item.setGameTimePast();
                            item.setDate(getReadDAte(convertDate(DateInMillis,"dd/MM/yyyy")));
                            item.setTime(convertDate(DateInMillis,"hh:mm a"));
                            item.setCityName(cityName);
                            item.setStadiumName(StadName);
                            //item.setMillies(DateDetail.getLong("millis"));
                            item.setMillies(MillisDate(convertDate(DateInMillis,"dd/MM/yyyy")));
                            item.setMatchStatus(matchStatus);
                            data.add(item);
                        }

                    }
                    recentModelList.addAll(data);
                    teamFixtureAdpter.notifyDataSetChanged();
                    upcoming_progress.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    System.out.println(e);
                }
                // swipeRefreshLayout.setRefreshing(false);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //   swipeRefreshLayout.setRefreshing(false);
                //    ErrorTextView.setVisibility(View.VISIBLE);
                Log.d("VolleyRequestError",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/fixtures");
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

    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    public String getReadDAte(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        String readableFormate=null;
        //int month;
        try{
            date = sdf.parse(dateString);
            // int day = date.getDay();
            //  int year = date.getYear();
            // int month = date.getMonth();
//Initialize your Date however you like it.
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            String monthName = null;
            switch (month){
                case 1:monthName="January";
                    break;
                case 2:monthName="February";
                    break;
                case 3:monthName="March";
                    break;
                case 4:monthName="April";
                    break;
                case 5:monthName="May";
                    break;
                case 6:monthName="June";
                    break;
                case 7:monthName="July";
                    break;
                case 8:monthName="August";
                    break;
                case 9:monthName="September";
                    break;
                case 10:monthName="October";
                    break;
                case 11:monthName="November";
                    break;
                case 12:monthName="December";
                    break;
            }

            readableFormate = day+" "+monthName+" "+year;

            // Log.d("MetaDataDay"," " + readableFormate);
        }catch (Exception e){
            Log.d("MetaDataDay"," "  + e);
        }

        return readableFormate;
    }

    public Long MillisDate(String dateINDate){
        String myDate = dateINDate;
        // Log.d("MIllisDate"," " + myDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        long millis = 0;
        try {
            date = sdf.parse(myDate);
            millis = date.getTime();
            int monthnum = date.getMonth();
            // Toast.makeText(getContext(), " " + monthnum, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  millis;
    }



}
