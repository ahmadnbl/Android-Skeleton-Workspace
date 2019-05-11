package id.ahmadnbl.skeletonproject.data.source.sharedpreferences;

import android.content.SharedPreferences;
import android.util.Base64;

import id.ahmadnbl.skeletonproject.SkeletonApplication;
import id.ahmadnbl.skeletonproject.helper.Crypto;

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

        String b64val = Crypto.Companion.base64(value, Base64.NO_WRAP);
        String secb64Val = b64val.replace("=", "&#").replace("m", ">");
        editor.putString(key.toString(), secb64Val);

        editor.apply();
    }

    public static String getString(UserPrefKey key) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        String secb64val = helper.getPreferences().getString(key.toString(), "");
        String b64Val = secb64val.replace(">", "m").replace("&#", "=");
        return Crypto.Companion.deBase64(b64Val, Base64.NO_WRAP);
    }

    public static void setInt(UserPrefKey key, Integer value) {
        if(value == null) return;
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putInt(key.toString(), value);
        editor.apply();
    }

    public static float getFloat(UserPrefKey key) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        return helper.getPreferences().getFloat(key.toString(), -1);
    }

    public static void setFloat(UserPrefKey key, float value) {
        UserPrefHelper helper = new UserPrefHelper();
        helper.initPref();
        SharedPreferences.Editor editor = helper.getPreferences().edit();
        editor.putFloat(key.toString(), value);
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
