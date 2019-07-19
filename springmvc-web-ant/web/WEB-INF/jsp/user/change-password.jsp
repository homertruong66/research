<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="error">
    <form:errors path="userDTO.*" />
</div>

<form:form commandName="userDTO" method="post" >
    <fieldset>
        <legend><fmt:message key="user.info" /></legend>
        <div class="required">
            <label for="username"><fmt:message key="user.username" /></label>
            <form:input path="username" disabled="true" cssClass="text" size="10" maxlength="66"/>
            <form:errors path="username" cssClass="error"/>
        </div>

        <div class="required">
            <label for="currentPassword"><fmt:message key="user.changePassword.currentPassword" /></label>
            <form:password path="currentPassword" cssClass="text" size="10" maxlength="66"/>
            <form:errors path="currentPassword" cssClass="error"/>
        </div>

        <div class="required">
            <label for="password"><fmt:message key="user.changePassword.newPassword" /></label>
            <form:password path="password" cssClass="text" size="10" maxlength="66"/>
            <form:errors path="password" cssClass="error"/>
        </div>

        <div class="required">
            <label for="confirmedPassword"><fmt:message key="user.confirmedPassword" /></label>
            <form:password path="confirmedPassword" cssClass="text" size="10" maxlength="66"/>
            <br/>
        </div>
    </fieldset>
    <br/>
    <div align="center">
        <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.save" />" />
    </div>
</form:form>
