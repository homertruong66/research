<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="lang-menu">
    <%-- English --%>
    <c:url var="englishUrl" value="${url}">
        <c:forEach items="${param}" var="pTemp">
            <c:if test="${(pTemp.key != 'locale') && (pTemp.key != 'page') }">
                <c:if test="${pTemp.value != ''}">
                    <c:param name="${pTemp.key}" value="${pTemp.value}"/>
                </c:if>
            </c:if>
        </c:forEach>
        <c:param name="locale" value="en" />
    </c:url>

    <%-- French --%>
    <c:url var="frenchUrl" value="${url}">
        <c:forEach items="${param}" var="pTemp">
            <c:if test="${(pTemp.key != 'locale') && (pTemp.key != 'page') }">
                <c:if test="${pTemp.value != ''}">
                    <c:param name="${pTemp.key}" value="${pTemp.value}"/>
                </c:if>
            </c:if>
        </c:forEach>
        <c:param name="locale" value="fr" />
    </c:url>

    <%-- Vietnamese --%>
    <c:url var="vietnameseUrl" value="${url}">
        <c:forEach items="${param}" var="pTemp">
            <c:if test="${(pTemp.key != 'locale') && (pTemp.key != 'page') }">
                <c:if test="${pTemp.value != ''}">
                    <c:param name="${pTemp.key}" value="${pTemp.value}"/>
                </c:if>
            </c:if>
        </c:forEach>
        <c:param name="locale" value="vi" />
    </c:url>

    <ul>
        <li>
            <a href="${englishUrl}">
                <img src="<c:url value="/asset/img/flag-ca.png"/>" width="24" height="18" />
            </a>
        </li>
        <li>
            <a href="${frenchUrl}">
                <img src="<c:url value="/asset/img/flag-fr.png"/>" width="24" height="18" />
            </a>
        </li>
        <li>
            <a href="${vietnameseUrl}">
                <img src="<c:url value="/asset/img/flag-vn.png"/>" width="24" height="18" />
            </a>
        </li>
    </ul>
</div>

<div id="app-menu">
    <ul>
        <li>
            <a href="<c:url value="/home/homepage.htm" />" accesskey="1" title="">
                <img src="<c:url value="/asset/img/home.png"/>" style="vertical-align: middle;" width="20" height="18" />
                 <fmt:message key="app.home" />
            </a>
        </li>
        <c:choose>
            <c:when test="${authenticated}">
                <li>
                    <a href="<c:url value="/logout" />" accesskey="2" title="">
                        <img src="<c:url value="/asset/img/logout.png"/>" style="vertical-align: middle;" width="20" height="18" />
                         <fmt:message key="app.logout" />
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="<c:url value="/login.htm" />" accesskey="2" title="">
                        <img src="<c:url value="/asset/img/login.png"/>" style="vertical-align: middle;" width="20" height="18" />
                         <fmt:message key="app.login" />
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
