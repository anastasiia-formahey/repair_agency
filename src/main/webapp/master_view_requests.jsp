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
                <td>№</td>
                <td><fmt:message key="description"/></td>
                <td><fmt:message key="dataTime"/></td>
                <td><fmt:message key="client"/></td>
                <td><fmt:message key="price"/></td>
                <td><fmt:message key="status"/></td>
                <td><fmt:message key="state"/></td>
                <td> </td>
            </tr>
            </thead>
            <tbody class="table-hover">
            <c:forEach items="${sessionScope.listOfRequests}" var="req" varStatus="loop">
                <form action="controller" method="post">
                    <tr>
                        <input type="hidden" name="command" value="requestProcessingByMaster"/>
                        <input  type="hidden" name="idRequest" value="${req.getId()}"/>
                        <td>${req.getId()}</td>
                        <td style=" width: 120px;"><div><div>${req.getDescription()}</div></div></td>
                        <td>${req.getDateTime()}</td>
                        <td>${req.getClient()}</td>
                        <td>${req.getPrice()} UAH </td>
                        <td> ${req.getStatus()}</td>
                        <td>
                            <c:if test="${req.getState() ne 'Finished'}">
                                <select name="state">
                                    <option>In process</option>
                                    <option>Finished</option>
                                </select>
                            </c:if>
                            <c:if test="${req.getState() eq 'Finished'}">
                                ${req.getState()}
                            </c:if>
                        </td>
                        <c:if test="${req.getState() ne 'Finished'}">
                        <td>
                            <input name="requestProcessingByMaster" type="submit" value="<fmt:message key="send"/>">
                        </td>
                        </c:if>
                        <td><c:if test="${req.getFeedback() > 0}">
                            ★ ${req.getFeedback()}
                        </c:if></td>
                    </tr>
                </form>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Navigation for responses">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item"><a class="page-link"
                                             href="controller?command=viewRequests&currentPage=${currentPage-1}&recordsPerPage=${recordsPerPage}&orderBy=id">Previous</a>
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
                                                     href="controller?command=viewRequests&currentPage=${i}&recordsPerPage=${recordsPerPage}&orderBy=id">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="controller?command=viewRequests&currentPage=${currentPage+1}&recordsPerPage=${recordsPerPage}&orderBy=id">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</main>
</body>
</html>