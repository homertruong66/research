<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="error">
    <form:errors path="place.*" />
</div>

<form:form commandName="place" method="post" >
    <fieldset>
      <legend><fmt:message key="place.info" /></legend>
      <div class="required">
        <label for="name"><fmt:message key="place.name" /></label>
        <form:input path="name" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="name" cssClass="error"/>
      </div>

      <div class="required">
        <label for="activities"><fmt:message key="place.activities" /></label>
        <form:input path="activities" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="activities" cssClass="error"/>
      </div>

      <div class="required">
        <label for="address"><fmt:message key="place.address" /></label>
        <form:input path="address" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="address" cssClass="error"/>
      </div>

      <div class="required">
        <label for="phone"><fmt:message key="place.phone" /></label>
        <form:input path="phone" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="phone" cssClass="error"/>
      </div>
    </fieldset>
    <br/>
    
    <div align="center">
      <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.save" />" />
    </div>
</form:form>

