package table.football.soccer.livescore.epl.epllive.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_clearance;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_goal;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_hit_woodwork;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_losses;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_offsides;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_own_goals;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_passes;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_red_cards;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_shots;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_tackles;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_touches;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_wins;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_team.Fragment_team_stats_yellow_cards;
import table.football.soccer.livescore.epl.epllive.Fragments_Fixture.Fragment_result;
import table.football.soccer.livescore.epl.epllive.Fragments_Fixture.Fragment_upcoming;



public class team_stat_adapter extends FragmentPagerAdapter {

    private int numofTabs;

    public team_stat_adapter(FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs = numofTabs;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return  new Fragment_team_stats_wins();
            case 1:
                return  new Fragment_team_stats_losses();
            case 2:
                return  new Fragment_team_stats_goal();
            case 3:
                return  new Fragment_team_stats_touches();
            case 4:
                return  new Fragment_team_stats_own_goals();
            case 5:
                return  new Fragment_team_stats_yellow_cards();
            case 6:
                return  new Fragment_team_stats_red_cards();
            case 7:
                return  new Fragment_team_stats_passes();
            case 8:
                return  new Fragment_team_stats_shots();
            case 9:
                return  new Fragment_team_stats_offsides();
            case 10:
                return  new Fragment_team_stats_hit_woodwork();
            case 11:
                return  new Fragment_team_stats_tackles();
            case 12:
                return  new Fragment_team_stats_clearance();
            default:
                return  new Fragment_team_stats_wins();
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
}
