package table.football.soccer.livescore.epl.epllive.Fragments_match;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.recent_meeting_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.recent_model;


public class Fragment_match_stats extends Fragment {
    View v;
    TextView total_match_played,
             total_match_draw,
             total_wins_Home_team,
             total_wins_Away_team,
             total_home_wins_home_team,
             total_home_wins_away_team,
             total_away_wins_home_team,
             total_away_wins_away_team;
    //Stats Statastics
    TextView text_possesion_home_team,text_possesion_away_team,text_shots_on_target_home_team,
             text_shots_on_target_away_team,text_shots_home_team,text_shots_away_team,
             text_touches_home_team,text_touches_away_team,text_passes_home_team,text_passes_away_team,
             text_tackles_home_team,text_tackles_away_team,text_clearance_home_team,
             text_clearance_away_team,text_corners_home_team,text_corners_away_team,
             text_offsides_home_team,text_offsides_away_team,text_yellow_cards_home_team,text_yellow_cards_away_team,
             text_red_cards_home_team,text_red_cards_away_team,text_fouls_home_team,text_fouls_away_team;


    //Season so for text
    TextView home_team_position,
             away_team_position,
             home_team_won,
             away_team_won,
             home_team_draw,
             away_team_draw,
             home_team_lost,
             away_team_lost,
             home_team_gd,
             away_team_gd;



    ProgressBar progressBar;

   // AdView mAdView;
  //  AdView mAdView2;
   // AdView mAdView3;

    TextView CommentryText;

    NestedScrollView statViewS;
    LinearLayout linearLayoutRecentMeeting;
    LinearLayout Match_stats_live;
    LinearLayout Head_to_Head;
    LinearLayout SeasonSofor;

