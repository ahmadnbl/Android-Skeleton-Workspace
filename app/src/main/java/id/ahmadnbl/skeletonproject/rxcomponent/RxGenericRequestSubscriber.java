package id.ahmadnbl.skeletonproject.rxcomponent;


import id.ahmadnbl.skeletonproject.data.model.response.GenericResp;
import id.ahmadnbl.skeletonproject.interfaces.OnNetworkRequestResponse;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Created by billy on 8/12/16.
 *
 */
public class RxGenericRequestSubscriber<T extends GenericResp> {
    private static final String TAG = RxGenericRequestSubscriber.class.getSimpleName();

    private OnNetworkRequestResponse mRequestCallback;
    private int mRequestTag;
    private Consumer<T> mResponseHandler;
    private Consumer<Throwable> mExceptionHandler;


    public RxGenericRequestSubscriber(OnNetworkRequestResponse requestCallback, int requestTag) {
        this.mRequestCallback = requestCallback;
        this.mRequestTag = requestTag;

        // Handling response from server
        this.mResponseHandler = new Consumer<T>() {
            @Override
            public void accept(@NonNull T t) throws Exception {
                if(isResponseSuccess(t.getStatus())){
                    mRequestCallback.onResponseSuccess(mRequestTag, t);
                }else{
                    mRequestCallback.onResponseFailure(mRequestTag, t);
                }
            }
        };

        // Handling exception occured
        this.mExceptionHandler = new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable t) throws Exception {
                mRequestCallback.onError(mRequestTag, t);
            }
        };
    }

    public Consumer getResponseConsumer(){
        return mResponseHandler;
    }

    public Consumer getExceptionConsumer(){
        return mExceptionHandler;
    }

    private boolean isResponseSuccess(int respStatus){
        // TODO: 8/25/16 CHECK THIS AGAIN
        return respStatus == 0 ||
                respStatus == 11014 ||
                respStatus == 1;
    }
}
