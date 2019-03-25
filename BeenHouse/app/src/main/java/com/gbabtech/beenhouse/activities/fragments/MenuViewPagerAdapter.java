package com.gbabtech.beenhouse.activities.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class MenuViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new LinkedList<>();
    private final List<String> fragmentTitles = new LinkedList<>();

    public MenuViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String fragmentTitle){
        fragmentList.add(fragment);
        fragmentTitles.add(fragmentTitle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}
