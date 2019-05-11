package id.ahmadnbl.skeletonproject.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.InsetDrawable
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import id.ahmadnbl.skeletonproject.R
import kotlinx.android.synthetic.main.d_dialog_generic.view.*


/**
 * Created by billy on 8/8/16.
 * Generic Dialog using builder-pattern
 */

class GenericDialog(private val context: Context) {


    companion object {
        fun createLoadingDialog(context: Context, withMessage: String? = null): AlertDialog {
            val dialogBuilder = GenericDialog (context)
                    .setCustomView(R.layout.d_dialog_loading)
                    .setCancelable(false)
                    .setCancelTouchOutside(false)
            val view = dialogBuilder.createView()
            val dialog = dialogBuilder.createDialog()
            view?.findViewById<TextView>(R.id.dialog_loading_message_tv)?.text = withMessage

            return dialog
        }

        fun createMessageDialog(context: Context, title: String, withMessage: String, closeText: String?): AlertDialog {
            val dialogBuilder = GenericDialog (context)
                    .setCustomView(R.layout.d_generic_message)
                    .setCancelable(false)
                    .setCancelTouchOutside(false)
            val view = dialogBuilder.createView()
            val dialog = dialogBuilder.createDialog()
            view?.findViewById<TextView>(R.id.dialog_generic_message_title_tv)?.text = title
            view?.findViewById<TextView>(R.id.dialog_generic_message_tv)?.text = withMessage
            closeText?.let {
                view?.findViewById<Button>(R.id.dialog_generic_message_close_btn)?.text = it
            }
            view?.findViewById<Button>(R.id.dialog_generic_message_close_btn)?.setOnClickListener {
                dialog.dismiss()
            }

            return dialog
        }
    }


    private var dialogView: View? = null
    private var positiveButton: Button? = null
    private var negativeButton: Button? = null
    private var titleText: TextView? = null
    private var messageText: TextView? = null

    private var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
    private var positiveBtnText: String? = null
    private var positiveButtonCallback: PositiveButtonCallback? = null
    private var negativeBtnText: String? = null
    private var negativeButtonCallback: NegativeButtonCallback? = null
    private var dialogMessage: String? = null
    private var dialogTitle: String? = null
    private var layoutResId: Int = 0
    private var cancelOnTouchOutside: Boolean = false

    init {
        dialogBuilder.setTitle(null)
        positiveBtnText = context.getString(R.string.generic_dialog_positive_button)
        positiveButtonCallback = null
        negativeBtnText = context.getString(R.string.generic_dialog_negative_button)
        negativeButtonCallback = null
        dialogMessage = ""
        layoutResId = R.layout.d_dialog_generic
        cancelOnTouchOutside = true
    }


    /**
     * Setting up the POSITIVE button. If the text supplied is empty, the button will be hidden.
     * @param text text to be shown on button
     * @param onClickListener listener when the button clicked.
     * @return current instance
     */
    fun setPositiveButton(text: String, onClickListener: PositiveButtonCallback): GenericDialog {
        if (text.isEmpty()) {
            positiveButtonCallback = null
        } else {
            positiveButtonCallback = onClickListener
        }
        positiveBtnText = text
        return this
    }

    /**
     * Setting up the NEGATIVE button. If the text supplied is empty, the button will be hidden.
     * @param text text to be shown on button
     * @param onClickListener listener when the button clicked.
     * @return current instance
     */
    fun setNegativeButton(text: String, onClickListener: NegativeButtonCallback): GenericDialog {
        if (text.isEmpty()) {
            negativeButtonCallback = null
        } else {
            negativeButtonCallback = onClickListener
        }
        negativeBtnText = text
        return this
    }

    /**
     * Make the dialog show the buttons layout in vertical order
     * @param value true if want to be in vertical order
     */
    fun setVerticalButtonLayout(value: Boolean): GenericDialog {
        if(value){
            layoutResId = R.layout.d_dialog_generic_vertical_buttons
        } else {
            layoutResId = R.layout.d_dialog_generic
        }
        return this
    }

