<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
    <title>Repair Agency/Create request</title>
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
                        <li><a href="user_personal_page.jsp"><fmt:message key="header.home"/></a></li>
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
        <a href="controller?command=personalPage">
            <input class="userMenu" name="personalPage" type="submit" value="<fmt:message key="personalPage"/>">
        </a>
    </a>
    <a>
        <a href="controller?command=viewRequests">
            <input class="userMenu" name="viewRequests" type="submit" value="<fmt:message key="viewRequests"/>">
        </a>
    </a>
    <a href="createRequest.jsp">
        <input class="userMenu" name="createRequest" type="submit" value="<fmt:message key="createRequest"/>">
    </a>
    <a href="topUpAccount.jsp">
        <input class="userMenu" name="topUpAccount" type="submit" value="<fmt:message key="topUpAccount"/>">
    </a>
</aside>
<main>
    <div>
        <form action="controller" method="post" style="    margin-left: 130px;    margin-top: 20px;">
            <div class="inputCont">
                <p><fmt:message key="createRequest"/></p>
            </div>
            <label>
                <input type="hidden" name="command" value="createRequest"/>
                <p class = errorMessage>${requestScope.errorMessage}</p>
                <textarea class="inputField"  name="createRequest" rows="5" cols="50"></textarea>
            </label>
            <input class="inputField" name="createRequest" type="submit" value="<fmt:message key="send"/>">
        </form>
    </div>
</main>
</body>
</html>