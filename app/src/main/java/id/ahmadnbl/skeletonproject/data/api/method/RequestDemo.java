package id.ahmadnbl.skeletonproject.data.api.method;


import id.ahmadnbl.skeletonproject.SkeletonApplication;
import id.ahmadnbl.skeletonproject.data.api.Api;
import id.ahmadnbl.skeletonproject.data.model.response.GenericResp;
import id.ahmadnbl.skeletonproject.interfaces.OnNetworkRequestResponse;
import id.ahmadnbl.skeletonproject.rxcomponent.RxGenericRequestSubscriber;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by billy on 8/8/16.
 *
 */
public class RequestDemo {
    public static final int TAG = -1;
    private Disposable mDisposable;
    private RxGenericRequestSubscriber subscriber;

    public RequestDemo(OnNetworkRequestResponse mRequestCallback) {
        this.subscriber = new RxGenericRequestSubscriber<>(mRequestCallback, TAG);
    }

    public void doRequest(String request){ // change this if you want: request
        Api api = SkeletonApplication.getInstance().getApi();
        Observable<GenericResp> mCall = api.demo(request); // change this: method to be called, response
        mDisposable = mCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber.getResponseConsumer(), subscriber.getExceptionConsumer());
    }

    public void cancelRequest(){
        if(mDisposable != null && !mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }
}
