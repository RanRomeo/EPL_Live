package table.football.soccer.livescore.epl.epllive.Utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import table.football.soccer.livescore.epl.epllive.Adapters.team_stat_rec_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.team_stat_model;

/**
 * Created by Ran on 24-Sep-18.
 */

public class Get_team_stat_data {

    View v;
    Context getContext;

    public Get_team_stat_data(View v, Context getContext) {
        this.v = v;
        this.getContext = getContext;
    }

    public  void loadStatData(View v, String url){

        //Get all widgets
        final List<team_stat_model> statTeamModels = new ArrayList<>();
        RecyclerView recyclerView = v.findViewById(R.id.player_stats_rec);
        final TextView ErrorTextView = v.findViewById(R.id.ErrorTextView);
        final SwipeRefreshLayout swipeRefreshLayout = v.findViewById(R.id.Swipe);
        final team_stat_rec_adapter stat_Team_Adapter = new team_stat_rec_adapter(statTeamModels,getContext);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(stat_Team_Adapter);
        recyclerView.addItemDecoration(new SimpleDivider(ContextCompat.getDrawable(getContext,R.drawable.line_divider)));




        //volley code
         swipeRefreshLayout.setRefreshing(true);
         final String UserAgent = new WebView(getContext).getSettings().getUserAgentString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // ErrorTextView.setVisibility(View.GONE);
                 swipeRefreshLayout.setRefreshing(false);
                ArrayList<team_stat_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONObject stats = obj.getJSONObject("stats");
                    JSONArray heroArray = stats.getJSONArray("content");
                //    System.out.println(heroArray.length());
                    team_stat_model item;
                    for(int i=0;i<heroArray.length();i++){
                        JSONObject StatData = heroArray.getJSONObject(i);
                        int getValue = StatData.getInt("value");
                        //Club
                        JSONObject OwnerData = StatData.getJSONObject("owner");
                        int ClubId = OwnerData.getInt("id");
                        String Clubname = OwnerData.getString("shortName");
                        Log.d("TeamData", "onResponse: " + getValue + " " + ClubId  + " " + Clubname);
                        item = new team_stat_model();
                        item.setClubId(ClubId);
                        item.setClubName(Clubname);
                        item.setValue(getValue);


                        data.add(item);
                    }
                    statTeamModels.addAll(data);
                    stat_Team_Adapter.notifyDataSetChanged();
                }catch (Exception e){
                   System.out.println(e);
                 //   Log.d("TeamData", "onResponse: " + e);
                }
                //  swipeRefreshLayout.setRefreshing(false);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                  swipeRefreshLayout.setRefreshing(false);
              ErrorTextView.setVisibility(View.VISIBLE);
            //    Log.d("VolleyRequestError",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent",UserAgent);
                params.put("Referer", "https://www.premierleague.com/fixtures");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext);
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


}
