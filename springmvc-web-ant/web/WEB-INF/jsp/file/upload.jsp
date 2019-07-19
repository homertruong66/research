<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="text-align: center;">
	<h2 style="color: green; font-weight: bold;">Please upload a file</h2>
	<br/>
	<form method="post" action="<c:url value="/file/upload.htm" />" enctype="multipart/form-data">
		<div>
			<label for="name">Name </label>
			<input type="text" name="name" class="text" />
		</div>
		<div>
			<label for="file">File</label>
			<input type="file" name="file" class="text" />
		</div>
		<br/>
		<div align="center">
			<input type="submit" value="Upload" class="ui-button ui-widget ui-state-default ui-corner-all button" />
		</div>
	</form>
	<br/>
	<span style="color: green">${done}</span>
</div>