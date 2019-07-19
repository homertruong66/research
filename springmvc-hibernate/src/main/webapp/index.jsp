<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>HVN Single Page Application</title>
	</head>
	 
	<body>
		Welcome to HVN Single Page Applications
		<ol>
			<li><a href="<c:url value='spa/hvnjquery/index.jsp' />">HVN jQuery SPA</a></li>
			<li><a href="<c:url value='spa/hvnangular/index.jsp' />">HVN Angular SPA</a></li>
			<li><a href="<c:url value='spa/hvnextjs/index.jsp' />">HVN Extjs SPA</a></li>
		</ol>
	</body>
</html>
