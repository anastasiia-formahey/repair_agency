package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewUsersCommand implements Command {
    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = new UserService(UserDAO.getInstance());
        List<User> listOfUsers = new ArrayList<>();
        String path = "viewUsers.jsp";
        if(req.getSession().getAttribute("role").equals("ADMIN")){
            listOfUsers = userService.findUserByRole("MANAGER");
            listOfUsers.addAll(userService.findUserByRole("MASTER"));
            path = "admin_view_users.jsp";
        }else if(req.getSession().getAttribute("role").equals("MANAGER")){
            listOfUsers = userService.findUserByRole("CLIENT");
        }
        req.getSession().setAttribute("listOfUsers",listOfUsers);
        return path;
    }
}
