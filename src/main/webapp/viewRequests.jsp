<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
    <title>Repair Agency/Requests</title>
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
            <div class="inputCont">
                <p>${sessionScope.payError}</p>
            </div>

        <table>
            <thead>
            <tr>
                <th>№</th>
                <th><fmt:message key="description"/></th>
                <th><fmt:message key="dataTime"/></th>
                <th><fmt:message key="price"/></th>
                <th><fmt:message key="status"/></th>
                <th><fmt:message key="state"/></th>
                <th><fmt:message key="master"/></th>
                <th></th>
            </tr>
            </thead>
                <tbody>
                <c:forEach items="${sessionScope.listOfRequests}" var="req" varStatus="loop">
                    <form action="controller" method="post">
                        <tr>
                            <input type="hidden" name="command" value="payForRequest"/>
                            <input  type="hidden" name="idRequest" value="${req.getId()}"/>
                        <td>${req.getId()}</td>
                            <td style=" width: 120px;"><div><div>${req.getDescription()}</div></div></td>
                        <td>${req.getDateTime()}</td>
                            <td>${req.getPrice()}
                                <c:if test="${req.getStatus() eq 'Wait for payment'}">
                                <input name="payForRequest" type="submit" value="Pay">
                            </c:if>
                        </td>
                        <td>${req.getStatus()}</td>
                    </form>
                        <td>${req.getState()}</td>
                        <td>${req.getMaster()}</td>
                        <td>
                            <c:if test="${req.getState() eq 'Finished'}">
                                <c:if test="${req.getFeedback() eq '0'}">
                                    <div class="feedback">
                                <form action="controller" method="post" style="width: auto;">
                                    <input type="hidden" name="command" value="goToFeedback"/>
                                    <input  type="hidden" name="idRequest" value="${req.getId()}"/>
                                    <input  type="hidden" name="master" value="${req.getMaster()}"/>
                                    <input name="createFeedback" type="submit" value="<fmt:message key="leaveFeedback"/>">
                                </form>
                                    </div>
                                </c:if>
                                <c:if test="${req.getFeedback() ne '0'}">
                                    ★ ${req.getFeedback()}
                            </c:if>
                            </c:if>
                            <c:if test="${req.getState() ne 'Finished'}">
                            <c:if test="${req.getMaster() eq ''}">
                                <form action="controller" method="post" style="width: auto;">
                                    <input type="hidden" name="command" value="deleteRequest"/>
                                    <input  type="hidden" name="idRequest" value="${req.getId()}"/>
                                    <input  type="hidden" name="price" value="${req.getPrice()}"/>
                                    <input  type="hidden" name="status" value="${req.getStatus()}"/>

                                    <input name="deleteRequest" type="submit" value="Delete">

                                </form>
                            </c:if>
                            </c:if>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        <nav aria-label="Navigation for responses">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item"><a class="page-link"
                                             href="controller?command=viewRequests&currentPage=${currentPage-1}&recordsPerPage=${recordsPerPage}"><</a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active"><a class="page-link">
                                    ${i} <span class="sr-only"></span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?command=viewRequests&currentPage=${i}&recordsPerPage=${recordsPerPage}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="controller?command=viewRequests&currentPage=${currentPage+1}&recordsPerPage=${recordsPerPage}">></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</main>
</body>
</html>