package table.football.soccer.livescore.epl.epllive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.more_video_adapter;

import table.football.soccer.livescore.epl.epllive.Utils.Helpers.InterAdsHelpers;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_Facebook_Adsetting;
import table.football.soccer.livescore.epl.epllive.Utils.PrefManager_googleAdSetting;
import table.football.soccer.livescore.epl.epllive.model.Top_videos;
import table.football.soccer.livescore.epl.epllive.model.video_model;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class activity_yt_player extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,more_video_adapter.OnItemClickListener {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference VideRef;

    @SuppressLint("InlinedApi")
    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    @SuppressLint("InlinedApi")
    private static final int LANDSCAPE_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

  //  private YouTubePlayer mPlayer = null;
    private boolean mAutoRotation = false;


    //MoreVideo Container
    RelativeLayout moreVideos_container;
    AVLoadingIndicatorView VideoLoading_progress;
    RecyclerView VideoMoreRec;
    List<Top_videos> topVideosList;
    more_video_adapter MoreVideoAdapter;


    public static String ApiKey = "AIzaSyArgtINC85tLUt36KzYpHUISAqL92qYkEc";
    public static String VideoId;

    YouTubePlayerView youTubePlayer;
    public YouTubePlayer youTubePlayers= null;

    public  boolean WasRestoed;
    private AdView mAdView;
    private  com.facebook.ads.AdView adView;
    LinearLayout adContainer;
    public boolean isYouTubePlayerFullScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yt_player);
        new InterAdsHelpers("Yout_tube_activity",getApplicationContext()).ShowInterAds();

        moreVideos_container = findViewById(R.id.moreVideos_container);
        VideoLoading_progress =findViewById(R.id.VideoLoading_progress);

      //  Toast.makeText(this, "Agiana", Toast.LENGTH_SHORT).show();
        mAutoRotation = Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1;

        VideoId = Objects.requireNonNull(getIntent().getExtras()).getString("Video");
       // String ChanelId = Objects.requireNonNull(getIntent().getExtras()).getString("chanID");
        mAdView = findViewById(R.id.adView);
        // Find the Ad Container
        adContainer = findViewById(R.id.banner_container);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        VideRef = db.collection("Videos").document("Status_Collection");
        showAds();
        youTubePlayer  = findViewById(R.id.youtube_player);
        youTubePlayer.initialize(ApiKey,this);


        MoreVideoRec();
    }

