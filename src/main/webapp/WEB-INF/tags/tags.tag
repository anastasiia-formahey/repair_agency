<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="errorMessages" required="true" %>

<c:if test="${not empty requestScope.errorMessage}">
<div>
    <p class = errorMessage style="margin-left: 0px; padding-right: 100px;">${requestScope.errorMessage}</p>
</div>
</c:if>