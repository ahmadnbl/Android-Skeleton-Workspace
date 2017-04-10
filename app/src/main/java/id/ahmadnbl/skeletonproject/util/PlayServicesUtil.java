package id.ahmadnbl.skeletonproject.util;

import android.app.Activity;
import android.os.Handler;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by billy on 21/11/16.
 *
 */

public class PlayServicesUtil {

    public static boolean isGooglePlayServicesAvailable(final Activity activity, final int requestCode) {
        final GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        final int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        googleApiAvailability.getErrorDialog(activity, status, requestCode).show();
                    }
                }, 1500);
            }
            return false;
        }
        return true;
    }
}
