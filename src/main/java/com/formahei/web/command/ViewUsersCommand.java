package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.Role;
import com.formahei.entity.User;
import com.formahei.service.UserService;
import com.formahei.utils.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewUsersCommand implements Command {
    private static final Logger log = Logger.getLogger(ViewUsersCommand.class);

    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("ViewUsersCommand starts");
        UserService userService = new UserService(UserDAO.getInstance());
        List<User> listOfUsers = new ArrayList<>();
        String path = "viewUsers.jsp";
        if(req.getSession().getAttribute(Constants.ROLE).equals(Role.ADMIN.name())){
            listOfUsers = userService.findUserByRole(Role.MANAGER.name());
            listOfUsers.addAll(userService.findUserByRole(Role.MASTER.name()));
            path = "admin_view_users.jsp";
        }else if(req.getSession().getAttribute(Constants.ROLE).equals(Role.MANAGER.name())){
            listOfUsers = userService.findUserByRole(Role.CLIENT.name());
        }
        req.getSession().setAttribute("listOfUsers",listOfUsers);
        log.debug("ViewUsersCommand finished");
        return new CommandResult( path, true);
    }
}
