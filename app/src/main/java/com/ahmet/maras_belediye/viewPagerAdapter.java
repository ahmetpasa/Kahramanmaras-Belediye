package com.ahmet.maras_belediye;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class viewPagerAdapter extends FragmentPagerAdapter {
    final List<Fragment> fragments=new ArrayList<>();
    List<String> basliklar=new ArrayList<>();
    public viewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new kesfedin1());
        fragments.add(new kesfedin2());
        fragments.add(new kesfedin3());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
