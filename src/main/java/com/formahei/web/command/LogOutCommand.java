package com.formahei.web.command;

import com.formahei.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    private static final Logger log = Logger.getLogger(LogOutCommand.class);
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("LogOutCommand starts");
        HttpSession session = req.getSession();
        if(session.getAttribute("user") != null){
            session.invalidate();
        }
        log.debug("LogOutCommand finished");
        return new CommandResult(Path.PAGE_LOGIN, false);
    }
}
