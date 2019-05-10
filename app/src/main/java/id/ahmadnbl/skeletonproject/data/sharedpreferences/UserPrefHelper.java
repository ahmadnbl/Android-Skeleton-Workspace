package id.ahmadnbl.skeletonproject.data.sharedpreferences;

import android.content.SharedPreferences;

import id.ahmadnbl.skeletonproject.SkeletonApplication;

/**
 * Created by billy on 8/8/16.
 * Preferences helper for user configuration
 */
public class UserPrefHelper {

    private SharedPreferences preferences;

    private void initPref() {
        preferences = SkeletonApplication.getInstance().getUserSharedPreferences();
    }

    private SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setString(UserPrefKey key, String value) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putString(key.toString(), value);
        editor.apply();
    }

    public static String getString(UserPrefKey key) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        return helper.getPreferences().getString(key.toString(), "");
    }

    public static void setInt(UserPrefKey key, int value) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putInt(key.toString(), value);
        editor.apply();
    }

    public static long getLong(UserPrefKey key) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        return helper.getPreferences().getLong(key.toString(), -1);
    }

    public static void setLong(UserPrefKey key, long value) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putLong(key.toString(), value);
        editor.apply();
    }

    public static int getInt(UserPrefKey key) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        return helper.getPreferences().getInt(key.toString(), -1);
    }

    public static void setBoolean(UserPrefKey key, boolean value) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putBoolean(key.toString(), value);
        editor.apply();
    }

    public static boolean getBoolean(UserPrefKey key) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        return helper.getPreferences().getBoolean(key.toString(), false);
    }

    public static void clearPreference(UserPrefKey key) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.remove(key.toString());
        editor.apply();
    }

    public static void clearAllPreferences() {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.clear();
        editor.apply();
    }

}
