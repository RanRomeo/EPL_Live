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


public class Fragment_home_team extends Fragment {



    View v;
    RecyclerView TeamA_RecyclerView;
    LineUpRecyclerHeaderItemAdapter lineUpRecyclerHeaderItemAdapter;
    List<player_model> playerModelList;

    RecyclerView TeamA_subs_rec;
    sub_player_adapter subPlayerAdapter;
    List<player_model> subPLayerList;

    TextView formationText;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView CommentryText;

    TextView Sub_Text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home_team, container, false);


        CommentryText = v.findViewById(R.id.CommentryStatus_Text);
        swipeRefreshLayout = v.findViewById(R.id.Swipe);

        Sub_Text = v.findViewById(R.id.sub_Text);

        formationText = v.findViewById(R.id.team_formation_text);
        formationText.setVisibility(View.GONE);
        final String Useragent =  new WebView(getContext()).getSettings().getUserAgentString();


        swipeRefreshLayout.setRefreshing(true);

        playerModelList= new ArrayList<>();
        TeamA_RecyclerView = v.findViewById(R.id.teamA_rec);
        lineUpRecyclerHeaderItemAdapter = new LineUpRecyclerHeaderItemAdapter(playerModelList,getContext());
        TeamA_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TeamA_RecyclerView.setAdapter(lineUpRecyclerHeaderItemAdapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(lineUpRecyclerHeaderItemAdapter);
        TeamA_RecyclerView.addItemDecoration(headersDecor);
        TeamA_RecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(TeamA_RecyclerView, headersDecor);
        TeamA_RecyclerView.addOnItemTouchListener(touchListener);
        TeamA_RecyclerView.hasFixedSize();
        TeamA_RecyclerView.setNestedScrollingEnabled(false);
        TeamA_RecyclerView.addOnItemTouchListener(new LineUpRecyclerItemClickListener(getContext(), new LineUpRecyclerItemClickListener.OnItemClickListener() {
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
        TeamA_subs_rec = v.findViewById(R.id.teamA_rec_subs);
        subPlayerAdapter = new sub_player_adapter(subPLayerList,getContext());
        LinearLayoutManager layoutManagerSub = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),layoutManagerSub.getOrientation());
        TeamA_subs_rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        TeamA_subs_rec.setAdapter(subPlayerAdapter);
        TeamA_subs_rec.hasFixedSize();
        TeamA_subs_rec.addItemDecoration(itemDecoration);
        TeamA_subs_rec.setNestedScrollingEnabled(false);


       // String   HomeTeam =  getActivity().getIntent().getExtras().getString("HomeTeam");
      //  String   AwayTeam =  getActivity().getIntent().getExtras().getString("AwayTeam");
        //TeamID
      //  String   HomeTeamID =  getActivity().getIntent().getExtras().getString("HomeTeamID");
      //  String   AwayTeamID =  getActivity().getIntent().getExtras().getString("AwayTeamID");
        //TeamScore
      //  String   HomeTeamScore =  getActivity().getIntent().getExtras().getString("homeTeamScore");
      //  String   AwayTeamScore =  getActivity().getIntent().getExtras().getString("awayTeamScore");
        //MatchData
        final String MatchId     = getActivity().getIntent().getExtras().getString("MatchId");
        final String MatchStatus = getActivity().getIntent().getExtras().getString("MatchStatus");


     //   if (MatchStatus!=null&&MatchStatus.equals("L")||MatchStatus!=null&&MatchStatus.equals("C")){

            getTeam("https://footballapi.pulselive.com/football/fixtures/"+MatchId,Useragent);
     //   }else {
      //      swipeRefreshLayout.setRefreshing(false);
      //      CommentryText.setVisibility(View.VISIBLE);
     //   }

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
              //  if (MatchStatus!=null&&MatchStatus.equals("L")||MatchStatus!=null&&MatchStatus.equals("C")){

                    getTeam("https://footballapi.pulselive.com/football/fixtures/"+MatchId,Useragent);
             //   }else {
             //       swipeRefreshLayout.setRefreshing(false);
             //       CommentryText.setVisibility(View.VISIBLE);
             //   }

            }
        });

       // getTeam("https://footballapi.pulselive.com/football/fixtures/38323");
        return v;
    }


    public void getTeam(String url,final String UserAgent){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                ArrayList<player_model> data = new ArrayList<>();
                //ArrayList<player_model> data2 = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    // JSONObject TeamList = obj.getJSONObject("teamLists");
                    //   Log.d("LinupData"," " + TeamList.length());teamLists
                    JSONArray TeamList  = obj.getJSONArray("teamLists");

                    JSONObject TeamA = TeamList.getJSONObject(0);
                    JSONArray TeamAlist = TeamA.getJSONArray("lineup");


                    player_model item;

                    int GoalKepper  =4;
                    int Defender    =3;
                    int Midfielder  =2;
                    int Forwards    =1;


                    for (int i=0;i<TeamAlist.length();i++){
                        item = new player_model();
                        JSONObject PlayerList = TeamAlist.getJSONObject(i);
                        String playerId = String.valueOf(PlayerList.getInt("id"));
                        //String position = PlayerList.getString("matchPosition");
                        JSONObject PlayerplayingInfo = PlayerList.optJSONObject("info");
                        String position = PlayerplayingInfo.optString("position");
                        String playerPosition = PlayerplayingInfo.optString("positionInfo");
                        JSONObject PlayerNationInfo = PlayerList.optJSONObject("nationalTeam");
                        if(PlayerNationInfo.has("country"));{
                            String playerCountry = PlayerNationInfo.optString("country");
                            item.setPlayerCountry(playerCountry);
                        }

                        JSONObject PlayerNameInfo = PlayerList.optJSONObject("name");
                        String playerName = PlayerNameInfo.optString("display");
                        String playerNo = PlayerList.optString("matchShirtNumber");

                     //   Log.d("TeamDataHec"," " + "PlayerPosition: " + playerPosition
                     //           +" " + "SetMatchPosition " + position );


                        item.setPlayerId(playerId);
                        item.setPlayerPosition(playerPosition);
                        item.setPlayerNo(playerNo);
                        item.setPlayerName(playerName);
                        //Forward
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
                    JSONObject FormationDetail = TeamA.getJSONObject("formation");
                    String FormationText = "Formation:"+FormationDetail.getString("label");
                    //
                    formationText.setText(FormationText);


                    JSONArray SubList = TeamA.getJSONArray("substitutes");
                    ArrayList<player_model> data2 = new ArrayList<>();
                    for (int i=0;i<SubList.length();i++){
                        item = new player_model();
                        JSONObject PlayerList = SubList.getJSONObject(i);

                          String playerId = String.valueOf(PlayerList.getInt("id"));
                        //String position = PlayerList.getString("matchPosition");
                        JSONObject PlayerplayingInfo = PlayerList.getJSONObject("info");


                        String position = PlayerplayingInfo.optString("position");
                        String playerPosition = PlayerplayingInfo.optString("positionInfo");
                        JSONObject PlayerNationInfo = PlayerList.getJSONObject("nationalTeam");
                        if(PlayerNationInfo.has("country")){
                            String playerCountry = PlayerNationInfo.optString("country");
                            item.setPlayerCountry(playerCountry);
                        }

                        JSONObject PlayerNameInfo = PlayerList.optJSONObject("name");
                        String playerName = PlayerNameInfo.optString("display");
                        String playerNo = PlayerList.optString("matchShirtNumber");


                        item.setPlayerId(playerId);
                        item.setPlayerPosition(playerPosition);
                        item.setPlayerNo(playerNo);
                        item.setPlayerName(playerName);
                         //Forward
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
                     //   Log.d("LinupDataHome"," " + playerName);

                    }

                    Sub_Text.setVisibility(View.VISIBLE);
                    formationText.setVisibility(View.VISIBLE);
                    Collections.sort(data);
                    Collections.sort(data2);
                    if (isVisible()){
                        playerModelList.addAll(data);
                        lineUpRecyclerHeaderItemAdapter.notifyDataSetChanged();
                        subPLayerList.addAll(data2);
                        subPlayerAdapter.notifyDataSetChanged();
                    }






                }catch (Exception e){
                  Log.d("LinupData"," " + e);
                    swipeRefreshLayout.setRefreshing(false);
                    CommentryText.setVisibility(View.VISIBLE);
                    formationText.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Log.d("LinupData","onErrorResponse " + error);
                swipeRefreshLayout.setRefreshing(false);
                CommentryText.setVisibility(View.VISIBLE);
                formationText.setVisibility(View.GONE);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", UserAgent);
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

    @Override
    public void onStop() {
        super.onStop();

    }
}
