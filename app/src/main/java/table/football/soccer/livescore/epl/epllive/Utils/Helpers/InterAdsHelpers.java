package table.football.soccer.livescore.epl.epllive.Utils.Helpers;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;

public class InterAdsHelpers {


    private String TAG;
    private Context context;
    private com.google.android.gms.ads.InterstitialAd Google_mInterstitialAd;
    private InterstitialAd Facebook_interstitialAd;

    public InterAdsHelpers(String TAG, Context context) {
        this.TAG = TAG;
        this.context = context;
    }



    public void ShowInterAds() {

        if (new PrefManager_googleAdSetting(context).CheckAdCanShow()) {
            showGoogle_Intertial();
        } else if (new PrefManager_Facebook_Adsetting(context).CheckAdCanShow()) {
            showFacebook_Intertial();
        }



    }


    private void showGoogle_Intertial() {
        Google_mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(context);
        Google_mInterstitialAd.setAdUnitId(context.getString(R.string.GoogleInter));
        Google_mInterstitialAd.loadAd(new AdRequest.Builder().build());
///.addTestDevice("01DE48E77EEFC7F7C6E3A641708BC457")
        Google_mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                super.onAdClosed();
               // Log.d(TAG, "onAdClosed: ");
             //   Google_mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("01DE48E77EEFC7F7C6E3A641708BC457").build());
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                //Log.d(TAG, "onAdFailedToLoad: " + i);
                showFacebook_if_Can();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
              //  Log.d(TAG, "onAdLeftApplication: ");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
               // Log.d(TAG, "onAdOpened: ");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
              //  Log.d(TAG, "onAdLoaded: ");
                Google_mInterstitialAd.show();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            //    Log.d(TAG, "onAdClicked: ");
                new PrefManager_googleAdSetting(context).SaveClickedTime();


            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            //    Log.d(TAG, "onAdImpression: ");
            }
        });


    }


    private void showFacebook_Intertial() {

        Facebook_interstitialAd = new InterstitialAd(context, context.getString(R.string.Facebook_intertial));
        // Set listeners for the Interstitial Ad
        //AdSettings.addTestDevice("56d51070-f693-46aa-9379-38cc81df9ae5");
        Facebook_interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
             //   Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
             //   Log.e(TAG, "Interstitial ad dismissed.");
                if (Facebook_interstitialAd != null) {
                    Facebook_interstitialAd.destroy();
                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {
               // ShowInterAds();
                // Ad error callback
              //  Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
              //  Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                Facebook_interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
             //   Log.d(TAG, "Interstitial ad clicked!");
                new PrefManager_Facebook_Adsetting(context).SaveClickedTime();

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
               // Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        Facebook_interstitialAd.loadAd();


    }

    private void showFacebook_if_Can(){
        if (new PrefManager_Facebook_Adsetting(context).CheckAdCanShow()) {
            showFacebook_Intertial();
        }
    }

}
