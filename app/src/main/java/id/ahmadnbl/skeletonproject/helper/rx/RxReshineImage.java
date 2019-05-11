package id.ahmadnbl.skeletonproject.helper.rx;


import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by billy on 3/3/17.
 *
 */

public class RxReshineImage {

    public interface ReshineListener{
        /**
         * This method will be called when current task is finished
         * @param targetFilePath processed file's path
         */
        void onFinished(String targetFilePath);
    }

    /**
     * Do image processing by resize it and compressing it's image quality
     * @param listener listeer when the operation occured
     */
    public static Disposable doImageReshine(final String targetFilepath, final ReshineListener listener){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
//                GraphicsHelper.resizeImage(targetFilepath);
                GraphicsHelper.rotateToExactRotaion(targetFilepath);
                emitter.onSuccess(targetFilepath);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        listener.onFinished(s);
                    }
                });


    }
}
