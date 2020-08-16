package table.football.soccer.livescore.epl.epllive.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import table.football.soccer.livescore.epl.epllive.Fragments_Fixture.Fragment_result;
import table.football.soccer.livescore.epl.epllive.Fragments_Fixture.Fragment_upcoming;

/**
 * Created by Ran on 16-Aug-18.
 */

public class FixtureAdapter extends FragmentPagerAdapter {

    private int numofTabs;

    public FixtureAdapter(FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return  new Fragment_upcoming();
            case 1:
                return  new Fragment_result();
            default:
                return  new Fragment_upcoming();
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
}
