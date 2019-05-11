package id.ahmadnbl.skeletonproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import id.ahmadnbl.skeletonproject.data.source.api.Api;
import id.ahmadnbl.skeletonproject.data.source.api.ApiService;

/**
 * Created by billy on 19/1/17.
 *
 * Base class for maintaining global application state. There we can provide our
 * implementation subclassing {@link Application} and specifying the fully-qualified name
 * of this class as the <code>"android:name"</code> attribute in our
 * AndroidManifest.xml's <code>&lt;application&gt;</code> tag. This class is instantiated
 * before any other class when the process for our application/package is created.
 *
 */

public class SkeletonApplication extends Application {

    // ============================== STATIC STUFFS ===================================
    private static SkeletonApplication currentApplication;

    /**
     * Getting instance of This Application
     * @return the Application object
     */
    public static SkeletonApplication getInstance(){
        return currentApplication;
    }


    // ============================== CLASS--RELATED STUFFS ===================================
    private final String TAG = "SkeletonApplication";

    private SharedPreferences mUserSharedPreferences;
    private SharedPreferences mAppSharedPreferences;
    private ApiService        mApiService;
    private int sessionDepth = 0;
    private boolean isInForeground = false;


    @Override
    public void onCreate() {
        super.onCreate();
        currentApplication = this;
    }

    /**
     * Increasing the session depth that indicating how many activities run
     */
    public void increaseSessionDepth(){
        Log.d(TAG, "increaseSessionDepth: increased");
        sessionDepth++;
        if(sessionDepth == 1){
            isInForeground = true;
            Log.d(TAG, "increaseSessionDepth: going foreground");
        }
    }

    /**
     * Decreasing the session depth that indicating how many activities run
     */
    public void decreaseSessionDepth(){
        Log.d(TAG, "decreaseSessionDepth: decreased");
        if (sessionDepth > 0){sessionDepth--;}
        if (sessionDepth == 0) {
            isInForeground = false;
            Log.d(TAG, "decreaseSessionDepth: going background");
        }
    }

    /**
     * Check if the app activity is in foreground
     * @return true if there's app activity in foreground
     */
    public boolean isInForeground(){
        return isInForeground;
    }

    /**
     * Get MyJne retrofit service instance
     * @return MyJne retrofit service instance
     */
    public ApiService getNetworkService(){
        if(mApiService == null){
            mApiService= new ApiService();
        }
        return mApiService;
    }

    /**
     * Get API for MyJne app: retrofit
     * @return MyJne app API
     */
    public Api getApi(){
        return getNetworkService().getApi();
    }


    // __________ APPLICATION PREFERENCES ________________

    /**
     * Initialize application-context shared preferences
     */
    public void setupAppSharedPreferences(){
        this.mAppSharedPreferences = getSharedPreferences(SkeletonApplication.class.getSimpleName()+"_A",
                Context.MODE_PRIVATE);
    }

    /**
     * Get application-context shared preferences instance
     * @return application-context spref instance
     */
    public SharedPreferences getAppSharedPreferences() {
        if(mAppSharedPreferences == null){
            setupAppSharedPreferences();
        }
        return mAppSharedPreferences;
    }

    // __________ USER PREFERENCES ________________
    /**
     * Initialize user-context shared preferences
     */
    public void setupUserSharedPreferences(){
        this.mUserSharedPreferences = getSharedPreferences(SkeletonApplication.class.getSimpleName()+"_U",
                Context.MODE_PRIVATE);
    }

    /**
     * Get user-context shared preferences instance
     * @return user-context spref instance
     */
    public SharedPreferences getUserSharedPreferences() {
        if(mUserSharedPreferences == null){
            setupUserSharedPreferences();
        }
        return mUserSharedPreferences;
    }
}
