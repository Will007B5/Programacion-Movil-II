package com.android_twitter_show_timelines_demo.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android_twitter_show_timelines_demo.R;
import com.android_twitter_show_timelines_demo.adapter.TabViewPagerAdapter;
import com.android_twitter_show_timelines_demo.tabs.UserTimelineFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        viewPager = findViewById(R.id.twitter_view_pager);
        setupViewPager();

        tabLayout = findViewById(R.id.twitter_tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {

        //Arreglo desde string.xml
        String[] tabArray = getResources().getStringArray(R.array.tab_items);

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(UserTimelineFragment.newInstance(), tabArray[0]);
        viewPager.setAdapter(adapter);
    }
}
