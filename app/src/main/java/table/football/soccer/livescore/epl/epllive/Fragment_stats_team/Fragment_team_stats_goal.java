package table.football.soccer.livescore.epl.epllive.Fragment_stats_team;


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
import table.football.soccer.livescore.epl.epllive.Adapters.team_stat_rec_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.Get_team_stat_data;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.stat_player_model;
import table.football.soccer.livescore.epl.epllive.model.team_stat_model;


public class Fragment_team_stats_goal extends Fragment {






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_fragment_team_stats_goal, container, false);
        Get_team_stat_data g = new Get_team_stat_data(v,getContext());
        g.loadStatData(v,"https://footballapi.pulselive.com/football/stats/ranked/teams/goals?page=0&pageSize=20&compSeasons=210&comps=1&altIds=true");
        return v;
    }





}
