package id.ahmadnbl.skeletonproject

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.squareup.leakcanary.LeakCanary

import id.ahmadnbl.skeletonproject.data.source.api.Api
import id.ahmadnbl.skeletonproject.data.source.api.ApiService

/**
 * Created by billy on 19/1/17.
 *
 * Base class for maintaining global application state. There we can provide our
 * implementation subclassing [Application] and specifying the fully-qualified name
 * of this class as the `"android:name"` attribute in our
 * AndroidManifest.xml's `<application>` tag. This class is instantiated
 * before any other class when the process for our application/package is created.
 *
 */

class SkeletonApplication : Application() {

    // ============================== STATIC STUFFS ===================================

    companion object {
        private lateinit var instance: SkeletonApplication

        /**
         * Getting instance of This Application
         * @return the Application object
         */
        public fun getInstance(): SkeletonApplication = instance // why force unwrap? because in logic it will not be null
    }


    // ============================== OBJECT--RELATED STUFFS ===================================

    private val TAG = this::class.java.simpleName

    private var mUserSharedPreferences: SharedPreferences? = null
    private var mAppSharedPreferences: SharedPreferences? = null
    private var mApiService: ApiService? = null
    private var sessionDepth = 0
    private var isInForeground = false

    init {
        instance = this
    }


    override fun attachBaseContext(base: Context?) {
        setupAppSharedPreferences(base)
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this)
    }


    /**
     * Increasing the session depth that indicating how many activities run
     */
    fun increaseSessionDepth() {
        Log.d(TAG, "increaseSessionDepth: increased")
        sessionDepth++
        if (sessionDepth == 1) {
            isInForeground = true
            Log.d(TAG, "increaseSessionDepth: going foreground")
        }
    }

    /**
     * Decreasing the session depth that indicating how many activities run
     */
    fun decreaseSessionDepth() {
        Log.d(TAG, "decreaseSessionDepth: decreased")
        if (sessionDepth > 0) {
            sessionDepth--
        }
        if (sessionDepth == 0) {
            isInForeground = false
            Log.d(TAG, "decreaseSessionDepth: going background")
        }
    }

    /**
     * Check if the app activity is in foreground
     * @return true if there's app activity in foreground
     */
    fun isInForeground(): Boolean {
        return isInForeground
    }

    /**
     * Get retrofit service instance
     * @return retrofit service instance
     */
    fun getNetworkService(): ApiService {
        if (mApiService == null) {
            mApiService = ApiService()
        }
        return mApiService!!
    }

    /**
     * Get API for app: retrofit
     * @return app API
     */
    fun getApi(): Api {
        return getNetworkService().api
    }


    // __________ APPLICATION PREFERENCES ________________

    /**
     * Initialize application-context shared preferences
     */
    fun setupAppSharedPreferences(context: Context? = null) {
        val contextUnwrap = context ?: this
        this.mAppSharedPreferences = contextUnwrap.getSharedPreferences(this::class.java.simpleName + "_A",
                Context.MODE_PRIVATE)
    }

    /**
     * Get application-context shared preferences instance
     * @return application-context spref instance
     */
    fun getAppSharedPreferences(): SharedPreferences {
        if (mAppSharedPreferences == null) {
            setupAppSharedPreferences()
        }
        return mAppSharedPreferences!! // why force unwrap? because it has been referenced to exist-object before
    }


    // __________ USER PREFERENCES ________________
    /**
     * Initialize user-context shared preferences
     */
    fun setupUserSharedPreferences() {
        this.mUserSharedPreferences = getSharedPreferences(this::class.java.simpleName + "_U",
                Context.MODE_PRIVATE)
    }

    /**
     * Get user-context shared preferences instance
     * @return user-context spref instance
     */
    fun getUserSharedPreferences(): SharedPreferences {
        if (mUserSharedPreferences == null) {
            setupUserSharedPreferences()
        }
        return mUserSharedPreferences!! // why force unwrap? because it has been referenced to exist-object before
    }
}
