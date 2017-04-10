package id.ahmadnbl.skeletonproject.rxcomponent;


import org.reactivestreams.Subscription;

import id.ahmadnbl.skeletonproject.data.model.response.GenericResp;
import id.ahmadnbl.skeletonproject.interfaces.OnNetworkRequestResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by billy on 8/12/16.
 *
 */
public class RxGenericRequestSubscriber<T extends GenericResp> implements Observer<T> {
    private static final String TAG = RxGenericRequestSubscriber.class.getSimpleName();

    private OnNetworkRequestResponse mRequestCallback;
    private Subscription mRequestSubscriber;
    private int mRequestTag;


    public RxGenericRequestSubscriber(OnNetworkRequestResponse mRequestCallback, Subscription requestSubscriber, int mRequestTag) {
        this.mRequestCallback = mRequestCallback;
        this.mRequestSubscriber = requestSubscriber;
        this.mRequestTag = mRequestTag;
    }

    private boolean isRequestSuccess(int respStatus){
        if(isResponseSuccess(respStatus)){
            return true;
        }else{
            return false;
        }
    }

    protected boolean isResponseSuccess(int respStatus){
        // TODO: 8/25/16 CHECK THIS AGAIN
        return respStatus == 0 ||
                respStatus == 11014 ||
                respStatus == 1;
    }

    @Override
    public void onComplete() {
        if(mRequestSubscriber!=null) {
            mRequestSubscriber.cancel();
        }
    }

    @Override
    public void onError(Throwable e) {
        mRequestCallback.onError(mRequestTag, e);
    }

    @Override
    public void onNext(T t) { // change this if you want
        if(isRequestSuccess(t.getStatus())){
            mRequestCallback.onResponseSuccess(mRequestTag, t);
        }else{
            mRequestCallback.onResponseFailure(mRequestTag, t);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }
}
