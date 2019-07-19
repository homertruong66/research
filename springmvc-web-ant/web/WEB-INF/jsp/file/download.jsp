<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2 style="text-align: center; color: green; font-weight: bold;">List of files to download</h2>
<ol style="padding: 16px; font-size: 12px;">
	<c:forEach items="${files}" var="file">
		<li style="margin: 2px;">
			<c:url var="downloadFileUrl" value="download-file.htm">
				<c:param name="name" value="${file}" />
			</c:url> 
			<a href="${downloadFileUrl}" target="_blank">${file}</a>
		</li>
	</c:forEach>
</ol>