package table.football.soccer.livescore.epl.epllive.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import table.football.soccer.livescore.epl.epllive.Fragments_match.Fragment_commentry;
import table.football.soccer.livescore.epl.epllive.Fragments_match.Fragment_lineup;
import table.football.soccer.livescore.epl.epllive.Fragments_match.Fragment_match_stats;
import table.football.soccer.livescore.epl.epllive.Fragments_match.Fragment_overview;
import table.football.soccer.livescore.epl.epllive.Fragments_match.Fragment_photos;

/**
 * Created by Ran on 18-Aug-18.
 */

public class Match_adapter extends FragmentPagerAdapter {

    private int numofTabs;

    public Match_adapter(FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return   new Fragment_commentry();
            case 1:
                return   new Fragment_overview();
            case 2:
                return   new Fragment_lineup();
            case 3:
                return   new Fragment_match_stats();
            default:
                return   new Fragment_commentry();
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
}
