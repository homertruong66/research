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

<form:form id="form" commandName="voteSearchCommand" method="post" >
    <div id="searchInfo">
        <form:hidden path="eventId" />
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
                        <th><fmt:message key="vote.date"/></th>
                        <th><fmt:message key="vote.user"/></th>
                        <th><fmt:message key="vote.votedPlaces"/></th>
                        <th><fmt:message key="app.actions"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pagedResult.pagedResults}" var="vote">
                        <tr>
                            <td>
                                ${vote.date}
                            </td>
                            <td>
                                ${vote.user.username}
                            </td>
                            <td>
                                <ul style="padding-left: 16px;">
                                    <c:forEach items="${vote.places}" var="place">
                                        <li>${place.name}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                            <td align="center">                            	
                                <c:if test="${vote.user.username == userName}" >
                                    <c:url var="updateUrl" value="${updateUri}">
                                        <c:param name="eId" value="${voteSearchCommand.eventId}"/>
                                        <c:param name="id" value="${vote.id}"/>
                                    </c:url>
                                    <a href="${updateUrl}" class="action">Update</a>

                                    <c:url var="deleteUrl" value="${deleteUri}">
                                        <c:param name="eId" value="${voteSearchCommand.eventId}"/>
                                        <c:param name="id" value="${vote.id}"/>
                                    </c:url>
                                    <a href="${deleteUrl}" class="action" onclick="return confirmDeletion();">Delete</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>

                    <tr>
                        <td>
                            <div>
                                <c:url var="createUrl" value="${createUri}">
                                    <c:param name="eId" value="${voteSearchCommand.eventId}"/>
                                </c:url>
                                <a href="${createUrl}" target="_blank" class="action">
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



