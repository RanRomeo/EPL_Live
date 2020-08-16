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
import android.widget.LinearLayout;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.Stat_player_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.Get_player_stat_data;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.player_model;
import table.football.soccer.livescore.epl.epllive.model.stat_player_model;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragement_player_goals extends Fragment {

    View v;
    RecyclerView recyclerView;
    Stat_player_adapter statPlayerAdapter;
    List<stat_player_model> statPlayerModels;
    TextView ErrorTextView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragement_player_goals, container, false);
        statPlayerModels =new ArrayList<>();
        recyclerView = v.findViewById(R.id.player_stats_rec);
        ErrorTextView = v.findViewById(R.id.ErrorTextView);
        swipeRefreshLayout = v.findViewById(R.id.Swipe);
        statPlayerAdapter = new Stat_player_adapter(statPlayerModels,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(statPlayerAdapter);
        recyclerView.addItemDecoration(new SimpleDivider(ContextCompat.getDrawable(getContext(),R.drawable.line_divider)));
        final Get_player_stat_data getPlayerStatData = new Get_player_stat_data(statPlayerModels,statPlayerAdapter,getContext(),swipeRefreshLayout,ErrorTextView);
        getPlayerStatData.loadStatData("https://footballapi.pulselive.com/football/stats/ranked/players/goals?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (statPlayerModels.size()>0){
                    statPlayerModels.clear();
                }

                getPlayerStatData.loadStatData("https://footballapi.pulselive.com/football/stats/ranked/players/goals?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true");
                //Log.d("stag", "onRefresh: " + statPlayerModels.size());
            }
        });


        return v;
    }




}
