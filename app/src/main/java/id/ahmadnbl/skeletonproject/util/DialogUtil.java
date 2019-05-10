package id.ahmadnbl.skeletonproject.util;

import android.app.Dialog;
import android.content.Context;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import id.ahmadnbl.skeletonproject.R;
import id.ahmadnbl.skeletonproject.data.model.response.GenericResp;
import id.ahmadnbl.skeletonproject.ui.custom.MyJneDialog;

/**
 * Created by billy on 6/3/17.
 * Helper for managing generic dialog
 */

public class DialogUtil {

    /**
     * Generic dialog for displaying error from MyJNE generic response
     * @param context page context
     * @param data response data from MyJNE server
     */
    public static void showGenericRequestFailure(Context context, GenericResp data){
        showGenericRequestFailure(context, data.getStatus(), data.getMessage());
    }

    /**
     * Generic dialog for displaying given error code and given error message
     * @param context page context
     * @param status response status from server
     * @param message message from server
     */
    public static void showGenericRequestFailure(Context context, int status, String message){
        new MyJneDialog(context)
                .setMessage(status+": "+message)
                .setPositiveButton(context.getString(R.string.close), new MyJneDialog.PositiveButtonCallback() {
                    @Override
                    public void onClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .createDialog().show();
    }

    /**
     * Generic dialog for displaying message related to what-that-exception
     * @param context page context
     * @param e {@link Throwable} exception that occured
     */
    public static void showGenericExceptionMessage(Context context, Throwable e){
        String message = context.getString(R.string.error_on_error_network);
        if(e instanceof TimeoutException || e instanceof SocketTimeoutException){
            message = context.getString(R.string.error_network_timeout_try_again);
        }else if(e instanceof FileNotFoundException){
            message = context.getString(R.string.error_file_notfound);
        }

        new MyJneDialog(context)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.close), new MyJneDialog.PositiveButtonCallback() {
                    @Override
                    public void onClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .createDialog().show();
    }


}
