package table.football.soccer.livescore.epl.epllive.Fragments_lineups;


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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.RequestManager;

import com.mopub.common.util.Json;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.LineUpRecyclerHeaderItemAdapter;
import table.football.soccer.livescore.epl.epllive.Adapters.LineUpRecyclerItemClickListener;
import table.football.soccer.livescore.epl.epllive.Adapters.sub_player_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.player_model;


public class Fragment_away_team extends Fragment {




     View v;
     RecyclerView TeamB_RecyclerView;
     LineUpRecyclerHeaderItemAdapter lineUpRecyclerHeaderItemAdapter;
     List<player_model> playerModelList;

    RecyclerView TeamA_subs_rec;
    sub_player_adapter subPlayerAdapter;
    List<player_model> subPLayerList;


    SwipeRefreshLayout swipeRefreshLayout;
    TextView CommentryText;

    TextView formationText;
    TextView Sub_Text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_away_team, container, false);


        formationText = v.findViewById(R.id.team_formation_text);
        formationText.setVisibility(View.GONE);

        CommentryText = v.findViewById(R.id.CommentryStatus_Text);
        swipeRefreshLayout = v.findViewById(R.id.Swipe);

        Sub_Text = v.findViewById(R.id.sub_Text);


        final String Useragent =  new WebView(getContext()).getSettings().getUserAgentString();



        swipeRefreshLayout.setRefreshing(true);


        playerModelList= new ArrayList<>();
        TeamB_RecyclerView = v.findViewById(R.id.teamB_rec);
        lineUpRecyclerHeaderItemAdapter = new LineUpRecyclerHeaderItemAdapter(playerModelList,getContext());
        TeamB_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TeamB_RecyclerView.setAdapter(lineUpRecyclerHeaderItemAdapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(lineUpRecyclerHeaderItemAdapter);
        TeamB_RecyclerView.addItemDecoration(headersDecor);
        TeamB_RecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(TeamB_RecyclerView, headersDecor);
        TeamB_RecyclerView.addOnItemTouchListener(touchListener);
        TeamB_RecyclerView.hasFixedSize();
        TeamB_RecyclerView.setNestedScrollingEnabled(false);
        TeamB_RecyclerView.addOnItemTouchListener(new LineUpRecyclerItemClickListener(getContext(), new LineUpRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                lineUpRecyclerHeaderItemAdapter.remove(lineUpRecyclerHeaderItemAdapter.getItem(position));
            }
        }));
        lineUpRecyclerHeaderItemAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        subPLayerList = new ArrayList<>();
        TeamA_subs_rec = v.findViewById(R.id.teamB_rec_sub);
        subPlayerAdapter = new sub_player_adapter(subPLayerList,getContext());
        LinearLayoutManager layoutManagerSub = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),layoutManagerSub.getOrientation());
        TeamA_subs_rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        TeamA_subs_rec.setAdapter(subPlayerAdapter);
        TeamA_subs_rec.hasFixedSize();
        TeamA_subs_rec.addItemDecoration(itemDecoration);
        TeamA_subs_rec.setNestedScrollingEnabled(false);



        //Team
       // String   HomeTeam =  getActivity().getIntent().getExtras().getString("HomeTeam");
      //  String   AwayTeam =  getActivity().getIntent().getExtras().getString("AwayTeam");
        //TeamID
      //  String   HomeTeamID =  getActivity().getIntent().getExtras().getString("HomeTeamID");
     //   String   AwayTeamID =  getActivity().getIntent().getExtras().getString("AwayTeamID");
        //TeamScore
     //   String   HomeTeamScore =  getActivity().getIntent().getExtras().getString("homeTeamScore");
      //  String   AwayTeamScore =  getActivity().getIntent().getExtras().getString("awayTeamScore");
        //MatchData
        final String MatchId     = getActivity().getIntent().getExtras().getString("MatchId");
        final String MatchStatus = getActivity().getIntent().getExtras().getString("MatchStatus");


      //  if (MatchStatus!=null&&MatchStatus.equals("L")||MatchStatus!=null&&MatchStatus.equals("C")){

            getTeam("https://footballapi.pulselive.com/football/fixtures/"+MatchId,Useragent,MatchId);
      //  }else {
      //      swipeRefreshLayout.setRefreshing(false);
      //      CommentryText.setVisibility(View.VISIBLE);
      //  }

