<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
    <title>Repair Agency/Feedbacks</title>
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
                        <li><a href="master_home_page.jsp"><fmt:message key="header.home"/></a></li>
                    </ul>
                </div>
            </td>
            <td style="width: 320px;"><p id = "user">${sessionScope.user}</p>

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
    <a>
        <a href="controller?command=viewFeedback"/>
        <input class="userMenu" name="viewFeedback" type="submit" value="<fmt:message key="feedback"/>">
    </a>
    </a>
</aside>
<main>
    <div>

        <table>
            <thead>
            <tr>
                <th><fmt:message key="request"/> ???</th>
                <th><fmt:message key="rating"/></th>
                <th><fmt:message key="dataTime"/></th>
                <th><fmt:message key="description"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.listOfFeedbacks}" var="feedback" varStatus="loop">
                <form action="controller" method="post">
                    <tr>
                        <td>${feedback.getIdRequest()}</td>
                        <td><span class="star">???</span> ${feedback.getStars()}</td>
                        <td>${feedback.getDateTime()}</td>
                        <td><div><div>${feedback.getDescription()}</div></div></td>
                    </tr>
                </form>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>