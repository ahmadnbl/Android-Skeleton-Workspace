package id.ahmadnbl.skeletonproject.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import id.ahmadnbl.skeletonproject.R;
import id.ahmadnbl.skeletonproject.data.constant.Font;


/**
 * Created by billy on 10/13/16.
 * Custom button with Monstreat typeface
 */

public class MontserratEditText extends AppCompatEditText{

    public MontserratEditText(Context context) {
        super(context);
    }

    public MontserratEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public MontserratEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }

    /**
     * Parsing the given attribute
     * @param context current view context
     * @param attrs user-defined attrs
     */
    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.MontserratEditText);
        //The value 0 is a default, but shouldn't ever be used since the attr is an enum
        int typeface = values.getInt(R.styleable.MontserratEditText_typeface, 0);
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
