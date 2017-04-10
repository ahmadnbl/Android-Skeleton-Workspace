package id.ahmadnbl.skeletonproject.rxcomponent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.HttpException;


/**
 * Created by billy on 8/12/16.
 * Class that can be use withing on retryWhen(?) method in observable object.
 * This logic will retry the connection request until the specific condition is reached.
 */
public class RxRetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {

    private int mRetryCount     = 0;
    private int mMaxRetryCount  = 3;
    private int mDelayInMilish  = 500;

    public RxRetryWithDelay(int mMaxRetryCount, int mDelayInMilish) {
        this.mMaxRetryCount = mMaxRetryCount;
        this.mDelayInMilish = mDelayInMilish;
    }

    public RxRetryWithDelay() {
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                boolean needRetry = false;
                if (mRetryCount < mMaxRetryCount) {
                    if (throwable instanceof IOException) {
                        needRetry = true;
                    } else if (throwable instanceof HttpException) {
                        if (((HttpException) throwable).code() != 200) {
                            // for now, nothing to do
                        }
                    }
                }

                if (needRetry) {
                    mRetryCount++;
                    return Observable.timer(mDelayInMilish, TimeUnit.MILLISECONDS);
                } else {
                    return Observable.error(throwable);
                }
            }
        });
    }
}
