package id.ahmadnbl.skeletonproject.ui;

import android.os.Bundle;
import android.view.View;

/**
 * Created by billy on 6/15/16.
 *
 */
public abstract class BaseFragmentPager extends BaseFragment {
    private boolean _mOnProgressLoaded        = false;
    private boolean _mOnProgressVisibleToUser = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!_mOnProgressLoaded && _mOnProgressVisibleToUser) {
            onInitialPreparation();
            _mOnProgressLoaded = true;
            if(_mOnProgressVisibleToUser){
                onFragmentVisible();
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(_mOnProgressLoaded){
            onReinitiate();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        _mOnProgressVisibleToUser = isVisibleToUser;
        if(isVisibleToUser){
            if(getView()!=null && !_mOnProgressLoaded) {
                onInitialPreparation();
                _mOnProgressLoaded = true;
            }
            if(_mOnProgressLoaded && _mOnProgressVisibleToUser) {
                onFragmentVisible();
            }
        }
    }

    /**
     * This method will called when the pager adapter need to get fragment title
     * @return title in String type
     */
    public String getTitle(){
        return "";
    }

    /**
     * onInitialPreparation
     * Called when the fragment is visible only for the first time.
     */
    public void onInitialPreparation(){}

    /**
     * onReinitiate
     * Called <b>ONLY</b> when the fragment was reinitated again. Usually called when the fragment
     * was stopped and UI destroyed, and then the fragment will be shown again but still not shown.
     */
    public void onReinitiate(){}

    /**
     * onFragmentVisible
     * Called everytime when the fragment was visible in the screen.
     */
    public void onFragmentVisible(){}

    /**
     * onFragmentInvisible
     * Called everytime when the fragment was invisible.
     */
    public void onFragmentInvisible(){}
}
