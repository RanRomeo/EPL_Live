package table.football.soccer.livescore.epl.epllive.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import table.football.soccer.livescore.epl.epllive.Fragment_home.News_fragment;
import table.football.soccer.livescore.epl.epllive.Fragment_home.Video_fragment;
import table.football.soccer.livescore.epl.epllive.Fragment_home.gif_fragment;
import table.football.soccer.livescore.epl.epllive.Fragments_Fixture.Fragment_result;
import table.football.soccer.livescore.epl.epllive.Fragments_Fixture.Fragment_upcoming;


public class home_adapter  extends FragmentPagerAdapter {



    private int numofTabs;

    public home_adapter(FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return  new News_fragment();
            case 1:
                return  new Video_fragment();
            default:
                return  new News_fragment();
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }






}
