package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserDAO;
import com.formahei.entity.RepairRequest;
import com.formahei.entity.User;
import com.formahei.service.RequestService;
import com.formahei.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayForRequestCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = new UserService(UserDAO.getInstance());
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        int id =  Integer.parseInt(req.getParameter("idRequest"));
        RepairRequest repairRequest = requestService.findRequestByID(id);
        int price = repairRequest.getPrice();
        String status = "Paid";
        User user = userService.getUserByLogin(String.valueOf(req.getSession().getAttribute("login")));
        double account = user.getAccount() - price;
        if(account<0){
            req.getSession().setAttribute("payError", "You don't have enough money");
        }else {
            userService.updateAccount(user.getLogin(), account);
           requestService.updateRequestByManager(id, status, price);
            user = userService.getUserByLogin(user.getLogin());
            req.getSession().removeAttribute("payError");
            req.getSession().removeAttribute("account");
            req.getSession().setAttribute("account", user.getAccount() + " UAH");
        }
        return new ViewRequestsCommand().execute(req, resp);
    }
}
