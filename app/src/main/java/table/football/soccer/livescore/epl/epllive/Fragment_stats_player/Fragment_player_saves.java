package table.football.soccer.livescore.epl.epllive.Fragment_stats_player;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import table.football.soccer.livescore.epl.epllive.Adapters.Stat_player_adapter;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.Get_player_stat_data;
import table.football.soccer.livescore.epl.epllive.Utils.SimpleDivider;
import table.football.soccer.livescore.epl.epllive.model.stat_player_model;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_player_saves extends Fragment {


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
        final Get_player_stat_data getPlayerStatData = new Get_player_stat_data(statPlayerModels,statPlayerAdapter,getContext(),swipeRefreshLayout,ErrorTextView);
        getPlayerStatData.loadStatData("https://footballapi.pulselive.com/football/stats/ranked/players/saves?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (statPlayerModels.size()>0){
                    statPlayerModels.clear();
                }

                getPlayerStatData.loadStatData("https://footballapi.pulselive.com/football/stats/ranked/players/saves?page=0&pageSize=20&compSeasons=210&comps=1&compCodeForActivePlayer=EN_PR&altIds=true");

            }
        });
        return v;
    }

}
