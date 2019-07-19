<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="error">
    <form:errors path="recipientDTO.*" />
</div>

<form:form commandName="recipientDTO" method="post" >
    <fieldset>
        <legend><fmt:message key="recipient.info" /></legend>
        <br/>
        <div class="required">
            <label for="name"><fmt:message key="recipient.name" /> </label>
            <form:input path="name" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="name" cssClass="error"/>
        </div>
        <div class="required">
            <label for="address"><fmt:message key="recipient.address" /> </label>
            <form:input path="address" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="address" cssClass="error"/>
        </div>
        
        <div align="center">
            <input type="submit" class="inputSubmit" value="Save" />
        </div>
        <br/>
    </fieldset>

    <div>
        <form:hidden path="mailerId" />
    </div>
</form:form>

