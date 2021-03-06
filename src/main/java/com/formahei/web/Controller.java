package com.formahei.web;

import com.formahei.web.command.Command;
import com.formahei.web.command.CommandContainer;
import com.formahei.web.command.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller
 * @author Anastasiia Formahei
 * */

public class Controller extends HttpServlet{
    private static final Logger log = Logger.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try{
            process(req, resp);
        }catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            process(req, resp);
        }catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method of Controller
     * */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Controller starts");

        // extract command name from the request
        String commandName = req.getParameter("command");
        log.trace("Request parameter: command => " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        log.trace("Obtained command => " + command);

        // execute command and get forward address
        CommandResult commandResult = command.execute(req, resp);
        log.trace("Forward address => " + commandResult.getPage());
        log.debug("Controller finished. Go to forward address " + commandResult.getPage());

        // if the forward address is not null go to the address
        if (commandResult.isRedirect()){
            resp.sendRedirect(commandResult.getPage());
        }else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(commandResult.getPage());
            requestDispatcher.forward(req, resp);
        }
    }
}
