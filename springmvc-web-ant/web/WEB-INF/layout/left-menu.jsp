<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${!authenticated}">
    <script type="text/javascript">
        $(document).ready(function() {            
           $("#left-menu").hide();
           $("#content").css({"margin-left":"0"});
        });
    </script>
</c:if>

<c:if test="${authenticated}">
    <sec:authorize ifAllGranted="ROLE_ADMIN">
        <div id="admin-menu">
            <div id="menu-title" >
                <h3><fmt:message key="app.admin" /></h3>
            </div>
            <ul>
                <li>
                    <a href="<c:url value="/person/index.htm"/>">
                        <img src="<c:url value="/asset/img/person.png"/>" style="vertical-align: middle;" width="16" height="16" />
                        <fmt:message key="person.text" />
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/organization/index.htm"/>">
                        <img src="<c:url value="/asset/img/org.png"/>" style="vertical-align: middle;" width="16" height="16" />
                        <fmt:message key="organization.text" />
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/user/index.htm"/>">
                        <img src="<c:url value="/asset/img/user.png"/>" style="vertical-align: middle;" width="16" height="16" />
                        <fmt:message key="user.text" />
                    </a>
                </li>
                 <li>
                    <a href="<c:url value="/role/index.htm"/>">
                        <img src="<c:url value="/asset/img/role.png"/>" style="vertical-align: middle;" width="16" height="16" />
                        <fmt:message key="role.text" />
                    </a>
                </li>
            </ul>
        </div>
        <br/>
    </sec:authorize>
	
    <div id="event-menu">
        <div id="menu-title" >
            <h3><fmt:message key="event.text" /></h3>
        </div>
        <ul>
            <li>
                <a href="<c:url value="/place/index.htm"/>">
                    <img src="<c:url value="/asset/img/place.png"/>" style="vertical-align: middle;" width="16" height="16" />
                    <fmt:message key="place.text" />
                </a>
            </li>
            <li>
                <a href="<c:url value="/event/index.htm"/>">
                    <img src="<c:url value="/asset/img/event.png"/>" style="vertical-align: middle;" width="16" height="16" />
                    <fmt:message key="event.text" />
                </a>
            </li>
        </ul>
    </div>
    
    <br/>    
    <div id="mail-menu">
        <div id="menu-title" >
            <h3><fmt:message key="mail.text" /></h3>
        </div>
        <ul>
            <li>
                <a href="<c:url value="/mail/update.htm"/>">
                    <img src="<c:url value="/asset/img/mail.png"/>" style="vertical-align: middle;" width="16" height="16" />
                    <fmt:message key="mail.text" />
                </a>
            </li>
            <li>
                <a href="<c:url value="/mailer/index.htm"/>">
                    <img src="<c:url value="/asset/img/mailer.png"/>" style="vertical-align: middle;" width="16" height="16" />
                    <fmt:message key="mailer.text" />
                </a>
            </li>
        </ul>
    </div>
    
    <br/>
    <div id="file-menu">
        <div id="menu-title" >
            <h3><fmt:message key="file.text" /></h3>
        </div>
        <ul>
            <li>
                <a href="<c:url value="/file/upload.htm"/>">
                    <img src="<c:url value="/asset/img/upload.png"/>" style="vertical-align: middle;" width="16" height="16" />
                    <fmt:message key="upload.text" />
                </a>
            </li>
            <li>
                <a href="<c:url value="/file/download.htm"/>">
                    <img src="<c:url value="/asset/img/download.png"/>" style="vertical-align: middle;" width="16" height="16" />
                    <fmt:message key="download.text" />
                </a>
            </li>
        </ul>
    </div>
</c:if>
