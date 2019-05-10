package id.ahmadnbl.skeletonproject.util;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

/**
 * Created by billy on 8/11/16.
 *
 */

public class LocationUtil {

    public static LocationSettingsRequest getLocationSettingRequest(LocationRequest locationRequest){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        return builder.build();
    }

    public static LocationRequest getLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(15000); //15 seconds
        locationRequest.setFastestInterval(10000); //10 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(10F); //10 meter
        return locationRequest;
    }

}
