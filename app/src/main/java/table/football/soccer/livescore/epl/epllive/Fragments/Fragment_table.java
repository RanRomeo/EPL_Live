package table.football.soccer.livescore.epl.epllive.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.table_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.model.commentry_model;
import table.football.soccer.livescore.epl.epllive.model.table_model;


public class Fragment_table extends Fragment {


    View v;
    table_adapter tableAdapter;
    RecyclerView recyclerView;
    List<table_model> tableModelList;

    SwipeRefreshLayout Swipe;
    NestedScrollView Nested_table_view;
    LinearLayout adContainer;
    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_table, container, false);
        Swipe = v.findViewById(R.id.Swipe);
        Nested_table_view =v.findViewById(R.id.Nested_table_view);
        Swipe.setRefreshing(true);

        tableModelList = new ArrayList<>();
        tableAdapter = new table_adapter(tableModelList,getContext());
        recyclerView = v.findViewById(R.id.table_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(tableAdapter);

        mAdView = v.findViewById(R.id.adView);
        adContainer = v.findViewById(R.id.banner_container);


        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Swipe.setRefreshing(true);

                if (tableModelList.size()>0){
                    tableModelList.clear();
                }
                loadSeasonData(new WebView(getContext()).getSettings().getUserAgentString());

            }
        });


        loadSeasonData(new WebView(getContext()).getSettings().getUserAgentString());






        return v;
    }


    void loadSeasonData(final String UserAgent){
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

                //    Log.d("FragmeTableData", "TeamPos: " + position + "TeamName: " + TeamName  + "Played:" + Played +" " + "Won:" + Won + "Drawn:" + Drawn +" GD:" + GD )
                    }
                    Nested_table_view.setVisibility(View.VISIBLE);
                    tableModelList.addAll(data);
                    tableAdapter.notifyDataSetChanged();
                    showAds();
                    Swipe.setRefreshing(false);
                }catch (Exception e){

                 //   Log.d("Fragment_seat_season_data"," " +  e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Swipe.setRefreshing(false);

                if(getContext()!=null){
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

                Nested_table_view.setVisibility(View.GONE);
              //  Log.d("Fragment_seat_season_data"," " +  error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent",UserAgent);
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
              //  Log.d("homeAtivity", "onAdFailedToLoad: " + i);
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

}
