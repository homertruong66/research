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

<form:form id="form" commandName="recipientSearchCommand" method="post" >
    <div id="searchInfo">
        <h2 style="font-weight:bold; color:green; text-align:center;">Recipient Search</h2>
        <br/>
        <label for="name"><fmt:message key="recipient.name" /></label>
        <form:input path="name" onchange="find();" cssClass="text" size="10" maxlength="66"/>
        <br/><br/>
        <div align="center">
            <input type="button" class="inputSubmit" value="<fmt:message key="app.search" />" onclick="find()" />
            <input type="button" class="inputSubmit" value="<fmt:message key="app.reset" />" onclick="resetSearch()" />
        </div>
        <form:hidden path="mailerId" />
        <form:hidden path="page" />        
    </div>
    <br/>
    Mailer: ${mailerName}
    <br/><br/>
    <c:if test="${pagedResult != null}" >
        <div id="searchResult">
            <div>
                <c:import url="/WEB-INF/layout/navigatorBar.jsp"  />
            </div>
            <table>
                <thead>
                    <tr>
                        <th><fmt:message key="recipient.name"/></th>
                        <th><fmt:message key="recipient.address"/></th>
                        <th><fmt:message key="app.actions"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pagedResult.pagedResults}" var="recipient">
                        <tr>
                            <td>
                                ${recipient.name}
                            </td>
                            <td>
                                ${recipient.address}
                            </td>
                            <td align="center">
                                <c:url var="updateUrl" value="${updateUri}">
                                    <c:param name="mId" value="${recipientSearchCommand.mailerId}"/>
                                    <c:param name="id" value="${recipient.id}"/>
                                </c:url>
                                <a href="${updateUrl}" target="_blank" class="action">
                                    <fmt:message key="app.update"/>
                                </a>

                                <c:url var="deleteUrl" value="${deleteUri}">
                                    <c:param name="mId" value="${recipientSearchCommand.mailerId}"/>
                                    <c:param name="id" value="${recipient.id}"/>
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
                                <c:url var="createUrl" value="${createUri}">
                                    <c:param name="mId" value="${recipientSearchCommand.mailerId}"/>                                    
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

