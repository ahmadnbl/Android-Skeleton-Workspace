package id.ahmadnbl.skeletonproject.data.source.sharedpreferences;

import android.util.Base64;

import id.ahmadnbl.skeletonproject.helper.Crypto;

/**
 * Created by billy on 8/8/16.
 * User-related configuration keys
 */
public enum UserPrefKey {
    IS_LOGGED_IN,
    IS_HAS_SOCIAL_MEDIA_CONNECTED,
    EMAIL,
    USER_ID,
    FULLNAME;

    @Override
    public String toString() {
        String b64S = Crypto.Companion.base64(super.toString(), Base64.NO_WRAP);
        return b64S.replace("=", "<><").replace("m", "<#&>");
    }
}
