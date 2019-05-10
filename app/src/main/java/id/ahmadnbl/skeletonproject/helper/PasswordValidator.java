package id.ahmadnbl.skeletonproject.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by glenrynaldi on 11/13/15.
 *
 */
public class PasswordValidator {

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z]).{6,20})";
    private Pattern pattern;

    /*
    *
    (			        # Start of group
      (?=.*\d)		    #   must contains one digit from 0-9
      (?=.*[a-z])		#   must contains one lowercase characters
      (?=.*[A-Z])		#   must contains one uppercase characters
      (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
                  .		#   match anything with previous condition checking
       {6,20}	        #   length at least 6 characters and maximum of 20
    )			# End of group
    *
    * */
    private Matcher matcher;

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password) {

        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
