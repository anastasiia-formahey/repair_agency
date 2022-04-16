package com.formahei.web.command;

import com.formahei.utils.Constants;
import com.formahei.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command {

    private static final Logger log = Logger.getLogger(LocaleCommand.class);
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.debug("LocaleCommand starts");
        String localeRequest = request.getParameter(Constants.LOCALE);
        if (localeRequest != null) {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.LOCALE, localeRequest);
            log.trace("Session attribute: locale ==> " + localeRequest);
        }else {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.LOCALE, "en");
            log.trace("Session attribute: locale ==> " + "en");
        }
        log.debug("LocaleCommand finished");
        if(request.getSession().getAttribute(Constants.LOGIN) == null)
            return new CommandResult( Path.PAGE_LOGIN, false);
        else return new PersonalPageCommand().execute(request, response);
    }
}
