package com.mpvreeken.rpgcompanion.Classes;

import android.content.Context;
import android.util.Log;

import com.mpvreeken.rpgcompanion.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sven on 11/2/2017.
 */

public class User implements Serializable{
    public String token, username, email;
    public int id, accountType;
    public Boolean isBanned, loggedIn, externalLinkAlert;

    public User() {
        this.token="";
        this.id=-1;
        this.username="";
        this.email="";
        this.accountType=5;
        this.isBanned=true;
        this.loggedIn=false;
        this.externalLinkAlert=true;
    }

    public User(String jwt) {
        this.token=jwt;
        this.id=-1;
        this.username="";
        this.email="";
        this.accountType=5;
        this.isBanned=true;
        this.loggedIn=false;
        this.externalLinkAlert=true;
    }

    public User(JSONObject j) throws Exception {
        this.token = j.getString("jwt");
        this.id = j.getInt("id");
        this.username = j.getString("username");
        this.email = j.getString("email");
        this.accountType = j.getInt("account_status");
        this.isBanned = accountType == 5;
        this.loggedIn=true;
        this.externalLinkAlert=true;
    }

    public void setExternalLinkAlert(boolean b) {
        this.externalLinkAlert = b;
    }

    public void login(String token) {
        this.token=token;
        this.loggedIn=true;
    }
    public void logout() {
        this.token="";
        this.loggedIn=false;
    }

    public void refreshToken(JSONObject j) throws Exception {
        this.token=j.getString("jwt");
        this.id=j.getInt("id");
        this.username=j.getString("username");
        this.email=j.getString("email");
        this.accountType = j.getInt("account_status");
        this.isBanned = accountType == 5;
        this.loggedIn=true;
    }

}
