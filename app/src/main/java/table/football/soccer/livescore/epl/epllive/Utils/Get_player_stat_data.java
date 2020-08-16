package table.football.soccer.livescore.epl.epllive.Utils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
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

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import table.football.soccer.livescore.epl.epllive.model.stat_player_model;




public class Get_player_stat_data  {


    List<stat_player_model> statPlayerModels;
    RecyclerView.Adapter adapter;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView ErrorTextView;

    public Get_player_stat_data(List<stat_player_model> statPlayerModels, RecyclerView.Adapter adapter, Context context, SwipeRefreshLayout swipeRefreshLayout, TextView errorTextView) {
        this.statPlayerModels = statPlayerModels;
        this.adapter = adapter;
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        ErrorTextView = errorTextView;
    }

    public  void loadStatData(String url){
        final String UserAgent = new WebView(context).getSettings().getUserAgentString();
        swipeRefreshLayout.setRefreshing(true);
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
                        item = new stat_player_model();
                        JSONObject StatData = heroArray.getJSONObject(i);

                        int getValue = StatData.optInt("value");

                        JSONObject OwnerData = StatData.optJSONObject("owner");
                        int PlayerId = OwnerData.optInt("id");

                        //Name
                        JSONObject PlayerNameData = OwnerData.optJSONObject("name");
                        String playerName = PlayerNameData.optString("display");

                        //Team
                        if (OwnerData.has("currentTeam")){
                            JSONObject TeamData = OwnerData.optJSONObject("currentTeam");
                            int TeamID = TeamData.optInt("id");
                            //   int TeamID = TeamData.getInt("id");
                            String TeamName = TeamData.optString("shortName");
                            item.setClubname(TeamName);
                        }


                        //for image id
                        if (OwnerData.has("altIds")){
                            JSONObject ImageData = OwnerData.optJSONObject("altIds");
                            String imageid = ImageData.optString("opta");
                            item.setImageId(imageid);
                        }



                        //Collections.sort(DateInMillis);
                        // Log.d("jsonResult"," "+ GameID+" " + HomeTeam + " " + HomeTeamId + " " + " VS " + AwayTeam + " " + AwayTeamID+"  " +convertDate(DateInMillis,"hh:mm a"));
                        // System.out.println(GameID+" " + HomeTeam + " " + HomeTeamId + " " + " VS " + AwayTeam + " " + AwayTeamID+"  " +convertDate(DateInMillis,"hh:mm a"));



                        item.setPlayerId(PlayerId);
                        item.setPlayername(playerName);
                        item.setValue(getValue);

                        data.add(item);
                    }
                    statPlayerModels.addAll(data);
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    Log.d("OwnGoal", "onResponse: " + e.getMessage());
                }
                //  swipeRefreshLayout.setRefreshing(false);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                  swipeRefreshLayout.setRefreshing(false);
                 ErrorTextView.setVisibility(View.VISIBLE);
             //   Log.d("VolleyRequestError",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent",  UserAgent);
                params.put("Referer", "https://www.premierleague.com/fixtures");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }




}
