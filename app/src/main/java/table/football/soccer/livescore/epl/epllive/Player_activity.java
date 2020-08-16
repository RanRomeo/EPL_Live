package table.football.soccer.livescore.epl.epllive;

import android.media.Image;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Fragments.Fragment_stats;
import table.football.soccer.livescore.epl.epllive.Utils.Helpers.InterAdsHelpers;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.model.table_model;

public class Player_activity extends AppCompatActivity {
    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    LinearLayout adContainer;

    //EPL OVervie
    TextView player_appearance,player_goals,player_assist,player_clean_Sheets;

    //Overview platyer
    TextView player_age,
             player_name
            ,player_nationality
            ,player_date_of_birth
            ,player_height
            ,player_club
            ,player_position
            ,player_shirt_number;
    RelativeLayout Progres_contariner;
    Boolean TopGoal = false;
    Boolean PrivateData = false;

    ImageView PlayerPic;
    Button ReloadButton;
    ProgressBar playerProgrees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_activity);

        Progres_contariner = findViewById(R.id.Progres_contariner);
        new InterAdsHelpers("Player_activt_",getApplicationContext()).ShowInterAds();

        final String PlayerID = Objects.requireNonNull(getIntent().getExtras()).getString("PlayerID");
        final String PlayerName = Objects.requireNonNull(getIntent().getExtras().getString("PlayerName")).replace(" ","-");

        Log.d("clicklisnte", "onCreate: " + PlayerID + " NAme " + PlayerName);
        ReloadButton = findViewById(R.id.player_reload_button);
        playerProgrees = findViewById(R.id.player_progress);
        PlayerPic = findViewById(R.id.PLayerImage);
        playerProgrees.setVisibility(View.VISIBLE);
           initTextView();
           getTopGoal(new WebView(getApplicationContext()).getSettings().getUserAgentString(),PlayerID);
      new  getPrivateData(new WebView(getApplicationContext()).getSettings().getUserAgentString(),PlayerID,PlayerName).execute();
        mAdView = findViewById(R.id.adView);
        // Find the Ad Container
        adContainer = findViewById(R.id.banner_container);

        showAds();
        //getTopGoal();
        ReloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 TopGoal = false;
                 PrivateData = false;
                showAds();
                ReloadButton.setVisibility(View.GONE);
                playerProgrees.setVisibility(View.VISIBLE);
                getTopGoal(new WebView(getApplicationContext()).getSettings().getUserAgentString(),PlayerID);
               new  getPrivateData(new WebView(getApplicationContext()).getSettings().getUserAgentString(),PlayerID,PlayerName).execute();

            }
        });
    }

    void initTextView(){
        //EPL stats data
        player_appearance = findViewById(R.id.player_appearance);
        player_goals = findViewById(R.id.player_goals);
        player_assist = findViewById(R.id.player_assist);
        player_clean_Sheets = findViewById(R.id.player_clean_Sheets);

        player_age = findViewById(R.id.player_age);
        player_nationality = findViewById(R.id.player_nationality);
        player_date_of_birth = findViewById(R.id.player_date_of_birth);
        player_height = findViewById(R.id.player_height);

        player_club = findViewById(R.id.player_club);
        player_position = findViewById(R.id.player_position);
        player_shirt_number = findViewById(R.id.player_shirt_number);
        player_name = findViewById(R.id.player_name);



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
        // AdSettings.addTestDevice("903898e4-a7b9-422f-b811-47646f070bef");



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

    void getTopGoal(final String UserAgent, final String PlayerID){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/stats/player/"+PlayerID+"?comps=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject obj = new JSONObject(response);
                    //  JSONArray Table = obj.getJSONArray("tables");
                    /// JSONObject MatchData = Table.getJSONObject(0);
                    // JSONArray TeamTable = MatchData.getJSONArray("entries");
                   //
                    JSONArray getDataArray = obj.getJSONArray("stats");
                  //  Log.d("playerData", "onResponse: " + PlayerID);
                   // Log.d("clicklisnte", "JSONObject: " +obj);
                    for(int i=0;i<getDataArray.length();i++){
                     JSONObject StatData = getDataArray.getJSONObject(i);
//                      /*  player_appearance,player_goals,player_assist,player_clean_Sheets;*/
                       String StateName = StatData.getString("name");
                        //TopStats and Attack stats
                        if (Objects.equals(StateName, "appearances")) {//wins
                         int Appear = StatData.getInt("value");
                            player_appearance.setText(String.valueOf(Appear));
                        }

                        if (Objects.equals(StateName, "goal")) {//wins
                            int goal = StatData.getInt("value");
                            player_goals.setText(String.valueOf(goal));
                        }

                        if (Objects.equals(StateName, "goal_assist")) {//wins
                            int goal_assist = StatData.getInt("value");
                            player_assist.setText(String.valueOf(goal_assist));
                        }

                        if (Objects.equals(StateName, "clean_sheet")) {//wins
                            int clean_sheet = StatData.getInt("value");
                            player_clean_Sheets.setText(String.valueOf(clean_sheet));
                        }





                    }
                    //
                     TopGoal = true;

                   // Boolean PrivateData = false;

                    if (TopGoal&&PrivateData){
                        playerProgrees.setVisibility(View.GONE);
                        Progres_contariner.setVisibility(View.GONE);
                    }


                }catch (Exception e){
                    //Log.d("Fragment_playerstat"," " +  e);
                    //  Log.d("Fragment_seat_season_data"," " +  e);
                    //Log.d("clicklisnte", "erroonCreate: " +e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Progres_contariner.setVisibility(View.VISIBLE);

                ReloadButton.setVisibility(View.VISIBLE);
                playerProgrees.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", UserAgent);
                params.put("Referer", "https://www.premierleague.com/players");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;}
        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    private class getPrivateData extends AsyncTask<Void,Void,Void>{

        String USER_AGENT;
        String PlayerID;
        String PlayerName;

        public getPrivateData(String USER_AGENT, String playerID, String playerName) {
            this.USER_AGENT = USER_AGENT;
            PlayerID = playerID;
            PlayerName = playerName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
               // String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36";

                String USER_AGENTS = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36";
                Document document;
                document = Jsoup.connect("https://www.premierleague.com/players/"+PlayerID+"/"+PlayerName+"/overview").userAgent(USER_AGENTS).timeout(10 * 1000).get();
                //Log.d("playerData", "doInBackground: " + PlayerID+ " link ::"+"https://www.premierleague.com/players/"+PlayerID+"/"+PlayerName+"/overview");
                //playerDetails
                Elements LastPage = document.select(".imgContainer");
                String dataId = LastPage.select("img").attr("data-player");
                final String playername = document.select(".playerDetails").select(".name").text();
                //     Log.d("Commentry", "doInBackground: " + "STarted" + dataId);
              final String  Link = "https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+dataId+".png";
                // System.out.println(dataId);
                //System.out.println();




                final String PlayerNuumber     = document.select(".playerDetails").select(".number").text();
                final String PlayerName        = document.select(".playerDetails").select(".name").text();
                final String PlayerNationality = document.select(".personalLists").select("ul.pdcol1").select(".playerCountry").text();

                 String Player_Age = null, Player_DOB=null;
                if (document.select(".personalLists").select("ul.pdcol2").select(".info").size()==2){
                    Player_Age        = document.select(".personalLists").select("ul.pdcol2").select(".info").get(0).text();
                    Player_DOB        = document.select(".personalLists").select("ul.pdcol2").select(".info").get(1).text();
                }



             //  Log.d("Playeract", "doInBackground: pdcol3 "+document.select(".personalLists").select("ul.pdcol3").select(".info").size());

                 String Player_Height;


                    Player_Height    = document.select(".personalLists").select("ul.pdcol3").select(".info").get(0).text();
                  //  Player_weight     = document.select(".personalLists").select("ul.pdcol3").select(".info").get(1).text();







               //  Log.d("Playeract", "doInBackground: t3-topBorder "+document.select("section.sideWidget.playerIntro.t3-topBorder").select(".info").size());


                 String Player_club = null,Player_position=null;

                if (document.select("section.sideWidget.playerIntro").select(".info").size()==2){
                    Player_club   = document.select("section.sideWidget.playerIntro").select(".info").get(0).text();
                    Player_position = document.select("section.sideWidget.playerIntro").select(".info").get(1).text();
                }


                //                TextView player_age
//                ,player_nationality
//                ,player_date_of_birth
//                ,player_height
//                ,player_wieght
//                ,player_club
//                ,player_position
//                ,player_shirt_number;


                final String finalPlayer_Height = Player_Height;

                final String finalPlayer_club = Player_club;
                final String finalPlayer_position = Player_position;
                final String finalPlayer_Age = Player_Age;
                final String finalPlayer_DOB = Player_DOB;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        player_name.setText(PlayerName);
                        player_shirt_number.setText(PlayerNuumber);
                        player_age.setText(finalPlayer_Age);
                        player_nationality.setText(PlayerNationality);
                        player_date_of_birth.setText(finalPlayer_DOB);
                        player_height.setText(finalPlayer_Height);
                        player_club.setText(finalPlayer_club);
                        player_position.setText(finalPlayer_position);
                        PlayerPic.clearColorFilter();
                        PrivateData = true;
                        Picasso.with(getApplicationContext()).load(Link).into(PlayerPic, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });

                        if (TopGoal&&PrivateData){
                            playerProgrees.setVisibility(View.GONE);
                            Progres_contariner.setVisibility(View.GONE);
                        }
                    }
                });



            /*

              //sideWidget playerIntro t3-topBorder


            //sideWidget playerSidebarTable t36-topBorder
            String PlayerApperance   = document.select("section.sideWidget.playerSidebarTable.t3-topBorder").toString();
            String CleanSheats;
            String Goals;
            String Assits;
            System.out.println(PlayerApperance);

            */


          /*
                      System.out.println("PlayerNumve: " + PlayerNuumber + " PlayerName:" + PlayerName + " PlayerNationality:"
                            + PlayerNationality + " Player_Age:"+Player_Age + " Player_DOB:"+Player_DOB
                            + "  Player_Height:"+Player_Height+ "  Player_weight:"+Player_weight);

            */

            } catch (Exception  e) {
               // Log.d("Playeract", "doInBackground: " + e);
              //  Log.d("clicklisnte", "ExceptionerroonCreate: " +e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Progres_contariner.setVisibility(View.VISIBLE);
                        // ReloadButton = findViewById(R.id.player_reload_button);
                        // playerProgrees = findViewById(R.id.player_progress);
                        ReloadButton.setVisibility(View.VISIBLE);
                        playerProgrees.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }





}
