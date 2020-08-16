package table.football.soccer.livescore.epl.epllive.Fragment_stats_player;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import table.football.soccer.livescore.epl.epllive.Adapters.Stat_player_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.Get_player_stat_data;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.stat_player_model;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_player_tackles extends Fragment {


    View v;
    RecyclerView recyclerView;
    Stat_player_adapter statPlayerAdapter;
    List<stat_player_model> statPlayerModels;
    TextView ErrorTextView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragement_player_goals, container, false);
        statPlayerModels =new ArrayList<>();
        ErrorTextView = v.findViewById(R.id.ErrorTextView);
        swipeRefreshLayout = v.findViewById(R.id.Swipe);
        recyclerView = v.findViewById(R.id.player_stats_rec);
        statPlayerAdapter = new Stat_player_adapter(statPlayerModels,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(statPlayerAdapter);
        recyclerView.addItemDecoration(new SimpleDivider(ContextCompat.getDrawable(getContext(),R.drawable.line_divider)));
       // Get_player_stat_data getPlayerStatData = new Get_player_stat_data(statPlayerModels,statPlayerAdapter,getContext());
      //  getPlayerStatData.loadStatData("https://footballapi.pulselive.com/football/stats/ranked/players/total_tackle?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                if (statPlayerModels.size()>0){
                    statPlayerModels.clear();
                }
                loadStatData("https://footballapi.pulselive.com/football/stats/ranked/players/penalty_save?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true",new WebView(getContext()).getSettings().getUserAgentString());

            }
        });
        loadStatData("https://footballapi.pulselive.com/football/stats/ranked/players/penalty_save?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true",new WebView(getContext()).getSettings().getUserAgentString());
        return v;
    }
    public  void loadStatData(String url, final String UserAgent){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // ErrorTextView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                ArrayList<stat_player_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONObject stats = obj.getJSONObject("stats");
                    JSONArray heroArray = stats.getJSONArray("content");
                  //  System.out.println(heroArray.length());
                    stat_player_model item;
                    for(int i=0;i<heroArray.length();i++){
                        JSONObject StatData = heroArray.getJSONObject(i);
                        int getValue = StatData.getInt("value");
                        //Plate
                        JSONObject OwnerData = StatData.getJSONObject("owner");
                        int PlayerId = OwnerData.getInt("id");
                        //Name
                        JSONObject PlayerNameData = OwnerData.getJSONObject("name");
                        String playerName = PlayerNameData.getString("display");
                        //Team
                        String TeamName="";
                        if (OwnerData.has("currentTeam")){
                            JSONObject TeamData = OwnerData.getJSONObject("currentTeam");
                            int TeamID = TeamData.getInt("id");
                            TeamName     = TeamData.getString("shortName");
                            Log.d("Stat_fragmenttac","Assit " + "aec");
                        }


                        //for image id
                        JSONObject ImageData = OwnerData.getJSONObject("altIds");
                        String imageid = ImageData.getString("opta");

                        item = new stat_player_model();
                        item.setClubname(TeamName);
                        item.setImageId(imageid);
                        item.setPlayerId(PlayerId);
                        item.setPlayername(playerName);
                        item.setValue(getValue);

                        data.add(item);
                    }
                    statPlayerModels.addAll(data);
                    statPlayerAdapter.notifyDataSetChanged();
                }catch (Exception e){
                  //  System.out.println(e);
                //    Log.d("Stat_fragmenttac","Assit" + e);
                }
                //  swipeRefreshLayout.setRefreshing(false);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                 swipeRefreshLayout.setRefreshing(false);
                 ErrorTextView.setVisibility(View.VISIBLE);
                //Log.d("VolleyRequestError",error.toString());
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
}
