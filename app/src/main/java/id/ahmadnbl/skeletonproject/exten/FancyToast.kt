package id.ahmadnbl.skeletonproject.exten

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import id.ahmadnbl.skeletonproject.R
import kotlinx.android.synthetic.main.l_eetoast.view.*


class FancyToast {

    enum class Type {
        SUCCESS,
        FAILURE,
        WARNING,
        INFO
    }

    companion object {

        fun makeText(context: Context?, message: String, duration: Int, type: Type): Toast {
            val toast = Toast.makeText(context, "", duration)

            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.l_eetoast, null)

            view.eetoast_msg_tv.text = message

            when (type) {
                Type.SUCCESS -> {
                    view.eetoast_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green_600))
                    view.eetoast_icon_iv.setImageResource(R.drawable.ic_success)
                }
                Type.FAILURE -> {
                    view.eetoast_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red_600))
                    view.eetoast_icon_iv.setImageResource(R.drawable.ic_failure)
                }
                Type.WARNING -> {
                    view.eetoast_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_600))
                    view.eetoast_icon_iv.setImageResource(R.drawable.ic_warning)
                }
                Type.INFO -> {
                    view.eetoast_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cyan_600))
                    view.eetoast_icon_iv.setImageResource(R.drawable.ic_info)
                }
            }

            toast.view = view
            val marginTop = context.resources.getDimension(R.dimen.space_xtra).toInt()
            toast.setGravity(Gravity.TOP, 0, marginTop)
            return toast
        }
    }

}

