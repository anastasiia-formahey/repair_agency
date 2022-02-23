<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
  <title>Repair Agency/Registration</title>
  <link rel="stylesheet" href="css/general_stylesheet.css">
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
        <a href="index.jsp" class="button" style="margin-top: -20px;"> <fmt:message key="header.login"/> </a>
        <a href="register.jsp" class="button" style="margin-top: -20px;"><fmt:message key="header.register"/></a>
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
<div id="head">
  <form action="controller" method="post">
    <input type="hidden" name="command" value="register"/>
    <div class="inputCont">
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
    <input type="submit" value="<fmt:message key="header.register"/>" style="    margin: 0;"><br>
    </div>
  </form>
</div>
</body>
</html>