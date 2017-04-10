package id.ahmadnbl.skeletonproject.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

import id.ahmadnbl.skeletonproject.R;
import id.ahmadnbl.skeletonproject.data.constant.Font;


/**
 * Created by billy on 10/13/16.
 * Custom button with Monstreat typeface
 */

public class MontserratButton extends AppCompatButton {

    public MontserratButton(Context context) {
        super(context);
    }

    public MontserratButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public MontserratButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }

    /**
     * Parsing the given attribute
     * @param context current view context
     * @param attrs user-defined attrs
     */
    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.MontserratButton);
        //The value 0 is a default, but shouldn't ever be used since the attr is an enum
        int typeface = values.getInt(R.styleable.MontserratButton_typeface, 0);
        switch(typeface) {
            case Font.MONTSERRAT: default:
                //You can instantiate your typeface anywhere, I would suggest as a
                //singleton somewhere to avoid unnecessary copies
                setTypeface(Font.getMontserratTypeface(context));
                break;
            case Font.MONTSERRAT_BOLD:
                setTypeface(Font.getMontserratBoldTypeface(context));
                break;
        }
        values.recycle();
    }
}
