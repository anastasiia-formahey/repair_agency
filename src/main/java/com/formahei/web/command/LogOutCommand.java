package com.formahei.web.command;

import com.formahei.utils.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {

    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if(session.getAttribute("user") != null){
            session.invalidate();
        }
        return new CommandResult(Path.PAGE_LOGIN, false);
    }
}
