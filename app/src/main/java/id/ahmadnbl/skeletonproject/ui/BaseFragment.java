package id.ahmadnbl.skeletonproject.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    private boolean mAttachCallbackCalled = false;

    /**
     * This method will called when the fragment is started. Used for define
     * which layout that will be used.
     * @return res id of layout that will inflate.
     */
    protected abstract int getLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Called when using API23 and above
     * @param context fragment--activity's context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!mAttachCallbackCalled) {
            onAttachReached(context);
            mAttachCallbackCalled = true;
        }
    }

    /**
     * Called when using below of API23
     * @param activity fragment's activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(!mAttachCallbackCalled) {
            onAttachReached(activity);
            mAttachCallbackCalled = true;
        }
    }

    /**
     * Called when fragment was successfully attached to activity
     * @param context activity's context
     */
    public void onAttachReached(Context context){}

    /**
     * Setting the toolbar title. If you want to use custom toolbar. This method will
     * call {@link BaseActivity#setToolbarTitle(String)} method.
     * @param title desired toolbar title
     */
    public void setToolbarTitle(String title) {
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).setToolbarTitle(title);
        }else{
            getActivity().setTitle(title);
        }
    }
}
