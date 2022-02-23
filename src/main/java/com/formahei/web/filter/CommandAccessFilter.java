package com.formahei.web.filter;

import com.formahei.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class CommandAccessFilter implements Filter {
    private static final Logger log = Logger.getLogger(CommandAccessFilter.class);
    private static Map<String, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Filter initialization starts");

        // roles
        accessMap.put("ADMIN", asList(filterConfig.getInitParameter("admin")));
        accessMap.put("CLIENT", asList(filterConfig.getInitParameter("client")));
        accessMap.put("MANAGER", asList(filterConfig.getInitParameter("manager")));
        accessMap.put("MASTER", asList(filterConfig.getInitParameter("master")));
        log.trace("Access map --> " + accessMap);

        // out of control
        outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
        log.trace("Out of control commands --> " + outOfControl);

        log.debug("Filter initialization finished");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("Filter starts");

        if (accessAllowed(servletRequest)) {
            log.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessage = "You do not have permission to access the requested resource" + servletRequest;

            servletRequest.setAttribute("errorMessage", errorMessage);
            log.trace("Set the request attribute: errorMessage --> " + errorMessage);

            servletRequest.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                    .forward(servletRequest, servletResponse);
        }

    }

    private boolean accessAllowed(ServletRequest request){
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        String locale = request.getParameter("locale");
        System.out.println(commandName);
        System.out.println(commandName);
        if (commandName == null || commandName.isEmpty())
            return false;
        if (outOfControl.contains(commandName))
            return true;

        HttpSession session = httpRequest.getSession();
        System.out.println(session);
        if (session == null)
            return false;
        String userRole = session.getAttribute("role").toString();
        if (userRole == null)
            return false;

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);

    }
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }


    @Override
    public void destroy() {
        log.debug("Filter destruction starts");

        log.debug("Filter destruction finished");
    }
}
