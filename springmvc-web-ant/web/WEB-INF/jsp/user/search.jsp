<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
    $(document).ready(function(){
        if (${pagedResult == null}) {
            find();
        }
    });

    function resetSearch()     {
        $("#username").val("");
        find();
    }

    function find()     {
        $("#page").val("1");
        $("#form").submit();
    }

    function confirmDeletion() {
        return (confirm('<fmt:message key="app.confirmMessage" />'));
    }
</script>

<form:form id="form" commandName="userSearchCommand" method="post" >
    <div id="searchInfo">
        <h2 style="font-weight:bold; color:blue; text-align:center;">User Search</h2>
        <br/>
        <label for="username"><fmt:message key="user.username" /></label>
        <form:input path="username" onchange="find();" cssClass="text" size="10" maxlength="66"/>
        <br/><br/>
        <div align="center">
            <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.search" />" onclick="find()" />
            <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.reset" />" onclick="resetSearch()" />
        </div>
        <form:hidden path="page" />
    </div>
    <br/><br/>

    <c:if test="${pagedResult != null}" >
        <div>
            <c:import url="/WEB-INF/layout/navigatorBar.jsp"  />
        </div>
        <div id="searchResult">
            <table>
                <thead>
                    <tr>
                        <th><fmt:message key="user.username" /></th>
                        <th><fmt:message key="user.email" /></th>
                        <th><fmt:message key="user.roles" /></th>
                        <th><fmt:message key="user.status" /></th>
                        <th><fmt:message key="app.actions" /></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pagedResult.pagedResults}" var="user">
                        <tr>
                            <td>
                                ${user.username}
                            </td>
                            <td>
                                ${user.email}
                            </td>
                            <td>
                                <ul style="margin: 0 0 0 15px;">
                                    <c:forEach items="${user.userRoles}" var="userRole">
                                        <li><fmt:message key="role.${userRole.role.name}" /></li>
                                    </c:forEach>
                                </ul>
                            </td>
                            <td align="center">
                                <c:choose>
                                    <c:when test="${user.enabled}">
                                        <fmt:message key="app.enabled"/>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: grey;">
                                            <fmt:message key="app.disabled"/>
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td align="center">
                                <%-- update --%>
                                <c:url var="updateUrl" value="${updateUri}">
                                    <c:param name="username" value="${user.username}"/>
                                </c:url>
                                <a href="${updateUrl}" target="_blank" class="action">
                                    <fmt:message key="app.update"/>
                                </a>

                                <%-- enable/disable --%>
                                <c:choose>
                                    <c:when test="${user.enabled}">
                                        <c:url var="disableUrl" value="${disableUri}">
                                            <c:param name="username" value="${user.username}"/>
                                        </c:url>
                                        <a href="${disableUrl}" class="action">
                                            <fmt:message key="app.disable"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="enableUrl" value="${enableUri}">
                                            <c:param name="username" value="${user.username}"/>
                                        </c:url>
                                        <a href="${enableUrl}" class="action">
                                            <fmt:message key="app.enable"/>
                                        </a>
                                    </c:otherwise>
                                </c:choose>

                                <%-- delete --%>
                                <c:url var="deleteUrl" value="${deleteUri}">
                                    <c:param name="id" value="${user.id}"/>
                                </c:url>
                                <a href="${deleteUrl}" class="action" onclick="return confirmDeletion();">
                                    <fmt:message key="app.delete"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                    <tr>
                        <td>
                            <div>
                                <a href="<c:url value="create.htm"/>" target="_blank" class="action">
                                    <fmt:message key="app.create" />
                                </a>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div>
                <c:import url="/WEB-INF/layout/navigatorBar.jsp"  />
            </div>
        </div>
    </c:if>
</form:form>
