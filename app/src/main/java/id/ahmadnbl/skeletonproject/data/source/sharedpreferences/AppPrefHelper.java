package id.ahmadnbl.skeletonproject.data.source.sharedpreferences;

import android.content.SharedPreferences;
import android.util.Base64;

import id.ahmadnbl.skeletonproject.SkeletonApplication;
import id.ahmadnbl.skeletonproject.helper.Crypto;

/**
 * Created by billy on 8/8/16.
 * Preferences helper for APPLICATION configuration
 */
public class AppPrefHelper {

    private SharedPreferences preferences;

    private void initPref() {
        preferences = SkeletonApplication.Companion.getInstance().getAppSharedPreferences();
    }

    public AppPrefHelper() {
        preferences = SkeletonApplication.Companion.getInstance().getAppSharedPreferences();
    }

    private SharedPreferences getPreferences() {
        return this.preferences;
    }

    public static void setString(AppPrefKey key, String value) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();

        String b64val = Crypto.Companion.base64(value, Base64.NO_WRAP);
        String secb64Val = b64val.replace("=", "&#").replace("m", ">");
        editor.putString(key.toString(), secb64Val);

        editor.apply();
    }

    public static String getString(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        String secb64val = helper.getPreferences().getString(key.toString(), "");
        String b64Val = secb64val.replace(">", "m").replace("&#", "=");
        return Crypto.Companion.deBase64(b64Val, Base64.NO_WRAP);
    }

    public static void setInt(AppPrefKey key, int value) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putInt(key.toString(), value);
        editor.apply();
    }

    public static int getInt(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        return helper.getPreferences().getInt(key.toString(), -1);
    }

    public static void setFloat(AppPrefKey key, float value) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putFloat(key.toString(), value);
        editor.apply();
    }

    public static float getFloat(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        return helper.getPreferences().getFloat(key.toString(), -1);
    }

    public static void setBoolean(AppPrefKey key, boolean value) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putBoolean(key.toString(), value);
        editor.apply();
    }

    public static boolean getBoolean(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        return helper.getPreferences().getBoolean(key.toString(), false);
    }

    public static void clearPreference(AppPrefKey key) {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.remove(key.toString());
        editor.apply();
    }

    public static void clearAllPreferences() {
        AppPrefHelper helper = new AppPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.clear();
        editor.apply();
    }


}