    /**
     * Setting up the title that will be shown on the dialog
     * @param title title that to be shown
     * @return current instance
     */
    fun setTitle(title: String): GenericDialog {
        dialogTitle = title
        return this
    }

    /**
     * Setting up the message that will be shown on the dialog
     * @param message message that to be shown
     * @return current instance
     */
    fun setMessage(message: String): GenericDialog {
        dialogMessage = message
        return this
    }

    /**
     * Set if the dialog can be cancelable or not
     * @param isCancelable boolean that indicate is cancelable
     * @return current instance
     */
    fun setCancelable(isCancelable: Boolean): GenericDialog {
        dialogBuilder.setCancelable(isCancelable)
        return this
    }

    /**
     * Set if wether the dialog can be canceled by touching outside or not
     * @param isCancelable is cancelably by touching outside
     * @return current instance
     */
    fun setCancelTouchOutside(isCancelable: Boolean): GenericDialog {
        cancelOnTouchOutside = isCancelable
        return this
    }

    /**
     * Setting the cancel listener when the dialog is canceled
     * @param cancelListener the listener
     * @return current instance
     */
    fun setOnCanceledListener(cancelListener: DialogInterface.OnCancelListener): GenericDialog {
        dialogBuilder.setOnCancelListener(cancelListener)
        return this
    }

    /**
     * Set custom view that to be displayed
     * @param viewResId custom view res id
     * @return current instance
     */
    fun setCustomView(@LayoutRes viewResId: Int): GenericDialog {
        layoutResId = viewResId
        return this
    }

    fun createView(): View? {
        dialogView = View.inflate(context, layoutResId, null)
        return dialogView
    }

    /**
     * Build up the dialog
     * @return Created AlertDialog
     */
    fun createDialog(): AlertDialog {
        // Inflating view
        if (dialogView == null){
            createView()
        }
        dialogBuilder.setView(dialogView)
        // Create dialog
        val createdDialog = dialogBuilder.create()
        //        createdDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        val background = createdDialog.window?.decorView?.background as? InsetDrawable
        background?.alpha = 0
        createdDialog.setCanceledOnTouchOutside(cancelOnTouchOutside)


        // Check if it is using custom dialog
        // if so, don't bind and don't do anything
        // if not using custom, do binding
        if (layoutResId == R.layout.d_dialog_generic || layoutResId == R.layout.d_dialog_generic_vertical_buttons) {
            bind(dialogView)
            messageText!!.text = dialogMessage
            // SETUP POSITIVE BUTTON
            positiveButton!!.text = positiveBtnText
            positiveButton!!.setOnClickListener {
                if (positiveButtonCallback != null) {
                    positiveButtonCallback!!.onClick(createdDialog)
                } else {
                    createdDialog.dismiss()
                }
            }
            positiveButton!!.visibility = View.VISIBLE
            //SETUP NEGATIVE BUTTON
            if (negativeButtonCallback != null) {
                negativeButton!!.visibility = View.VISIBLE
                negativeButton!!.text = negativeBtnText
                negativeButton!!.setOnClickListener { negativeButtonCallback!!.onClick(createdDialog) }
            } else {
                negativeButton!!.visibility = View.GONE
            }
            // Check if title is setAdapter, if so, setAdapter visibility to visible and setAdapter text
            if (dialogTitle != null && !dialogTitle!!.isEmpty()) {
                titleText!!.visibility = View.VISIBLE
                titleText!!.text = dialogTitle
            }
        }

        // Ready to serve dialog
        return createdDialog
    }


    private fun bind(view: View?) {
        positiveButton = view?.dialog_generic_positive_btn
        negativeButton = view?.dialog_generic_negative_btn
        titleText = view?.dialog_generic_title_tv
        messageText = view?.dialog_generic_message_tv
    }


    /** ================= INTERFACES =====================  */

    interface PositiveButtonCallback {
        fun onClick(dialog: Dialog)
    }

    interface NegativeButtonCallback {
        fun onClick(dialog: Dialog)
    }

}