    List<recent_model> recentModelList;
    RecyclerView rec_recent_meeting;
    recent_meeting_adapter recentMeetingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_match_stats, container, false);
        initTextView();
        initTextViewStatastic();
        initRecentRecycler();
        initSeasonTextView();//nest_scorll

        CommentryText = v.findViewById(R.id.CommentryStatus_Text);
        statViewS = v.findViewById(R.id.nest_scorll);
        progressBar = v.findViewById(R.id.progress_stat);

        progressBar.setVisibility(View.VISIBLE);

        String UserAgent = new WebView(getContext()).getSettings().getUserAgentString();

        linearLayoutRecentMeeting = v.findViewById(R.id.recentMeeting);
        Match_stats_live = v.findViewById(R.id.Match_stats_live);
        Head_to_Head = v.findViewById(R.id.Head_to_Head);
        SeasonSofor = v.findViewById(R.id.this_Season_so_For);


        final String MatchId     = getActivity().getIntent().getExtras().getString("MatchId");
        String   HomeTeamID =  getActivity().getIntent().getExtras().getString("HomeTeamID");
        String   AwayTeamID =  getActivity().getIntent().getExtras().getString("AwayTeamID");
      //  String MatchStatus = getActivity().getIntent().getExtras().getString("MatchStatus");

       /* if (MatchStatus!=null&&MatchStatus.equals("L")||MatchStatus!=null&&MatchStatus.equals("C")){

        }else {
            Match_stats_live.setVisibility(View.GONE);

        }*/

        loadData("https://footballapi.pulselive.com/football/stats/match/"+MatchId,new WebView(getContext()).getSettings().getUserAgentString(),MatchId);

        loadSeasonData(HomeTeamID,AwayTeamID,new WebView(getContext()).getSettings().getUserAgentString());




        //Head to Head//
        new getMatchHeadStats(UserAgent,"https://www.premierleague.com/match/"+MatchId).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new getRecentMatch("https://www.premierleague.com/match/"+MatchId,UserAgent).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return v;
    }

    void initTextView(){
        total_match_played =        v.findViewById(R.id.total_match_played);
        total_match_draw =          v.findViewById(R.id.total_match_draw);
        total_wins_Home_team =      v.findViewById(R.id.total_wins_Home_team);
        total_wins_Away_team=       v.findViewById(R.id.total_wins_Away_team);
        total_home_wins_home_team=  v.findViewById(R.id.total_home_wins_home_team);
        total_home_wins_away_team=  v.findViewById(R.id.total_home_wins_away_team);
        total_away_wins_home_team=  v.findViewById(R.id.total_away_wins_home_team);
        total_away_wins_away_team=  v.findViewById(R.id.total_away_wins_away_team);
    }

    void initTextViewStatastic(){
        text_possesion_home_team = v.findViewById(R.id.text_possesion_home_team);
        text_possesion_away_team = v.findViewById(R.id.text_possesion_away_team);

        text_shots_on_target_home_team = v.findViewById(R.id.text_shots_on_target_home_team);
        text_shots_on_target_away_team = v.findViewById(R.id.text_shots_on_target_away_team);

        text_shots_home_team = v.findViewById(R.id.text_shots_home_team);
        text_shots_away_team = v.findViewById(R.id.text_shots_away_team);

        text_touches_home_team = v.findViewById(R.id.text_touches_home_team);
        text_touches_away_team = v.findViewById(R.id.text_touches_away_team);

        text_passes_home_team = v.findViewById(R.id.text_passes_home_team);
        text_passes_away_team = v.findViewById(R.id.text_passes_away_team);

        text_tackles_home_team = v.findViewById(R.id.text_tackles_home_team);
        text_tackles_away_team = v.findViewById(R.id.text_tackles_away_team);

        text_clearance_home_team = v.findViewById(R.id.text_clearance_home_team);
        text_clearance_away_team = v.findViewById(R.id.text_clearance_away_team);

        text_corners_home_team = v.findViewById(R.id.text_corners_home_team);
        text_corners_away_team = v.findViewById(R.id.text_corners_away_team);

        text_offsides_home_team = v.findViewById(R.id.text_offsides_home_team);
        text_offsides_away_team = v.findViewById(R.id.text_offsides_away_team);

        text_yellow_cards_home_team = v.findViewById(R.id.text_yellow_cards_home_team);
        text_yellow_cards_away_team = v.findViewById(R.id.text_yellow_cards_away_team);

        text_red_cards_home_team = v.findViewById(R.id.text_red_cards_home_team);
        text_red_cards_away_team = v.findViewById(R.id.text_red_cards_away_team);

        text_fouls_home_team = v.findViewById(R.id.text_fouls_home_team);
        text_fouls_away_team = v.findViewById(R.id.text_fouls_away_team);

    }



    void initRecentRecycler(){
        recentModelList = new ArrayList<>();
        rec_recent_meeting = v.findViewById(R.id.rec_recent_meeting);
        recentMeetingAdapter = new recent_meeting_adapter(recentModelList,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation());
        rec_recent_meeting.setHasFixedSize(true);
        rec_recent_meeting.setAdapter(recentMeetingAdapter);
        rec_recent_meeting.setLayoutManager(linearLayoutManager);
        rec_recent_meeting.setNestedScrollingEnabled(false);
        rec_recent_meeting.addItemDecoration(itemDecoration);
    }

    void initSeasonTextView(){
        home_team_position = v.findViewById(R.id.home_team_position);
        away_team_position = v.findViewById(R.id.away_team_position);
        home_team_won = v.findViewById(R.id.home_team_won);
        away_team_won = v.findViewById(R.id.away_team_won);

        home_team_draw = v.findViewById(R.id.home_team_draw);
        away_team_draw = v.findViewById(R.id.away_team_draw);


        home_team_lost = v.findViewById(R.id.home_team_lost);
        away_team_lost = v.findViewById(R.id.away_team_lost);

        home_team_gd = v.findViewById(R.id.home_team_gd);
        away_team_gd = v.findViewById(R.id.away_team_gd);


    }


    //Curent match Stats loadSeasonDats is used in thi method
    void loadData(String Url, final String UserAgent, final String MatchID){                     //https://www.premierleague.com/match/38319
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{//entity
                    JSONObject obj = new JSONObject(response);
                    JSONObject TeamDeatak = obj.getJSONObject("entity");
                    JSONArray TeamID = TeamDeatak.getJSONArray("teams");
                    JSONObject HomeTeam = TeamID.getJSONObject(0);
                    JSONObject AwayTeam = TeamID.getJSONObject(1);

                    JSONObject HomTeamIDData = HomeTeam.getJSONObject("team");
                    JSONObject AwayTeamIDData = AwayTeam.getJSONObject("team");


                    String HomeTeamID = String.valueOf(HomTeamIDData.getInt("id"));
                    String AwayTeamID = String.valueOf(AwayTeamIDData.getInt("id"));

                    //get the stats of match
                    //For home Team Stats
                    JSONObject HomeTeamStatsData = obj.getJSONObject("data");
                    JSONObject HomeTeamStats = HomeTeamStatsData.getJSONObject(HomeTeamID);
                    JSONArray HomeTeamStatsM = HomeTeamStats.getJSONArray("M");

                    for (int i =0;i<HomeTeamStatsM.length();i++){
                        JSONObject getType = HomeTeamStatsM.getJSONObject(i);
                        String StatsName = getType.getString("name");

                        String StatNumber = null;
                        if(Objects.equals(StatsName, "possession_percentage")){
                            StatNumber = String.valueOf(getType.getDouble("value"));
                            text_possesion_home_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "ontarget_scoring_att")){//getDouble("value")
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_shots_on_target_home_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "total_scoring_att")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_shots_home_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "touches")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_touches_home_team.setText(StatNumber);
                        //    Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }



                        if(Objects.equals(StatsName, "total_pass")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_passes_home_team.setText(StatNumber);
                        //    Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }



                        if(Objects.equals(StatsName, "total_tackle")){
                            StatNumber =  String.valueOf(getType.getInt("value"));
                            text_tackles_home_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }



                        if(Objects.equals(StatsName, "total_clearance")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_clearance_home_team.setText(StatNumber);
                         //   Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "won_corners")){
                            StatNumber =  String.valueOf(getType.getInt("value"));
                            text_corners_home_team.setText(StatNumber);
                         //   Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }


                        if(Objects.equals(StatsName, "total_offside")){
                            StatNumber =  String.valueOf(getType.getInt("value"));
                            text_offsides_home_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }
                        if(Objects.equals(StatsName, "total_yel_card")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_yellow_cards_home_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "total_red_card")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_red_cards_home_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }


                        if(Objects.equals(StatsName, "fk_foul_lost")){
                            StatNumber =  String.valueOf(getType.getInt("value"));
                            text_fouls_home_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                    }



                    JSONObject AwayTeamStatsData = obj.getJSONObject("data");
                    JSONObject AwayTeamStats = AwayTeamStatsData.getJSONObject(AwayTeamID);
                    JSONArray AwayTeamStatsM = AwayTeamStats.getJSONArray("M");



                    for (int i =0;i<AwayTeamStatsM.length();i++){
                        JSONObject getType = AwayTeamStatsM.getJSONObject(i);
                        String StatsName = getType.getString("name");

                        String StatNumber = null;
                        if(Objects.equals(StatsName, "possession_percentage")){
                            StatNumber = String.valueOf(getType.getDouble("value"));
                            text_possesion_away_team.setText(StatNumber);
                         //   Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "ontarget_scoring_att")){//getDouble("value")
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_shots_on_target_away_team.setText(StatNumber);
                         //   Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "total_scoring_att")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_shots_away_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "touches")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_touches_away_team.setText(StatNumber);
                         //   Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }



                        if(Objects.equals(StatsName, "total_pass")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_passes_away_team.setText(StatNumber);
                          //  Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }



                        if(Objects.equals(StatsName, "total_tackle")){
                            StatNumber =  String.valueOf(getType.getInt("value"));
                            text_tackles_away_team.setText(StatNumber);
                         //   Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }



                        if(Objects.equals(StatsName, "total_clearance")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_clearance_away_team.setText(StatNumber);
                        //    Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "won_corners")){
                            StatNumber =  String.valueOf(getType.getInt("value"));
                            text_corners_away_team.setText(StatNumber);
                        //    Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }


                        if(Objects.equals(StatsName, "total_offside")){
                            StatNumber =  String.valueOf(getType.getInt("value"));
                            text_offsides_away_team.setText(StatNumber);
                        //    Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }
                        if(Objects.equals(StatsName, "total_yel_card")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_yellow_cards_away_team.setText(StatNumber);
                        //    Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                        if(Objects.equals(StatsName, "total_red_card")){
                            StatNumber = String.valueOf(getType.getInt("value"));
                            text_red_cards_away_team.setText(StatNumber);
                         //   Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }


                        if(Objects.equals(StatsName, "fk_foul_lost")){
                            StatNumber =  String.valueOf(getType.getInt("value"));
                            text_fouls_away_team.setText(StatNumber);
                         //   Log.d("Fragment_match_stat"," "  + " "  + StatNumber );
                        }

                    }

                    Match_stats_live.setVisibility(View.VISIBLE);
                    //  Log.d("Fragment_match_stat"," " + HomeTeamID + " "  +AwayTeamID );

                }catch (Exception e){
                    Match_stats_live.setVisibility(View.GONE);
                    CommentryText.setVisibility(View.VISIBLE);
                    //Log.d("Fragment_match_stat"," " +  e );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Match_stats_live.setVisibility(View.GONE);
                CommentryText.setVisibility(View.VISIBLE);
              //  Log.d("Fragment_match_stat"," " + error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent",UserAgent);
                params.put("Referer", "https://www.premierleague.com/match/"+MatchID);
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



    //This season so for
    void loadSeasonData(final String homeTeamID, final String AwayTeaMID, final String UserAgent){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://footballapi.pulselive.com/football/standings?compSeasons=210", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject obj = new JSONObject(response);
                    JSONArray Table = obj.getJSONArray("tables");
                    JSONObject MatchData = Table.getJSONObject(0);
                    JSONArray TeamTable = MatchData.getJSONArray("entries");
                    for(int i =0;i<TeamTable.length();i++){
                        //team
                        JSONObject TeamObject= TeamTable.getJSONObject(i);
                        JSONObject Team = TeamObject.getJSONObject("team");
                        String TeamID = String.valueOf(Team.getInt("id"));
                        if (Objects.equals(homeTeamID, TeamID)){
                            String position = TeamObject.getString("position");
                            JSONObject TeamStats = TeamObject.getJSONObject("overall");

                            String Drawn = String.valueOf(TeamStats.getInt("drawn"));
                            String Won = String.valueOf(TeamStats.getInt("won"));
                            String Lost = String.valueOf(TeamStats.getInt("lost"));
                            String GD = String.valueOf(TeamStats.getInt("goalsDifference"));
                            home_team_position.setText(position);
                            home_team_won.setText(Won);
                            home_team_lost.setText(Lost);
                            home_team_gd.setText(GD);
                            home_team_draw.setText(Drawn);
                          //  Log.d("Fragment_seat_season_data"," " + TeamID + " " + position + " " +Drawn + " " +Won + " " + Lost + " " +GD);
                        }
                        if (Objects.equals(AwayTeaMID, TeamID)){
                            String position = TeamObject.getString("position");
                            JSONObject TeamStats = TeamObject.getJSONObject("overall");

                            String Drawn = String.valueOf(TeamStats.getInt("drawn"));
                            String Won = String.valueOf(TeamStats.getInt("won"));
                            String Lost = String.valueOf(TeamStats.getInt("lost"));
                            String GD = String.valueOf(TeamStats.getInt("goalsDifference"));

                            away_team_position.setText(position);
                            away_team_won.setText(Won);
                            away_team_draw.setText(Drawn);
                            away_team_lost.setText(Lost);
                            away_team_gd.setText(GD);

                          //  Log.d("Fragment_seat_season_data"," " + TeamID + " " + position + " " +Drawn + " " +Won + " " + Lost + " " +GD);
                        }
                       /// statViewS.setVisibility(View.VISIBLE);
                    }

                }catch (Exception e){
                   // Log.d("Fragment_seat_season_data"," " +  e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressBar.setVisibility(View.GONE);
                SeasonSofor.setVisibility(View.GONE);
                CommentryText.setVisibility(View.VISIBLE);
                //CommentryText.setV
                //Log.d("Fragment_seat_season_data"," " +  error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", UserAgent);
                params.put("Referer", "https://www.premierleague.com/fixtures");
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



    //Head to headt stats
    public class getMatchHeadStats extends AsyncTask<String,Void,String> {

        String USER_AGENT;
        String Link;

        public getMatchHeadStats(String USER_AGENT, String link) {
            this.USER_AGENT = USER_AGENT;
            Link = link;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   Log.d("statMatch","getMatchHead Started");
        }

        @Override
        protected String doInBackground(String... strings) {
            Document document = null;

            try{
                document = Jsoup.connect(Link).userAgent(USER_AGENT).referrer("https://www.premierleague.com").get();

                Elements Matches = document.select(".headToHeadTable.headToHeadTableLeft").select(".statRow");
                Elements AwayTeamMatches = document.select(".headToHeadTable.headToHeadTableRight").select(".statRow");
                //homeTeam
                final String totalWinsTeam_home = Matches.get(0).select(".count").text();
                final String home = Matches.get(1).select(".count").text();
                final String away = Matches.get(2).select(".count").text();

                //awayTeam
                final String totalWinsTeam_Away = AwayTeamMatches.get(0).select(".count").text();
                final String homeTeam_Away = AwayTeamMatches.get(1).select(".count").text();
                final String awayTeam_Away = AwayTeamMatches.get(2).select(".count").text();


                final String matchPlayed = document.select(".middle-section").select("p.number").text();
                final String matchDraw = document.select(".middle-section").select(".draws").select("span").text();

           //     Log.d("Fragment_getHeadStats","MAtch_played " + matchPlayed  );
                if (v.isEnabled()&& v !=null){
                    if (getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            total_match_played.setText(matchPlayed);
                            total_match_draw.setText(matchDraw);

                            total_wins_Home_team.setText(totalWinsTeam_home);
                            total_wins_Away_team.setText(totalWinsTeam_Away);


                            total_home_wins_home_team.setText(home);
                            total_home_wins_away_team.setText(away);



                            total_away_wins_home_team.setText(homeTeam_Away);
                            total_away_wins_away_team.setText(awayTeam_Away);


                        }
                    });

                }

                //  System.out.println(totalWinsTeam_Away + " home: " + homeTeam_Away + " away: " + awayTeam_Away+ " " + matchPlayed + " " +matchDraw);

            }catch (Exception e){
              //  Log.d("Fragment_getHeadStats"," " + e );
                if (v.isEnabled()&& v !=null){

                    if (getActivity()!=null)
                        getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Head_to_Head.setVisibility(View.GONE);

                        }
                    });

                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          //b  Log.d("statMatch","getMatchHead End");
            progressBar.setVisibility(View.GONE);
        }
    }

    public class getRecentMatch extends  AsyncTask<String,Void, List<recent_model>>{

        String Url;
        String UserAgent;

        public getRecentMatch(String url, String userAgent) {
            Url = url;
            UserAgent = userAgent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Log.d("statMatch","getRecentMatch start");
        }

        @Override
        protected List<recent_model> doInBackground(String... strings) {
            Document document;
            ArrayList<recent_model> data = new ArrayList<>();
            try{
                //document = Jsoup.parse(htmlData);
                document = Jsoup.connect(Url).userAgent(UserAgent).referrer("https://www.premierleague.com").get();

                Elements Matches = document.select(".previous-meetings").select(".matchFixtureContainer");
                //
                //  recent_model item;
                //System.out.println(Matches.size()); score
                recent_model item;
                for(Element Data : Matches){
                    String time = Data.select("time").text();
                    String statdium = Data.select(".stadiumName").text();
                    String MatchLink = Data.select(".fixture").select("a").attr("href");
                    String TeamA = Data.select(".fixture").select(".team").get(0).text();
                    String TeamScore = Data.select(".fixture").select(".score").text();
                    String TeamB = Data.select(".fixture").select(".team").get(1).text();

                    item = new recent_model();

                    item.setMatchStatdium(statdium);
                    item.setMatchDate(time);
                    item.setMatchLink(MatchLink);
                    item.setMatchscore(TeamScore);
                    item.setMatchTeamA(TeamA);
                    item.setMatchTeamB(TeamB);
                    data.add(item);
              //  Log.d("Framgnet_Recent_meeting"," "+ time + " " + statdium + " " + MatchLink + " " + TeamA +" " +TeamScore +" " + TeamB);
                }


            }catch(Exception e){
            //    Log.d("Framgnet_Recent_meeting"," " +e);

            }

            return data;
        }

        @Override
        protected void onPostExecute(List<recent_model> data) {
            super.onPostExecute(data);
            //Log.d("statMatch","getRecentMatch end");
            if (data.size()>0){
                linearLayoutRecentMeeting.setVisibility(View.VISIBLE);
                //  statViewS.setVisibility(View.VISIBLE);
                recentModelList.addAll(data);
                recentMeetingAdapter.notifyDataSetChanged();
            }





        }
    }


}








/*
 mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("01DE48E77EEFC7F7C6E3A641708BC457").build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView2 = v.findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().addTestDevice("01DE48E77EEFC7F7C6E3A641708BC457").build();
        mAdView2.loadAd(adRequest2);

        mAdView2.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView2.setVisibility(View.VISIBLE);
            }
        });


        mAdView3 = v.findViewById(R.id.adView3);
        AdRequest adRequest3 = new AdRequest.Builder().addTestDevice("01DE48E77EEFC7F7C6E3A641708BC457").build();
        mAdView3.loadAd(adRequest3);

        mAdView3.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView3.setVisibility(View.VISIBLE);
            }
        });




 */



































/*
TabLayout tabLayout = v.findViewById(R.id.home_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Head-to-Head"));
        tabLayout.addTab(tabLayout.newTab().setText("Match Stats"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = v.findViewById(R.id.home_fragment_pager);
        final stats_adapter Pageradapter = new stats_adapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(Pageradapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("TabPostionlayout "+tab.getPosition());
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
        //viewPager.setOffscreenPageLimit(3);




//XML code

    <android.support.design.widget.TabLayout
        android:id="@+id/home_tablayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?attr/colorPrimaryDark"
        android:minHeight="?attr/actionBarSize"
        android:theme="@style/MyCustomTablayout"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        app:tabTextColor="@color/colorPrimary"
        app:tabSelectedTextColor="@color/colorAccent"
        />

    <android.support.v4.view.ViewPager
        android:id="@+id/home_fragment_pager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        />




 */
