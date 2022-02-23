<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
    <title>Login</title>
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
                        <li><a href="admin_home_page.jsp"><fmt:message key="header.home"/></a></li>
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
    <a href="addUserByAdmin.jsp">
        <input class="userMenu" name="addUser" type="submit" value="<fmt:message key="addUser"/>">
    </a>
    <a href="controller?command=viewRequests">
        <input class="userMenu" name="viewRequests" type="submit" value="<fmt:message key="viewRequests"/>">
    </a>
    <a href="controller?command=viewUsers">
        <input class="userMenu" name="viewUsers" type="submit" value="<fmt:message key="viewUsers"/>">
    </a>
</aside>
<main>
<div id="head" style="width: 90%;">
    <form action="controller" method="post" style="margin-top: -40px; margin-left: 280px;">
        <input type="hidden" name="command" value="addUser"/>
        <div class="inputCont">
            <p><fmt:message key="form"/></p>
            <p class = errorMessage>${requestScope.errorMessage}</p>
        </div>
        <div class="inputCont">
            <input name="login" pattern="[A-Za-zА-Яа-я0-9]{4,10}$"
                   onchange ="this.setCustomValidity(this.validity.patternMismatch ? 'Must content only letters and numbers, length must be 4-10 symbols' :'')"
                   class="inputField" type="text" placeholder="<fmt:message key="login"/>">
        </div>
        <br>
        <div class="inputCont" id="pass">
            <label>
                <input name="password" pattern="^\S{4,10}$"
                       onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Length must be 4-10 symbols': '')"
                       class="inputField" type="password" placeholder="<fmt:message key="password"/>">
            </label>
        </div>
        <br>
        <div class="inputCont">
            <label>
                <input name="firstname" pattern="{4,40}$"
                       onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must content only letters': '')"
                       class="inputField" type="text" placeholder="<fmt:message key="firstName"/>">
            </label>
        </div>
        <br>
        <div class="inputCont">
            <label>
                <input name="lastname" pattern="{4,40}$"
                       onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must content only letters': '')"
                       class="inputField" type="text" placeholder="<fmt:message key="lastName"/>">
            </label>
        </div>
        <br>
        <div class="inputCont">
            <label>
                <input name="email" pattern="^[A-Za-z0-9+_.-]+@(.+)$"
                       class="inputField" type="email" placeholder="<fmt:message key="email"/>">
            </label>
        </div>
        <br>
        <div class="inputCont">
            <label>
                <select name = "role">
                    <option value="MASTER"><fmt:message key="master"/></option>
                    <option value="MANAGER"><fmt:message key="manager"/></option>
                </select>
            </label>
        </div>
        <input type="submit" value="<fmt:message key="addUser"/>" style="    margin-left: 30px;"><br>
    </form>
</div>
</main>
</body>
</html>