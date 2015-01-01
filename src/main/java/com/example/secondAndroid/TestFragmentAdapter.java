package com.example.secondAndroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.secondAndroid.fragment.DeploymentFragment;
import com.example.secondAndroid.fragment.GeoPlotFragment;
import com.example.secondAndroid.fragment.ServiceFragment;
import com.viewpagerindicator.IconPagerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/08/30
 * Time: 5:27 PM
 * To change this template use File | SettingsActivity | File Templates.
 */

public class TestFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "Service", "Deployment", "Geo-Plot Site" };
    public int mCount= CONTENT.length;
    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getIconResId(int index)
    {
           return 0;
    }

    @Override
    public Fragment getItem(int position) {

     Fragment fragment=new ServiceFragment();
        switch(position)
        {
            case 0: fragment=new ServiceFragment();
                break;
            case 1: fragment=new DeploymentFragment();
                break;
            case 2: fragment=new GeoPlotFragment();
                break;


        }

        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length].toUpperCase();
    }

    @Override
    public int getCount() {
        return mCount;
    }
}
