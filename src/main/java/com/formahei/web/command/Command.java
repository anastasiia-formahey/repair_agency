package com.formahei.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Interface for Command pattern implementation
 *
 * @author Anastasiia Formahei
 * */

public interface Command extends Serializable {
    /**
     * Execution method for command
     * @return Address to go after command executed
     * */
    String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
