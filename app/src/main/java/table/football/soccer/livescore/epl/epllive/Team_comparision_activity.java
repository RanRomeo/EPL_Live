package table.football.soccer.livescore.epl.epllive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.snipper_season_adapter;
import table.football.soccer.livescore.epl.epllive.Custom_dailog.Compare_team_b_selector;
import table.football.soccer.livescore.epl.epllive.Custom_dailog.compar_team_selector;
import table.football.soccer.livescore.epl.epllive.Utils.Helpers.InterAdsHelpers;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.model.season_model;

public class Team_comparision_activity extends AppCompatActivity {

    LinearLayout layout_container_stats;

    String TeamID_A;

    public String getMyTeam_A()
    {
        //include validation, logic, logging or whatever you like here
        return this.TeamID_A;
    }
    public void setMyTeam_A(String value)
    {
        //include more logic
        this.TeamID_A = value;
    }

    //For Team Away
    String TeamID_B;

    public String getMyTeam_B()
    {
        //include validation, logic, logging or whatever you like here
        return this.TeamID_B;
    }
    public void setMyTeam_B(String value)
    {
        //include more logic
        this.TeamID_B = value;
    }



    private ArrayList<season_model> seasonModelsList;
    private snipper_season_adapter snipperSeasonAdapter;
     //Overview
     TextView overview_compare_first_wins,overview_compare_second_wins
                ,overview_compare_first_losses,overview_compare_second_losses,
                 overview_compare_first_draw,overview_compare_second_draw,
                 overview_compare_first_goals,overview_compare_second_goals;
    //attack
    TextView attack_compare_goals_first,attack_compare_goals_second
            ,attack_compare_goals_per_match_first,attack_compare_goals_per_match_second,
            attack_compare_shots_first,attack_compare_shots_second,
            attack_compare_shots_Ontarget_first,attack_compare_shots_Ontarget_second,
            attack_compare_shooting_accuracy_first,attack_compare_shooting_accuracy_second,
            attack_compare_penalties_socred_first,attack_compare_penalties_socred_second,
            attack_compare_big_chances_created_first,attack_compare_big_chances_created_second,
            attack_compare_hit_woodwork_first,attack_compare_hit_woodwork_second;


    //defence
    TextView defence_compare_clean_sheets_first,defence_compare_clean_sheets_second
            ,defence_compare_goalConceded_first,defence_compare_goalConceded_second,
            defence_compare_goalConcededPMatch_first,defence_compare_goalConcededPMatch_second,
            defence_compare_saves_first,defence_compare_saves_second,
            defence_compare_tackles_first,defence_compare_tackles_second,
            defence_compare_tacklesSucess_first,defence_compare_tacklesSucess_second,
            defence_compare_blaocked_first,defence_compare_blaocked_second,
            defence_compare_interceptions_first,defence_compare_interceptions_second,
            defence_compare_clearances_fisrt,defence_compare_clearances_second,
            defence_compare_headedClearnce_first,defence_compare_headedClearnce_second,
            defence_compare_Own_goals_first,defence_compare_Own_goals_second,
            defence_compare_PenaltiesCOn_first,defence_compare_PenaltiesCOn_second;


    //TeamPlay
    TextView team_play_compare_passes_first,team_play_compare_passes_second
            ,team_play_compare_passmatch_first,team_play_compare_passmatch_second,
            team_play_compare_passacc_first,team_play_compare_passacc_second,
            team_play_compare_crosses_first,team_play_compare_crosses_second,
            team_play_compare_crossesaccu_first,team_play_compare_crossesaccu_seond;

    //discipline
    TextView discipline_compare_yellowCards_first,discipline_compare_yellowCards_second
            ,discipline_compare_red_cards_first,discipline_compare_red_cards_second,
            discipline_compare_fouls_first,discipline_compare_fouls_seoncd;


    //team image
    public  ImageView ImagefirstTeam,image_view_b;

    //TextView
    public  TextView team_a_name,team_B_name;

    //Imagebutton
    public ImageButton team_a_image_button,team_b_image_button;

