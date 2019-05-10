package id.ahmadnbl.skeletonproject.ui.activity;

import android.os.Bundle;

import id.ahmadnbl.skeletonproject.R;
import id.ahmadnbl.skeletonproject.ui.BaseActivity;

/**
 * Created by billy on 19/1/17.
 *
 * Main activity that act as main page when application started for the first time.
 *
 */

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.a_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
