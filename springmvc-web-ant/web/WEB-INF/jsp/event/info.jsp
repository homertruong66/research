<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${remindSent == 'successful'}" >
        The remind email has been sent to all members. <br/>
    </c:if>

    <c:if test="${remindSent == 'failed'}" >
        Error sending the remind email. <br/>
        ${error} <br/>
    </c:if>

    <c:if test="${resultSent == 'successful'}" >
        The result email has been sent to all members. <br/>
    </c:if>

    <c:if test="${resultSent == 'failed'}" >
        Error sending the result email. <br/>
        ${error} <br/>
    </c:if>

    <a href="<c:url value="/event/index.htm"/>">Return</a>
</div>
