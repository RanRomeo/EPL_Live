package table.football.soccer.livescore.epl.epllive.Fragment_home;



import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import table.football.soccer.livescore.epl.epllive.Adapters.Gif_adapter;
import table.football.soccer.livescore.epl.epllive.Adapters.news_adapter;
import table.football.soccer.livescore.epl.epllive.Adapters.today_match_adapter;
import table.football.soccer.livescore.epl.epllive.Adapters.top_video_adapter;
import table.football.soccer.livescore.epl.epllive.Custom_dailog.fav_team_setting;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.LinePagerIndicatorDecoration;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.Top_videos;
import table.football.soccer.livescore.epl.epllive.model.gif_model;
import table.football.soccer.livescore.epl.epllive.model.match_model;
import table.football.soccer.livescore.epl.epllive.model.news_model;

/**
 * A simple {@link Fragment} subclass.
 */
public class News_fragment extends Fragment {


    private static final String TAG = "News_fragment";

    View v;

    AppBarLayout app_bar_layout;


    //News
    RecyclerView mnew_recyclerView;
    news_adapter newsAdapter;
    List<news_model> newsModelList;
    Boolean isScolling = false;
    int currentItems,totalItems,scrollOutItems;
    int Page = 1;


    //Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference VideRef;
//
    //topvideos recyclerview
    List<Top_videos> topVideosList;

    top_video_adapter topVideoAdapter;
    RecyclerView top_rec;



    //Today Match Re
    RecyclerView recyclerViewToday;
    List<match_model> TodatMatchList;
    today_match_adapter todayMatchAdapter;
    LinearLayout LiveContainer;


