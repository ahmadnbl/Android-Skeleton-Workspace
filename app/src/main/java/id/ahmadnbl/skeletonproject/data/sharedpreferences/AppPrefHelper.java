package id.ahmadnbl.skeletonproject.data.sharedpreferences;

import android.content.SharedPreferences;

import id.ahmadnbl.skeletonproject.SkeletonApplication;

/**
 * Created by billy on 8/8/16.
 * Preferences helper for APPLICATION configuration
 */
public class AppPrefHelper {

    private SharedPreferences preferences;

    public AppPrefHelper() {
        this.preferences = SkeletonApplication.getInstance().getAppSharedPreferences();
    }

    private SharedPreferences getPreferences() {
        return this.preferences;
    }

    public static void setString(AppPrefKey key, String value) {
        AppPrefHelper helper = new AppPrefHelper();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putString(key.toString(), value);
        editor.apply();
    }

    public static String getString(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        return helper.getPreferences().getString(key.toString(), "");
    }

    public static void setInt(AppPrefKey key, int value) {
        AppPrefHelper helper = new AppPrefHelper();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putInt(key.toString(), value);
        editor.apply();
    }

    public static int getInt(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        return helper.getPreferences().getInt(key.toString(), -1);
    }

    public static void setFloat(AppPrefKey key, float value) {
        AppPrefHelper helper = new AppPrefHelper();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putFloat(key.toString(), value);
        editor.apply();
    }

    public static float getFloat(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        return helper.getPreferences().getFloat(key.toString(), -1);
    }

    public static void setBoolean(AppPrefKey key, boolean value) {
        AppPrefHelper helper = new AppPrefHelper();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putBoolean(key.toString(), value);
        editor.apply();
    }

    public static boolean getBoolean(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        return helper.getPreferences().getBoolean(key.toString(), false);
    }

    public static void clearPreference(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.remove(key.toString());
        editor.apply();
    }

    public static void clearAllPreferences() {
        AppPrefHelper helper = new AppPrefHelper();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.clear();
        editor.apply();
    }

}
