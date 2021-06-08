package com.blueweidy.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blueweidy.myapplication.Adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class Fragment1 extends Fragment {
    private View myFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.frag1, container, false);
        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);
        return myFragment;
    }

    //call onActivity create method
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState != null){
            int pos = savedInstanceState.getInt("tab");
            viewPager.setCurrentItem(pos);
        }else {
            setCurDay();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setUpViewPager(final ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragments(new subjectTable(), "M0N");
        adapter.addFragments(new subjectTable2(), "TUE");
        adapter.addFragments(new subjectTable3(), "WED");
        adapter.addFragments(new subjectTable4(), "THU");
        adapter.addFragments(new subjectTable5(), "FRI");
        adapter.addFragments(new subjectTable6(), "SAT");
        adapter.addFragments(new subjectTable7(), "SUN");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Fragment1.super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("tab", tabLayout.getSelectedTabPosition());
        super.onSaveInstanceState(outState);
    }

    private void setCurDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.MONDAY:{
                viewPager.setCurrentItem(0);
                break;
            }
            case Calendar.TUESDAY:{
                viewPager.setCurrentItem(1);
                break;
            }
            case Calendar.WEDNESDAY:{
                viewPager.setCurrentItem(2);
                break;
            }
            case Calendar.THURSDAY:{
                viewPager.setCurrentItem(3);
                break;
            }
            case Calendar.FRIDAY:{
                viewPager.setCurrentItem(4);
                break;
            }
            case Calendar.SATURDAY:{
                viewPager.setCurrentItem(5);
                break;
            }
            case Calendar.SUNDAY:{
                viewPager.setCurrentItem(6);
                break;
            }
        }
    }

    @Override
    public void onStart() {
        Fragment1.super.onStart();
        //setCurDay();
    }

}
