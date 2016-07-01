package com.codepath.apps.restclienttemplate;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.Serializable;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kemleynieva on 6/28/16.
 */
public class ComposeActivity extends AppCompatActivity implements Serializable {
    TwitterClient client;
    User user;
    EditText tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);



        client = TwitterApplication.getRestClient();

        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfile);
                Picasso.with(ComposeActivity.this).load(user.getProfileImportUrl()).into(ivProfileImage);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ComposedActivity", "Failed");
            }
        });



        tweet = (EditText) findViewById(R.id.etTweet);


        final TextView tvCount = (TextView) findViewById(R.id.tvCount);
        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
                tvCount.setText( String.valueOf(140 - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
        tweet.addTextChangedListener(mTextEditorWatcher);
    }

    public void Tweet(View view) {
        //tweet = (EditText) findViewById(R.id.etTweet);

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


    public void onExitCompose(View view) {
        Intent data = new Intent();
        setResult(1, data);
        finish();
    }
}
