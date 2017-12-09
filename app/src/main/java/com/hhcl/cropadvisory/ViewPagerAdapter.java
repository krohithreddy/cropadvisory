package com.hhcl.cropadvisory;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Karthik Kumar K on 16-11-2017.
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                NewsList tab1=new NewsList();

                return tab1;
            case 1:
                Wishlist tab2=new Wishlist();
                return  tab2;
                default:
                    return null;
        }
//        if (position == 0)
//        {
//            fragment = new NewsList();
//
//        }
//        else if (position == 1)
//        {
//            fragment = new Wishlist();
//        }

       // return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      //  return super.getPageTitle(position);
        String title = null;
        if (position == 0)
        {
            title = "అన్ని కార్డులు";

        }
        else if (position == 1)
        {
            title = "ఇష్టమైన";
        }

        return title;
    }

}
