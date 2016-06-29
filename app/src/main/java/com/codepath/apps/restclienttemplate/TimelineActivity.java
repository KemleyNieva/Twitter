package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.MentionsTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragments;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;


public class TimelineActivity extends AppCompatActivity {
    private SmartFragmentStatePagerAdapter adapterViewPager;
    static Tweet t;
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        getSupportActionBar().setTitle("Home");
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        tabStrip.setViewPager(vpPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem ni){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void onComposeTweet(View view) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 ) {
            Log.d("loading", "yes");
            t = (Tweet) data.getSerializableExtra("newTweet");
            HomeTimelineFragment fragmentHomeTweets = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
            fragmentHomeTweets.appendTweet(t);

            //listView.setSelection(0);
            //get the tweet out of data intent
            //add it to tweetsarrayadapter
            //notify the adapter that data has changed
        }
    }

    public void onProfile(View view) {
        Intent i = new Intent(this, UsersProfileActivity.class);
        //i.putExtra("user_id", )
        startActivity(i);
    }

    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter{

        private String tabTitles[] = {"Home", "Mentions"};

        public  TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HomeTimelineFragment();
            } else if(position == 1){
                return new MentionsTimelineFragment();
            } else{
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return UserTimelineFragment.newInstance(t.getUser().getScreenName());
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return UserTimelineFragment.newInstance(t.getUser().getScreenName());
                case 2: // Fragment # 1 - This will show SecondFragment
                    return UserTimelineFragment.newInstance(t.getUser().getScreenName());
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }



}