    Spinner spinnerDate1,spinnerDate2;
    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    LinearLayout adContainer;
    //progresss_indecater
    AVLoadingIndicatorView progresss_indecater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_comparision_activity);
        new InterAdsHelpers("Team_Comparte_activty",getApplicationContext()).ShowInterAds();

        layout_container_stats = findViewById(R.id.layout_container_stats);
        progresss_indecater =findViewById(R.id.progresss_indecater);
        ImagefirstTeam = findViewById(R.id.ImagefirstTeam);
        image_view_b = findViewById(R.id.image_view_b);

        team_a_image_button = findViewById(R.id.team_a_image_button);
        team_b_image_button = findViewById(R.id.team_b_image_button);
        mAdView = findViewById(R.id.adView);
        // Find the Ad Container
        adContainer = findViewById(R.id.banner_container);

        showAds();

        team_a_name = findViewById(R.id.team_a_name);
        team_B_name = findViewById(R.id.team_B_name);

        ImagefirstTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFirstDailog();
            }
        });
        image_view_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensecondDialog();
            }
        });


        team_a_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFirstDailog();
            }
        });
        team_b_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensecondDialog();
            }
        });

        initList();
        initOverViewText();
        initAttackText();
        initDefence();
        initteam_play();
        initdiscipline();
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null second_date_choose
        setSupportActionBar(toolbar);
        setTitle("Compare Team");
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//         //   getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        snipperSeasonAdapter = new snipper_season_adapter(this,seasonModelsList);
         spinnerDate1 = findViewById(R.id.firstTeam_Date_choose);
         spinnerDate2 = findViewById(R.id.second_date_choose);

      //  loadStatDataforTeamOne("https://footballapi.pulselive.com/football/stats/team/1?comps=1");
      //  loadStatDataforTeamTwo("https://footballapi.pulselive.com/football/stats/team/1?comps=1");
     //    getTeamFixtureFirst("https://footballapi.pulselive.com/football/stats/team/1?comps=1");
    //    getTeamFixtureSecond("https://footballapi.pulselive.com/football/stats/team/1?comps=1");
        spinnerDate1.setAdapter(snipperSeasonAdapter);
        spinnerDate2.setAdapter(snipperSeasonAdapter);
        //homeTeam
        final  int[] snipper1 ={0};
        spinnerDate1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                season_model seasonModelData = (season_model) parent.getItemAtPosition(position);
                int seasonID = seasonModelData.getId();

                snipper1[0]++;
                if ( snipper1[0]>1){
                  //  Log.d("CompreaTeam", "getMyTeam_A: " + getMyTeam_A());
                    //   Toast.makeText(Team_comparision_activity.this, "Hellowoeld " + getMyTeam_A(), Toast.LENGTH_SHORT).show();
                    //999 defautl
                    if (seasonID==999){
                        getTeamFixtureFirst("https://footballapi.pulselive.com/football/stats/team/"+getMyTeam_A()+"?comps=1",new WebView(getApplicationContext()).getSettings().getUserAgentString());
                    }else{
                        getTeamFixtureFirst("https://footballapi.pulselive.com/football/stats/team/"+ getMyTeam_A()+"?comps=1&compSeasons="+seasonID,new WebView(getApplicationContext()).getSettings().getUserAgentString());
                    }

                    //     Toast.makeText(Team_comparision_activity.this, " " + seasonID, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //AwayTeam
        final int[] snipper2 = {0};
        //
        spinnerDate2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                season_model seasonModelData = (season_model) parent.getItemAtPosition(position);
                snipper2[0]++;

                if( snipper2[0]>1){
                    int seasonID = seasonModelData.getId();

                    if (seasonID==999){
                        getTeamFixtureFirst("https://footballapi.pulselive.com/football/stats/team/"+getMyTeam_B()+"?comps=1",new WebView(getApplicationContext()).getSettings().getUserAgentString());
                    }else {
                        getTeamFixtureSecond("https://footballapi.pulselive.com/football/stats/team/"+getMyTeam_B()+"?comps=1&compSeasons="+seasonID,new WebView(getApplicationContext()).getSettings().getUserAgentString());

                    }

                    //  Toast.makeText(Team_comparision_activity.this, "Helloworld " + getMyTeam_B(), Toast.LENGTH_SHORT).show();
                 //   Log.d("CompreaTeam", "getMyTeam_B: " + getMyTeam_B());
                    //   Toast.makeText(Team_comparision_activity.this, " " + seasonID, Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void showAds(){
        if(new PrefManager_googleAdSetting(getApplicationContext()).CheckAdCanShow()){
            showgoogleAds();
            adContainer.setVisibility(View.VISIBLE);
        }else if (new PrefManager_Facebook_Adsetting(getApplicationContext()).CheckAdCanShow()){
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
                showFacebookAdCan();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            //this method is perform when ads is clicked
            @Override
            public void onAdOpened() {
                super.onAdOpened();
                new PrefManager_googleAdSetting(getApplicationContext()).SaveClickedTime();
                //   Toast.makeText(getApplicationContext(), "Adopen", Toast.LENGTH_SHORT).show();
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
        adView = new com.facebook.ads.AdView(getApplicationContext(), getApplicationContext().getString(R.string.Facebook_bannerAd), AdSize.BANNER_HEIGHT_50);
       // AdSettings.addTestDevice("1e4e05c7-005c-41a3-bc59-c2ccc0008956");



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
                // Log.d("FacebookAdsSettings","Current time is :  " +  System.currentTimeMillis());
                //  LasTimeClicked = System.currentTimeMillis();
                //Saves current time and clicke trie
                new PrefManager_Facebook_Adsetting(getApplicationContext()).SaveClickedTime();
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


    private void showFacebookAdCan(){
        if (new PrefManager_Facebook_Adsetting(getApplicationContext()).CheckAdCanShow()){
            mAdView.setVisibility(View.GONE);
            showFacebookAds();
            adContainer.setVisibility(View.VISIBLE);
        }
    }


    void initOverViewText(){
        overview_compare_first_wins = findViewById(R.id.overview_compare_first_wins);
        overview_compare_second_wins = findViewById(R.id.overview_compare_second_wins);

        overview_compare_first_losses = findViewById(R.id.overview_compare_first_losses);
        overview_compare_second_losses = findViewById(R.id.overview_compare_second_losses);

        overview_compare_first_draw = findViewById(R.id.overview_compare_first_draw);
        overview_compare_second_draw = findViewById(R.id.overview_compare_second_draw);

        overview_compare_first_goals = findViewById(R.id.overview_compare_first_goals);
        overview_compare_second_goals = findViewById(R.id.overview_compare_second_goals);

    }

    void initAttackText(){
        attack_compare_goals_first = findViewById(R.id.attack_compare_goals_first);
        attack_compare_goals_second = findViewById(R.id.attack_compare_goals_second);

        attack_compare_goals_per_match_first = findViewById(R.id.attack_compare_goals_per_match_first);
        attack_compare_goals_per_match_second = findViewById(R.id.attack_compare_goals_per_match_second);

        attack_compare_shots_first = findViewById(R.id.attack_compare_shots_first);
        attack_compare_shots_second = findViewById(R.id.attack_compare_shots_second);

        attack_compare_shots_Ontarget_first = findViewById(R.id.attack_compare_shots_Ontarget_first);
        attack_compare_shots_Ontarget_second = findViewById(R.id.attack_compare_shots_Ontarget_second);

        attack_compare_shooting_accuracy_first = findViewById(R.id.attack_compare_shooting_accuracy_first);
        attack_compare_shooting_accuracy_second = findViewById(R.id.attack_compare_shooting_accuracy_second);

        attack_compare_penalties_socred_first = findViewById(R.id.attack_compare_penalties_socred_first);
        attack_compare_penalties_socred_second = findViewById(R.id.attack_compare_penalties_socred_second);

        attack_compare_big_chances_created_first = findViewById(R.id.attack_compare_big_chances_created_first);
        attack_compare_big_chances_created_second = findViewById(R.id.attack_compare_big_chances_created_second);

        attack_compare_hit_woodwork_first = findViewById(R.id.attack_compare_hit_woodwork_first);
        attack_compare_hit_woodwork_second = findViewById(R.id.attack_compare_hit_woodwork_second);









    }

    void initDefence(){

        defence_compare_clean_sheets_first = findViewById(R.id.defence_compare_clean_sheets_first);
        defence_compare_clean_sheets_second = findViewById(R.id.defence_compare_clean_sheets_second);

        defence_compare_goalConceded_first = findViewById(R.id.defence_compare_goalConceded_first);
        defence_compare_goalConceded_second = findViewById(R.id.defence_compare_goalConceded_second);

        defence_compare_goalConcededPMatch_first = findViewById(R.id.defence_compare_goalConcededPMatch_first);
        defence_compare_goalConcededPMatch_second = findViewById(R.id.defence_compare_goalConcededPMatch_second);

        defence_compare_saves_first = findViewById(R.id.defence_compare_saves_first);
        defence_compare_saves_second = findViewById(R.id.defence_compare_saves_second);

        defence_compare_tackles_first = findViewById(R.id.defence_compare_tackles_first);
        defence_compare_tackles_second = findViewById(R.id.defence_compare_tackles_second);

        defence_compare_tacklesSucess_first = findViewById(R.id.defence_compare_tacklesSucess_first);
        defence_compare_tacklesSucess_second = findViewById(R.id.defence_compare_tacklesSucess_second);

        defence_compare_blaocked_first = findViewById(R.id.defence_compare_blaocked_first);
        defence_compare_blaocked_second = findViewById(R.id.defence_compare_blaocked_second);

        defence_compare_interceptions_first = findViewById(R.id.defence_compare_interceptions_first);
        defence_compare_interceptions_second = findViewById(R.id.defence_compare_interceptions_second);

        defence_compare_clearances_fisrt = findViewById(R.id.defence_compare_clearances_fisrt);
        defence_compare_clearances_second = findViewById(R.id.defence_compare_clearances_second);

        defence_compare_headedClearnce_first = findViewById(R.id.defence_compare_headedClearnce_first);
        defence_compare_headedClearnce_second = findViewById(R.id.defence_compare_headedClearnce_second);

        defence_compare_Own_goals_first = findViewById(R.id.defence_compare_Own_goals_first);
        defence_compare_Own_goals_second = findViewById(R.id.defence_compare_Own_goals_second);

        defence_compare_PenaltiesCOn_first = findViewById(R.id.defence_compare_PenaltiesCOn_first);
        defence_compare_PenaltiesCOn_second = findViewById(R.id.defence_compare_PenaltiesCOn_second);






    }


    void initteam_play(){
        team_play_compare_passes_first = findViewById(R.id.team_play_compare_passes_first);
        team_play_compare_passes_second = findViewById(R.id.team_play_compare_passes_second);

        team_play_compare_passmatch_first = findViewById(R.id.team_play_compare_passmatch_first);
        team_play_compare_passmatch_second = findViewById(R.id.team_play_compare_passmatch_second);

        team_play_compare_passacc_first = findViewById(R.id.team_play_compare_passacc_first);
        team_play_compare_passacc_second = findViewById(R.id.team_play_compare_passacc_second);

        team_play_compare_crosses_first = findViewById(R.id.team_play_compare_crosses_first);
        team_play_compare_crosses_second = findViewById(R.id.team_play_compare_crosses_second);

        team_play_compare_crossesaccu_first = findViewById(R.id.team_play_compare_crossesaccu_first);
        team_play_compare_crossesaccu_seond = findViewById(R.id.team_play_compare_crossesaccu_seond);


    }

    void initdiscipline(){
        discipline_compare_yellowCards_first = findViewById(R.id.discipline_compare_yellowCards_first);
        discipline_compare_yellowCards_second = findViewById(R.id.discipline_compare_yellowCards_second);

        discipline_compare_red_cards_first = findViewById(R.id.discipline_compare_red_cards_first);
        discipline_compare_red_cards_second = findViewById(R.id.discipline_compare_red_cards_second);

        discipline_compare_fouls_first = findViewById(R.id.discipline_compare_fouls_first);
        discipline_compare_fouls_seoncd = findViewById(R.id.discipline_compare_fouls_seoncd);


    }


    void openFirstDailog(){
        compar_team_selector favTeamSetting_dailog = new compar_team_selector();
        favTeamSetting_dailog.setCancelable(false);
        favTeamSetting_dailog.show(getSupportFragmentManager(),"compate_team");
    }

    //Compare_team_b_selector
    void opensecondDialog(){
        Compare_team_b_selector favTeamSetting_dailog = new Compare_team_b_selector();
        favTeamSetting_dailog.setCancelable(false);
        favTeamSetting_dailog.show(getSupportFragmentManager(),"compate_teamsecond");
    }



    private void initList(){
        seasonModelsList = new ArrayList<>();
        seasonModelsList.add(new season_model(999,"All"));
        seasonModelsList.add(new season_model(210,"2018/19"));
        seasonModelsList.add(new season_model(79,"2017/18"));
        seasonModelsList.add(new season_model(54,"2016/17"));
        seasonModelsList.add(new season_model(42,"2015/16"));
        seasonModelsList.add(new season_model(27,"2014/15"));
        seasonModelsList.add(new season_model(22,"2013/14"));
        seasonModelsList.add(new season_model(21,"2012/13"));
        seasonModelsList.add(new season_model(20,"2011/12"));
        seasonModelsList.add(new season_model(19,"2010/11"));
//        seasonModelsList.add(new season_model(18,"2009/10"));
//        seasonModelsList.add(new season_model(17,"2008/09"));
//        seasonModelsList.add(new season_model(16,"2007/08"));
//        seasonModelsList.add(new season_model(15,"2006/07"));
//        seasonModelsList.add(new season_model(14,"2005/06"));
//        seasonModelsList.add(new season_model(13,"2004/05"));
//        seasonModelsList.add(new season_model(12,"2003/04"));
//        seasonModelsList.add(new season_model(11,"2002/03"));
//        seasonModelsList.add(new season_model(10,"2001/02"));
//        seasonModelsList.add(new season_model(9,"2000/01"));
//        seasonModelsList.add(new season_model(8,"1999/00"));
//        seasonModelsList.add(new season_model(7,"1998/99"));
//        seasonModelsList.add(new season_model(6,"1997/98"));
//        seasonModelsList.add(new season_model(5,"1996/97"));
//        seasonModelsList.add(new season_model(4,"1995/96"));
//        seasonModelsList.add(new season_model(3,"1994/95"));
//        seasonModelsList.add(new season_model(2,"1993/94"));
//        seasonModelsList.add(new season_model(1,"1992/93"));



    }


    //getTeamStats for first Team
    public  void getTeamFixtureFirst(final String TeamID,final String UserAgent){
        //getTeamStats for first Team
        progresss_indecater.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TeamID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject obj = new JSONObject(response);

                    JSONArray StatArray = obj.getJSONArray("stats");

                    //Attack and top Stats
                    Log.d("TeamStat"," " + StatArray.length());
                    int     Match_wins = 0, Match_draw= 0, Match_losse= 0,
                            Total_MathsPlayed= 0,Goals= 0,
                            Clean_sheets= 0,Total_Shots= 0,Total_Shots_on_Target= 0,
                            Total_pen_scored= 0,Tota_Big_chance_created= 0,Total_HitWoodwork= 0;
                    double Goal_per_match= 0,Total_shooting_accuracyPercent= 0;


                    //for total match played
                    boolean wins = false,draw=false,lose=false,matchplayed = false;
                    //Goals per match
                    boolean goalhave = false;
                    //Shooting accuracy %
                    boolean getontarget_scoring_att = false,gettotal_scoring_att=false;
                    //Goals Conceded
                    boolean goalConceded =false;
                    //Tackle percent getTotal_tackle getwontackle
                    boolean getTotal_tackle = false,getwontackle = false;
                    //For ariale dule
                    boolean getarail_wpon = false , getduel_won = false;
                    //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                    boolean getTotalPasses = false,get_accurate_pass=false;
                    //cross per match TotalCrosses Total_accurate_cross getTotalCross getTotal_accurate_croos
                    boolean getTotalCross =false , getTotal_accurate_croos = false;

                    //Defence aerial_won,duel_won
                    int clean_sheet= 0,goals_conceded=0,saves= 0,
                            total_tackle= 0,tackle_won=0,blocked_scoring_att= 0,
                            interception= 0,total_clearance= 0,
                            effective_head_clearance= 0,aerail_won =0,duel_won=0
                            ,error_lead_to_goal= 0,own_goals= 0;

                    double Goals_Conceded_per_match= 0,Tackles_succes_percent= 0;

                    //Teamplay TotalCrosses Total_accurate_cross
                    int Total_passes = 0,TotalCrosses= 0,Total_accurate_pass = 0,Total_accurate_cross =0;
                    double Total_passes_per_match= 0,Totoal_pass_accuracy_per= 0,Total_cross_accuracy_per= 0;


                    //Disiplene
                    int total_yel_card= 0,total_red_card= 0,attempted_tackle_foul= 0,total_offside= 0;

                    for(int i=0;i<StatArray.length();i++){

                        JSONObject StatData = StatArray.getJSONObject(i);

                        String StateName = StatData.getString("name");
                        //TopStats and Attack stats
                        if (Objects.equals(StateName, "wins")) {//wins
                            Match_wins = StatData.getInt("value");
                          overview_compare_first_wins.setText(getFormatedAmount(Match_wins));
                            wins = true;
                            // Log.d("TeamStat", " Match_wins " + i+ " " + Match_wins);
                        }
                        else {
                            if (!wins){
                                wins = true;

                            }
                        }


                        if (Objects.equals(StateName, "draws")){//draws
                            Match_draw = StatData.getInt("value");
                            overview_compare_first_draw.setText(getFormatedAmount(Match_draw));
                            draw = true;
                            //   Log.d("TeamStat", " Match_draw " + i+ " " +Match_draw);
                        }else {
                            if (!draw){
                                draw = true;

                            }
                        }
                        if (Objects.equals(StateName, "losses")){//losses
                            Match_losse = StatData.getInt("value");
                            lose = true;
                        overview_compare_first_losses.setText(getFormatedAmount(Match_losse));
                            //  Log.d("TeamStat", " Match_losse " +i+ " " + Match_losse);
                        }else {
                            if (!lose){
                                lose = true;

                            }
                        }

                        if (Objects.equals(StateName, "total_scoring_att")){//total_Shots
                            Total_Shots = StatData.getInt("value");
                        attack_compare_shots_first.setText(getFormatedAmount(Total_Shots));
                            gettotal_scoring_att=true;
                            //  Log.d("TeamStat", " Total_Shots " +i+ " " + Total_Shots);  boolean getontarget_scoring_att = false,gettotal_scoring_att=false;
                        }
                        if (Objects.equals(StateName, "ontarget_scoring_att")){//Shots on target
                            Total_Shots_on_Target = StatData.getInt("value");
                            attack_compare_shots_Ontarget_first.setText(getFormatedAmount(Total_Shots_on_Target));
                            getontarget_scoring_att =true;
                            // Log.d("TeamStat", " Total_Shots_on_Target " + i+ " " +Total_Shots_on_Target);
                        }
                        if (Objects.equals(StateName, "att_pen_goal")){//Penalties scored
                            Total_pen_scored = StatData.getInt("value");
                            attack_compare_penalties_socred_first.setText(getFormatedAmount(Total_pen_scored));
                            //  Log.d("TeamStat", " Total_pen_scored " +i+ " " + Total_pen_scored);
                        }
                        if (Objects.equals(StateName, "big_chance_created")){//big_chance_created
                            Tota_Big_chance_created = StatData.getInt("value");
                            attack_compare_big_chances_created_first.setText(getFormatedAmount(Tota_Big_chance_created));
                            //Log.d("TeamStat", " Tota_Big_chance_created " +i+ " " + Tota_Big_chance_created);
                        }
                        if (Objects.equals(StateName, "hit_woodwork")){//hit_woodwork
                            Total_HitWoodwork = StatData.getInt("value");
                            attack_compare_hit_woodwork_first.setText(getFormatedAmount(Total_HitWoodwork));
                            //Log.d("TeamStat", " Total_HitWoodwork " +i+ " " + Total_HitWoodwork);
                        }//

                        if (Objects.equals(StateName, "clean_sheet")){//Clean sheets
                            Clean_sheets = StatData.getInt("value");
                          defence_compare_clean_sheets_second.setText(getFormatedAmount(Clean_sheets));
                          //  text_view_total_clean.setText(String.valueOf(Clean_sheets));
                            //Log.d("TeamStat", " Clean_sheets " + Clean_sheets);
                        }
                        if (Objects.equals(StateName, "total_scoring_att")){//Shooting accuracy%--
                            Total_shooting_accuracyPercent = StatData.getInt("value");
                            //  Log.d("TeamStat", " Total_shooting_accuracyPercent " + Total_shooting_accuracyPercent);
                        }


                        if (Objects.equals(StateName, "goals")){//Goal
                            Goals = StatData.getInt("value");
                            goalhave = true;
               //      overview_compare_first_goals.setText(getNumberByString(String.valueOf(Goals)));
                     attack_compare_goals_first.setText(getFormatedAmount(Goals));
                            overview_compare_first_goals.setText(getFormatedAmount(Goals));
                         //   text_view_total_match_goals.setText(String.valueOf(Goals));
                            //    Log.d("TeamStat", " Goals " + Goals);



                        }







                        //Defence
                       if (Objects.equals(StateName, "clean_sheet")){//Cleasn Sheets
                                Clean_sheets = StatData.getInt("value");
                                defence_compare_clean_sheets_first.setText(getFormatedAmount(Clean_sheets));
                          //  Log.d("TeamStat", " Clean_sheets " + Clean_sheets);
                         }
                        if (Objects.equals(StateName, "goals_conceded")){//Goals conceded
                            goals_conceded = StatData.getInt("value");
                            defence_compare_goalConceded_first.setText(getFormatedAmount(goals_conceded));
                          //  text_view_total_goal_conceded.setText(String.valueOf(goals_conceded));
                            goalConceded = true;
                            //  Log.d("TeamStat", " goals_conceded " + goals_conceded);
                        }

                        if (Objects.equals(StateName, "saves")){//saves
                            saves = StatData.getInt("value");
                        defence_compare_saves_first.setText(getFormatedAmount(saves));
                            //  Log.d("TeamStat", " saves " + saves);
                        }
                        if (Objects.equals(StateName, "total_tackle")){//Tackles getTotal_tackle getwontackle total_tackle tackle_won
                            total_tackle = StatData.getInt("value");
                          defence_compare_tackles_first.setText(getFormatedAmount(total_tackle));
                            getTotal_tackle =true;
                            //  Log.d("TeamStat", " total_tackle " + total_tackle);
                        }
                        if (Objects.equals(StateName, "won_tackle")){//Tackles getTotal_tackle getwontackle
                            tackle_won = StatData.getInt("value");
                            getwontackle = true;
                          //  defence_compare_tacklesSucess_first.setText(String.valueOf(total_tackle));
                            //  Log.d("TeamStat", " total_tackle " + total_tackle);
                        }

                        if (Objects.equals(StateName, "blocked_scoring_att")){//Blocked shots
                            blocked_scoring_att = StatData.getInt("value");
                           defence_compare_blaocked_first.setText(getFormatedAmount(blocked_scoring_att));
                            // Log.d("TeamStat", " blocked_scoring_att " + blocked_scoring_att);
                        }
                        if (Objects.equals(StateName, "interception")){//Intercepition
                            interception = StatData.getInt("value");
                        defence_compare_interceptions_first.setText(getFormatedAmount(interception));
                            // Log.d("TeamStat", " interception " + interception);
                        }
                        if (Objects.equals(StateName, "total_clearance")){//Clearnce
                            total_clearance = StatData.getInt("value");
                            defence_compare_clearances_fisrt.setText(getFormatedAmount(total_clearance));
                            // Log.d("TeamStat", " total_clearance " + total_clearance);
                        }
                        if (Objects.equals(StateName, "effective_head_clearance")){//Headed clearance
                            effective_head_clearance = StatData.getInt("value");
                            defence_compare_headedClearnce_first.setText(getFormatedAmount(effective_head_clearance));
                            //Log.d("TeamStat", " effective_head_clearance " + effective_head_clearance);
                        }

                        //aerial_won,duel_won aerail_won =0,duel_won=0  boolean getarail_wpon = false , getduel_won = false;
                        if (Objects.equals(StateName, "aerial_won")){//Errors leading to goa
                            aerail_won = StatData.getInt("value");
                            getarail_wpon =true;
                            //textview_errors.setText(String.valueOf(error_lead_to_goal));
                            //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                        }
                        //aerial_won,duel_won aerail_won =0,duel_won=0
                        if (Objects.equals(StateName, "duel_won")){//Errors leading to goa
                            duel_won = StatData.getInt("value");
                            getduel_won = true;
                            //textview_errors.setText(String.valueOf(error_lead_to_goal));
                            //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                        }

                        if (Objects.equals(StateName, "error_lead_to_goal")){//Errors leading to goa
                            error_lead_to_goal = StatData.getInt("value");
                         //   textview_errors.setText(String.valueOf(error_lead_to_goal));
                            //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                        }
                        if (Objects.equals(StateName, "own_goals")){///Own Goals
                            own_goals = StatData.getInt("value");
                            defence_compare_Own_goals_first.setText(getFormatedAmount(own_goals));
                            // Log.d("TeamStat", " own_goals " + own_goals); penalty_conceded
                        }

                        if (Objects.equals(StateName, "penalty_conceded")){///Own Goals
                            own_goals = StatData.getInt("value");
                            defence_compare_PenaltiesCOn_first.setText(getFormatedAmount(own_goals));
                            // Log.d("TeamStat", " own_goals " + own_goals); penalty_conceded
                        }


                        //-------------TeamPlat //Passes per match matchplayed getTotalPasses
                        if (Objects.equals(StateName, "total_pass")){//total_pass
                            Total_passes = StatData.getInt("value");
                            team_play_compare_passes_first.setText(getFormatedAmount(Total_passes));
                            getTotalPasses = true;
                            // Log.d("TeamStat", " Total_passes " + Total_passes);
                        }

                        if (Objects.equals(StateName, "accurate_pass")){//Pass accuracy%
                            Total_accurate_pass = StatData.getInt("value");
                            get_accurate_pass =true;
                            //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                            // Log.d("TeamStat", " Totoal_pass_accuracy_per " + Totoal_pass_accuracy_per);
                        }

                        if (Objects.equals(StateName, "total_cross")){//Crosses
                            TotalCrosses = StatData.getInt("value");
                            getTotalCross =true;
                             team_play_compare_crosses_first.setText(getFormatedAmount(TotalCrosses));
                            // Log.d("TeamStat", " TotalCrosses " + TotalCrosses);
                        }
                        if (Objects.equals(StateName, "accurate_cross")){//Crosses accuracy%
                            Total_accurate_cross = StatData.getInt("value");
                            getTotal_accurate_croos = true;
                            //Log.d("TeamStat", " Total_cross_accuracy_per " + Total_cross_accuracy_per);
                        }





                        //------------Disiple
                        if (Objects.equals(StateName, "total_yel_card")){//yellow cards
                            total_yel_card = StatData.getInt("value");
                            discipline_compare_yellowCards_first.setText(getFormatedAmount(total_yel_card));
                            //   Log.d("TeamStat", " Match_losse " + total_yel_card);
                        }
                        if (Objects.equals(StateName, "total_red_card")){//Red cards
                            total_red_card = StatData.getInt("value");
                            discipline_compare_red_cards_first.setText(getFormatedAmount(total_red_card));
                            //   Log.d("TeamStat", " Match_losse " + total_red_card);
                        }
                        if (Objects.equals(StateName, "attempted_tackle_foul")){//attempted_tackle_foul
                            attempted_tackle_foul = StatData.getInt("value");
                             discipline_compare_fouls_first.setText(getFormatedAmount(attempted_tackle_foul));
                            //   Log.d("TeamStat", " Match_losse " + attempted_tackle_foul);
                        }








                        /*
                        if (Objects.equals(StateName, "wins")){//Matches played --

                            //  Log.d("TeamStat", " Total_MathsPlayed " + Total_MathsPlayed);
                          } boolean wins,draw,lose;
                         */

                        //Match played
                        if(wins && draw && lose){
                            Total_MathsPlayed = Match_wins+Match_draw+Match_losse;
                            matchplayed =true;
                            //Toast.makeText(getContext(), "Hellpw " + Match_wins + " " + Match_draw + " " + Match_losse, Toast.LENGTH_LONG).show();
                       //     text_view_total_match_played.setText(String.valueOf(Total_MathsPlayed));
                        }

                        //Goal per match
                        if (matchplayed&&goalhave&&Total_MathsPlayed!=0){
                            //00text_view_goals_per_match
                            double GoalPER = getCalculatedData(Goals,Total_MathsPlayed);
                            attack_compare_goals_per_match_first.setText(String.valueOf(GoalPER));
                       //     text_view_goals_per_match.setText(String.valueOf(GoalPER));
                            //matchplayed =false;
                        }

                        //Shooting accuracy
                        if (gettotal_scoring_att && getontarget_scoring_att && Total_Shots!=0 && Total_Shots_on_Target !=0){
                            //   getCalculatedPercent
                            float Percetn = getCalculatedPercent(Total_Shots_on_Target,Total_Shots);
                            String Text = String.valueOf(Math.round(Percetn)) + "%";
                            attack_compare_shooting_accuracy_first.setText(Text);

                        }

                        //goalConceded per match
                        if (goalConceded&&matchplayed){
                            double Goalconcedde = getCalculatedData(goals_conceded,Total_MathsPlayed);
                            // Log.d("TeamStat", " Total_Shots_on_Target " + matchplayed + " goalConceded " + goalConceded  + "  "+ Goalconcedde);
                            defence_compare_goalConcededPMatch_first.setText(String.valueOf(Goalconcedde));
                        }

                        //Tackles getTotal_tackle getwontackle Tackles getTotal_tackle getwontackle total_tackle
                        if (getTotal_tackle && getwontackle ){
                            //   getCalculatedPercent
                            float TacklePercetn = getCalculatedPercent(tackle_won,total_tackle);
                            String Text = String.valueOf(Math.round(TacklePercetn)) + "%";
                         defence_compare_tacklesSucess_first.setText(Text);

                        }

                        //Areal won duel won //aerial_won,duel_won aerail_won =0,duel_won=0  boolean getarail_wpon = false , getduel_won = false;
                        if (getarail_wpon && getduel_won){
                            int ArealWOnData = aerail_won + duel_won;
                         //   textView_arialwon.setText(String.valueOf(ArealWOnData));
                        }
                        //Passes per match matchplayed getTotalPasses
                        if (matchplayed&&getTotalPasses){
                            double passperMatch = getCalculatedData(Total_passes,Total_MathsPlayed);
                            team_play_compare_passmatch_first.setText(String.valueOf(passperMatch));
                        }

                        //Pass accurracy //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                        if (get_accurate_pass&&getTotalPasses){
                            float acuratePassPer = getCalculatedPercent(Total_accurate_pass,Total_passes);
                            String Text = String.valueOf(Math.round(acuratePassPer))+"%";
                            team_play_compare_passacc_first.setText(Text);
                        }

                        //cross accuress    per match TotalCrosses Total_accurate_cross getTotalCross getTotal_accurate_croos
                        if (getTotal_accurate_croos&&getTotalCross){
                            float acurateCrossPer = getCalculatedPercent(Total_accurate_cross,TotalCrosses);
                            String Text = String.valueOf(Math.round(acurateCrossPer))+"%";
                            team_play_compare_crossesaccu_first.setText(Text);
                        }

                    }




//getTeamStats for first Team
                    layout_container_stats.setVisibility(View.VISIBLE);
                    progresss_indecater.hide();
                }catch (Exception e){
                    progresss_indecater.hide();
                    Toast.makeText(Team_comparision_activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                    // swipeRefreshLayout.setRefreshing(false);
                   // ErrorTextView.setVisibility(View.VISIBLE);
                }
              //  LayoutContainer.setVisibility(View.VISIBLE);
               // swipeRefreshLayout.setRefreshing(false);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progresss_indecater.hide();
                Toast.makeText(Team_comparision_activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
               // swipeRefreshLayout.setRefreshing(false);
               // ErrorTextView.setVisibility(View.VISIBLE);
                // Log.d("VolleyRequestError",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent",UserAgent);
                params.put("Referer", "https://www.premierleague.com/clubs");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    //getTeamStats for Second Team
    public void getTeamFixtureSecond(final String TeamID, final String UserAgent){
        progresss_indecater.show();
   //     Toast.makeText(Team_comparision_activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, TeamID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject obj = new JSONObject(response);

                    JSONArray StatArray = obj.getJSONArray("stats");

                    //Attack and top Stats
                //    Log.d("TeamStat"," " + StatArray.length());
                    int     Match_wins = 0, Match_draw= 0, Match_losse= 0,
                            Total_MathsPlayed= 0,Goals= 0,
                            Clean_sheets= 0,Total_Shots= 0,Total_Shots_on_Target= 0,
                            Total_pen_scored= 0,Tota_Big_chance_created= 0,Total_HitWoodwork= 0;
                    double Goal_per_match= 0,Total_shooting_accuracyPercent= 0;


                    //for total match played
                    boolean wins = false,draw=false,lose=false,matchplayed = false;
                    //Goals per match
                    boolean goalhave = false;
                    //Shooting accuracy %
                    boolean getontarget_scoring_att = false,gettotal_scoring_att=false;
                    //Goals Conceded
                    boolean goalConceded =false;
                    //Tackle percent getTotal_tackle getwontackle
                    boolean getTotal_tackle = false,getwontackle = false;
                    //For ariale dule
                    boolean getarail_wpon = false , getduel_won = false;
                    //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                    boolean getTotalPasses = false,get_accurate_pass=false;
                    //cross per match TotalCrosses Total_accurate_cross getTotalCross getTotal_accurate_croos
                    boolean getTotalCross =false , getTotal_accurate_croos = false;

                    //Defence aerial_won,duel_won
                    int clean_sheet= 0,goals_conceded=0,saves= 0,
                            total_tackle= 0,tackle_won=0,blocked_scoring_att= 0,
                            interception= 0,total_clearance= 0,
                            effective_head_clearance= 0,aerail_won =0,duel_won=0
                            ,error_lead_to_goal= 0,own_goals= 0;

                    double Goals_Conceded_per_match= 0,Tackles_succes_percent= 0;

                    //Teamplay TotalCrosses Total_accurate_cross
                    int Total_passes = 0,TotalCrosses= 0,Total_accurate_pass = 0,Total_accurate_cross =0;
                    double Total_passes_per_match= 0,Totoal_pass_accuracy_per= 0,Total_cross_accuracy_per= 0;


                    //Disiplene
                    int total_yel_card= 0,total_red_card= 0,attempted_tackle_foul= 0,total_offside= 0;

                    for(int i=0;i<StatArray.length();i++){

                        JSONObject StatData = StatArray.getJSONObject(i);

                        String StateName = StatData.getString("name");
                        //TopStats and Attack stats
                        if (Objects.equals(StateName, "wins")) {//wins
                            Match_wins = StatData.getInt("value");
                            overview_compare_second_wins.setText(getFormatedAmount(Match_wins));
                            wins = true;
                            // Log.d("TeamStat", " Match_wins " + i+ " " + Match_wins);
                        }
                        else {
                            if (!wins){
                                wins = true;

                            }
                        }


                        if (Objects.equals(StateName, "draws")){//draws
                            Match_draw = StatData.getInt("value");
                            overview_compare_second_draw.setText(getFormatedAmount(Match_draw));
                            draw = true;
                            //   Log.d("TeamStat", " Match_draw " + i+ " " +Match_draw);
                        }else {
                            if (!draw){
                                draw = true;

                            }
                        }
                        if (Objects.equals(StateName, "losses")){//losses
                            Match_losse = StatData.getInt("value");
                            lose = true;
                            overview_compare_second_losses.setText(getFormatedAmount(Match_losse));
                            //  Log.d("TeamStat", " Match_losse " +i+ " " + Match_losse);
                        }else {
                            if (!lose){
                                lose = true;

                            }
                        }

                        if (Objects.equals(StateName, "total_scoring_att")){//total_Shots
                            Total_Shots = StatData.getInt("value");
                            attack_compare_shots_second.setText(getFormatedAmount(Total_Shots));
                            gettotal_scoring_att=true;
                            //  Log.d("TeamStat", " Total_Shots " +i+ " " + Total_Shots);  boolean getontarget_scoring_att = false,gettotal_scoring_att=false;
                        }
                        if (Objects.equals(StateName, "ontarget_scoring_att")){//Shots on target
                            Total_Shots_on_Target = StatData.getInt("value");
                            attack_compare_shots_Ontarget_second.setText(getFormatedAmount(Total_Shots_on_Target));
                            getontarget_scoring_att =true;
                            // Log.d("TeamStat", " Total_Shots_on_Target " + i+ " " +Total_Shots_on_Target);
                        }
                        if (Objects.equals(StateName, "att_pen_goal")){//Penalties scored
                            Total_pen_scored = StatData.getInt("value");
                            attack_compare_penalties_socred_second.setText(getFormatedAmount(Total_pen_scored));
                            //  Log.d("TeamStat", " Total_pen_scored " +i+ " " + Total_pen_scored);
                        }
                        if (Objects.equals(StateName, "big_chance_created")){//big_chance_created
                            Tota_Big_chance_created = StatData.getInt("value");
                            attack_compare_big_chances_created_second.setText(getFormatedAmount(Tota_Big_chance_created));
                            //Log.d("TeamStat", " Tota_Big_chance_created " +i+ " " + Tota_Big_chance_created);
                        }
                        if (Objects.equals(StateName, "hit_woodwork")){//hit_woodwork
                            Total_HitWoodwork = StatData.getInt("value");
                            attack_compare_hit_woodwork_second.setText(getFormatedAmount(Total_HitWoodwork));
                            //Log.d("TeamStat", " Total_HitWoodwork " +i+ " " + Total_HitWoodwork);
                        }//

                        if (Objects.equals(StateName, "clean_sheet")){//Clean sheets
                            Clean_sheets = StatData.getInt("value");
                            defence_compare_clean_sheets_second.setText(getFormatedAmount(Clean_sheets));
                            //  text_view_total_clean.setText(String.valueOf(Clean_sheets));
                            //Log.d("TeamStat", " Clean_sheets " + Clean_sheets);
                        }
                        if (Objects.equals(StateName, "total_scoring_att")){//Shooting accuracy%--
                            Total_shooting_accuracyPercent = StatData.getInt("value");
                            //  Log.d("TeamStat", " Total_shooting_accuracyPercent " + Total_shooting_accuracyPercent);
                        }


                        if (Objects.equals(StateName, "goals")){//Goal
                            Goals = StatData.getInt("value");
                            goalhave = true;
                            //      overview_compare_first_goals.setText(getNumberByString(String.valueOf(Goals)));
                            attack_compare_goals_second.setText(getFormatedAmount(Goals));
                            overview_compare_second_goals.setText(getFormatedAmount(Goals));
                            //   text_view_total_match_goals.setText(String.valueOf(Goals));
                            //    Log.d("TeamStat", " Goals " + Goals);



                        }







                        //Defence
                        // if (Objects.equals(StateName, "clean_sheet")){//Cleasn Sheets
                        ///     Clean_sheets = StatData.getInt("value");
                        //     Log.d("TeamStat", " Clean_sheets " + Clean_sheets);
                        // }
                        if (Objects.equals(StateName, "goals_conceded")){//Goals conceded
                            goals_conceded = StatData.getInt("value");
                            defence_compare_goalConceded_second.setText(getFormatedAmount(goals_conceded));
                            //  text_view_total_goal_conceded.setText(String.valueOf(goals_conceded));
                            goalConceded = true;
                            //  Log.d("TeamStat", " goals_conceded " + goals_conceded);
                        }

                        if (Objects.equals(StateName, "saves")){//saves
                            saves = StatData.getInt("value");
                            defence_compare_saves_second.setText(getFormatedAmount(saves));
                            //  Log.d("TeamStat", " saves " + saves);
                        }
                        if (Objects.equals(StateName, "total_tackle")){//Tackles getTotal_tackle getwontackle total_tackle tackle_won
                            total_tackle = StatData.getInt("value");
                            defence_compare_tackles_second.setText(getFormatedAmount(total_tackle));
                            getTotal_tackle =true;
                            //  Log.d("TeamStat", " total_tackle " + total_tackle);
                        }
                        if (Objects.equals(StateName, "won_tackle")){//Tackles getTotal_tackle getwontackle
                            tackle_won = StatData.getInt("value");
                            getwontackle = true;
                            //  defence_compare_tacklesSucess_first.setText(String.valueOf(total_tackle));
                            //  Log.d("TeamStat", " total_tackle " + total_tackle);
                        }

                        if (Objects.equals(StateName, "blocked_scoring_att")){//Blocked shots
                            blocked_scoring_att = StatData.getInt("value");
                            defence_compare_blaocked_second.setText(getFormatedAmount(blocked_scoring_att));
                            // Log.d("TeamStat", " blocked_scoring_att " + blocked_scoring_att);
                        }
                        if (Objects.equals(StateName, "interception")){//Intercepition
                            interception = StatData.getInt("value");
                            defence_compare_interceptions_second.setText(getFormatedAmount(interception));
                            // Log.d("TeamStat", " interception " + interception);
                        }
                        if (Objects.equals(StateName, "total_clearance")){//Clearnce
                            total_clearance = StatData.getInt("value");
                            defence_compare_clearances_second.setText(getFormatedAmount(total_clearance));
                            // Log.d("TeamStat", " total_clearance " + total_clearance);
                        }
                        if (Objects.equals(StateName, "effective_head_clearance")){//Headed clearance
                            effective_head_clearance = StatData.getInt("value");
                            defence_compare_headedClearnce_second.setText(getFormatedAmount(effective_head_clearance));
                            //Log.d("TeamStat", " effective_head_clearance " + effective_head_clearance);
                        }

                        //aerial_won,duel_won aerail_won =0,duel_won=0  boolean getarail_wpon = false , getduel_won = false;
                        if (Objects.equals(StateName, "aerial_won")){//Errors leading to goa
                            aerail_won = StatData.getInt("value");
                            getarail_wpon =true;
                            //textview_errors.setText(String.valueOf(error_lead_to_goal));
                            //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                        }
                        //aerial_won,duel_won aerail_won =0,duel_won=0
                        if (Objects.equals(StateName, "duel_won")){//Errors leading to goa
                            duel_won = StatData.getInt("value");
                            getduel_won = true;
                            //textview_errors.setText(String.valueOf(error_lead_to_goal));
                            //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                        }

                        if (Objects.equals(StateName, "error_lead_to_goal")){//Errors leading to goa
                            error_lead_to_goal = StatData.getInt("value");
                            //   textview_errors.setText(String.valueOf(error_lead_to_goal));
                            //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                        }
                        if (Objects.equals(StateName, "own_goals")){///Own Goals
                            own_goals = StatData.getInt("value");
                            defence_compare_Own_goals_second.setText(getFormatedAmount(own_goals));
                            // Log.d("TeamStat", " own_goals " + own_goals); penalty_conceded
                        }

                        if (Objects.equals(StateName, "penalty_conceded")){///Own Goals
                            own_goals = StatData.getInt("value");
                            defence_compare_PenaltiesCOn_second.setText(getFormatedAmount(own_goals));
                            // Log.d("TeamStat", " own_goals " + own_goals); penalty_conceded
                        }


                        //-------------TeamPlat //Passes per match matchplayed getTotalPasses
                        if (Objects.equals(StateName, "total_pass")){//total_pass
                            Total_passes = StatData.getInt("value");
                            team_play_compare_passes_second.setText(getFormatedAmount(Total_passes));
                            getTotalPasses = true;
                            // Log.d("TeamStat", " Total_passes " + Total_passes);
                        }

                        if (Objects.equals(StateName, "accurate_pass")){//Pass accuracy%
                            Total_accurate_pass = StatData.getInt("value");
                            get_accurate_pass =true;
                            //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                            // Log.d("TeamStat", " Totoal_pass_accuracy_per " + Totoal_pass_accuracy_per);
                        }

                        if (Objects.equals(StateName, "total_cross")){//Crosses
                            TotalCrosses = StatData.getInt("value");
                            getTotalCross =true;
                            team_play_compare_crosses_second.setText(getFormatedAmount(TotalCrosses));
                            // Log.d("TeamStat", " TotalCrosses " + TotalCrosses);
                        }
                        if (Objects.equals(StateName, "accurate_cross")){//Crosses accuracy%
                            Total_accurate_cross = StatData.getInt("value");
                            getTotal_accurate_croos = true;
                            //Log.d("TeamStat", " Total_cross_accuracy_per " + Total_cross_accuracy_per);
                        }





                        //------------Disiple
                        if (Objects.equals(StateName, "total_yel_card")){//yellow cards
                            total_yel_card = StatData.getInt("value");
                            discipline_compare_yellowCards_second.setText(getFormatedAmount(total_yel_card));
                            //   Log.d("TeamStat", " Match_losse " + total_yel_card);
                        }
                        if (Objects.equals(StateName, "total_red_card")){//Red cards
                            total_red_card = StatData.getInt("value");
                            discipline_compare_red_cards_second.setText(getFormatedAmount(total_red_card));
                            //   Log.d("TeamStat", " Match_losse " + total_red_card);
                        }
                        if (Objects.equals(StateName, "attempted_tackle_foul")){//attempted_tackle_foul
                            attempted_tackle_foul = StatData.getInt("value");
                            discipline_compare_fouls_seoncd.setText(getFormatedAmount(attempted_tackle_foul));
                            //   Log.d("TeamStat", " Match_losse " + attempted_tackle_foul);
                        }








                        /*
                        if (Objects.equals(StateName, "wins")){//Matches played --

                            //  Log.d("TeamStat", " Total_MathsPlayed " + Total_MathsPlayed);
                          } boolean wins,draw,lose;
                         */

                        //Match played
                        if(wins && draw && lose){
                            Total_MathsPlayed = Match_wins+Match_draw+Match_losse;
                            matchplayed =true;
                            //Toast.makeText(getContext(), "Hellpw " + Match_wins + " " + Match_draw + " " + Match_losse, Toast.LENGTH_LONG).show();
                            //     text_view_total_match_played.setText(String.valueOf(Total_MathsPlayed));
                        }

                        //Goal per match
                        if (matchplayed&&goalhave&&Total_MathsPlayed!=0){
                            //00text_view_goals_per_match
                            double GoalPER = getCalculatedData(Goals,Total_MathsPlayed);
                            attack_compare_goals_per_match_second.setText(String.valueOf(GoalPER));
                            //     text_view_goals_per_match.setText(String.valueOf(GoalPER));
                            //matchplayed =false;
                        }

                        //Shooting accuracy
                        if (gettotal_scoring_att && getontarget_scoring_att && Total_Shots!=0 && Total_Shots_on_Target !=0){
                            //   getCalculatedPercent
                            float Percetn = getCalculatedPercent(Total_Shots_on_Target,Total_Shots);
                            String Text = String.valueOf(Math.round(Percetn)) + "%";
                            attack_compare_shooting_accuracy_second.setText(Text);

                        }

                        //goalConceded per match
                        if (goalConceded&&matchplayed){
                            double Goalconcedde = getCalculatedData(goals_conceded,Total_MathsPlayed);
                            // Log.d("TeamStat", " Total_Shots_on_Target " + matchplayed + " goalConceded " + goalConceded  + "  "+ Goalconcedde);
                            defence_compare_goalConcededPMatch_second.setText(String.valueOf(Goalconcedde));
                        }

                        //Tackles getTotal_tackle getwontackle Tackles getTotal_tackle getwontackle total_tackle
                        if (getTotal_tackle && getwontackle ){
                            //   getCalculatedPercent
                            float TacklePercetn = getCalculatedPercent(tackle_won,total_tackle);
                            String Text = String.valueOf(Math.round(TacklePercetn)) + "%";
                            defence_compare_tacklesSucess_second.setText(Text);

                        }

                        //Areal won duel won //aerial_won,duel_won aerail_won =0,duel_won=0  boolean getarail_wpon = false , getduel_won = false;
                        if (getarail_wpon && getduel_won){
                            int ArealWOnData = aerail_won + duel_won;
                            //   textView_arialwon.setText(String.valueOf(ArealWOnData));
                        }
                        //Passes per match matchplayed getTotalPasses
                        if (matchplayed&&getTotalPasses){
                            double passperMatch = getCalculatedData(Total_passes,Total_MathsPlayed);
                            team_play_compare_passmatch_second.setText(String.valueOf(passperMatch));
                        }

                        //Pass accurracy //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                        if (get_accurate_pass&&getTotalPasses){
                            float acuratePassPer = getCalculatedPercent(Total_accurate_pass,Total_passes);
                            String Text = String.valueOf(Math.round(acuratePassPer))+"%";
                            team_play_compare_passacc_second.setText(Text);
                        }

                        //cross accuress    per match TotalCrosses Total_accurate_cross getTotalCross getTotal_accurate_croos
                        if (getTotal_accurate_croos&&getTotalCross){
                            float acurateCrossPer = getCalculatedPercent(Total_accurate_cross,TotalCrosses);
                            String Text = String.valueOf(Math.round(acurateCrossPer))+"%";
                            team_play_compare_crossesaccu_seond.setText(Text);
                        }

                    }



                    progresss_indecater.show();
                    layout_container_stats.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    progresss_indecater.hide();
                    Toast.makeText(Team_comparision_activity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                  //  System.out.println(e.getMessage());
                    // swipeRefreshLayout.setRefreshing(false);
                    // ErrorTextView.setVisibility(View.VISIBLE);
                }
                //  LayoutContainer.setVisibility(View.VISIBLE);
                // swipeRefreshLayout.setRefreshing(false);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                // swipeRefreshLayout.setRefreshing(false);
                // ErrorTextView.setVisibility(View.VISIBLE);
                // Log.d("VolleyRequestError",error.toString());
                progresss_indecater.hide();
                Toast.makeText(Team_comparision_activity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", UserAgent);
                params.put("Referer", "https://www.premierleague.com/clubs");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    public float getCalculatedPercent(int DataStat,int Data_Denominator){
        float Ans = ((float) DataStat/(float)Data_Denominator)*100;
        return Math.round(Ans);
    }

    public double getCalculatedData(int DataStat,int Data_Denominator){
        double Ans = (double) DataStat/ (double ) Data_Denominator;
        return Math.round(Ans);
    }


    private String getFormatedAmount(int amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }




    //Set Snipper1 to default
    public void SetSnipper1ToDefault(){
        spinnerDate1.setSelection(0);

    }



    //Ser Snipper 2 to defaultT
    public void SetSnipper2ToDefault(){
        spinnerDate2.setSelection(0);
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }
}
