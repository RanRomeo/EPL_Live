package table.football.soccer.livescore.epl.epllive.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import table.football.soccer.livescore.epl.epllive.Fragments_lineups.Fragment_away_team;
import table.football.soccer.livescore.epl.epllive.Fragments_lineups.Fragment_home_team;

/**
 * Created by Ran on 21-Aug-18.
 */

public class lineupAdapter extends FragmentPagerAdapter {

    private int numofTabs;

    public lineupAdapter(FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new Fragment_home_team();
            case 1:
                return  new Fragment_away_team();
            default:
                return  new Fragment_home_team();
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
}
