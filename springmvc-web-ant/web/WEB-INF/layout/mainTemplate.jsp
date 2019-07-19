<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="keywords" content=""/>
        <meta name="description" content=""/>
        <title><tiles:getAsString name="title"/></title>
        <link type="text/css" href="<c:url value="/asset/themes/redmond/jquery-ui-1.8.5.custom.css" />" rel="stylesheet"  />
        <link type="text/css" href="<c:url value="/asset/css/main.css" />" rel="stylesheet" />
        <link type="text/css" href="<c:url value="/asset/css/form.css" />" rel="stylesheet" />        
        <script type="text/javascript" src ="<c:url value="/asset/js/jquery-1.4.2.min.js" />"></script>
    </head>

    <body>
        <div id="container">
            <div id="header">
                <tiles:insertAttribute name="header" />
            </div>

            <div id="top-menu">
                <tiles:insertAttribute name="top-menu" />
            </div>
            <div id="left-menu">
                <tiles:insertAttribute name="left-menu" />
            </div>
            <div id="content">
                <tiles:insertAttribute name="content" />
            </div>
            <br style="clear: both;" />
            <div id="footer">
                <tiles:insertAttribute name="footer" />
            </div>
        </div>
    </body>
</html>
