package table.football.soccer.livescore.epl.epllive;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wang.avi.AVLoadingIndicatorView;

import table.football.soccer.livescore.epl.epllive.Utils.Helpers.InterAdsHelpers;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_subscirbe_match_setting;
import table.football.soccer.livescore.epl.epllive.ViewPagerAdapter.FixtureAdapter;
import table.football.soccer.livescore.epl.epllive.ViewPagerAdapter.Match_adapter;

public class Match_Activity extends AppCompatActivity {


    ImageButton BackButton,match_sub_scribed_button;
    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    LinearLayout adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        new InterAdsHelpers("MatchActivitAds",getApplicationContext()).ShowInterAds();
        // init();
        intallPager();

        final String MatchId     =  getIntent().getExtras().getString("MatchId");
        //
        BackButton = findViewById(R.id.back_button);
        match_sub_scribed_button = findViewById(R.id.notification_button);

       // final String MatchID = getIntent().getExtras().getString("MatchID");

        setButtonImage(MatchId);
        match_sub_scribed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Pref_subscirbe_match_setting(getApplicationContext()).isNotificationEnable(MatchId)){
                    //if true then disable
                    Toast.makeText(Match_Activity.this, "Match Notification Disable", Toast.LENGTH_SHORT).show();
                    new Pref_subscirbe_match_setting(getApplicationContext()).disabeNotification(MatchId);
                    match_sub_scribed_button.setImageResource(R.drawable.ic_notifications_none_black_24dp);

                }
                else {
                    //if false the enalbe
                    new Pref_subscirbe_match_setting(getApplicationContext()).enableNotifcation(MatchId);
                    Toast.makeText(Match_Activity.this, "Match Notification Enable", Toast.LENGTH_SHORT).show();
                    match_sub_scribed_button.setImageResource(R.drawable.ic_notifications_black_24dp);
                }
            }
        });




        //goBack
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        mAdView = findViewById(R.id.adView);
        // Find the Ad Container
        adContainer = findViewById(R.id.banner_container);

        showAds();

    }

    //Sub button image
    void setButtonImage(String MatchId){
        if (!new Pref_subscirbe_match_setting(getApplicationContext()).isNotificationEnable(MatchId)){
            match_sub_scribed_button.setImageResource(R.drawable.ic_notifications_none_black_24dp);

        }
        else {
            match_sub_scribed_button.setImageResource(R.drawable.ic_notifications_black_24dp);
        }
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

    //Pager
    void intallPager(){
        TabLayout tabLayout = findViewById(R.id.home_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Commentry"));
        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("LineUp"));
        tabLayout.addTab(tabLayout.newTab().setText("Stats"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.home_fragment_pager);
        final Match_adapter Pageradapter = new Match_adapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(Pageradapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //  System.out.println("TabPostionlayout "+tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(3);
        //viewPager.setOffscreenPageLimit(2);
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
