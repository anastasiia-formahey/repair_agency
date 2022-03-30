package com.formahei.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;
/**
 * Container for all commands
 *
 * @author Anastasiia Formahei
 * */

public class CommandContainer{
    private static final Logger log = Logger.getLogger(CommandContainer.class);
    private static final Map<String, Command> commandMap = new TreeMap<>();

    static {
        commandMap.put("login", new LoginCommand());
        commandMap.put("logout", new LogOutCommand());
        commandMap.put("register", new RegisterCommand());
        commandMap.put("personalPage", new PersonalPageCommand());
        commandMap.put("viewRequests", new ViewRequestsCommand());
        commandMap.put("createRequest", new CreateRequestCommand());
        commandMap.put("topUpAccount", new TopUpAccountCommand());
        commandMap.put("addUser", new AddUserCommand());
        commandMap.put("viewUsers", new ViewUsersCommand());
        commandMap.put("requestProcessingByManager", new RequestProcessingByManagerCommand());
        commandMap.put("payForRequest", new PayForRequestCommand());
        commandMap.put("createFeedback", new CreateFeedbackCommand());
        commandMap.put("goToFeedback", new FeedbackCommand());
        commandMap.put("viewFeedback", new ViewFeedbackCommand());
        commandMap.put("requestProcessingByMaster", new RequestProcessingByMasterCommand());
        commandMap.put("changeStatusOfUser", new ChangeStatusOfUserCommand());
        commandMap.put("locale", new LocaleCommand());
    }

    /**
     * Returns command object with the given name
     * @param commandName
     *          Name of the command
     * @return Command object
     * */
    public static Command get(String commandName) {
        if(commandName == null || !commandMap.containsKey(commandName)){
            log.trace("Command not found, name => " + commandName);
            return commandMap.get("login");
        }
        return commandMap.get(commandName);
    }
    private CommandContainer(){}
}
