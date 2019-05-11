package id.ahmadnbl.skeletonproject.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.content.ContextCompat;

import id.ahmadnbl.skeletonproject.helper.LocationUtil;
import id.ahmadnbl.skeletonproject.rxbus.LocationTrackBus;


/**
 * Created by billy on 4/5/17.
 *
 */

public class LocationTrackService extends Service
        implements LocationListener {

    private final String TAG = "LocationTrackService";
    private final long locationUpdateInterval = 10 * 1000; // milliseconds
    private final float locationDistanceInterval = 20; // meters

    private final long lifeCheckInterval = 30000; // miliseconds

    private boolean isRunning;
    private LocationManager locManager;
    private Location lastLocation;
    private Handler serviceHandler;
    private Runnable serviceLifeCheckerRunable;

    @androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: invoked");
        if (!isRunning) {
            Log.d(TAG, "onStartCommand: it's first time");
            isRunning = true;
            doLocationTracking();
            runServiceLifeChecker();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: serivce will be destroyed");
        stopLocationTracking();
        if(serviceHandler!=null){
            serviceHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    /**
     * ============ LOCATION LISTENER =============
     **/

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: received new location");
        lastLocation = location;
        proccessIt(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged: invoked");
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Starting the location tracking process
     */
    private void doLocationTracking() {
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Checking the GPS settings
        if (!LocationUtil.isLocationProviderEnabled(this)) {
            Log.e(TAG, "LocSvc - #11: Both gps and network location provider is disabled.");
            stopSelf();
            return;
        }
        // Getting the location
        Log.i(TAG, "LocSvc - #1: getting location");
        getLocationFromNetwork();
        getLocatonFromGps();
        if(lastLocation != null){
            proccessIt(lastLocation);
        }
    }

    /**
     * Getting coarse location from network location service
     */
    private void getLocationFromNetwork() {
        Log.d(TAG, "getLocationFromNetwork: invoke get location from network");
        // check if app has permissions
        if (!LocationUtil.isNetworkLocationProviderEnabled(this) ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) return;
        // request location updateSave
        locManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, locationUpdateInterval, locationDistanceInterval, this);
        // if last location is null, updateSave it with last known location
        if(lastLocation == null) {
            lastLocation = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    /**
     * Getting fine location from gps location service
     */
    private void getLocatonFromGps() {
        Log.d(TAG, "getLocatonFromGps: invoke get location from gps");
        // check if app has permissions
        if (!LocationUtil.isNetworkLocationProviderEnabled(this) ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) return;
        // request location updateSave
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, locationUpdateInterval, locationDistanceInterval, this);
        // if last location is null, updateSave it with last known location
        Location gpsLastLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastLocation == null || gpsLastLocation != null) {
            lastLocation = gpsLastLocation;
        }
    }

    /**
     * Stopping the location tracking
     */
    private void stopLocationTracking() {
        locManager.removeUpdates(this);
    }

    /**
     * Process the received location updateSave
     *
     * @param location received location updateSave
     */
    private void proccessIt(Location location) {
        // ignore temporary non-gps fix
        if (shouldIgnore(location.getProvider(), System.currentTimeMillis())) {
            return;
        }
        Log.i(TAG, "processIt() run...");
        Log.d(TAG, "proccessIt: stillHasReceiver=" + String.valueOf(LocationTrackBus.instanceOf().isBusHasReceiver()));
        if(!LocationTrackBus.instanceOf().isBusHasReceiver()){
            stopSelf();
            return;
        }
        LocationTrackBus.instanceOf().sendCurrentLocation(location);
    }

    /**
     * Run the delayed procedure to check if service is still needed or not
     */
    private void runServiceLifeChecker(){
        if(serviceHandler == null){
            serviceHandler = new Handler();
        }
        if(serviceLifeCheckerRunable == null){
            serviceLifeCheckerRunable = new Runnable() {
                @Override
                public void run() {
                    if(!LocationTrackBus.instanceOf().isBusHasReceiver()){
                        LocationTrackService.this.stopSelf();
                    }else{
                        LocationTrackService.this.runServiceLifeChecker();
                    }
                }
            };
        }
        serviceHandler.postDelayed(serviceLifeCheckerRunable, lifeCheckInterval);
    }


    /** last time we got a location from the gps provider */
    private long mLastGps = 0;

    /**
     * Whether we should ignore this location.
     *
     * @param pProvider
     *            the provider that provided the location
     * @param pTime
     *            the time of the location
     * @return true if we should ignore this location, false if not
     */
    public boolean shouldIgnore(final String pProvider, final long pTime) {
        if (LocationManager.GPS_PROVIDER.equals(pProvider)) {
            mLastGps = pTime;
        } else {
            // gps wait time, 20s
            return pTime < mLastGps + 20000;
        }
        return false;
    }

}
