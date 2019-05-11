package id.ahmadnbl.skeletonproject.helper

import android.util.Base64


class Crypto {

    companion object {
        fun base64(with: String?, flags: Int = Base64.NO_WRAP): String{
            var data = "".toByteArray(Charsets.UTF_8)
            with?.let { inputString: String ->
                data = inputString.toByteArray(Charsets.UTF_8)
            }
            return Base64.encodeToString(data, flags)
        }

        fun deBase64(from: String?, flags: Int = Base64.NO_WRAP): String {
            var deString = ""
            from?.let {
                val data = Base64.decode(it, flags)
                deString = String(data, Charsets.UTF_8)
            }
            return deString
        }
    }

}