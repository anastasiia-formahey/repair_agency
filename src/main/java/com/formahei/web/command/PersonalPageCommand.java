package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.service.UserService;
import com.formahei.utils.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PersonalPageCommand implements Command {
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
        User client = userService.getUserByLogin(req.getSession().getAttribute("login")
                .toString());
        req.getSession().removeAttribute("account");
        req.getSession().setAttribute("account", client.getAccount() + " UAH");
        String path;
        switch (client.getRole()){
            case "CLIENT": {
                path =  Path.PAGE_CLIENT_PERSONAL;
                break;
            }
            case "MANAGER":{
                path = Path.PAGE_MANAGER_HOME;
                break;
            }
            case "MASTER":{
                path =Path.PAGE_MASTER_HOME;
                break;
            }
            case "ADMIN":{
                path = Path.PAGE_ADMIN_HOME;
                break;
            }
            default: path = Path.PAGE_LOGIN;
        }
        return path;
    }
}
