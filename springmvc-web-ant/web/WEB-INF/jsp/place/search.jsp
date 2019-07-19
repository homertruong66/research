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

<form:form id="form" commandName="placeSearchCommand" method="post" >
    <div id="searchInfo">
        <h2 style="font-weight:bold; color:green; text-align:center;">Place Search</h2>
        <br/>
        <label for="name"><fmt:message key="place.name" /></label>
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
                        <th><fmt:message key="place.name"/></th>
                        <th><fmt:message key="place.activities"/></th>
                        <th><fmt:message key="place.address"/></th>
                        <th><fmt:message key="place.phone"/></th>
                        <th><fmt:message key="app.actions"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pagedResult.pagedResults}" var="place">
                        <tr>
                            <td>
                                ${place.name}
                            </td>
                            <td>
                                ${place.activities}
                            </td>
                            <td>
                                ${place.address}
                            </td>
                            <td>
                                ${place.phone}
                            </td>
                            <td align="center" width="150">
                                <c:url var="updateUrl" value="${updateUri}">
                                    <c:param name="id" value="${place.id}"/>
                                </c:url>
                                <a href="${updateUrl}" target="_blank" class="action">
                                    <fmt:message key="app.update"/>
                                </a>

                                <c:url var="deleteUrl" value="${deleteUri}">
                                    <c:param name="id" value="${place.id}"/>
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

