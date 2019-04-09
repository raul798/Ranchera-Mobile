package ado.edu.pucmm.rancherasystem.remote;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionService {

    private static final String SHARED_PREFERENCES_FILE = "ranch_session_services";
    private static final String KEY_PASSWORD = "keyPassword";
    private static final String KEY_USER_NAME = "keyUsername";
    private static final String KEY_EMAIL = "keyEmail";
    private static final String KEY_ID= "keyId";
    private static final String KEY_DISPLAY_NAME= "displayName";

    static private SessionService instance;
    private Context context;

    public static SessionService getInstance(Context context){
        if(instance == null)
            instance = new SessionService(context);
        return instance;
    }

    private SessionService(Context context){
        this.context = context;
    }

    public String getId(){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return pref.getString(KEY_ID, null);
    }

    public String getEmail(){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return pref.getString(KEY_EMAIL, null);
    }

    public String getUsername(){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return pref.getString(KEY_USER_NAME, null);
    }

    public String getPassword(){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return pref.getString(KEY_PASSWORD, null);
    }

    public void setPassword(String password){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public void setUsername(String username){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_USER_NAME, username);
        editor.apply();
    }

    public void setId(String id){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_ID, id);
        editor.apply();
    }

    public void setEmail(String email){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getDisplay(){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return pref.getString(KEY_DISPLAY_NAME, null);
    }

    public void setDisplay(String displayName){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_DISPLAY_NAME, displayName);
        editor.apply();
    }

    public void logout(){
        setDisplay(null);
        setEmail(null);
        setId(null);
        setUsername(null);
        setPassword(null);
    }
}
