package table.football.soccer.livescore.epl.epllive.Fragments_stats;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import table.football.soccer.livescore.epl.epllive.Adapters.recent_meeting_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.recent_model;

//not use anywhere
public class Fragment_head_to_head extends Fragment {



    View v;
    TextView total_match_played,total_match_draw,total_wins_Home_team,total_wins_Away_team,
            total_home_wins_home_team,total_home_wins_away_team,total_away_wins_home_team,total_away_wins_away_team;
    ProgressBar progress_total_wins_home_team,progress_total_away_team,progress_home_wins_home_team,progress_home_wins_away_team,
            progress_away_wins_home_team,progress_away_wins_away_team;

    //Recent metting recyclerView and adadapet
    List<recent_model> recentModelList;
    RecyclerView rec_recent_meeting;
    recent_meeting_adapter recentMeetingAdapter;




   /*

   //Season so for
    TextView season_home_team_name,season_away_team_name,position_home_team,position_away_team,position_home_team_won,
            position_away_team_away,position_home_team_draw,position_away_team_draw,position_home_team_lost,position_away_team_lost;

    ProgressBar progress_position_home_team,progress_position_away_team,progress_won_team_home,progress_won_away_team,
    progress_draw_team_home,progress_draw_away_team,progress_lost_team_home,progress_lost_away_team;


    void initSeasonTextAndprog(){
        season_home_team_name = v.findViewById(R.id.season_home_team_name);
        season_away_team_name = v.findViewById(R.id.season_away_team_name);
        position_home_team = v.findViewById(R.id.position_home_team);
        position_away_team = v.findViewById(R.id.position_away_team);
        position_home_team_won = v.findViewById(R.id.position_home_team_won);
        position_away_team_away = v.findViewById(R.id.position_away_team_away);
        position_home_team_draw = v.findViewById(R.id.position_home_team_draw);
        position_away_team_draw = v.findViewById(R.id.position_away_team_draw);
        position_home_team_lost = v.findViewById(R.id.position_home_team_lost);
        position_away_team_lost = v.findViewById(R.id.position_away_team_lost);

        progress_position_home_team = v.findViewById(R.id.progress_position_home_team);
        progress_position_away_team = v.findViewById(R.id.progress_position_away_team);

        progress_won_team_home = v.findViewById(R.id.progress_won_team_home);
        progress_won_away_team = v.findViewById(R.id.progress_won_away_team);

        progress_draw_team_home = v.findViewById(R.id.progress_draw_team_home);
        progress_draw_away_team = v.findViewById(R.id.progress_draw_away_team);

        progress_lost_team_home = v.findViewById(R.id.progress_lost_team_home);
        progress_lost_away_team = v.findViewById(R.id.progress_lost_away_team);

    }

    */



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_head_to_head, container, false);
        initTextView();
        initRecentRecycler();
        new getMatchHeadStats("","").execute();

        return v;
    }


    void initTextView(){
       /*
        progress_total_wins_home_team = v.findViewById(R.id.progress_total_wins_home_team);
        progress_total_away_team=       v.findViewById(R.id.progress_total_away_team);
        progress_home_wins_home_team=   v.findViewById(R.id.progress_home_wins_home_team);
        progress_home_wins_away_team=   v.findViewById(R.id.progress_home_wins_away_team);
        progress_away_wins_home_team=   v.findViewById(R.id.progress_away_wins_home_team);
        progress_away_wins_away_team=   v.findViewById(R.id.progress_away_wins_away_team);
        */


                total_match_played =        v.findViewById(R.id.played_text);
                total_match_draw =          v.findViewById(R.id.draw_text);
                total_wins_Home_team =      v.findViewById(R.id.total_wins_home_team);
                total_wins_Away_team=       v.findViewById(R.id.total_wins_away_team);
                total_home_wins_home_team=  v.findViewById(R.id.home_wins_home_team);
                total_home_wins_away_team=  v.findViewById(R.id.home_wins_away_team);
                total_away_wins_home_team=  v.findViewById(R.id.away_wins_home_team);
                total_away_wins_away_team=  v.findViewById(R.id.away_wins_away_team);
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




    public class getMatchHeadStats extends AsyncTask<String,Void,String>{

        String USER_AGENT;
        String Link;

        public getMatchHeadStats(String USER_AGENT, String link) {
            this.USER_AGENT = USER_AGENT;
            Link = link;
        }
        @Override
        protected String doInBackground(String... strings) {
            Document document = null;

            try{
                document = Jsoup.connect("https://www.premierleague.com/match/38323").userAgent(USER_AGENT).get();
                Elements Matches = document.select(".headToHeadTable.headToHeadTableLeft").select(".statRow");
                Elements AwayTeamMatches = document.select(".headToHeadTable.headToHeadTableRight").select(".statRow");
                //homeTeam
                final String totalWinsTeam_home = Matches.get(0).select(".count").text();
                final String home = Matches.get(1).select(".count").text();
                final String away = Matches.get(2).select(".count").text();
                final int TotalWins     = Integer.parseInt(totalWinsTeam_home);
                final int TotalWinshome = Integer.parseInt(home);
                final int TotalWinsaway = Integer.parseInt(away);
                //awayTeam
                final String totalWinsTeam_Away = AwayTeamMatches.get(0).select(".count").text();
                final String homeTeam_Away = AwayTeamMatches.get(1).select(".count").text();
                final String awayTeam_Away = AwayTeamMatches.get(2).select(".count").text();
                final int TotalWinstotalWinsTeam_Away = Integer.parseInt(totalWinsTeam_Away);
                final int TotalWinshomehomeTeam_Away = Integer.parseInt(homeTeam_Away);
                final int TotalWinsawayawayTeam_Away = Integer.parseInt(awayTeam_Away);
                //Total Match wins of both team
                final int progressmax1;
                if (TotalWins>TotalWinstotalWinsTeam_Away){
                    progressmax1 = TotalWins+5;
                }
                else
                    progressmax1 = TotalWinstotalWinsTeam_Away+5;
                //total wins home place both rteam
                final int progressmax2;
                if (TotalWinshome>TotalWinshomehomeTeam_Away){
                    progressmax2 = TotalWinshome+5;
                }
                else
                    progressmax2 = TotalWinshomehomeTeam_Away+5;
                //total wins away place both
                final int progressmax3;
                if (TotalWinsaway>TotalWinsawayawayTeam_Away){
                    progressmax3 = TotalWinsaway+5;
                }
                else
                    progressmax3 = TotalWinsawayawayTeam_Away+5;
                final String matchPlayed = document.select(".middle-section").select("p.number").text();
                final String matchDraw = document.select(".middle-section").select(".draws").select("span").text();


                if (v.isEnabled()&& v !=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            total_match_played.setText(matchPlayed);
                            total_match_draw.setText(matchDraw);

                            total_wins_Home_team.setText(totalWinsTeam_home);
                            total_wins_Away_team.setText(totalWinsTeam_Away);
                            progress_total_wins_home_team.setMax(progressmax1);
                            progress_total_away_team.setMax(progressmax1);
                            progress_total_wins_home_team.setProgress(TotalWins);
                            progress_total_away_team.setProgress(TotalWinstotalWinsTeam_Away);

                            total_home_wins_home_team.setText(home);
                            total_home_wins_away_team.setText(away);
                            progress_home_wins_home_team.setMax(progressmax2);
                            progress_home_wins_away_team.setMax(progressmax2);
                            progress_home_wins_home_team.setProgress(TotalWinshome);
                            progress_home_wins_away_team.setProgress(TotalWinshomehomeTeam_Away);


                            total_away_wins_home_team.setText(homeTeam_Away);
                            total_away_wins_away_team.setText(awayTeam_Away);
                            progress_away_wins_home_team.setMax(progressmax3);
                            progress_away_wins_away_team.setMax(progressmax3);
                            progress_away_wins_home_team.setProgress(TotalWinsaway);
                            progress_away_wins_away_team.setProgress(TotalWinsawayawayTeam_Away);

                        }
                    });

                }

              //  System.out.println(totalWinsTeam_Away + " home: " + homeTeam_Away + " away: " + awayTeam_Away+ " " + matchPlayed + " " +matchDraw);

            }catch (Exception e){
                Log.d("Fragment_head_to"," " + e );
            }









            new getRecentMatch(document.toString()).execute();


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

  public class getRecentMatch extends  AsyncTask<String,Void, List<recent_model>>{

      String htmlData;

      public getRecentMatch(String htmlData) {
          this.htmlData = htmlData;
      }

      @Override
      protected List<recent_model> doInBackground(String... strings) {
          Document document;
          ArrayList<recent_model> data = new ArrayList<>();
          try{
              document = Jsoup.parse(htmlData);
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
              //  Log.d("Framgnet_head_to_head"," "+ time + " " + statdium + " " + MatchLink + " " + TeamA +" " +TeamScore +" " + TeamB);
              }


          }catch(Exception e){
            //  Log.d("Framgnet_head_to_head"," " +e);

          }

          return data;
      }

      @Override
      protected void onPostExecute(List<recent_model> data) {
          super.onPostExecute(data);

          recentModelList.addAll(data);
          recentMeetingAdapter.notifyDataSetChanged();



      }
  }
   }


