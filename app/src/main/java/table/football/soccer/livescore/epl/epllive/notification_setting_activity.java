package table.football.soccer.livescore.epl.epllive;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import table.football.soccer.livescore.epl.epllive.Adapters.notification_setting_adapter;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.notification_team_id;

public class notification_setting_activity extends AppCompatActivity {
    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    LinearLayout adContainer;
    Toolbar myToolbar;
    RecyclerView recyclerView;
    List<notification_team_id> notification_team_idsList;
    notification_setting_adapter  notificationSettingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting_activity);

        recyclerView = findViewById(R.id.rec_notify);
        notification_team_idsList = new ArrayList<>();
        notificationSettingAdapter = new notification_setting_adapter(notification_team_idsList,getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDivider(ContextCompat.getDrawable(getApplicationContext(),R.drawable.line_divider)));
        recyclerView.hasFixedSize();
//        recyclerView.is
        recyclerView.setAdapter(notificationSettingAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Notification Settings");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        UpdateList();
        mAdView = findViewById(R.id.adView);
        // Find the Ad Container
        adContainer = findViewById(R.id.banner_container);

        showAds();
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }


    }


    public void UpdateList(){
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


        notification_team_idsList.addAll(data);
        notificationSettingAdapter.notifyDataSetChanged();
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

}
