package id.ahmadnbl.skeletonproject.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.Nullable;

import id.ahmadnbl.skeletonproject.R;
import id.ahmadnbl.skeletonproject.SkeletonApplication;

/**
 * Created by billy on 8/25/16.
 * General base for every activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    private boolean mIsUseCustomToolbar = false;
    private Toolbar mToolbar;
    private boolean isActivityInBackground = false;

    protected abstract int getLayout();


    @Override
    protected void onStart() {
        super.onStart();
        SkeletonApplication.Companion.getInstance().increaseSessionDepth();
    }

    @Override
    protected void onResume() {
        isActivityInBackground = false;
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayout() != 0) {
            setContentView(getLayout());
        }
//        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        isActivityInBackground = true;
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SkeletonApplication.Companion.getInstance().decreaseSessionDepth();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Setting custom bar into current activity
     *
     * @param toolbar desired custom toolbar
     */
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        mIsUseCustomToolbar = true;
        mToolbar = toolbar;
        setToolbarElevation(0f);
        setToolbarTitle(getTitle().toString());
    }

    /**
     * Setting custom elevation into toolbar
     *
     * @param elevation
     */
    public void setToolbarElevation(float elevation) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(elevation);
        }
    }

    /**
     * Setting the toolbar title. If you want to use custom toolbar, call {@link #setToolbar(Toolbar)} first.
     *
     * @param title desired toolbar title
     */
    public void setToolbarTitle(String title) {
        if (mIsUseCustomToolbar) {
            ((TextView) mToolbar.findViewById(R.id.toolbar_title)).setText(title);
        } else if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void setToolbarTitleWhite(String title) {
        if (mIsUseCustomToolbar) {
            ((TextView) mToolbar.findViewById(R.id.toolbar_title)).setText(title);
            ((TextView) mToolbar.findViewById(R.id.toolbar_title)).setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * Set the toolbar to show the back button
     *
     * @param val boolean value
     */
    public void showToolbarBackBtn(boolean val) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(val);
        }
    }

    public void setStatusbarFullTransparent(){
        // transparent statusbar for marshmallow and above
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (decorView != null) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.grey_500_a75));
        }
    }

    /**
     * Check if current activity is in background state. Background state means that either the application
     * is minimized or the activity is behind another activity.
     *
     * @return boolean value
     */
    public boolean isActivityInBackground() {
        return isActivityInBackground;
    }

    /**
     * Check if current activity is has been setAdapter to translucent the status bar
     *
     * @return the boolean value
     */
    protected boolean isTranslucentStatusBar() {
        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        int flags = lp.flags;
        // Here I'm comparing the binary value of Translucent Status Bar with flags in the window
        if ((flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) {
            return true;
        }
        return false;
    }

    private void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
