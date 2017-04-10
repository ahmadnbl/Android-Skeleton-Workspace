package id.ahmadnbl.skeletonproject.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import id.ahmadnbl.skeletonproject.SkeletonApplication;

/**
 * Created by billy on 29/11/16.
 *
 */

public class AppUtil {

    /**
     * Checking if the device is connected to internet
     * @return boolean value that indicated is device connected to internet or not
     */
    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) SkeletonApplication.getInstance().getApplicationContext()
                                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Hiding the keyboard
     * @param activity current opened activity
     */
    public static void hideKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
