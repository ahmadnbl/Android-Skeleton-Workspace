package id.ahmadnbl.skeletonproject.helper;

import java.util.Locale;

/**
 * Created by billy on 5/11/2015.
 */
public class PriceFormatter {

    public static String format(double amount){
        String amountOnString = "Rp" + String.format(Locale.getDefault(),"%1$,.0f", amount);
        amountOnString = amountOnString.replaceAll(",",".");
        return amountOnString;
    }

    public static String formatAbsolute(int amount){
        String amountOnString = "Rp" + String.format(Locale.getDefault(),"%1$,d", amount);
        amountOnString = amountOnString.replaceAll(",",".");
        return amountOnString;
    }

    public static String format(String amount){
        try {
            double amountDouble = Double.parseDouble(amount);
            String amountOnString = "Rp" + String.format(Locale.getDefault(),"%1$,.0f", amountDouble);
            amountOnString = amountOnString.replaceAll(",",".");
            return amountOnString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Rp. 0";
    }

    public static String formatWithoutCurrency(double amount){
        String amountOnString = "" + String.format(Locale.getDefault(),"%1$,.0f", amount);
        amountOnString = amountOnString.replaceAll(",",".");
        return amountOnString;
    }

}
