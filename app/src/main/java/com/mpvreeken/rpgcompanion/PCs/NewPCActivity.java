package com.mpvreeken.rpgcompanion.PCs;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mpvreeken.rpgcompanion.PCs.PC.PC;
import com.mpvreeken.rpgcompanion.PCs.PC.PCAbility;
import com.mpvreeken.rpgcompanion.PCs.PC.PCSkill;
import com.mpvreeken.rpgcompanion.R;
import com.mpvreeken.rpgcompanion.RPGCActivity;

import java.util.ArrayList;
import java.util.List;

public class NewPCActivity extends RPGCActivity
        implements NewPCRaceClassFragment.OnFragmentInteractionListener,
            NewPCAbilitiesFragment.OnFragmentInteractionListener,
            NewPCSkillsFragment.OnFragmentInteractionListener {

    private static final int NUM_PAGES=3;
    private int currentPage;

    public PC pc;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private FrameLayout fragmentLayout;

    private List<Fragment> frags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pc);

        pc = new PC();

        /*
        viewPager = findViewById(R.id.new_pc_viewpager);
        pagerAdapter = new NewPCPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        */

        fragmentLayout = findViewById(R.id.new_pc_fragment_frame);

        NewPCRaceClassFragment raceClassFragment = NewPCRaceClassFragment.newInstance();
        NewPCAbilitiesFragment abilitiesFragment = NewPCAbilitiesFragment.newInstance();
        NewPCSkillsFragment skillsFragment = NewPCSkillsFragment.newInstance();

        currentPage=0;
        frags=new ArrayList<>();
        frags.add(raceClassFragment);
        frags.add(abilitiesFragment);
        frags.add(skillsFragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.new_pc_fragment_frame, raceClassFragment)
                .commit();

    }


    private void nextFragment() {
        if (currentPage<NUM_PAGES-1) {
            currentPage+=1;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.new_pc_fragment_frame, frags.get(currentPage))
                    .addToBackStack(null)
                    .commit();
        }
        /*
        if (viewPager.getCurrentItem()<NUM_PAGES-1) {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            //setNextPrevButtonText();
        }
        else {
            //save();
        }
        */
    }
    private void prevFragment() {
        /*
        if (currentPage>0) {
            currentPage-=1;
            getSupportFragmentManager().beginTransaction()
                    .add(frags.get(currentPage), "raceClass")
                    .addToBackStack(null)
                    .commit();
        }
        */

        onBackPressed();
        /*
        if (viewPager.getCurrentItem()>0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            //setNextPrevButtonText();
        }
        else {
            onBackPressed();
        }
        */
    }
    /*
    private void setNextPrevButtonText() {
        if (viewPager.getCurrentItem()==NUM_PAGES-1) {
            next_btn.setText("Finish");
        }
        else {
            next_btn.setText("Next");
        }
        if (viewPager.getCurrentItem()==0) {
            prev_btn.setText("Cancel");
        }
        else {
            prev_btn.setText("Previous");
        }
    }
    */



    @Override
    public void onBackPressed() {
        /*
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
        */
        super.onBackPressed();
        currentPage-=1;
    }

    @Override
    public void onFragmentInteraction(String command) {
        switch (command) {
            case "next":
                nextFragment();
                break;
            case "prev":
                prevFragment();
                break;
        }
    }




    private class NewPCPagerAdapter extends FragmentStatePagerAdapter {
        public NewPCPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("getItem", ""+position);
            switch(position) {
                case 0:
                    return NewPCRaceClassFragment.newInstance();
                case 1:
                    return NewPCAbilitiesFragment.newInstance();
                default:
                    return NewPCRaceClassFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
