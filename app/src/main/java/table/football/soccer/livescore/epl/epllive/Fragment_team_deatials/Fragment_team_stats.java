package table.football.soccer.livescore.epl.epllive.Fragment_team_deatials;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.R;


public class Fragment_team_stats extends Fragment {

    //Top statsTextview
    TextView    text_view_total_match_played,
                text_view_total_match_wins,
                text_view_total_match_losses,
                text_view_total_match_goals,
                text_view_total_goal_conceded,
                text_view_total_clean;

    //Attack Textview
    TextView textView_goals,
            text_view_goals_per_match,
            text_view_shots,
            text_view_shots_on_target,
            textView_shooting_accuracy,
            textView_penalties_scored,
            text_view_bigChance,
            text_view_hit_wood;


    //defence
    TextView TextView_clean_sheets,
            textView_goal_concedded,
            textView_goals_conceed_permatch,
            textView_saves,
            textview_tackles,
            textview_tacklessucces_percent,
            textview_bloacked_shots,
            textview_interception,
            textview_clearances,
            textview_headedClearnce,
            textView_arialwon,
            textview_errors,
            textview_owngoals;


    //Teamplay
    TextView tect_view_passes,
            textview_passes_per_match,
            textviewpass_perce,
            textview_crosses,
            text_view_crooss_accur;

    //Desciplne
    TextView textview_yellow_cards,
            textView_red_cards,
            textview_fouls,
            text_View_offsided;


    void inittetview(){

        //TopStats
        text_view_total_match_played = v.findViewById(R.id.text_view_total_match_played);
        text_view_total_match_wins = v.findViewById(R.id.text_view_total_match_wins);
        text_view_total_match_losses = v.findViewById(R.id.text_view_total_match_losses);
        text_view_total_match_goals = v.findViewById(R.id.text_view_total_match_goals);
        text_view_total_goal_conceded = v.findViewById(R.id.text_view_total_goal_conceded);
        text_view_total_clean = v.findViewById(R.id.text_view_total_clean);

        //Attack
        textView_goals = v.findViewById(R.id.textView_goals);
        text_view_goals_per_match = v.findViewById(R.id.text_view_goals_per_match);
        text_view_shots = v.findViewById(R.id.text_view_shots);
        text_view_shots_on_target = v.findViewById(R.id.text_view_shots_on_target);
        textView_shooting_accuracy = v.findViewById(R.id.textView_shooting_accuracy);
        textView_penalties_scored = v.findViewById(R.id.textView_penalties_scored);
        text_view_bigChance = v.findViewById(R.id.text_view_bigChance);
        text_view_hit_wood = v.findViewById(R.id.text_view_hit_wood);

        //Defence
        TextView_clean_sheets = v.findViewById(R.id.TextView_clean_sheets);
        textView_goal_concedded = v.findViewById(R.id.textView_goal_concedded);
        textView_goals_conceed_permatch = v.findViewById(R.id.textView_goals_conceed_permatch);
        textView_saves = v.findViewById(R.id.textView_saves);
        textview_tackles = v.findViewById(R.id.textview_tackles);
        textview_tacklessucces_percent = v.findViewById(R.id.textview_tacklessucces_percent);
        textview_bloacked_shots = v.findViewById(R.id.textview_bloacked_shots);
        textview_interception = v.findViewById(R.id.textview_interception);
        textview_clearances = v.findViewById(R.id.textview_clearances);
        textview_headedClearnce = v.findViewById(R.id.textview_headedClearnce);
        textView_arialwon = v.findViewById(R.id.textView_arialwon);
        textview_errors = v.findViewById(R.id.textview_errors);
        textview_owngoals = v.findViewById(R.id.textview_owngoals);

        //teamPlay
        tect_view_passes = v.findViewById(R.id.tect_view_passes);
        textview_passes_per_match = v.findViewById(R.id.textview_passes_per_match);
        textviewpass_perce = v.findViewById(R.id.textviewpass_perce);
        textview_crosses = v.findViewById(R.id.textview_crosses);
        text_view_crooss_accur = v.findViewById(R.id.text_view_crooss_accur);

        //Desiplen
        textview_yellow_cards = v.findViewById(R.id.textview_yellow_cards);
        textView_red_cards = v.findViewById(R.id.textView_red_cards);
        textview_fouls = v.findViewById(R.id.textview_fouls);
        text_View_offsided = v.findViewById(R.id.text_View_offsided);
    }




