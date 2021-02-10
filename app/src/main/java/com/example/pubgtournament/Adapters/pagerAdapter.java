package com.example.pubgtournament.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pubgtournament.Fragment.DuoFragment;
import com.example.pubgtournament.Fragment.SoloFragment;
import com.example.pubgtournament.Fragment.SquadFragment;

/**
 * Created by Darshan Soni on 03-Jun-19.
 */
public class pagerAdapter extends FragmentPagerAdapter {

    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                SoloFragment requestFragment=new SoloFragment();
                return requestFragment;

            case 1:
                DuoFragment chatsFragment=new DuoFragment();
                return chatsFragment;

            case 2:
                SquadFragment friendsFragment=new SquadFragment();
                return friendsFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:
                return "SOLO";
            case 1:
                return "DUO";
            case 2:
                return "SQUADS";

            default:
                return null;
        }

    }
}
