package com.formahei.web.command;

import com.formahei.utils.Constants;
import com.formahei.utils.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String localeRequest = request.getParameter(Constants.LOCALE);
        if (localeRequest != null) {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.LOCALE, localeRequest);
        }else {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.LOCALE, "en");
        }
        if(request.getSession().getAttribute(Constants.LOGIN) == null) return Path.PAGE_LOGIN;
        else return new PersonalPageCommand().execute(request, response);
    }
}
