package id.ahmadnbl.skeletonproject.data.constant;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by billy on 10/13/16.
 *
 * Place of custom font field values
 *
 */

public class Font {
    public final static int MONTSERRAT = 0;
    public final static int MONTSERRAT_BOLD = 1;

    /**
     * Normal MONTSERRAT TypeFace
     */
    private static Typeface montserratTypeface;
    public static Typeface getMontserratTypeface(Context context){
        if(montserratTypeface == null){
            montserratTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
        }
        return montserratTypeface;
    }

    /**
     * Bold MONTSERRAT TypeFace
     */
    private static Typeface montserratBoldTypeface;
    public static Typeface getMontserratBoldTypeface(Context context){
        if(montserratBoldTypeface == null){
            montserratBoldTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.ttf");
        }
        return montserratBoldTypeface;
    }


}
