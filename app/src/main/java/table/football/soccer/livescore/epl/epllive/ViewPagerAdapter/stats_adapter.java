package table.football.soccer.livescore.epl.epllive.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import table.football.soccer.livescore.epl.epllive.Fragments_stats.Fragment_head_to_head;
import table.football.soccer.livescore.epl.epllive.Fragments_stats.Fragment_live_match_stats;

/**
 * Created by Ran on 22-Aug-18.
 */

public class stats_adapter   extends FragmentPagerAdapter {

    private int numofTabs;

    public stats_adapter(FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return   new Fragment_head_to_head();
            case 1:
                return   new Fragment_live_match_stats();
            default:
                return   new Fragment_head_to_head();
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
}
