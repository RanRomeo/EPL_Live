package table.football.soccer.livescore.epl.epllive.Fragment_team_deatials;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.LineUpRecyclerHeaderItemAdapter;
import table.football.soccer.livescore.epl.epllive.Adapters.LineUpRecyclerItemClickListener;
import table.football.soccer.livescore.epl.epllive.Adapters.SquadRecyclerHeaderItemAdapter;
import table.football.soccer.livescore.epl.epllive.Adapters.SquadRecyclerItemAdapter;
import table.football.soccer.livescore.epl.epllive.Adapters.SquadRecyclerItemClickListener;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.player_model;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_team_squad extends Fragment {



    View v;

    RecyclerView Team_RecyclerView;
    SquadRecyclerHeaderItemAdapter squadRecyclerItemAdapter;
    List<player_model> playerModelList;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_team_squad, container, false);
        swipeRefreshLayout = v.findViewById(R.id.Swipe);
        playerModelList= new ArrayList<>();
        Team_RecyclerView = v.findViewById(R.id.Team_RecyclerView);
        squadRecyclerItemAdapter = new SquadRecyclerHeaderItemAdapter(playerModelList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);


        final String TeamID = getActivity().getIntent().getExtras().getString("TeamID");

        Team_RecyclerView.setLayoutManager(layoutManager);
        Team_RecyclerView.setAdapter(squadRecyclerItemAdapter);


        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(squadRecyclerItemAdapter);
        Team_RecyclerView.addItemDecoration(headersDecor);
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(Team_RecyclerView, headersDecor);
        Team_RecyclerView.addOnItemTouchListener(touchListener);
        Team_RecyclerView.hasFixedSize();
        Team_RecyclerView.setNestedScrollingEnabled(false);

        Team_RecyclerView.addItemDecoration(new SimpleDivider(ContextCompat.getDrawable(getContext(),R.drawable.line_divider)));

        Team_RecyclerView.addOnItemTouchListener(new SquadRecyclerItemClickListener(getContext(),
                new SquadRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        squadRecyclerItemAdapter.remove(squadRecyclerItemAdapter.getItem(position));
                    }
                }));
        squadRecyclerItemAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (playerModelList.size()>0){
                    playerModelList.clear();
                }

                getTeamSqaud("https://footballapi.pulselive.com/football/teams/"+TeamID+"/compseasons/210/staff?altIds=true&compCodeForActivePlayer=EN_PR&date=2018-09-09",
                        new WebView(getContext()).getSettings().getUserAgentString());

                //new getSquadTeam(TeamID,UserAgent).execute();

            }
        });
       // String TeamID = getActivity().getIntent().getExtras().getString("TeamID");
        getTeamSqaud("https://footballapi.pulselive.com/football/teams/"+TeamID+"/compseasons/210/staff?altIds=true&compCodeForActivePlayer=EN_PR&date=2018-09-09",
                new WebView(getContext()).getSettings().getUserAgentString());
        return v;
    }



    public void getTeamSqaud(String url, final String UserAgent){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
//                swipeRefreshLayout.setRefreshing(false);
                ArrayList<player_model> data = new ArrayList<>();
                //ArrayList<player_model> data2 = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    // JSONObject TeamList = obj.getJSONObject("teamLists");
                    //   Log.d("LinupData"," " + TeamList.length());teamLists
                   // JSONArray TeamList  = obj.getJSONArray("teamLists");

                    //JSONObject TeamA = TeamList.getJSONObject(0);
                    //JSONArray TeamAlist = TeamA.getJSONArray("lineup");

                    JSONArray TeamSquad = obj.getJSONArray("players");




                    player_model item;

                    int GoalKepper         = 4;
                    int Defender           = 3;
                    int Midfielder         = 2;
                    int Forwards           = 1;


                    for (int i=0;i<TeamSquad.length();i++){
                        item = new player_model();
                        JSONObject PlayerList = TeamSquad.getJSONObject(i);
                        String playerId = String.valueOf(PlayerList.getInt("id"));
                        //String position = PlayerList.getString("matchPosition");
                        JSONObject PlayerplayingInfo = PlayerList.getJSONObject("info");
                        String position = PlayerplayingInfo.optString("position");
                        String playerPosition = PlayerplayingInfo.optString("positionInfo");
                        String playerNo = PlayerplayingInfo.optString("shirtNum");


                        if (PlayerList.has("nationalTeam")){
                            JSONObject PlayerNationInfo = PlayerList.getJSONObject("nationalTeam");
                            String playerCountry = PlayerNationInfo.optString("country");
                            item.setPlayerCountry(playerCountry); //Forward
                        }

                        JSONObject PlayerNameInfo = PlayerList.getJSONObject("name");
                        String playerName = PlayerNameInfo.getString("display");

                        //p11334.png
                        if (PlayerList.has("altIds")){
                            JSONObject PlayerImageData = PlayerList.getJSONObject("altIds");
                            String ImageID = PlayerImageData.getString("opta");
                            String ImageLinke = "https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+ImageID+".png";
                            item.setPlayerImageLink(ImageLinke);
                        }

                   //     Log.d("TeamDataHec","DAtais Stating " + "PlayerPosition: " + playerPosition
                     //           +" " + "SetMatchPosition " + ImageLinke );


                        item.setPlayerId(playerId);
                        item.setPlayerPosition(playerPosition);
                        item.setPlayerNo(playerNo);
                        item.setPlayerName(playerName);

                        item.setMatchPostion(position);

                        if (Objects.equals(position, "G")){
                            item.setPlayerType(GoalKepper);
                        }

                        if (Objects.equals(position,"D")){
                            item.setPlayerType(Defender);
                        }

                        if (Objects.equals(position,"M")){
                            item.setPlayerType(Midfielder);
                        }

                        if (Objects.equals(position,"F")){
                            item.setPlayerType(Forwards);
                        }

                        data.add(item);
                      //  Log.d("LinupDataHome"," " + playerName);

                    }

                    Collections.sort(data);

                    playerModelList.addAll(data);
                    squadRecyclerItemAdapter.notifyDataSetChanged();




                }catch (Exception e){
                    Log.d("LinupData"," " + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TeamDataHec"," " + error);
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






}

/*


      // new getSquadTeam(TeamID,UserAgent).execute();


 */

/*


 */