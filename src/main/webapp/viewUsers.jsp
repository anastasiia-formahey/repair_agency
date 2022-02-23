<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
    <title>Repair Agency/Home</title>
    <link rel="stylesheet" href="css/general_stylesheet.css">
</head>
<body>
<header>
    <table style="width: 100%;">
        <tr>
            <td>
                <h1><div class="img"></div> <a> <fmt:message key="header.repairAgency"/></a></h1>
                <div class="menu">
                    <ul>
                        <li><a href="manager_home_page.jsp"><fmt:message key="header.home"/></a></li>
                    </ul>
                </div>
            </td>
            <td style="width: 320px;"><p id = "user">${sessionScope.user}</p>
                <p id = "account">${sessionScope.account}</p>
            </td>
            <td><a href="controller?command=logout">
                <input type="submit" class="button" id="logout" value="<fmt:message key="logout"/>"/>
            </a></td>
            <td>
                <form class="setlocale" action="controller" method="get">
                    <input type="hidden" name="command" value="locale"/>
                    <select id="locale" name="locale" onchange="submit()">
                        <option value="en" ${locale == 'en' ? 'selected' : ''}>EN</option>
                        <option value="ua" ${locale == 'ua' ? 'selected' : ''}>UA</option>
                    </select>
                </form></td>
        </tr></table>
</header>
<br>
<aside>
    <a>
        <a href="controller?command=viewRequests">
            <input class="userMenu" name="viewRequests" type="submit" value="<fmt:message key="viewRequests"/>">
        </a>
    </a>
    <a href="controller?command=viewUsers">
        <input class="userMenu" name="viewUsers" type="submit" value="<fmt:message key="viewUsers"/>">
    </a>
    <a href="manager_top_up_account.jsp">
        <input class="userMenu" name="topUpAccount" type="submit" value="<fmt:message key="topUpAccount"/>">
    </a>
</aside>
<main>
    <div>

        <table>
            <thead>
            <tr>
                <td><fmt:message key="login"/></td>
                <td><fmt:message key="firstName"/></td>
                <td><fmt:message key="lastName"/></td>
                <td><fmt:message key="email"/></td>
                <td><fmt:message key="account"/></td>
                <td><fmt:message key="status"/></td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.listOfUsers}" var="req" varStatus="loop">
                <form action="controller" method="post">
                    <tr>
                        <input type="hidden" name="command" value="changeStatusOfUser"/>
                        <input  type="hidden" name="loginOfUser" value="${req.getLogin()}"/>
                       <td>${req.getLogin()}</td>
                       <td>${req.getFirstName()}</td>
                       <td>${req.getLastName()}</td>
                       <td>${req.getEmail()}</td>
                       <td>${req.getAccount()} UAH</td>
                       <td>${req.getStatus()}</td>
                        <c:if test="${req.getStatus() eq 'blocked'}">
                            <td>
                                <input name="changeStatusOfUser" type="submit" value="<fmt:message key="unblock"/>">
                            </td>
                        </c:if>
                        <c:if test="${req.getStatus() eq 'unblocked'}">
                            <td>
                                <input name="changeStatusOfUser" type="submit" value="<fmt:message key="block"/>">
                            </td>
                        </c:if>
                    </tr>
                </form>
            </c:forEach>
            </tbody>
        </table>


    </div>
</main>
</body>
</html>