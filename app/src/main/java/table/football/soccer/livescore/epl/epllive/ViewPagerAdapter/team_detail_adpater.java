package table.football.soccer.livescore.epl.epllive.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import table.football.soccer.livescore.epl.epllive.Fragment_team_deatials.Fragement_team_result;
import table.football.soccer.livescore.epl.epllive.Fragment_team_deatials.Fragment_team_fixtures;
import table.football.soccer.livescore.epl.epllive.Fragment_team_deatials.Fragment_team_overview;
import table.football.soccer.livescore.epl.epllive.Fragment_team_deatials.Fragment_team_squad;
import table.football.soccer.livescore.epl.epllive.Fragment_team_deatials.Fragment_team_stats;


/**
 * Created by Ran on 03-Sep-18.
 */

public class team_detail_adpater  extends FragmentPagerAdapter {

    private int numofTabs;

    public team_detail_adpater(FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return   new Fragment_team_overview();
            case 1:
                return   new Fragment_team_squad();
            case 2:
                return   new Fragment_team_fixtures();
            case 3:
                return   new Fragement_team_result();
            case 4:
                return   new Fragment_team_stats();
            default:
                return   new Fragment_team_overview();
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }



}