    //LinearLayoutContainer
    LinearLayout LayoutContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView ErrorTextView;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_team_stats, container, false);
        LayoutContainer = v.findViewById(R.id.LinearLayoutContainer);
        ErrorTextView = v.findViewById(R.id.erroText);


        swipeRefreshLayout = v.findViewById(R.id.Swipe);
        swipeRefreshLayout.setRefreshing(true);
        String TeamID = getActivity().getIntent().getExtras().getString("TeamID");
        inittetview();//
        getTeamFixture("https://footballapi.pulselive.com/football/stats/team/"+TeamID+"?comps=1&compSeasons=210",new WebView(getContext()).getSettings().getUserAgentString());
        return v;
    }






    private void getTeamFixture(final String TeamID,final String UserAgent){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, TeamID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject obj = new JSONObject(response);

                   JSONArray StatArray = obj.getJSONArray("stats");

                    //Attack and top Stats
                   //   Log.d("TeamStat"," " + StatArray.length());
                    int     Match_wins = 0, Match_draw= 0, Match_losse= 0,
                            Total_MathsPlayed= 0,Goals= 0,
                            Clean_sheets= 0,Total_Shots= 0,Total_Shots_on_Target= 0,
                            Total_pen_scored= 0,Tota_Big_chance_created= 0,Total_HitWoodwork= 0;
                    double Goal_per_match= 0,Total_shooting_accuracyPercent= 0;


                    //for total match played
                    boolean wins = false,draw=false,lose=false,matchplayed = false;
                    //Goals per match
                    boolean goalhave = false;
                    //Shooting accuracy %
                    boolean getontarget_scoring_att = false,gettotal_scoring_att=false;
                    //Goals Conceded
                    boolean goalConceded =false;
                    //Tackle percent getTotal_tackle getwontackle
                    boolean getTotal_tackle = false,getwontackle = false;
                    //For ariale dule
                    boolean getarail_wpon = false , getduel_won = false;
                    //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                    boolean getTotalPasses = false,get_accurate_pass=false;
                    //cross per match TotalCrosses Total_accurate_cross getTotalCross getTotal_accurate_croos
                    boolean getTotalCross =false , getTotal_accurate_croos = false;

                    //Defence aerial_won,duel_won
                    int clean_sheet= 0,goals_conceded=0,saves= 0,
                            total_tackle= 0,tackle_won=0,blocked_scoring_att= 0,
                            interception= 0,total_clearance= 0,
                    effective_head_clearance= 0,aerail_won =0,duel_won=0
                            ,error_lead_to_goal= 0,own_goals= 0;

                   double Goals_Conceded_per_match= 0,Tackles_succes_percent= 0;

                    //Teamplay TotalCrosses Total_accurate_cross
                    int Total_passes = 0,TotalCrosses= 0,Total_accurate_pass = 0,Total_accurate_cross =0;
                    double Total_passes_per_match= 0,Totoal_pass_accuracy_per= 0,Total_cross_accuracy_per= 0;


                    //Disiplene
                    int total_yel_card= 0,total_red_card= 0,attempted_tackle_foul= 0,total_offside= 0;

                      for(int i=0;i<StatArray.length();i++){

                          JSONObject StatData = StatArray.getJSONObject(i);

                          String StateName = StatData.getString("name");
                          //TopStats and Attack stats
                          if (Objects.equals(StateName, "wins")) {//wins
                              Match_wins = StatData.getInt("value");
                              text_view_total_match_wins.setText(String.valueOf(Match_wins));
                               wins = true;
                            // Log.d("TeamStat", " Match_wins " + i+ " " + Match_wins);
                          }
                          else {
                              if (!wins){
                                  wins = true;

                              }
                          }


                           if (Objects.equals(StateName, "draws")){//draws
                              Match_draw = StatData.getInt("value");
                               draw = true;
                          //   Log.d("TeamStat", " Match_draw " + i+ " " +Match_draw);
                          }else {
                              if (!draw){
                                  draw = true;

                              }
                           }
                           if (Objects.equals(StateName, "losses")){//losses
                              Match_losse = StatData.getInt("value");
                               lose = true;
                          text_view_total_match_losses.setText(String.valueOf(Match_losse));
                          //  Log.d("TeamStat", " Match_losse " +i+ " " + Match_losse);
                          }else {
                               if (!lose){
                                   lose = true;

                               }
                           }

                         if (Objects.equals(StateName, "total_scoring_att")){//total_Shots
                              Total_Shots = StatData.getInt("value");
                             text_view_shots.setText(String.valueOf(Total_Shots));
                             gettotal_scoring_att=true;
                          //  Log.d("TeamStat", " Total_Shots " +i+ " " + Total_Shots);  boolean getontarget_scoring_att = false,gettotal_scoring_att=false;
                          }
                          if (Objects.equals(StateName, "ontarget_scoring_att")){//Shots on target
                              Total_Shots_on_Target = StatData.getInt("value");
                              text_view_shots_on_target.setText(String.valueOf(Total_Shots_on_Target));
                              getontarget_scoring_att =true;
                           // Log.d("TeamStat", " Total_Shots_on_Target " + i+ " " +Total_Shots_on_Target);
                          }
                         if (Objects.equals(StateName, "att_pen_goal")){//Penalties scored
                              Total_pen_scored = StatData.getInt("value");
                              textView_penalties_scored.setText(String.valueOf(Total_pen_scored));
                          //  Log.d("TeamStat", " Total_pen_scored " +i+ " " + Total_pen_scored);
                          }
                         if (Objects.equals(StateName, "big_chance_created")){//big_chance_created
                              Tota_Big_chance_created = StatData.getInt("value");
                              text_view_bigChance.setText(String.valueOf(Tota_Big_chance_created));
                           //Log.d("TeamStat", " Tota_Big_chance_created " +i+ " " + Tota_Big_chance_created);
                          }
                           if (Objects.equals(StateName, "hit_woodwork")){//hit_woodwork
                               Total_HitWoodwork = StatData.getInt("value");
                               text_view_hit_wood.setText(String.valueOf(Total_HitWoodwork));
                          //Log.d("TeamStat", " Total_HitWoodwork " +i+ " " + Total_HitWoodwork);
                          }//

                          if (Objects.equals(StateName, "clean_sheet")){//Clean sheets
                              Clean_sheets = StatData.getInt("value");
                              TextView_clean_sheets.setText(String.valueOf(Clean_sheets));
                              text_view_total_clean.setText(String.valueOf(Clean_sheets));
                              //Log.d("TeamStat", " Clean_sheets " + Clean_sheets);
                          }
                           if (Objects.equals(StateName, "total_scoring_att")){//Shooting accuracy%--
                              Total_shooting_accuracyPercent = StatData.getInt("value");
                            //  Log.d("TeamStat", " Total_shooting_accuracyPercent " + Total_shooting_accuracyPercent);
                          }


                          if (Objects.equals(StateName, "goals")){//Goal
                              Goals = StatData.getInt("value");
                              goalhave = true;
                              textView_goals.setText(String.valueOf(Goals));
                              text_view_total_match_goals.setText(String.valueOf(Goals));
                          //    Log.d("TeamStat", " Goals " + Goals);
                          }







                          //Defence
                         // if (Objects.equals(StateName, "clean_sheet")){//Cleasn Sheets
                         ///     Clean_sheets = StatData.getInt("value");
                         //     Log.d("TeamStat", " Clean_sheets " + Clean_sheets);
                         // }
                          if (Objects.equals(StateName, "goals_conceded")){//Goals conceded
                              goals_conceded = StatData.getInt("value");
                              textView_goal_concedded.setText(String.valueOf(goals_conceded));
                              text_view_total_goal_conceded.setText(String.valueOf(goals_conceded));
                              goalConceded = true;
                            //  Log.d("TeamStat", " goals_conceded " + goals_conceded);
                          }

                          if (Objects.equals(StateName, "saves")){//saves
                              saves = StatData.getInt("value");
                              textView_saves.setText(String.valueOf(saves));
                            //  Log.d("TeamStat", " saves " + saves);
                          }
                          if (Objects.equals(StateName, "total_tackle")){//Tackles getTotal_tackle getwontackle total_tackle tackle_won
                              total_tackle = StatData.getInt("value");
                              textview_tackles.setText(String.valueOf(total_tackle));
                              getTotal_tackle =true;
                            //  Log.d("TeamStat", " total_tackle " + total_tackle);
                          }
                          if (Objects.equals(StateName, "won_tackle")){//Tackles getTotal_tackle getwontackle
                              tackle_won = StatData.getInt("value");
                              getwontackle = true;
                              //textview_tackles.setText(String.valueOf(total_tackle));
                              //  Log.d("TeamStat", " total_tackle " + total_tackle);
                          }

                          if (Objects.equals(StateName, "blocked_scoring_att")){//Blocked shots
                              blocked_scoring_att = StatData.getInt("value");
                              textview_bloacked_shots.setText(String.valueOf(blocked_scoring_att));
                             // Log.d("TeamStat", " blocked_scoring_att " + blocked_scoring_att);
                          }
                          if (Objects.equals(StateName, "interception")){//Intercepition
                              interception = StatData.getInt("value");
                              textview_interception.setText(String.valueOf(interception));
                             // Log.d("TeamStat", " interception " + interception);
                          }
                          if (Objects.equals(StateName, "total_clearance")){//Clearnce
                              total_clearance = StatData.getInt("value");
                              textview_clearances.setText(String.valueOf(total_clearance));
                             // Log.d("TeamStat", " total_clearance " + total_clearance);
                          }
                          if (Objects.equals(StateName, "effective_head_clearance")){//Headed clearance
                              effective_head_clearance = StatData.getInt("value");
                              textview_headedClearnce.setText(String.valueOf(effective_head_clearance));
                              //Log.d("TeamStat", " effective_head_clearance " + effective_head_clearance);
                          }

                          //aerial_won,duel_won aerail_won =0,duel_won=0  boolean getarail_wpon = false , getduel_won = false;
                          if (Objects.equals(StateName, "aerial_won")){//Errors leading to goa
                              aerail_won = StatData.getInt("value");
                              getarail_wpon =true;
                              //textview_errors.setText(String.valueOf(error_lead_to_goal));
                              //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                          }
                          //aerial_won,duel_won aerail_won =0,duel_won=0
                          if (Objects.equals(StateName, "duel_won")){//Errors leading to goa
                              duel_won = StatData.getInt("value");
                              getduel_won = true;
                              //textview_errors.setText(String.valueOf(error_lead_to_goal));
                              //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                          }

                          if (Objects.equals(StateName, "error_lead_to_goal")){//Errors leading to goa
                              error_lead_to_goal = StatData.getInt("value");
                              textview_errors.setText(String.valueOf(error_lead_to_goal));
                            //  Log.d("TeamStat", " error_lead_to_goal " + error_lead_to_goal);
                          }
                          if (Objects.equals(StateName, "own_goals")){///Own Goals
                              own_goals = StatData.getInt("value");
                              textview_owngoals.setText(String.valueOf(own_goals));
                             // Log.d("TeamStat", " own_goals " + own_goals);
                          }



                          //-------------TeamPlat //Passes per match matchplayed getTotalPasses
                          if (Objects.equals(StateName, "total_pass")){//total_pass
                              Total_passes = StatData.getInt("value");
                              tect_view_passes.setText(String.valueOf(Total_passes));
                              getTotalPasses = true;
                             // Log.d("TeamStat", " Total_passes " + Total_passes);
                          }

                          if (Objects.equals(StateName, "accurate_pass")){//Pass accuracy%
                              Total_accurate_pass = StatData.getInt("value");
                              get_accurate_pass =true;
                              //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                            // Log.d("TeamStat", " Totoal_pass_accuracy_per " + Totoal_pass_accuracy_per);
                          }
                          //cross per match TotalCrosses Total_accurate_cross getTotalCross getTotal_accurate_croos
                          if (Objects.equals(StateName, "total_cross")){//Crosses
                              TotalCrosses = StatData.getInt("value");
                              getTotalCross =true;
                              textview_crosses.setText(String.valueOf(TotalCrosses));
                             // Log.d("TeamStat", " TotalCrosses " + TotalCrosses);
                          }
                          if (Objects.equals(StateName, "accurate_cross")){//Crosses accuracy%
                              Total_accurate_cross = StatData.getInt("value");
                              getTotal_accurate_croos = true;
                              //Log.d("TeamStat", " Total_cross_accuracy_per " + Total_cross_accuracy_per);
                          }





                          //------------Disiple
                         if (Objects.equals(StateName, "total_yel_card")){//yellow cards
                              total_yel_card = StatData.getInt("value");
                              textview_yellow_cards.setText(String.valueOf(total_yel_card));
                           //   Log.d("TeamStat", " Match_losse " + total_yel_card);
                          }
                          if (Objects.equals(StateName, "total_red_card")){//Red cards
                              total_red_card = StatData.getInt("value");
                              textView_red_cards.setText(String.valueOf(String.valueOf(total_red_card)));
                           //   Log.d("TeamStat", " Match_losse " + total_red_card);
                          }
                          if (Objects.equals(StateName, "attempted_tackle_foul")){//attempted_tackle_foul
                              attempted_tackle_foul = StatData.getInt("value");
                              textview_fouls.setText(String.valueOf(attempted_tackle_foul));
                           //   Log.d("TeamStat", " Match_losse " + attempted_tackle_foul);
                          }
                          if (Objects.equals(StateName, "total_offside")){//Offsides
                              total_offside = StatData.getInt("value");
                              text_View_offsided.setText(String.valueOf(total_offside));
                           //   Log.d("TeamStat", " Match_losse " + total_offside);
                          }







                        /*
                        if (Objects.equals(StateName, "wins")){//Matches played --

                            //  Log.d("TeamStat", " Total_MathsPlayed " + Total_MathsPlayed);
                          } boolean wins,draw,lose;
                         */

                        //Match played
                        if(wins && draw && lose){
                            Total_MathsPlayed = Match_wins+Match_draw+Match_losse;
                            matchplayed =true;
                            //Toast.makeText(getContext(), "Hellpw " + Match_wins + " " + Match_draw + " " + Match_losse, Toast.LENGTH_LONG).show();
                            text_view_total_match_played.setText(String.valueOf(Total_MathsPlayed));
                        }

                        //Goal per match
                          if (matchplayed&&goalhave&&Total_MathsPlayed!=0){
                            //00text_view_goals_per_match
                              double GoalPER = getCalculatedData(Goals,Total_MathsPlayed);
                              text_view_goals_per_match.setText(String.valueOf(GoalPER));
                              //matchplayed =false;
                        }

                        //Shooting accuracy
                        if (gettotal_scoring_att && getontarget_scoring_att && Total_Shots!=0 && Total_Shots_on_Target !=0){
                         //   getCalculatedPercent
                            float Percetn = getCalculatedPercent(Total_Shots_on_Target,Total_Shots);
                            textView_shooting_accuracy.setText(String.valueOf(Math.round(Percetn)) + "%");

                        }

                        //goalConceded
                        if (goalConceded&&matchplayed){
                            double Goalconcedde = getCalculatedData(goals_conceded,Total_MathsPlayed);
                          // Log.d("TeamStat", " Total_Shots_on_Target " + matchplayed + " goalConceded " + goalConceded  + "  "+ Goalconcedde);
                            textView_goals_conceed_permatch.setText(String.valueOf(Goalconcedde));
                        }

                        //Tackles getTotal_tackle getwontackle Tackles getTotal_tackle getwontackle total_tackle
                          if (getTotal_tackle && getwontackle ){
                              //   getCalculatedPercent
                              float TacklePercetn = getCalculatedPercent(tackle_won,total_tackle);
                              textview_tacklessucces_percent.setText(String.valueOf(Math.round(TacklePercetn)) + "%");

                          }

                          //Areal won duel won //aerial_won,duel_won aerail_won =0,duel_won=0  boolean getarail_wpon = false , getduel_won = false;
                          if (getarail_wpon && getduel_won){
                              int ArealWOnData = aerail_won + duel_won;
                              textView_arialwon.setText(String.valueOf(ArealWOnData));
                          }
                          //Passes per match matchplayed getTotalPasses
                          if (matchplayed&&getTotalPasses){
                            double passperMatch = getCalculatedData(Total_passes,Total_MathsPlayed);
                              textview_passes_per_match.setText(String.valueOf(passperMatch));
                          }

                          //Pass accurracy //Passes per match matchplayed getTotalPasses Total_accurate_pass get_accurate_pass
                          if (get_accurate_pass&&getTotalPasses){
                              float acuratePassPer = getCalculatedPercent(Total_accurate_pass,Total_passes);
                              textviewpass_perce.setText(String.valueOf(Math.round(acuratePassPer))+"%");
                          }

                         //cross per match TotalCrosses Total_accurate_cross getTotalCross getTotal_accurate_croos
                          if (getTotal_accurate_croos&&getTotalCross){
                              float acurateCrossPer = getCalculatedPercent(Total_accurate_cross,TotalCrosses);
                              text_view_crooss_accur.setText(String.valueOf(Math.round(acurateCrossPer))+"%");
                          }

                        }






                }catch (Exception e){
                 //   System.out.println(e.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                    ErrorTextView.setVisibility(View.VISIBLE);
                }
                LayoutContainer.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                  swipeRefreshLayout.setRefreshing(false);
                  ErrorTextView.setVisibility(View.VISIBLE);
               // Log.d("VolleyRequestError",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", UserAgent);
                params.put("Referer", "https://www.premierleague.com/clubs");
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


    public float getCalculatedPercent(int DataStat,int Data_Denominator){
        float Ans = ((float) DataStat/(float)Data_Denominator)*100;
        return Math.round(Ans);
    }

    public double getCalculatedData(int DataStat,int Data_Denominator){
        double Ans = (double) DataStat/ (double ) Data_Denominator;
        return Ans;
    }

}
