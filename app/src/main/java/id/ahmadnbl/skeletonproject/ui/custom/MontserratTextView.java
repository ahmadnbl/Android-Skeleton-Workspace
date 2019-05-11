package id.ahmadnbl.skeletonproject.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import id.ahmadnbl.skeletonproject.R;
import id.ahmadnbl.skeletonproject.data.source.constant.Font;


/**
 * Created by billy on 10/13/16.
 * Custom button with Monstreat typeface
 */

public class MontserratTextView extends AppCompatTextView{

    public MontserratTextView(Context context) {
        super(context);
    }

    public MontserratTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public MontserratTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }

    /**
     * Parsing the given attribute
     * @param context current view context
     * @param attrs user-defined attrs
     */
    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.MontserratTextView);
        //The value 0 is a default, but shouldn't ever be used since the attr is an enum
        int typeface = values.getInt(R.styleable.MontserratTextView_typeface, 0);
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
