package table.football.soccer.livescore.epl.epllive.Fragments_match;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Adapters.LineUpRecyclerHeaderItemAdapter;
import table.football.soccer.livescore.epl.epllive.Adapters.LineUpRecyclerItemClickListener;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.ViewPagerAdapter.lineupAdapter;
import table.football.soccer.livescore.epl.epllive.model.player_model;


public class Fragment_lineup extends Fragment {



    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.fragment_lineup, container, false);
        TabLayout tabLayout = v.findViewById(R.id.home_tablayout);




        String   HomeTeam =  getActivity().getIntent().getExtras().getString("HomeTeam");
        String   AwayTeam =  getActivity().getIntent().getExtras().getString("AwayTeam");


        tabLayout.addTab(tabLayout.newTab().setText(HomeTeam));
        tabLayout.addTab(tabLayout.newTab().setText(AwayTeam));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = v.findViewById(R.id.home_fragment_pager);

         // getTeam("https://footballapi.pulselive.com/football/fixtures/"+"38323");
        final lineupAdapter Pageradapter = new lineupAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(Pageradapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("TabPostionlayout "+tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //viewPager.setOffscreenPageLimit(3);

        return v;
    }












}
