package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.Serializable;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kemleynieva on 6/28/16.
 */
public class ComposeActivity extends AppCompatActivity implements Serializable {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();

        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ComposedActivity", "Failed");
            }
        });
        //finish();
    }

    public void Tweet(View view) {
        EditText tweet = (EditText) findViewById(R.id.etTweet);
        String status = tweet.getText().toString();

        client.postTweet(status, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                final Intent data = new Intent();
                //Log.d("Compose", "Workedddd");
                Tweet newTweet = Tweet.fromJSON(response);
                data.putExtra("newTweet", (Serializable) newTweet);
                setResult(0, data);
                finish();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ComposeActivity", "Failed");
            }
        });
        //Create a local tweet

        //Add it to intent and set the result

    }




    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return  super.onOptionsItemSelected(item);
    }
}
