package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kemleynieva on 6/27/16.
 */
public class User implements Serializable {
    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImportUrl() {
        return profileImportUrl;
    }

    public String getProfilebackgroundImportUrl() {
        return profilebackgroundImportUrl;
    }

    private String name;
    private long uid;
    private  String screenName;
    private  String profileImportUrl;

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    private  String tagline;
    private int followersCount;
    private  int followingsCount;
    private String profilebackgroundImportUrl;


    public static User fromJSON(JSONObject json){
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImportUrl= json.getString("profile_image_url");
            u.profilebackgroundImportUrl = json.getString("profile_background_image_url");
            u.tagline = json.getString("description");
            u.followersCount =json.getInt("followers_count");
            u.followingsCount = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }


}
