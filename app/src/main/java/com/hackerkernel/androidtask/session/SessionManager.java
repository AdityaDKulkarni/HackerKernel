package com.hackerkernel.androidtask.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackerkernel.androidtask.util.Constants;

import java.util.HashMap;

public class SessionManager {

    private static SessionManager sessionManager;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public static SessionManager getInstance(Context context) {
        if(sessionManager == null){
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }

    public void createSession(String name, String email){
        editor.putString(Constants.NAME, name);
        editor.putString(Constants.EMAIL, email);
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.commit();
    }

    public HashMap<String, String> getSession(){
        HashMap<String, String> session = new HashMap<>();
        session.put(Constants.NAME, sharedPreferences.getString(Constants.NAME, null));
        session.put(Constants.EMAIL, sharedPreferences.getString(Constants.EMAIL, null));
        return session;
    }

    public void destroySession(Context context){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false);
    }
}