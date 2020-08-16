package table.football.soccer.livescore.epl.epllive.Fragment_home;


import android.content.ClipData;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

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
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdsManager;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.delet_this_adapter;
import table.football.soccer.livescore.epl.epllive.Adapters.natvie_video_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.match_model;
import table.football.soccer.livescore.epl.epllive.model.video_model;

/**
 * A simple {@link Fragment} subclass.
 */
public class Video_fragment extends Fragment {


    View v;

    //EspnRec

    NativeAdsManager nativeAdsManager;
    List<Object> EspnVideoList;
    delet_this_adapter deletThisAdapter;


    //442toons
    NativeAdsManager Toons_nativeAdsManager;
    List<Object>  Toons_VideoList;
    delet_this_adapter  Toons_deletThisAdapter;

    //SkySports
    NativeAdsManager Sky_nativeAdsManager;
    List<Object>  Sky_VideoList;
    delet_this_adapter Sky_deletThisAdapter;

    //football daily
    NativeAdsManager daily_nativeAdsManager;
    List<Object>  daily_VideoList;
    delet_this_adapter daily_deletThisAdapter;


    LinearLayout Layout_Container;
    AVLoadingIndicatorView video_loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_video, container, false);
        AudienceNetworkAds.initialize(getContext());



        Layout_Container = v.findViewById(R.id.Layout_Container);
        video_loading = v.findViewById(R.id.video_loading);

        final Handler Videohandler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //your code
                Videohandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Top video Rec
                        video_loading.show();
                      EspnRecycler();
                      toonsRecycler();
                      SkySportsRecycler();
                      dailyRecycler();




                        //  Parameter.parameterOneVolley(SettingsActivity.this, getApplicationContext());
                    }
                });
            }
        }).start();




        return v;
    }

    void EspnRecycler(){
         // AdSettings.addTestDevice("43423ebb-0ce7-4c8f-8139-6af1260a0892");//AIzaSyArgtINC85tLUt36KzYpHUISAqL92qYkEc
        EspnVideoList =  getEspnData("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArgtINC85tLUt36KzYpHUISAqL92qYkEc&channelId=UC6c1z7bA__85CIWZ_jpCK-Q&fields=nextPageToken,items(id/videoId,snippet(title,thumbnails/medium/url))&part=snippet,id&order=date&maxResults=20"
        );


        final RecyclerView rvEspn =v.findViewById(R.id.rec_espn);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false);


        deletThisAdapter = new delet_this_adapter(EspnVideoList,getContext());

        nativeAdsManager = new NativeAdsManager(getContext(), getString(R.string.Facebook_native_ads),5);
        nativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
               // Log.d("Fragment_video","AdLosdde" );



                if(EspnVideoList.size()>=20||EspnVideoList.size()>20){

                    for (int x =1; x < 20; x++){
                        NativeAd ad = nativeAdsManager.nextNativeAd();
                        if (x%3==1) {
                          //  System.out.println(x);
                            EspnVideoList.add(x,ad);
                            // recipeList.add(4, ad);
                            deletThisAdapter.notifyDataSetChanged();
                        }

                    }


                }



            }

            @Override
            public void onAdError(AdError adError) {
                Log.d("Fragment_video"," " + adError.getErrorMessage());
            }
        });
        nativeAdsManager.loadAds();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                layoutManager.getOrientation());
        rvEspn.addItemDecoration(dividerItemDecoration);

        rvEspn.setAdapter(deletThisAdapter);
        rvEspn.setLayoutManager(layoutManager);

    }

    void toonsRecycler(){

        Toons_VideoList = gettoonsData("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArgtINC85tLUt36KzYpHUISAqL92qYkEc&channelId=UC4SUUloEcrgjsxbmy_rQQXA&fields=nextPageToken,items(id/videoId,snippet(title,thumbnails/medium/url))&part=snippet,id&order=date&maxResults=20");

        final RecyclerView rvToons =v.findViewById(R.id.rec_toons);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false);


        Toons_deletThisAdapter = new delet_this_adapter(Toons_VideoList,getContext());

        Toons_nativeAdsManager = new NativeAdsManager(getContext(),  getString(R.string.Facebook_native_ads),5);
        Toons_nativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
              //  Log.d("Fragment_video","AdLosdde" );



                if(Toons_VideoList.size() >= 20){

                    for (int x =1; x < 20; x++){
                        NativeAd ad = Toons_nativeAdsManager.nextNativeAd();
                        if (x%3==1) {
                           // System.out.println(x);
                            Toons_VideoList.add(x,ad);
                            // recipeList.add(4, ad);
                            Toons_deletThisAdapter.notifyDataSetChanged();
                        }

                    }


                }



            }

            @Override
            public void onAdError(AdError adError) {
                Log.d("Fragment_video"," " + adError.getErrorMessage());
            }
        });
        Toons_nativeAdsManager.loadAds();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                layoutManager.getOrientation());
        rvToons.addItemDecoration(dividerItemDecoration);

        rvToons.setAdapter(Toons_deletThisAdapter);
        rvToons.setLayoutManager(layoutManager);

    }

    void SkySportsRecycler(){
        //  AdSettings.addTestDevice("43423ebb-0ce7-4c8f-8139-6af1260a0892");
        Sky_VideoList =  getSkySportsData("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArgtINC85tLUt36KzYpHUISAqL92qYkEc&channelId=UCNAf1k0yIjyGu3k9BwAg3lg&fields=nextPageToken,items(id/videoId,snippet(title,thumbnails/medium/url))&part=snippet,id&order=date&maxResults=20"
        );


        final RecyclerView rvSkySports =v.findViewById(R.id.rec_skyspots);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false);


        Sky_deletThisAdapter = new delet_this_adapter(Sky_VideoList,getContext());

        Sky_nativeAdsManager = new NativeAdsManager(Objects.requireNonNull(getContext()),  getString(R.string.Facebook_native_ads),5);
        Sky_nativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
            //    Log.d("Fragment_video","AdLosdde" );



                if(Sky_VideoList.size()>=20||Sky_VideoList.size()>20){

                    for (int x =1; x < Sky_VideoList.size(); x++){
                        NativeAd ad = Sky_nativeAdsManager.nextNativeAd();
                        if (x%3==1) {
                       //     System.out.println(x);
                            Sky_VideoList.add(x,ad);
                            // recipeList.add(4, ad);
                            Sky_deletThisAdapter.notifyDataSetChanged();
                        }

                    }


                }



            }

            @Override
            public void onAdError(AdError adError) {
                Log.d("Fragment_video"," " + adError.getErrorMessage());
            }
        });
        Sky_nativeAdsManager.loadAds();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                layoutManager.getOrientation());
        rvSkySports.addItemDecoration(dividerItemDecoration);

        rvSkySports.setAdapter(Sky_deletThisAdapter);
        rvSkySports.setLayoutManager(layoutManager);
    }

    void dailyRecycler(){
        //  AdSettings.addTestDevice("43423ebb-0ce7-4c8f-8139-6af1260a0892");
        daily_VideoList =  getdailyData("https://www.googleapis.com/youtube/v3/search?key=AIzaSyArgtINC85tLUt36KzYpHUISAqL92qYkEc&channelId=UCbWUEnTRHb3bRdrnovq8iuA&fields=nextPageToken,items(id/videoId,snippet(title,thumbnails/medium/url))&part=snippet,id&order=date&maxResults=20"
        );


        final RecyclerView rvdaily =v.findViewById(R.id.rec_footballdaily);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false);


        daily_deletThisAdapter = new delet_this_adapter(daily_VideoList,getContext());

        daily_nativeAdsManager = new NativeAdsManager(getContext(),  getString(R.string.Facebook_native_ads),5);
        daily_nativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
              //  Log.d("Fragment_video","AdLosdde" );



                if(daily_VideoList.size()>=20 ||daily_VideoList.size()>20){

                    for (int x =1; x < 20; x++){
                        NativeAd ad = daily_nativeAdsManager.nextNativeAd();
                        if (x%3==1) {
                            System.out.println(x);
                            daily_VideoList.add(x,ad);
                            // recipeList.add(4, ad);
                            daily_deletThisAdapter.notifyDataSetChanged();
                        }

                    }


                }



            }

            @Override
            public void onAdError(AdError adError) {
                Log.d("Fragment_video"," " + adError.getErrorMessage());
            }
        });
        daily_nativeAdsManager.loadAds();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),
                layoutManager.getOrientation());
        rvdaily.addItemDecoration(dividerItemDecoration);

        rvdaily.setAdapter(daily_deletThisAdapter);
        rvdaily.setLayoutManager(layoutManager);
        video_loading.hide();
        Layout_Container.setVisibility(View.VISIBLE);
    }


    public List<Object> getEspnData( String url){
        final List<Object> VideoList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              // ArrayList<video_model> data = new ArrayList<>()
                try {
                 JSONObject obj = new JSONObject(response);

                    JSONArray DataArray = obj.getJSONArray("items");
                    final String ChannelID =  "UC6c1z7bA__85CIWZ_jpCK-Q";

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

                            video_model item = new video_model();



                            //    System.out.println(VideoID + " " + Video_title + " " + ImageLinke);
                            item.setImageLink(ImageLinke);
                            item.setVideID(VideoID);
                            item.setVideoTitle(Video_title);
                            item.setChannelId(ChannelID);
                            VideoList.add(item);


                        }


                    }
              deletThisAdapter.notifyDataSetChanged();
            //        EspnVideoList.addAll(data);
             //       System.out.println("HeorHonde123 " + EspnVideoList.size());
                 //   Log.d("Fragment_video","AdLosddeListVideoList " + VideoList.size() );

                    }catch (Exception e){
                   // Log.d("Fragment_video"," rrodhelo " + e);
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

        return VideoList;
    }

    public List<Object> gettoonsData( String url){
        final List<Object> VideoList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // ArrayList<video_model> data = new ArrayList<>()
                try {
                    JSONObject obj = new JSONObject(response);

                    JSONArray DataArray = obj.getJSONArray("items");

                    final String ChannelID =  "UC4SUUloEcrgjsxbmy_rQQXA";
                    for(int i=0;i<DataArray.length();i++){
                        JSONObject ItemObj = DataArray.getJSONObject(i);

                        if(ItemObj.has("id")){
                            JSONObject Video_id_obj = ItemObj.getJSONObject("id");
                            String VideoID = Video_id_obj.getString("videoId");

                            JSONObject Video_detail  = ItemObj.getJSONObject("snippet");
                            String Video_title = Video_detail.getString("title");

                            JSONObject VideoThumb_obj =  Video_detail.getJSONObject("thumbnails");
                            JSONObject Thumb_obj = VideoThumb_obj.getJSONObject("medium");
                            String ImageLinke = Thumb_obj.getString("url");

                            video_model item = new video_model();




                            item.setImageLink(ImageLinke);
                            item.setVideID(VideoID);
                            item.setVideoTitle(Video_title);
                            item.setChannelId(ChannelID);
                            VideoList.add(item);



                        }

                    }
                    Toons_deletThisAdapter.notifyDataSetChanged();
                    //        EspnVideoList.addAll(data);
                    //       System.out.println("HeorHonde123 " + EspnVideoList.size());
                 ///   Log.d("Fragment_video","AdLosddeListVideoList " + VideoList.size() );

                }catch (Exception e){
                    Log.d("Fragment_video"," rrodhelo " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

        return VideoList;
    }

    public List<Object> getSkySportsData( String url){
        final List<Object> VideoList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // ArrayList<video_model> data = new ArrayList<>()
                try {
                    JSONObject obj = new JSONObject(response);

                    JSONArray DataArray = obj.getJSONArray("items");
                    final String ChannelID =  "UCNAf1k0yIjyGu3k9BwAg3lg";
                  //  Log.d("Fragment_video", "onResponse: " + obj);
                    for(int i=0;i<DataArray.length();i++){
                        JSONObject ItemObj = DataArray.getJSONObject(i);


                        if(ItemObj.has("id")){
                            JSONObject Video_id_obj = ItemObj.getJSONObject("id");
                            String VideoID = Video_id_obj.getString("videoId");

                            JSONObject Video_detail  = ItemObj.getJSONObject("snippet");
                            String Video_title = Video_detail.getString("title");

                            JSONObject VideoThumb_obj =  Video_detail.getJSONObject("thumbnails");
                            JSONObject Thumb_obj = VideoThumb_obj.getJSONObject("medium");
                            String ImageLinke = Thumb_obj.getString("url");

                            video_model item = new video_model();


                          //  Log.d("Fragment_video","Skysports " +Video_title);

                            item.setImageLink(ImageLinke);
                            item.setVideID(VideoID);
                            item.setVideoTitle(Video_title);
                            item.setChannelId(ChannelID);
                            VideoList.add(item);

                        }


                    }
                    Sky_deletThisAdapter.notifyDataSetChanged();
                    //        EspnVideoList.addAll(data);
                    //       System.out.println("HeorHonde123 " + EspnVideoList.size());
                  //  Log.d("Fragment_video","AdLosddeListVideoList " + VideoList.size() );

                }catch (Exception e){
                    Log.d("Fragment_video"," rrodhelo " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

        return VideoList;
    }

    public List<Object> getdailyData( String url){
        final List<Object> VideoList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // ArrayList<video_model> data = new ArrayList<>()
                try {
                    JSONObject obj = new JSONObject(response);

                    JSONArray DataArray = obj.getJSONArray("items");
                    final String ChannelID =  "UCbWUEnTRHb3bRdrnovq8iuA";

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

                            video_model item = new video_model();




                            item.setImageLink(ImageLinke);
                            item.setVideID(VideoID);
                            item.setVideoTitle(Video_title);
                            item.setChannelId(ChannelID);
                            VideoList.add(item);


                        }


                    }
                    daily_deletThisAdapter.notifyDataSetChanged();
                    //        EspnVideoList.addAll(data);
                    //       System.out.println("HeorHonde123 " + EspnVideoList.size());
                    //  Log.d("Fragment_video","AdLosddeListVideoList " + VideoList.size() );

                }catch (Exception e){
                    Log.d("Fragment_video"," rrodhelo " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

        return VideoList;
    }
}
