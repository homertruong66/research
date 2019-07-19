<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
    $(document).ready(function(){
        if (${pagedResult == null}) {
            find();
        }
    });

    function resetSearch()     {
        $("#name").val("");
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

<form:form id="form" commandName="eventSearchCommand" method="post" >
    <div id="searchInfo">
        <h2 style="font-weight:bold; color:green; text-align:center;">Event Search</h2>
        <br/>
        <label for="name"><fmt:message key="event.name" /></label>
        <form:input path="name" onchange="find();" cssClass="text" size="10" maxlength="66"/>
        <br/><br/>
        <div align="center">
            <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.search" />" onclick="find()" />
            <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.reset" />" onclick="resetSearch()" />
        </div>
        <form:hidden path="page" />
    </div>
    <br/><br/>
    <c:if test="${pagedResult != null}" >
        <div id="searchResult">
            <div>
                <c:import url="/WEB-INF/layout/navigatorBar.jsp"  />
            </div>
            <table>
                <thead>
                    <tr>
                        <th><fmt:message key="event.name"/></th>
                        <th><fmt:message key="event.date"/></th>
                        <th><fmt:message key="event.place"/></th>
                        <th><fmt:message key="event.reserver"/></th>
                        <th><fmt:message key="app.actions"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pagedResult.pagedResults}" var="event">
                        <tr>
                            <td>
                                ${event.name}
                            </td>
                            <td>
                                ${event.date}
                            </td>
                            <td>
                                ${event.place}
                            </td>
                            <td>
                                ${event.reserver}
                            </td>
                            <td align="center" width="300">
                                <c:url var="acceptUrl" value="${acceptUri}">
                                    <c:param name="eId" value="${event.id}"/>
                                </c:url>
                                <a href="${acceptUrl}" target="_blank" class="action">
                                    <fmt:message key="event.accept"/>
                                </a>

                                <c:url var="voteUrl" value="${voteUri}">
                                    <c:param name="eId" value="${event.id}"/>
                                </c:url>
                                <a href="${voteUrl}" target="_blank" class="action">
                                    <fmt:message key="event.vote"/>
                                </a>

								<sec:authorize ifAllGranted="ROLE_ADMIN">
	                                <c:url var="remindUrl" value="${remindUri}">
	                                    <c:param name="id" value="${event.id}"/>
	                                </c:url>
	                                <a href="${remindUrl}" target="_blank" class="action">
	                                    <fmt:message key="event.remind"/>
	                                </a>
	
	                                <c:url var="mailUrl" value="${mailUri}">
	                                    <c:param name="id" value="${event.id}"/>
	                                </c:url>
	                                <a href="${mailUrl}" target="_blank" class="action">
	                                    <fmt:message key="event.mail"/>
	                                </a>
	
	                                <c:url var="deleteUrl" value="${deleteUri}">
	                                    <c:param name="id" value="${event.id}"/>
	                                </c:url>
	                                <a href="${deleteUrl}" class="action" onclick="return confirmDeletion();">
	                                    <fmt:message key="app.delete"/>
	                                </a>
	                             </sec:authorize>
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

