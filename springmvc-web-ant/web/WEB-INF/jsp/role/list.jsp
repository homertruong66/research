<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
    function confirmDeletion() {   
        return (confirm('Are you sure you want to delete this role?'));
    }
</script>

<div>        
    <div>
        <table>
            <thead>
                <tr>
                    <th>Name</th>                    
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pagedResult.pagedResults}" var="role">
                    <tr>
                        <td style="font-weight:bold;">
                            ${role.name}
                        </td>
                        <td align="center">
                            <c:if test="${role.name != 'ROLE_ADMIN'}">
                                <c:url var="updateUrl" value="${updateUri}">
                                    <c:param name="id" value="${role.id}"/>
                                </c:url>
                                <a href="${updateUrl}" class="action">Modify</a>

                                <c:url var="deleteUrl" value="${deleteUri}">
                                    <c:param name="id" value="${role.id}"/>
                                </c:url>
                                <a href="${deleteUrl}" class="action" onclick="return confirmDeletion();">Delete</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>

                <tr>
                    <td>
                        <div>
                            <a href="<c:url value="${createUri}" />" class="action">Create</a>
                        </div>
                    </td>                    
                </tr>
            </tbody>
        </table>
        
		<c:if test="${pagedResult.hasPreviousPage}">
	    	<c:url var="firstUrl" value="${indexUrl}">
	        	<c:param name="page" value="1"/>
	        </c:url>        
	        <a class="action" href="${firstUrl}" > &lt;&lt; </a>
	        
	        <c:url var="previousUrl" value="${indexUrl}">
	        	<c:param name="page" value="${pagedResult.pageIndex - 1}"/>
	        </c:url>
	        <a class="action" href="${previousUrl}" > &lt; </a>
	    </c:if>
	    Role <b>${pagedResult.firstRowIndexInPage} - ${pagedResult.lastRowIndexInPage}</b> of ${pagedResult.totalRows}
	        |
	    Page <b>${pagedResult.pageIndex}</b> of ${pagedResult.numOfPages}
	    <c:if test="${pagedResult.hasNextPage}">        
	    	<c:url var="nextUrl" value="${indexUrl}">
	        	<c:param name="page" value="${pagedResult.pageIndex + 1}"/>
	        </c:url>                
	        <a class="action" href="${nextUrl}" > &gt; </a>
	        
	        <c:url var="lastUrl" value="${indexUrl}">
	        	<c:param name="page" value="${pagedResult.numOfPages}"/>
	        </c:url>
	        <a class="action" href="${lastUrl}" > &gt;&gt; </a>
	    </c:if>        
    </div>
</div>

