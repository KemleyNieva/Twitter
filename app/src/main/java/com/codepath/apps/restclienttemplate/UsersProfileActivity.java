package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kemleynieva on 6/29/16.
 */
public class UsersProfileActivity extends AppCompatActivity{
    TwitterClient client;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //getSupportActionBar().setTitle("@" + user.getScreenName());
        //getSupportActionBar().setTitle("Kemley");
        client = TwitterApplication.getRestClient();

        user = (User) getIntent().getSerializableExtra("user");
        Log.d("UserProfileActivity", String.valueOf(user.getUid()));

        client.getProfilesInfo(user.getUid(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                getSupportActionBar().setTitle("@" + user.getScreenName());
                Log.d("UserProfileActivity", "Worked");
                populateProfileHeader(user);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("UserProfileActivity", "Failed");
            }
        });

        String screenName = getIntent().getStringExtra("screen_name");
        if(savedInstanceState == null) {

            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        TextView tvUsername = (TextView) findViewById(R.id.tvAtname);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagLine.setText(user.getTagline());
        Log.d("ProfileActivity", user.getScreenName());
        tvUsername.setText("@"+user.getScreenName());
        tvFollowers.setText(user.getFollowersCount() + " Following");
        tvFollowing.setText(user.getFollowingsCount() + " Followers");
        Picasso.with(this).load(user.getProfileImportUrl()).into(ivProfileImage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return  super.onOptionsItemSelected(item);
    }
}
