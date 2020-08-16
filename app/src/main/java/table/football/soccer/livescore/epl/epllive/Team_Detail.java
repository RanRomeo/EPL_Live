package table.football.soccer.livescore.epl.epllive;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import table.football.soccer.livescore.epl.epllive.Utils.Helpers.InterAdsHelpers;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.ViewPagerAdapter.team_detail_adpater;

public class Team_Detail extends AppCompatActivity {



    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    LinearLayout adContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyRandomTheme);
        if (Build.VERSION.SDK_INT >= 21) {

            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_team__detail);
       // setTitle("dev2qa.com - Android Collapsing Toolbar Example.");
        final String TeamName = getIntent().getExtras().getString("TeamName");

        new InterAdsHelpers("Team_detail_atvity",getApplicationContext()).ShowInterAds();

        //Init adding tablayout
        intallPager();


        final Toolbar myToolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(myToolbar);
        setTitle(TeamName);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar!=null)
//        {
//            // Display home menu item.
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(TeamName);

                    myToolbar.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                   // .//collapsingToolbarLayout.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.foulcolorRed));
                 //   collapsingToolbarLayout.setCollapsedTitleTextColor(getApplicationContext().getResources().getColor(R.color.foulcolorRed));
                    isShow = true;
                } else if(isShow) {

                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    myToolbar.setBackgroundColor(getApplicationContext().getResources().getColor(android.R.color.transparent));
                    isShow = false;
                }




            }
        });


        mAdView = findViewById(R.id.adView);
        // Find the Ad Container
        adContainer = findViewById(R.id.banner_container);

        showAds();

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

    //Create

    //Pager
    void intallPager(){
        TabLayout tabLayout = findViewById(R.id.home_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Squad"));
        tabLayout.addTab(tabLayout.newTab().setText("Fixture"));
        tabLayout.addTab(tabLayout.newTab().setText("Result"));
        tabLayout.addTab(tabLayout.newTab().setText("Stats"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.home_fragment_pager);
        final team_detail_adpater Pageradapter = new team_detail_adpater(getSupportFragmentManager(),tabLayout.getTabCount());
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
        viewPager.setOffscreenPageLimit(2);
        //viewPager.setOffscreenPageLimit(2);
    }
}