    TextView featured_videos_text;
    AVLoadingIndicatorView LoadingProgresNews;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_news_, container, false);
        final String ua=new WebView(getContext()).getSettings().getUserAgentString();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        VideRef = db.collection("Videos").document("Status_Collection");

        featured_videos_text = v.findViewById(R.id.featured_videos_text);
        LoadingProgresNews = v.findViewById(R.id.LoadingProgresNews);
        LoadingProgresNews.show();
        //news_recycler
        mnew_recyclerView = v.findViewById(R.id.news_recycler);
        newsModelList = new ArrayList<>();
        final LinearLayoutManager newsManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        newsAdapter = new news_adapter(newsModelList,getContext());
        mnew_recyclerView.setLayoutManager(newsManager);
        mnew_recyclerView.setAdapter(newsAdapter);
        mnew_recyclerView.setHasFixedSize(true);

        //Today Match
        recyclerViewToday = v.findViewById(R.id.today_match_rec);
        TodatMatchList = new ArrayList<>();
        todayMatchAdapter = new today_match_adapter(TodatMatchList,getContext());
        final LinearLayoutManager TodayMatchManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewToday.setLayoutManager(TodayMatchManager);
        recyclerViewToday.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerViewToday.setAdapter(todayMatchAdapter);
        recyclerViewToday.setHasFixedSize(true);






        app_bar_layout = v.findViewById(R.id.app_bar_layout);

        //to disable appbarlayout drag
        if (app_bar_layout.getLayoutParams() != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) app_bar_layout.getLayoutParams();
            AppBarLayout.Behavior appBarLayoutBehaviour = new AppBarLayout.Behavior();
            appBarLayoutBehaviour.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
            layoutParams.setBehavior(appBarLayoutBehaviour);
        }





      //  new FetchNews(ua).execute();
        new FetchNewsSprort(ua).execute();
        final Handler handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //your code
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Top video Rec
                        topVideo_rec();
                        //Check if true then retrive data
                        checkVideoStatus();
                        LoadTodayMatchData();



                        //  Parameter.parameterOneVolley(SettingsActivity.this, getApplicationContext());
                    }
                });
            }
        }).start();


        LiveContainer = v.findViewById(R.id.liveMatch_Lay);

        mnew_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScolling = true;

                }



            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = newsManager.getChildCount();
                totalItems = newsManager.getItemCount();
               // y=dy;
                scrollOutItems = newsManager.findFirstVisibleItemPosition();





                if (isScolling && (currentItems + scrollOutItems == totalItems)){
                    isScolling = false;
                   // new FetchNews(ua).execute();
                    new FetchNewsSprort(ua).execute();

                }
            }
        });




        return v;
    }


    private void topVideo_rec(){
        topVideosList = new ArrayList<>();
        top_rec = v.findViewById(R.id.top_video_rec);
        topVideoAdapter = new top_video_adapter(topVideosList,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(top_rec);
        top_rec.setLayoutManager(linearLayoutManager);
        // pager indicator
        top_rec.addItemDecoration(new LinePagerIndicatorDecoration(getContext()));
        top_rec.setAdapter(topVideoAdapter);

    }





    public class FetchNews extends AsyncTask<String,Void,List<news_model>> {

        String UserAgent;

        public FetchNews(String userAgent) {
            UserAgent = userAgent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadingProgresNews.show();
        }

        @Override
        protected List<news_model> doInBackground(String... strings) {
           //String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
            Document document;
            ArrayList<news_model> data = new ArrayList<>();
            news_model item;
//            Document document;
           // Log.d("UserNotLno", "doInBackground: "+UserAgent);
            try{
                // Elements mathcdetail = document.select(".team.away");competitors sm-score
                // Elements teamAway = document.select(".team.away");//team home  src
                //https://www.skysports.com/premier-league-news/more/3
                document = Jsoup.connect("https://www.skysports.com/premier-league-news/more/"+Page).referrer("https://www.skysports.com").userAgent(UserAgent).get();
                Elements LatestNews = document.select("div.news-list__item.news-list__item--show-thumb-bp30");
               // System.out.println(LatestNews.size());

                //news link news-list__figure news-list__figure label__timestamp news-list__snippet
                for(Element news:LatestNews){
                    String newsLink = news.select("a.news-list__figure").attr("href");
                    String TimeStamp =news.select("span.label__timestamp").text();
                    String ImageLink =  news.getElementsByTag("img").get(1).attr("src");
                    String newsHeading = news.select("h4.news-list__headline").text();
                    String newsSnippit = news.select("p.news-list__snippet").text();

                    //   System.out.println("TimeStamp: " +news.select("span.label__timestamp").text());
                    //  System.out.println("NewsLink: " + news.select("a.news-list__figure").attr("href"));
                    //   System.out.println("ImageLink: " + news.getElementsByTag("img").get(1).attr("src"));
//                    //  System.out.println("NewsHeading: " + news.select("h4.news-list__headline").text());
//
////             if(!newsHeading.contains("WATCH"))
////             }//             {
//                    System.out.println(newsHeading);
//                    System.out.println(newsLink);
//                    System.out.println(TimeStamp);
//                    System.out.println(ImageLink);
//                    System.out.println(newsSnippit);
//                    System.out.println("                              ");
//                    System.out.println("--------********--------------");

                    item = new news_model();
                    item.setHeadText(newsSnippit);
                    item.setThumburl(ImageLink);
                    item.setNewsUrl(newsLink);
                    data.add(item);

                    // System.out.println("                                          ");
                    //  System.out.println("--------********--------------");

                }

            }catch(Exception e){
                System.out.println(e);
            }






            return data;
        }


        @Override
        protected void onPostExecute(List<news_model> data) {
            super.onPostExecute(data);
            LoadingProgresNews.hide();
            if (data.size()>0){
                Page++;
                newsModelList.addAll(data);
                newsAdapter.notifyDataSetChanged();


                //to disable appbarlayout drag

            }



        }
    }



    public class FetchNewsSprort extends  AsyncTask<String,Void,List<news_model>>{

        String UserAgent;

        public FetchNewsSprort(String userAgent) {
            UserAgent = userAgent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadingProgresNews.show();
        }

        @Override
        protected List<news_model> doInBackground(String... strings) {
            Document document;
            ArrayList<news_model> data = new ArrayList<>();
            news_model item;
            //String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

            try{
                document = Jsoup.connect("https://www.sportskeeda.com/go/epl?page="+Page).userAgent(UserAgent).get();
                Elements NewsDataList = document.select(".story-wrapper");//team home

                for (Element NewsData :NewsDataList){

                    String link =      NewsData.select("a").attr("abs:href");
                    final String headline =  NewsData.select("a").text();
                    String thumblink = NewsData.select(".in-block-img").attr("data-lazy");
                    String NewsID =    NewsData.select(".list-story-link").attr("data-id");


                    item = new news_model();
                    item.setHeadText(headline);
                    item.setThumburl(thumblink);
                    item.setNewsUrl(link);
                    data.add(item);
                }



            }catch(Exception e){
                Log.d(TAG, "doInBackground: " + e);
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<news_model> data) {
            super.onPostExecute(data);
            LoadingProgresNews.hide();
            if (data.size()>0){
                Page++;
                newsModelList.addAll(data);
                newsAdapter.notifyDataSetChanged();
                if (app_bar_layout.getLayoutParams() != null) {
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) app_bar_layout.getLayoutParams();
                    AppBarLayout.Behavior appBarLayoutBehaviour = new AppBarLayout.Behavior();
                    appBarLayoutBehaviour.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                        @Override
                        public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                            return true;
                        }
                    });
                    layoutParams.setBehavior(appBarLayoutBehaviour);
                }
            }else {
                new FetchNews(UserAgent).execute();
            }
        }
    }


    //Check top videos status
    private void  checkVideoStatus(){

       // db.collection("Videos").whereEqualTo("Status","0").get().
        VideRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    if (documentSnapshot.getBoolean("Status")){

                            // private DocumentReference VideRef = db.collection("Videos").document("Status_Collection");
                        featured_videos_text.setVisibility(View.VISIBLE);
                        top_rec.setVisibility(View.VISIBLE);
                        getVideosData();



                       // Log.d(TAG," " + documentSnapshot.getBoolean("Status") );
                    }else {
                        //Log.d(TAG," false ");
                    }
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    //used insde checkVIdeosStatus
    private  void getVideosData(){
        VideRef.collection("Data").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    Top_videos topVideos = queryDocumentSnapshot.toObject(Top_videos.class);
                    Log.d(TAG," " + topVideos.getVideo_Title());
                    topVideosList.add(topVideos);
                }

                topVideoAdapter.notifyDataSetChanged();
                Log.d(TAG," " + topVideosList.size());


            }
        });
    }

    private void LoadTodayMatchData(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/fixtures?comps=1&teams=1,127,131,43,46,4,6,7,34,159,26,10,11,12,23,20,21,33,25,38&compSeasons=210&page=0&pageSize=10&statuses=L&altIds=true", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // System.out.println(response);
                //  Log.d("VolleyRequest",response);
              //  ErrorTextView.setVisibility(View.GONE);
                   ArrayList<match_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("content");
              //      System.out.println(heroArray.length());
                 //   "https://footballapi.pulselive.com/football/fixtures?comps=1&teams=1,127,131,43,46,4,6,7,34,159,26,10,11,12,23,20,21,33,25,38&compSeasons=210&page=0&pageSize=10&statuses=U,L&altIds=true"
                    match_model item;
                    for(int i=0;i<heroArray.length();i++){
                        JSONObject gameData = heroArray.getJSONObject(i);
                        String GameID = String.valueOf(gameData.getInt("id"));
                        String matchStatus = gameData.getString("status");
                      //  String matchPhase = gameData.getString("phase");
                        // System.out.println("Match Data: "+matchStatus + " " + matchPhase);

                        //Toast.makeText(MainActivity.this, " "+GameID, Toast.LENGTH_SHORT).show();
                        //team Array
                        JSONArray Teams = gameData.getJSONArray("teams");
                        //get the first object
                        JSONObject TeamDetail = Teams.getJSONObject(0);
                        //Object team

                        JSONObject AllTeam = TeamDetail.getJSONObject("team");
                        String HomeTeam = AllTeam.getString("shortName");
                        String HomeTeamId= String.valueOf(AllTeam.getInt("id"));

                        //get the first  away team
                        JSONObject TeamDetail1 = Teams.getJSONObject(1);
                        //Object team
                        JSONObject AllTeam1 = TeamDetail1.getJSONObject("team");
                        String AwayTeam = AllTeam1.getString("shortName");
                        String AwayTeamID= String.valueOf(AllTeam1.getInt("id"));


                        item = new match_model();

                        //item.setGameTimePast();

                        if (matchStatus.equals("L")){
                            item.setHomeTeam(HomeTeam);
                            item.setAwayTeam(AwayTeam);
                            item.setHomeTeamID(HomeTeamId);
                            item.setAwayTeamID(AwayTeamID);

                            if(TeamDetail.has("score")){
                                String HomeTeamSccore = String.valueOf(TeamDetail.getInt("score"));
                                String AwayTeamScore = String.valueOf(TeamDetail1.getInt("score"));
                                item.setHomeTeamScore(HomeTeamSccore);
                                item.setAwayTeamScore(AwayTeamScore);

                            }


                            item.setMathcId(GameID);
                            item.setMatchStatus(matchStatus);
                            data.add(item);
                        }



                        //item.setGameTimePast();
                       //item.setDate(getReadDAte(convertDate(DateInMillis,"dd/MM/yyyy")));
                      //item.setTime(convertDate(DateInMillis,"hh:mm a"));


                        //item.setMillies(DateDetail.getLong("millis"));
                        //item.setMillies(MillisDate(convertDate(DateInMillis,"dd/MM/yyyy")));


                    }
                    TodatMatchList.addAll(data);
                    todayMatchAdapter.notifyDataSetChanged();

                    if(TodatMatchList.size()>0){
                        LiveContainer.setVisibility(View.VISIBLE);
                    }

                }catch (Exception e){
                    Log.d(TAG, "onResponse:TodayMatchsData " + e);
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                //Log.d("VolleyRequestError",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/fixtures");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
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


}
