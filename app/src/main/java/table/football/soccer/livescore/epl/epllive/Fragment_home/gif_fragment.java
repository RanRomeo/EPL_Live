package table.football.soccer.livescore.epl.epllive.Fragment_home;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.mopub.nativeads.MoPubAdAdapter;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.mopub.TwitterMoPubAdAdapter;
import com.twitter.sdk.android.mopub.TwitterStaticNativeAdRenderer;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

import table.football.soccer.livescore.epl.epllive.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class gif_fragment extends Fragment {


    View v;
   // private MoPubAdAdapter moPubAdAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v =  inflater.inflate(R.layout.fragment_gif, container, false);
      /*  TwitterConfig config = new TwitterConfig.Builder(getContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.CONSUMER_KEY), getResources().getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);



        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()),LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#ManUtd").languageCode("en")
                .maxItemsPerRequest(50)
                .build();

        final  TweetTimelineRecyclerViewAdapter  adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                        .setTimeline(searchTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();




        recyclerView.setAdapter(adapter);





 */

        return  v;
    }

}
