<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<SCRIPT type="text/javascript">
    function goToFirstPage() {        
        document.getElementById("page").value = 1;
        document.getElementById("form").submit();
    }

    function goToPreviousPage() {        
        document.getElementById("page").value = ${pagedResult.pageIndex - 1};
        document.getElementById("form").submit();
    }

    function goToNextPage() {        
        document.getElementById("page").value = ${pagedResult.pageIndex + 1};
        document.getElementById("form").submit();
    }

    function goToLastPage() {        
        document.getElementById("page").value = ${pagedResult.numOfPages};
        document.getElementById("form").submit();
    }
</SCRIPT>

<div class="pager">
    <c:if test="${pagedResult.hasPreviousPage}">
        <a class="first" href="javascript: goToFirstPage()" > &lt;&lt; </a>
        <a class="previous" href="javascript: goToPreviousPage()" > &lt; </a>
    </c:if>

    Results <b>${pagedResult.firstRowIndexInPage} - ${pagedResult.lastRowIndexInPage}</b> of ${pagedResult.totalRows}
        |
    Page <b>${pagedResult.pageIndex}</b> of ${pagedResult.numOfPages}

    <c:if test="${pagedResult.hasNextPage}">        
        <a class="next" href="javascript: goToNextPage()" > &gt; </a>
        <a class="last" href="javascript: goToLastPage()" > &gt;&gt; </a>
    </c:if>
</div>
