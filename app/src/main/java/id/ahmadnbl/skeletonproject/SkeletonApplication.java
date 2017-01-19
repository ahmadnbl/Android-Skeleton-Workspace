package id.ahmadnbl.skeletonproject;

import android.app.Application;
import android.content.SharedPreferences;

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
     * Getting instance of MyJNE Application
     * @return the Application object
     */
    public static SkeletonApplication getInstance(){
        return currentApplication;
    }


    // ============================== CLASS--RELATED STUFFS ===================================
    private final String TAG = "SkeletonApplication";

    private SharedPreferences mUserSharedPreferences;
    private SharedPreferences mAppSharedPreferences;


}
