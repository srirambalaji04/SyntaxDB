package com.shiru.syntaxdb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by shiru on 2/18/2017.
 */
public class ConceptPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mFragmentManager;

    public ConceptPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
