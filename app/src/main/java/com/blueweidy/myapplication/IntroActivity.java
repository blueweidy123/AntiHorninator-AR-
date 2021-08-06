package com.blueweidy.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        tabIndicator = findViewById(R.id.tab_indicator);


        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("TKB", "mot thu gi do rat dai dong de test layout cua cai textview nay va no can dai hon nua van chua du .-. 1 ti nua", R.drawable.intro_tkb));
        mList.add(new ScreenItem("TTB", "mot thu gi do rat dai dong de test layout cua cai textview nay va no can dai hon nua van chua du .-. 1 ti nua", R.drawable.intro_ttb));
        mList.add(new ScreenItem("FCM", "mot thu gi do rat dai dong de test layout cua cai textview nay va no can dai hon nua van chua du .-. 1 ti nua", R.drawable.intro_focusmode));

        screenPager = findViewById(R.id.screem_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);
    }
}