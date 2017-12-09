package com.hhcl.cropadvisory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Karthik Kumar K on 17-11-2017.
 */

public class Detailedfragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPager1Adapter viewPagerAdapter;
    Integer value;
    int tabcount;
    ArrayList<DBModel> transaction;
    NavigationView navi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailedfragment, container, false);
        navi = (NavigationView) getActivity().findViewById(R.id.nav_view);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPager1Adapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setMinimumHeight(50);

        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        try {
            value = getArguments().getInt("position");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Log.d("positionint2",value.toString());
        tabLayout.setTabTextColors(Color.parseColor("#3e3e3e"), Color.parseColor("#d9535a"));

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "ramabhadra.ttf");
        for (int i=0;i<tabLayout.getTabCount();i++){
            TabLayout.Tab tab=tabLayout.getTabAt(i);

            //((TextView)).setTypeface(font);
        }
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedtab = tab.getPosition();
                // Log.d("selectedtab",String.valueOf(selectedtab));

                navi.getMenu().getItem(selectedtab).setChecked(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private class ViewPager1Adapter extends FragmentStatePagerAdapter {
        public ViewPager1Adapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    NewsList tab1 = new NewsList();
                    DBModel movie = new DBModel();
                    try {
                        tabcount = getArguments().getInt("tab1");
                        if (tabcount == 0) {
                            viewPager.setCurrentItem(tabcount);
                        }
                        Bundle args = new Bundle();
                        args.putSerializable("YourKey", transaction);
                        args.putInt("Position", value);
                        tab1.setArguments(args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return tab1;
                case 1:
                    tabcount = getArguments().getInt("tab1");
                    if (tabcount == 1) {
                        viewPager.setCurrentItem(tabcount);
                    } else {

                    }
                    Wishlist tab2 = new Wishlist();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //  return super.getPageTitle(position);
            String title = null;
            if (position == 0) {
                title = "అన్ని కార్డులు";

            } else if (position == 1) {
                title = "ఇష్టమైన";
            }

            return title;
        }
    }

}
