package table.football.soccer.livescore.epl.epllive.Fragment_stats_team;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.Utils.Get_team_stat_data;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_team_stats_wins extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_fragment_team_stats_goal, container, false);
        Get_team_stat_data g = new Get_team_stat_data(v,getContext());
        g.loadStatData(v,"https://footballapi.pulselive.com/football/stats/ranked/teams/wins?page=0&pageSize=20&compSeasons=210&comps=1&altIds=true");
        return v;
    }
}