//playerModelList subPLayerList
                 swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CommentryText.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(true);

                if (playerModelList.size()>0){
                    playerModelList.clear();
                }
                if (subPLayerList.size()>0){
                    subPLayerList.clear();
                }

                getTeam("https://footballapi.pulselive.com/football/fixtures/"+MatchId,Useragent,MatchId);

             //   if (MatchStatus!=null&&MatchStatus.equals("L")||MatchStatus!=null&&MatchStatus.equals("C")){


             //   }else {
             //       swipeRefreshLayout.setRefreshing(false);
            //        CommentryText.setVisibility(View.VISIBLE);
           //     }

            }
        });



        return v;
    }
    public void getTeam(String url, final String UserAgent, final String MatchID){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                ArrayList<player_model> data = new ArrayList<>();

                try{
                    JSONObject obj = new JSONObject(response);
                    // JSONObject TeamList = obj.getJSONObject("teamLists");
                    //   Log.d("LinupData"," " + TeamList.length());teamLists
                    JSONArray TeamList  = obj.getJSONArray("teamLists");
//formationText


                    JSONObject TeamB = TeamList.getJSONObject(1);
                    JSONArray TeamBlist = TeamB.getJSONArray("lineup");
                    player_model item;

                    int GoalKepper  =4;
                    int Defender    =3;
                    int Midfielder  =2;
                    int Forwards    =1;


                    //TeamB
                    for (int i=0;i<TeamBlist.length();i++){
                        JSONObject PlayerList = TeamBlist.getJSONObject(i);
                        String playerId = String.valueOf(PlayerList.getInt("id"));
                        //String position = PlayerList.getString("matchPosition");
                        JSONObject PlayerplayingInfo = PlayerList.getJSONObject("info");
                        String position = PlayerplayingInfo.getString("position");
                        String playerPosition = PlayerplayingInfo.getString("positionInfo");
                        JSONObject PlayerNationInfo = PlayerList.getJSONObject("nationalTeam");
                        String playerCountry = PlayerNationInfo.optString("country");
                        JSONObject PlayerNameInfo = PlayerList.getJSONObject("name");
                        String playerName = PlayerNameInfo.optString("display");
                        String playerNo = PlayerList.optString("matchShirtNumber");

                        item = new player_model();
                        item.setPlayerId(playerId);
                        item.setPlayerPosition(playerPosition);
                        item.setPlayerNo(playerNo);
                        item.setPlayerName(playerName);
                        item.setPlayerCountry(playerCountry); //Forward
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
                     //   Log.d("LinupDataAway"," " + playerName);

                    }

                    JSONObject FormationDetail = TeamB.getJSONObject("formation");
                    String FormationText = "Formation:"+FormationDetail.getString("label");
                   //
                    formationText.setText(FormationText);
                    JSONArray SubList = TeamB.getJSONArray("substitutes");
                    ArrayList<player_model> data2 = new ArrayList<>();
                    for (int i=0;i<SubList.length();i++){
                        item = new player_model();
                        JSONObject PlayerList = SubList.getJSONObject(i);
                        String playerId = String.valueOf(PlayerList.optInt("id"));
                        //String position = PlayerList.getString("matchPosition");
                        JSONObject PlayerplayingInfo = PlayerList.getJSONObject("info");
                        String position = PlayerplayingInfo.optString("position");
                        String playerPosition = PlayerplayingInfo.optString("positionInfo");
                        JSONObject PlayerNationInfo = PlayerList.getJSONObject("nationalTeam");
                       // String playerCountry = PlayerNationInfo.getString("country");

                            String playerCountry = PlayerNationInfo.optString("country");
                            item.setPlayerCountry(playerCountry);


                        JSONObject PlayerNameInfo = PlayerList.getJSONObject("name");
                        String playerName = PlayerNameInfo.optString("display");
                        String playerNo = PlayerList.optString("matchShirtNumber");


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
                        data2.add(item);
                    //    Log.d("LinupDataHome"," " + playerName);

                    }


                    if (isVisible()){
                        formationText.setVisibility(View.VISIBLE);
                        Sub_Text.setVisibility(View.VISIBLE);
                        Collections.sort(data);
                        Collections.sort(data2);
                        playerModelList.addAll(data);
                        lineUpRecyclerHeaderItemAdapter.notifyDataSetChanged();
                        subPLayerList.addAll(data2);
                        subPlayerAdapter.notifyDataSetChanged();
                    }






                }catch (Exception e){
                //    Log.d("LinupData"," " + e);
                    swipeRefreshLayout.setRefreshing(false);
                    CommentryText.setVisibility(View.VISIBLE);
                    formationText.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.d("LinupData"," " + error);
                swipeRefreshLayout.setRefreshing(false);
                CommentryText.setVisibility(View.VISIBLE);
                formationText.setVisibility(View.GONE);
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

    @Override
    public void onStop() {
        super.onStop();

    }
}
