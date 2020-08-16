package table.football.soccer.livescore.epl.epllive.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragement_player_goals;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_penalties_saved;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_Offsides;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_appearances;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_assits;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_clean_sheets;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_fouls;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_hit_wood;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_own_goals;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_passes;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_red_cards;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_shots;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_tackles;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragment_player_touches;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragmet_player_Big_Chaces_Missed;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Fragmet_player_time_played;
import table.football.soccer.livescore.epl.epllive.Fragment_stats_player.Framgent_player_yellow_cards;

/**
 * Created by Ran on 20-Sep-18. player_stat_adapter
 */

public class player_stat_adapter   extends FragmentPagerAdapter {

    private int numofTabs;

    public player_stat_adapter(FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return   new Fragement_player_goals();
            case 1:
                return   new Fragment_player_assits();
            case 2:
                return   new Fragment_player_passes();
            case 3:
                return   new Fragment_player_shots();
            case 4:
                return   new Fragmet_player_time_played();
            case 5:
                return   new Fragment_player_Offsides();
            case 6:
                return   new Fragment_player_fouls();
            case 7:
                return   new Fragment_player_red_cards();
            case 8:
                return   new Framgent_player_yellow_cards();
            case 9:
                return   new Fragment_player_passes();
            case 10:
                return   new Fragment_player_appearances();
            case 11:
                return   new Fragment_player_tackles();
            case 12:
                return   new Fragmet_player_Big_Chaces_Missed();
            case 13:
                return   new Fragment_player_clean_sheets();
            case 14:
                return   new Fragment_player_touches();
            case 15:
                return   new Fragment_player_hit_wood();
            case 16:
                return   new Fragment_player_own_goals();
            default:
                return   new Fragement_player_goals();
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
}