//   public void  playVideo(){
//        if (!WasRestoed){
//            youTubePlayers.cueVideo("1uHt2LrCSWg");
//            youTubePlayers.play();
//        }//1uHt2LrCSWg
//    }
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


    private void MoreVideoRec(){
         topVideosList = new ArrayList<>();
         VideoMoreRec = findViewById(R.id.Video_rec_yt);
         MoreVideoAdapter = new more_video_adapter(this,topVideosList,getApplicationContext());
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

         VideoMoreRec.setLayoutManager(linearLayoutManager);
         String ChanelId = Objects.requireNonNull(getIntent().getExtras()).getString("chanID");
         VideoMoreRec.setAdapter(MoreVideoAdapter);
         getVideosData(ChanelId);
    }

    private  void getVideosData(String ChannelID){
        VideoLoading_progress.show();

        if (ChannelID !=null){

            getMoreVideos(ChannelID);
        }else {
            VideRef.collection("Data").orderBy("T_NO",Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                        Top_videos topVideos = queryDocumentSnapshot.toObject(Top_videos.class);

                        topVideosList.add(topVideos);
                    }

                    MoreVideoAdapter.notifyDataSetChanged();
                    VideoLoading_progress.hide();
                }
            });
        }


    }

    //uses getCustomVideoData
    private void getMoreVideos(String ChannelID){
      //  https://www.googleapis.com/youtube/v3/search?key=AIzaSyArgtINC85tLUt36KzYpHUISAqL92qYkEc&channelId=UC6c1z7bA__85CIWZ_jpCK-Q&fields=nextPageToken,items(id/videoId,snippet(title,thumbnails/medium/url))&part=snippet,id&order=date&maxResults=20
//"UC6c1z7bA__85CIWZ_jpCK-Q+"
        getCustomVideoData("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArgtINC85tLUt36KzYpHUISAqL92qYkEc&channelId="+ChannelID+"&fields=nextPageToken,items(id/videoId,snippet(title,thumbnails/medium/url))&part=snippet,id&order=date&maxResults=20",
                ChannelID);

    }

    public void getCustomVideoData(String url, final String ChannelID){
      //  final List<Object> VideoList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               ArrayList<Top_videos> data = new ArrayList<>();
                try {
                    JSONObject obj = new JSONObject(response);

                    JSONArray DataArray = obj.getJSONArray("items");


                    for(int i=0;i<DataArray.length();i++){
                        JSONObject ItemObj = DataArray.getJSONObject(i);
                        if (ItemObj.has("id")){
                            JSONObject Video_id_obj = ItemObj.getJSONObject("id");
                            String VideoID = Video_id_obj.getString("videoId");

                            JSONObject Video_detail  = ItemObj.getJSONObject("snippet");
                            String Video_title = Video_detail.getString("title");

                            JSONObject VideoThumb_obj =  Video_detail.getJSONObject("thumbnails");
                            JSONObject Thumb_obj = VideoThumb_obj.getJSONObject("medium");
                            String ImageLinke = Thumb_obj.getString("url");

                            Top_videos item = new Top_videos();

                            if (!VideoID.equals(VideoId)){
                                item.setChannelId(ChannelID);
                                item.setImageLink(ImageLinke);
                                item.setYt_id(VideoID);
                                item.setVideo_Title(Video_title);
                                data.add(item);
                            }
                        }
                    }
                    topVideosList.addAll(data);
                    MoreVideoAdapter.notifyDataSetChanged();
                    VideoLoading_progress.hide();
                }catch (Exception e){
                    // Log.d("Fragment_video"," rrodhelo " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity_yt_player.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }




    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer Player, boolean wasRestored) {
        Player.setPlayerStateChangeListener(playerStateChangeListener);
        Player.setPlaybackEventListener(playbackEventListener);
        youTubePlayers = Player;
        WasRestoed = wasRestored;
        if (!wasRestored){
            Player.cueVideo(VideoId);
           // Player.play();
        }//1uHt2LrCSWg

        Player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean fullsize) {
                if (fullsize) {
                    setRequestedOrientation(LANDSCAPE_ORIENTATION);
                    adContainer.setVisibility(View.GONE);
                    moreVideos_container.setVisibility(View.GONE);
                 ViewGroup.LayoutParams params = youTubePlayer.getLayoutParams();
                    // Changes the height and width to the specified *pixels*
                    params.height = MATCH_PARENT;
                    params.width = MATCH_PARENT;
                    youTubePlayer.setLayoutParams(params);
                } else {
                    moreVideos_container.setVisibility(View.VISIBLE);
                    setRequestedOrientation(PORTRAIT_ORIENTATION);
                    adContainer.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = youTubePlayer.getLayoutParams();
                    // Changes the height and width to the specified *pixels*
                    params.height = 450;
                    params.width = MATCH_PARENT;
                    youTubePlayer.setLayoutParams(params);
                }
            }
        });

      //  Player.setOnFullscreenListener(this);

        if (mAutoRotation) {
            Player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                    | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                    | YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                    | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        } else {
            Player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                    | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                    | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (youTubePlayers != null)
            {
                youTubePlayers.setFullscreen(true);
                //Toast.makeText(this, "OrientTation LandScape", Toast.LENGTH_SHORT).show();
            }
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (youTubePlayers != null)
            {
                youTubePlayers.setFullscreen(false);
              //  Toast.makeText(this, "Portarit", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (youTubePlayers != null && isYouTubePlayerFullScreen){
            youTubePlayers.setFullscreen(false);
        } else {
           // super.onBackPressed();
           // new InterAdsHelpers("Youtube_activtiy",getApplicationContext()).ShowInterAds();
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





    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener(){


        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {
            // showAd();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
    }

    //
    @Override
    public void onItemClicked(String VideID) {
        if (!WasRestoed){
            youTubePlayers.cueVideo(VideID);
            youTubePlayers.play();
        }
    }
}
