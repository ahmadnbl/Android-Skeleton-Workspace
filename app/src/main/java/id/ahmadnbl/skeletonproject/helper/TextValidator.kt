package id.ahmadnbl.skeletonproject.helper

import id.ahmadnbl.skeletonproject.data.source.constant.DefaultConstant
import java.util.regex.Pattern

class TextValidator {

    companion object {

        fun isEmailFormatted (text: String): Boolean {
            val ptr = Pattern.compile(DefaultConstant.VALID_EMAIL_PATTERN)
            return !(!text.contains("@") ||
                    !text.substring(text.indexOf("@")).contains(".") ||
                    !ptr.matcher(text).matches())
        }

        fun isPhoneFormatted (text: String): Boolean {
//            return text.startsWith("08", true)
//                    && text.count() >= 10
            return text.contains(" ").not()
                    && text.toDoubleOrNull() != null
                    && text.count() >= 1
        }

    }

}