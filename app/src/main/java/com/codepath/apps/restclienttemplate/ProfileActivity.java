package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;
    String screenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //getSupportActionBar().setTitle("@" + user.getScreenName());
        //getSupportActionBar().setTitle("Kemley");
        client = TwitterApplication.getRestClient();


        user = (User) getIntent().getSerializableExtra("user");

        if(user == null) {

            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    populateProfileHeader(user);
                    screenName= getIntent().getStringExtra("screen_name");

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("ProfileActivity", "Failed");
                }
            });

        }
        else {
            getSupportActionBar().setTitle("@" + user.getScreenName());
            populateProfileHeader(user);
            screenName = user.getScreenName();
        }



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
        ImageView ivBackgroundImage = (ImageView) findViewById(R.id.ivProfileBackground);
        tvName.setText(user.getName());
        tvTagLine.setText(user.getTagline());
        Log.d("ProfileActivity", user.getScreenName());
        tvUsername.setText("@"+user.getScreenName());
        tvFollowers.setText(user.getFollowersCount() + " Following");
        tvFollowing.setText(user.getFollowingsCount() + " Followers");
        Picasso.with(this).load(user.getProfileImportUrl()).into(ivProfileImage);
        Picasso.with(this).load(user.getProfilebackgroundImportUrl()).fit().into(ivBackgroundImage);

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
