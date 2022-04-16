<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
    <title>Repair Agency</title>
    <link rel="stylesheet" href="css/general_stylesheet.css">
</head>

</head>
<body>
<header>
    <table style="width: 100%;">
    <tr>
        <td><h1><div class="img"></div> <a> <fmt:message key="header.repairAgency"/></a></h1>
    <div class="menu">
        <ul>
            <li><a href="index.jsp"><fmt:message key="header.home"/></a></li>
        </ul>
    </div></td>
        <td>
    <a href="index.jsp" class="button" style="margin-top: -20px; border: 1px white solid;"> <fmt:message key="header.login"/> </a>
    <a href="register.jsp" class="button" style="margin-top: -20px; border: 1px white solid;"><fmt:message key="header.register"/></a>
        </td>
        <td>
    <div class="locale">
        <form class="setlocale" action="controller" method="get">
            <input type="hidden" name="command" value="locale"/>
            <select id="locale" name="locale" onchange="submit()">
            <option value="en" ${locale == 'en' ? 'selected' : ''}>EN</option>
            <option value="ua" ${locale == 'ua' ? 'selected' : ''}>UA</option>
        </select>
        </form>
    </div>
        </td>
    </tr>
    </table>
</header>
<br>
<div id="head" style="    height: 500px;">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="login"/>
        <div class="inputCont">
            <p><fmt:message key="header.login"/></p>
            <custom:tags errorMessages="${requestScope.errorMessage}"/>
        </div>

        <div class="inputCont">
            <input name="login" class="inputField" type="text" placeholder="<fmt:message key="login"/>" required/>
        </div>
        <br>
        <div class="inputCont" id="pass">
            <label>
                <input name="password" class="inputField" type="password" placeholder="<fmt:message key="password"/>" required/>
                <br>
            </label>
        </div>
        <br>
        <input type="submit" value="<fmt:message key="header.login"/>"><br>
    </form>
</div>
</body>
</html>