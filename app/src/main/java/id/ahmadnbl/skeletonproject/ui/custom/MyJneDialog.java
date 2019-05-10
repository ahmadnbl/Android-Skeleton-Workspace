package id.ahmadnbl.skeletonproject.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.InsetDrawable;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import id.ahmadnbl.skeletonproject.R;

/**
 * Created by billy on 26/10/16.
 *
 */

public class MyJneDialog {

    @BindView(R.id.dialog_generic_positive_btn)
    Button positiveButton;
    @BindView(R.id.dialog_generic_negative_btn)
    Button negativeButton;
    @BindView(R.id.dialog_generic_title_tv)
    TextView titleText;
    @BindView(R.id.dialog_generic_message_tv)
    TextView messageText;

    private Context mContext;
    private AlertDialog.Builder dialogBuilder;
    private String positiveBtnText;
    private PositiveButtonCallback positiveButtonCallback;
    private String negativeBtnText;
    private NegativeButtonCallback negativeButtonCallback;
    private String dialogMessage;
    private String dialogTitle;
    private int layoutResId;
    private boolean cancelOnTouchOutside;


    public MyJneDialog(Context context) {
        this.mContext = context;
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(null);
        positiveBtnText     = context.getString(R.string.generic_dialog_positive_button);
        positiveButtonCallback = null;
        negativeBtnText     = context.getString(R.string.generic_dialog_negative_button);
        negativeButtonCallback = null;
        dialogMessage       = "";
        layoutResId         = R.layout.d_dialog_generic;
        cancelOnTouchOutside = true;
    }


    /**
     * Setting up the POSITIVE button. If the text supplied is empty, the button will be hidden.
     * @param text text to be shown on button
     * @param onClickListener listener when the button clicked.
     * @return current instance
     */
    public MyJneDialog setPositiveButton(String text, PositiveButtonCallback onClickListener){
        if(text.isEmpty()){
            positiveButtonCallback = null;
        }else{
            positiveButtonCallback = onClickListener;
        }
        positiveBtnText = text;
        return this;
    }

    /**
     * Setting up the NEGATIVE button. If the text supplied is empty, the button will be hidden.
     * @param text text to be shown on button
     * @param onClickListener listener when the button clicked.
     * @return current instance
     */
    public MyJneDialog setNegativeButton(String text, NegativeButtonCallback onClickListener){
        if(text.isEmpty()){
            negativeButtonCallback = null;
        }else{
            negativeButtonCallback = onClickListener;
        }
        negativeBtnText = text;
        return this;
    }

    /**
     * Setting up the title that will be shown on the dialog
     * @param title title that to be shown
     * @return current instance
     */
    public MyJneDialog setTitle(String title){
        dialogTitle = title;
        return this;
    }

    /**
     * Setting up the message that will be shown on the dialog
     * @param message message that to be shown
     * @return current instance
     */
    public  MyJneDialog setMessage(String message){
        dialogMessage = message;
        return this;
    }

    /**
     * Set if the dialog can be cancelable or not
     * @param isCancelable boolean that indicate is cancelable
     * @return current instance
     */
    public MyJneDialog setCancelable(boolean isCancelable){
        dialogBuilder.setCancelable(isCancelable);
        return this;
    }

    /**
     * Set if wether the dialog can be canceled by touching outside or not
     * @param isCancelable is cancelably by touching outside
     * @return current instance
     */
    public MyJneDialog setCancelTouchOutside(boolean isCancelable){
        cancelOnTouchOutside = isCancelable;
        return this;
    }

    /**
     * Setting the cancel listener when the dialog is canceled
     * @param cancelListener the listener
     * @return current instance
     */
    public MyJneDialog setOnCanceledListener(DialogInterface.OnCancelListener cancelListener){
        dialogBuilder.setOnCancelListener(cancelListener);
        return this;
    }

    /**
     * Set custom view that to be displayed
     * @param viewResId custom view res id
     * @return current instance
     */
    public MyJneDialog setCustomView(@LayoutRes int viewResId){
        layoutResId = viewResId;
        return this;
    }

    /**
     * Build up the dialog
     * @return Created AlertDialog
     */
    public AlertDialog createDialog(){
        // Inflating view
        View customView = View.inflate(mContext, layoutResId, null);
        dialogBuilder.setView(customView);
        // Create dialog
        final AlertDialog createdDialog = dialogBuilder.create();
//        createdDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        InsetDrawable background = (InsetDrawable) createdDialog.getWindow().getDecorView().getBackground();
        background.setAlpha(0);
        createdDialog.setCanceledOnTouchOutside(cancelOnTouchOutside);


        // Check if it is using custom dialog
        // if so, don't bind and don't do anything
        // if not using custom, do binding
        if(layoutResId == R.layout.d_dialog_generic){
            ButterKnife.bind(this, customView);
            messageText.setText(dialogMessage);
            // SETUP POSITIVE BUTTON
            positiveButton.setText(positiveBtnText);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(positiveButtonCallback != null){
                        positiveButtonCallback.onClick(createdDialog);
                    }else{
                        createdDialog.dismiss();
                    }
                }
            });
            positiveButton.setVisibility(View.VISIBLE);
            //SETUP NEGATIVE BUTTON
            if(negativeButtonCallback!=null){
                negativeButton.setVisibility(View.VISIBLE);
                negativeButton.setText(negativeBtnText);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        negativeButtonCallback.onClick(createdDialog);
                    }
                });
            }else{
                negativeButton.setVisibility(View.GONE);
            }
            // Check if title is set, if so, set visibility to visible and set text
            if(dialogTitle!=null && !dialogTitle.isEmpty()){
                titleText.setVisibility(View.VISIBLE);
                titleText.setText(dialogTitle);
            }
        }

        // Ready to serve dialog
        return createdDialog;
    }


    /** ================= INTERFACES ===================== **/

    public interface PositiveButtonCallback{
        void onClick(Dialog dialog);
    }

    public interface NegativeButtonCallback{
        void onClick(Dialog dialog);
    }
}
