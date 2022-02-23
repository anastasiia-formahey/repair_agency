<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
    <title>Repair Agency/Leave Feedback</title>
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
        <form action="controller" method="post" class="feedback">
            <div class="inputCont"> <p><fmt:message key="leaveFeedback"/></p> </div>
            <input type="hidden" name="command" value="createFeedback"/>
            <input  type="hidden" name="idRequest" value="${sessionScope.idRequest}"/>
            <input  type="hidden" name="master" value="${sessionScope.masterLogin}"/>
            <table>
                <tr>
                    <td class="right"><fmt:message key="request"/> № ${sessionScope.idRequest}</td>

                </tr>
                <tr>
                    <td class="right"><fmt:message key="master"/>  ${sessionScope.masterLogin}</td>

                </tr>
                <tr>
                    <td class="right"><fmt:message key="description"/></td>
                </tr>
                <tr>
                <td class="left"><textarea class="inputField"  name="description" rows="5" cols="40" style=" margin-left: 0;
"></textarea></td></tr>
                <tr>
                    <td class="right">
                        <div class="rating-area" style=" margin-left: 0;">
                            <input type="radio" id="star-5" name="rating" value="5">
                            <label for="star-5" title="5"></label>
                            <input type="radio" id="star-4" name="rating" value="4">
                            <label for="star-4" title="4"></label>
                            <input type="radio" id="star-3" name="rating" value="3">
                            <label for="star-3" title="3"></label>
                            <input type="radio" id="star-2" name="rating" value="2">
                            <label for="star-2" title="2"></label>
                            <input type="radio" id="star-1" name="rating" value="1" >
                            <label for="star-1" title="1"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="right">
                        <input name="createFeedback" type="submit" value="<fmt:message key="leaveFeedback"/>">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</main>
</body>
</html>