package table.football.soccer.livescore.epl.epllive.Fragments_match;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import table.football.soccer.livescore.epl.epllive.Adapters.overView_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.overviewmodel;


public class Fragment_overview extends Fragment {

    RecyclerView Rec_overView;
    overView_adapter overViewAdapter;
    List<overviewmodel> overviewmodelList;
    View v;


    SwipeRefreshLayout swipeRefreshLayout;
    TextView CommentryText;
    public  AsyncTask getDataTask;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_overview, container, false);


        CommentryText = v.findViewById(R.id.CommentryStatus_Text);
        swipeRefreshLayout = v.findViewById(R.id.Swipe);



        swipeRefreshLayout.setRefreshing(true);





        overviewmodelList = new ArrayList<>();
        Rec_overView = v.findViewById(R.id.Rec_overView);
        overViewAdapter = new overView_adapter(overviewmodelList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        Rec_overView.setLayoutManager(layoutManager);
        Rec_overView.setAdapter(overViewAdapter);
        Rec_overView.setHasFixedSize(true);//setHasStableIds(true);
       // overViewAdapter.setHasStableIds(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),layoutManager.getOrientation());
        Rec_overView.addItemDecoration(itemDecoration);
        Rec_overView.setNestedScrollingEnabled(false);

        final String MatchId     = getActivity().getIntent().getExtras().getString("MatchId");
       // final String MatchStatus = getActivity().getIntent().getExtras().getString("MatchStatus");

       // if (MatchStatus!=null&&MatchStatus.equals("L")||MatchStatus!=null&&MatchStatus.equals("C")){
            new getData(MatchId,new WebView(getContext()).getSettings().getUserAgentString()).execute();
       // }else{
        //    swipeRefreshLayout.setRefreshing(false);
        //    CommentryText.setVisibility(View.VISIBLE);
        //}
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (overviewmodelList.size()>0){
                    overviewmodelList.clear();
                }

                CommentryText.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(true);


               // if (MatchStatus!=null&&MatchStatus.equals("L")||MatchStatus!=null&&MatchStatus.equals("C")){
                    new getData(MatchId,new WebView(getContext()).getSettings().getUserAgentString()).execute();
               // }else{
               //     swipeRefreshLayout.setRefreshing(false);
                //    CommentryText.setVisibility(View.VISIBLE);
             //   }

            }
        });

        return v;
    }

    public class getData extends AsyncTask<String,Void,List<overviewmodel>>{

        String matchID;
        //
        String UserAgent;

        public getData(String matchID, String userAgent) {
            this.matchID = matchID;
            UserAgent = userAgent;
        }


        @Override
        protected List<overviewmodel> doInBackground(String... strings) {
            Document document;
            ArrayList<overviewmodel> data = new ArrayList<>();
            overviewmodel item;
           // Log.d("iscaneloverview", "doInBackground: checking cancel or not:: "+ isCancelled());
               // Log.d("iscaneloverview", "doInBackground: checking cancel or not:: "+ isCancelled());
                try{
                    document = Jsoup.connect("https://www.premierleague.com/match/"+matchID).referrer("https://www.premierleague.com").userAgent(UserAgent).get();
                    Elements Matcheshome = document.select(".timeLine.timeLineContainer").select(".eventLine.timeLineEventsContainer").select(".event.home,.event.away");

                    for(Element MatchData:Matcheshome){//.eventPlayerInfo:not(eventInfoContent subOn eventInfoContent subOn
                        item = new overviewmodel();
                        String getType = MatchData.select(".icn").select(".visuallyHidden").text();
                        String time = MatchData.select("time.min").text();
                        String Score = MatchData.select(".teamScore").text().replaceAll("\\D*", "").replace(" ", "");
                        StringBuilder NewScore = new StringBuilder(Score);

                        Log.d("Overview", "doInBackground: " + Score.length() + " " + NewScore.length() + NewScore.toString());
                        String playerImageID = MatchData.select(".eventInfoContent").select("img").attr("data-player");
                        String PlayerLink =MatchData.select(".eventPlayerInfo").select("a").attr("href");
                        String PlayerName = MatchData.select(".eventPlayerInfo").select("a.name").first().text()
                                .replace("0","").replace("1","").replace("2","").replace("3","")
                                .replace("4","").replace("5","").replace("6","").replace("7","")
                                .replace("8","").replace("9","").replace(".","").replace(" Substitution Off", "");
                        String  subplayerImageID = MatchData.select(".eventInfoContent.subOn").select("img").attr("data-player");
                        String  subPlayerLink =MatchData.select(".eventInfoContent.subOn").select(".eventPlayerInfo").select("a").attr("href");
                        String  subPlayerName = MatchData.select(".eventInfoContent.subOn").select(".eventPlayerInfo").select("a.name").text()
                                .replace("0","").replace("1","").replace("2","").replace("3","")
                                .replace("4","").replace("5","").replace("6","").replace("7","")
                                .replace("8","").replace("9","").replace(". ","").replace(" Substitution On", "");
                        String AssitPlayerName = MatchData.select(".eventPlayerInfo").select(".assist").text()
                                .replace("0","").replace("1","").replace("2","").replace("3","")
                                .replace("4","").replace("5","").replace("6","").replace("7","")
                                .replace("8","").replace("9","").replace(".","");
                        String AssitPlayerLink = MatchData.select(".eventPlayerInfo").select(".assist").select("a").attr("href");
                        String className = MatchData.attr("class");


                        if (AssitPlayerName.length()>0){
                            getType = "Goal With Assit";
                        }


                        item.setType(getType);

                        if(NewScore.length()==2)
                        {
                            NewScore.insert(1,"-");
                            item.setScore("["+NewScore.toString()+"]");

                        }

                        item.setTime(time);
                        item.setClassName(className);

                        item.setAssitplayerlink(AssitPlayerLink);
                        item.setAssitplayerName(AssitPlayerName);

                        item.setSubplayerlink(subPlayerLink);
                        item.setSubplayerName(subPlayerName);
                        item.setSubplayerImageID(subplayerImageID);

                        item.setPlayerlink(PlayerLink);
                        item.setPlayerImageID(playerImageID);
                        item.setPlayerName(PlayerName);
                        data.add(item);



                        //Log.d("Fragment_overView","Player ID " +getType);

                        //





                    }

                }catch(Exception e){
                    //  System.out.println(e);
                   // Log.d("iscaneloverview", "Error :" + e);
                }


            return data;
        }
        @Override
        protected void onPostExecute(List<overviewmodel> data) {
            super.onPostExecute(data);
          // Log.d("iscaneloverview", " onPostExecute Overview" + data.size());
            if (data.size()<=0){
                CommentryText.setVisibility(View.VISIBLE);

            }
            swipeRefreshLayout.setRefreshing(false);
            overviewmodelList.addAll(data);
            overViewAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
