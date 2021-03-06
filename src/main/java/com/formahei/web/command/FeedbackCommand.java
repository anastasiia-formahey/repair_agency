package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.entity.RepairRequest;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FeedbackCommand implements Command {
    private static final Logger log = Logger.getLogger(FeedbackCommand.class);

    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("FeedbackCommand starts");
        int idRequest = Integer.parseInt(req.getParameter(Constants.ID_REQUEST));
        String masterLogin = req.getParameter(Constants.MASTER);
        req.getSession().setAttribute(Constants.ID_REQUEST, idRequest);
        req.getSession().setAttribute("masterLogin", masterLogin);
        log.debug("FeedbackCommand finished");
        return new CommandResult(Path.PAGE_CLIENT_CREATE_FEEDBACK, true);
    }
}
