package table.football.soccer.livescore.epl.epllive.Fragment_team_deatials;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.Team_fixture_Adpter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.match_model;


public class Fragement_team_result extends Fragment {



    View v;
    List<match_model> listItems;
    RecyclerView recyclerView;
    String url = "https://footballapi.pulselive.com/football/fixtures?comps=1&compSeasons=210&teams=1,127,131,43,46,4,6,7,34,159,26,10,11,12,23,20,21,33,25,38&page=0&pageSize=380&sort=desc&statuses=C&altIds=true";
    SwipeRefreshLayout swipeRefreshLayout;
    Team_fixture_Adpter teamFixtureAdpter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragement_team_result, container, false);
        recyclerView = v.findViewById(R.id.Team_Result_recyclerView);
        listItems = new ArrayList<>();
        teamFixtureAdpter = new Team_fixture_Adpter(listItems,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        // DividerItemDecoration itemDecoration = new DividerItemDecoration(v.getContext(),linearLayoutManager.getOrientation());
        //recyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(teamFixtureAdpter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new SimpleDivider(ContextCompat.getDrawable(getContext(),R.drawable.line_divider)));
        String TeamID = getActivity().getIntent().getExtras().getString("TeamID");
        loadHeroList(TeamID,new WebView(getContext()).getSettings().getUserAgentString());
        return v;

    }




    private void loadHeroList(final String TeamID, final String UserAgent){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                  //  System.out.println(heroArray.length());

                    match_model item;
                    for(int i=0;i<heroArray.length();i++){
                        JSONObject gameData = heroArray.getJSONObject(i);
                        String GameID = String.valueOf(gameData.getInt("id"));
                        String matchStatus = gameData.getString("status");
                        String matchPhase = gameData.getString("phase");

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






                        //DAte get from json
                        JSONObject DateDetail = gameData.getJSONObject("kickoff");
                        String DateInMillis = String.valueOf(DateDetail.getLong("millis"));
                        //Collections.sort(DateInMillis);
                        item = new match_model();
                        JSONObject GroundName = gameData.getJSONObject("ground");
                        String cityName = GroundName.getString("city");
                        String StadName= GroundName.getString("name");


                        //item.setHomeTeam(HomeTeam);
                      ///  item.setAwayTeam(AwayTeam);

                       // item.setHomeTeamID(HomeTeamId);
                       // item.setAwayTeamID(AwayTeamID);//score
                       // item.setMatchStatus(matchStatus);

                        if (matchStatus.equals("C")){
                            String HomeTeamSccore = String.valueOf(TeamDetail.getInt("score"));
                            String AwayTeamScore = String.valueOf(TeamDetail1.getInt("score"));
//score
                      //      item.setHomeTeamScore(HomeTeamSccore);
                       //     item.setAwayTeamScore(AwayTeamScore);
                            if (Objects.equals(HomeTeamId, TeamID) || Objects.equals(AwayTeamID, TeamID)){
                                item.setHomeTeam(HomeTeam);
                                item.setAwayTeam(AwayTeam);

                                item.setHomeTeamID(HomeTeamId);
                                item.setAwayTeamID(AwayTeamID);
                                item.setMathcId(GameID);
                                // item.setGameTimePast();
                                item.setDate(getReadDAte(convertDate(DateInMillis,"dd/MM/yyyy")));
                                item.setTime(HomeTeamSccore+" : "+AwayTeamScore);
                                item.setCityName(cityName);
                                item.setStadiumName(StadName);
                                //item.setMillies(DateDetail.getLong("millis"));
                                item.setMillies(MillisDate(convertDate(DateInMillis,"dd/MM/yyyy")));
                                item.setMatchStatus(matchStatus);
                                data.add(item);
                            }

                        }

                        /*

                         */


                       // data.add(item);
                    }
                    listItems.addAll(data);
                    teamFixtureAdpter.notifyDataSetChanged();
                }catch (Exception e){
                    System.out.println(e);
                }
              //  swipeRefreshLayout.setRefreshing(false);
//


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
               // Log.d("VolleyRequestError",error.toString());
              //  swipeRefreshLayout.setRefreshing(false);
                ///.//ErrorTextView.setVisibility(View.VISIBLE);
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


    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }


    public String getReadDAte(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        String readableFormate=null;
        //int month;
        try{
            date = sdf.parse(dateString);
            // int day = date.getDay();
            //  int year = date.getYear();
            // int month = date.getMonth();
//Initialize your Date however you like it.
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            String monthName = null;
            switch (month){
                case 1:monthName="January";
                    break;
                case 2:monthName="February";
                    break;
                case 3:monthName="March";
                    break;
                case 4:monthName="April";
                    break;
                case 5:monthName="May";
                    break;
                case 6:monthName="June";
                    break;
                case 7:monthName="July";
                    break;
                case 8:monthName="August";
                    break;
                case 9:monthName="September";
                    break;
                case 10:monthName="October";
                    break;
                case 11:monthName="November";
                    break;
                case 12:monthName="December";
                    break;
            }

            readableFormate = day+" "+monthName+" "+year;

            // Log.d("MetaDataDay"," " + readableFormate);
        }catch (Exception e){
            Log.d("MetaDataDay"," "  + e);
        }

        return readableFormate;
    }


    public Long MillisDate(String dateINDate){
        String myDate = dateINDate;
        //Log.d("MIllisDate"," " + myDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        long millis = 0;
        try {
            date = sdf.parse(myDate);
            millis = date.getTime();
            int monthnum = date.getMonth();
            // Toast.makeText(getContext(), " " + monthnum, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  millis;
    }


}
