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
    <br>
    <hr>
    <br>
    <div>
    <div class="sort">
        <a style="    float: right;    width: 245px;">
            <img src="image/icons8-filter-50.png" height="25px" style="float: left">
            <input type="text" id="filterMaster" onkeyup="filterMaster()" placeholder="Search for master.." style=" height: 26px;
">
            <input type="text" id="filterStatus" onkeyup="filterStatus()" placeholder="Search for status.." style=" height: 26px; margin-left: 25px;
">
        </a>
        <a href="controller?command=viewRequests&orderBy=price">
            <input class="userMenu" name="viewRequests" type="submit" value="<fmt:message key="sortByPrice"/>">
        </a>
        <a href="controller?command=viewRequests&orderBy=dateTime">
            <input class="userMenu" name="viewRequests" type="submit" value="<fmt:message key="sortByDate"/>">
        </a>
        <a href="controller?command=viewRequests&orderBy=status">
            <input class="userMenu" name="viewRequests" type="submit" value="<fmt:message key="sortByStatus"/>">
        </a>

    </div>
    </div>
</aside>
<main>
    <div>
        <br>


            <table id="filterTable">
                <thead>
                <tr>
                    <th>№</th>
                    <th style="    padding-right: 65px;"><fmt:message key="description"/></th>
                    <th style="width: 148px;"><fmt:message key="dataTime"/></th>
                    <th><fmt:message key="client"/></th>
                    <th><fmt:message key="price"/></th>
                    <th><fmt:message key="status"/></th>
                    <th><fmt:message key="state"/></th>
                    <th><fmt:message key="master"/></th>
                    <th>==></th>
                </tr>
                </thead>
                <tbody class="table-hover">
                <c:forEach items="${sessionScope.listOfRequests}" var="req" varStatus="loop">
                <form action="controller" method="get">
                    <tr>
                        <input type="hidden" name="command" value="requestProcessingByManager"/>
                        <input  type="hidden" name="idRequest" value="${req.getId()}"/>
                        <td>${req.getId()}</td>
                        <td style=" width: 120px;"><div><div>${req.getDescription()}</div></div></td>
                        <td>${req.getDateTime()}</td>
                        <td>${req.getClient()}</td>
                        <td>
                            <c:if test="${req.getStatus() ne 'Canceled'}">
                            <c:if test="${req.getPrice() eq 0}">
                            <input class="inputField" pattern="[0-9]{1,10}" required min = "1"
                                   name="setPrice" type="number" placeholder="<fmt:message key="enterAmount"/>" style="width: 150px;">
                            </c:if>
                            <c:if test="${req.getPrice() > 0 }">
                                ${req.getPrice()} UAH
                                <input  type="hidden" name="setPrice" value="${req.getPrice()}"/>
                            </c:if>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${req.getStatus() eq ''}">
                            <select name="status">
                                <option>Wait for payment</option>
                                <option>Paid</option>
                                <option>Canceled</option>
                        </select>
                            </c:if>
                            <c:if test="${req.getStatus() ne ''}">
                                ${req.getStatus()}
                                <input  type="hidden" name="status" value="${req.getStatus()}"/>
                            </c:if>
                        </td>
                        <td>${req.getState()}</td>
                        <td>
                            <c:if test="${req.getStatus() eq 'Paid'}">
                            <c:if test="${req.getMaster() ne ''}">
                                ${req.getMaster()}
                            </c:if>
                                <c:if test="${req.getMaster() eq ''}">
                            <select name = "master">
                                <c:forEach items="${sessionScope.listOfMaster}" var="master" varStatus="loop">
                                     <option>${master.getLogin()}</option>
                                </c:forEach>
                            </select>
                                </c:if>
                            </c:if>
                        </td>
                        <c:if test="${req.getStatus() ne 'Canceled'}">
                        <c:if test="${req.getState() ne 'Finished'}">
                            <c:if test="${req.getMaster() eq ''}">
                        <td>
                        <input name="requestProcessingByManager" type="submit" value="<fmt:message key="send"/>">
                        </td>
                            </c:if>
                            <c:if test="${req.getMaster() ne ''}">
                                <td>
                                    <input name="requestProcessingByManager" type="hidden" value="<fmt:message key="send"/>">
                                </td>
                            </c:if>
                        </c:if>
                        <c:if test="${req.getState() eq 'Finished'}">
                            <td>
                                <input name="getReportOfRequest" type="submit" value="<fmt:message key="getTheReport"/>">
                            </td>
                        </c:if></c:if>
                    </tr>
                </form>
                </c:forEach>
                </tbody>
            </table>
        <p class="errorMessage">${sessionScope.errorMessage}</p>


        <nav aria-label="Navigation for responses">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item"><a class="page-link"
                                             href="controller?command=viewRequests&currentPage=${currentPage-1}&recordsPerPage=${recordsPerPage}&orderBy=${orderBy}"><</a>
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
                                                     href="controller?command=viewRequests&currentPage=${i}&recordsPerPage=${recordsPerPage}&orderBy=${orderBy}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="controller?command=viewRequests&currentPage=${currentPage+1}&recordsPerPage=${recordsPerPage}&orderBy=${orderBy}">></a>
                    </li>
                </c:if>
            </ul>
        </nav>

<%--        JS script--%>
        <script>
            function filterMaster() {
                // Declare variables
                var input, filter, table, tr, td, i;
                input = document.getElementById("filterMaster");
                filter = input.value.toUpperCase();
                table = document.getElementById("filterTable");
                tr = table.getElementsByTagName("tr");

                // Loop through all table rows, and hide those who don't match the search query
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[7];
                    if (td) {
                        if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
            function filterStatus() {
                // Declare variables
                var input, filter, table, tr, td, i;
                input = document.getElementById("filterStatus");
                filter = input.value.toUpperCase();
                table = document.getElementById("filterTable");
                tr = table.getElementsByTagName("tr");

                // Loop through all table rows, and hide those who don't match the search query
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[5];
                    if (td) {
                        if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
        </script>
    </div>
</main>
</body>
</html>