package id.ahmadnbl.skeletonproject.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import id.ahmadnbl.skeletonproject.R;
import id.ahmadnbl.skeletonproject.SkeletonApplication;

/**
 * Created by billy on 8/25/16.
 * General base for every activities
 */
public abstract class BaseActivity extends AppCompatActivity{

    private boolean mIsUseCustomToolbar = false;
    private Toolbar mToolbar;

    protected abstract int getLayout();

    @Override
    protected void onStart() {
        super.onStart();
        SkeletonApplication.getInstance().increaseSessionDepth();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayout() != 0) {
            setContentView(getLayout());
        }
        ButterKnife.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SkeletonApplication.getInstance().decreaseSessionDepth();
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
     * @param toolbar desired custom toolbar
     */
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        mIsUseCustomToolbar = true;
        mToolbar = toolbar;
    }

    /**
     * Setting custom elevation into toolbar
     * @param elevation
     */
    public void setToolbarElevation(float elevation){
        if(getSupportActionBar() != null){
            getSupportActionBar().setElevation(elevation);
        }
    }

    /**
     * Setting the toolbar title. If you want to use custom toolbar, call {@link #setToolbar(Toolbar)} first.
     * @param title desired toolbar title
     */
    public void setToolbarTitle(String title) {
        if (mIsUseCustomToolbar) {
            ((TextView) mToolbar.findViewById(R.id.toolbar_title)).setText(title);
        } else if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * Set the toolbar to show the back button
     * @param val boolean value
     */
    public void showToolbarBackBtn(boolean val) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(val);
        }
    }

    /**
     * Check if current activity is has been set to translucent the status bar
     * @return the boolean value
     */
    protected boolean isTranslucentStatusBar()
    {
        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        int flags = lp.flags;
        // Here I'm comparing the binary value of Translucent Status Bar with flags in the window
        if ((flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) {
            return true;
        }
        return false;
    }

}
