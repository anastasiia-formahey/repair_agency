package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.service.UserService;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class TopUpAccountCommand implements Command {

    public static final String ACCOUNT = "account";

    /**
     * Execution method for command
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @return Address to go after command executed
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = new UserService(UserDAO.getInstance());
        String loginOfClient;
        String loginOfManager = null;
        String topUpAccount =req.getParameter(Constants.TOP_UP_ACCOUNT);
        boolean isManager = req.getSession().getAttribute(Constants.ROLE).equals("MANAGER");
        req.removeAttribute(Constants.ERROR_MESSAGE);
        if(topUpAccount.isEmpty()){
            req.setAttribute(Constants.ERROR_MESSAGE, "This field can't be empty");
            return isManager?Path.PAGE_MANAGER_TOP_UP_ACCOUNT: Path.PAGE_CLIENT_TOP_UP_ACCOUNT;
        }

        double amount = Double.parseDouble(topUpAccount);

        req.getSession().removeAttribute(ACCOUNT);

        if(isManager){
            loginOfClient = req.getParameter("userLogin");
            if(loginOfClient.isEmpty()){
                req.setAttribute(Constants.ERROR_MESSAGE, "This field can't be empty");
                return Path.PAGE_MANAGER_TOP_UP_ACCOUNT;
            }
            if(userService.getUserByLogin(loginOfClient) == null){
                req.setAttribute(Constants.ERROR_MESSAGE, "User not registered");
                return Path.PAGE_MANAGER_TOP_UP_ACCOUNT;
            }
            loginOfManager = req.getSession().getAttribute(Constants.LOGIN).toString();
        }else {
            loginOfClient = req.getSession().getAttribute(Constants.LOGIN).toString();
        }

            User client = userService.getUserByLogin(loginOfClient);
            userService.updateAccount(client.getLogin(), client.getAccount() + amount);

        if(isManager){
            User manager = userService.getUserByLogin(loginOfManager);
            if(!Objects.equals(client.getRole(), "MANAGER")){
                userService.updateAccount(manager.getLogin(), manager.getAccount() - amount);
                manager = userService.getUserByLogin(loginOfManager);
            }
            req.getSession().setAttribute(ACCOUNT, manager.getAccount() + " UAH");
            return Path.PAGE_MANAGER_TOP_UP_ACCOUNT;
        }else {
            client = userService.getUserByLogin(loginOfClient);
            req.getSession().setAttribute(ACCOUNT, client.getAccount() + " UAH");
            return Path.PAGE_CLIENT_TOP_UP_ACCOUNT;
        }
    }
}
