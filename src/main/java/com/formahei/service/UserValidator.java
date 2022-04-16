package com.formahei.service;

import com.formahei.entity.User;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final Logger log = Logger.getLogger(UserValidator.class);

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.]+@[a-zA-Z]+?\\.[a-zA-Z]{2,3}$";
    private static final String PASSWORD_REGEX = "^\\S{4,}$";
    private static final Pattern PATTERN_EMAIL_COMPILE = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_PASSWORD_COMPILE = Pattern.compile(PASSWORD_REGEX, Pattern.CASE_INSENSITIVE);

    public String validate(User user) {
        log.debug("Method starts");
        Matcher emailMatcher = PATTERN_EMAIL_COMPILE.matcher(user.getEmail());
        Matcher passwordMatcher = PATTERN_PASSWORD_COMPILE.matcher(user.getPass());
        if (!emailMatcher.matches()) {
            log.trace("Data invalidate");
            log.debug("Method finished");
           return ("Email must content letters, @, ., 2 or 3 characters in the end");
        }
        if (!passwordMatcher.matches()){
            log.trace("Data invalidate");
            log.debug("Method finished");
            return ("Password must have at least 4 characters");
        }else {
            log.debug("Method finished");
            return null;
        }

    }
}
