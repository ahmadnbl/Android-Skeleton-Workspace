package id.ahmadnbl.skeletonproject.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

/**
 * Created by billy on 8/11/16.
 * todo: include google location services to gradle
 */

public class LocationUtil {

//    public static LocationSettingsRequest getLocationSettingRequest(LocationRequest locationRequest){
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//        return builder.build();
//    }
//
//    public static LocationRequest getLocationRequest(){
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(15000); //15 seconds
//        locationRequest.setFastestInterval(10000); //10 seconds
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setSmallestDisplacement(10F); //10 meter
//        return locationRequest;
//    }


    /**
     * Check if we had permission to acccess Location.
     * @param context Context
     * @return true: if we had permission
     *          false: otherwise
     */
    public static boolean isHasGPSPermission(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Check if GPS provider is available so we can access it.
     * @param context context from activity that want to check GPS provider availability
     * @return boolean : true if available, else false
     */
    public static boolean isGpsLocationProviderEnabled(Context context){
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Check if Network location provider is available so we can access it.
     * @param context context from activity that want to check Network location provider availability
     * @return boolean : true if available, else false
     */
    public static boolean isNetworkLocationProviderEnabled(Context context){
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * Check if location provider (location system wide service) is activated or enabled so we
     * can access any location provider that available to us.
     * @param context context from activity that want to check location provider availability
     * @return boolean : true if enabled, else false
     */
    public static boolean isLocationProviderEnabled(Context context){
        return isGpsLocationProviderEnabled(context) || isNetworkLocationProviderEnabled(context);
    }

}
