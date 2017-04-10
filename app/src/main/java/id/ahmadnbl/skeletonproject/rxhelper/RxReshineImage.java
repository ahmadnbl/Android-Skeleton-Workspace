package id.ahmadnbl.skeletonproject.rxhelper;


import id.ahmadnbl.skeletonproject.util.GraphicsUtil;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
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
    public static void doImageReshine(final String targetFilepath, final ReshineListener listener){

        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> e) throws Exception {
                GraphicsUtil.rotateToExactRotaion(targetFilepath);
                GraphicsUtil.resizeImage(targetFilepath);
            }
        }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                listener.onFinished(s);
            }
        });
    }
}
