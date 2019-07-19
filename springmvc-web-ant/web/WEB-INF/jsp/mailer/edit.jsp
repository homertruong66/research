<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="error">
    <form:errors path="mailer.*" />
</div>

<form:form commandName="mailer" method="post" >
    <fieldset>
        <legend>Mailer Information</legend>
        <br/>
        <div align="left" style="font-size: 0.8em;" >
            <b>Bold label</b> indicates required field.
        </div>
        <br/>
        <div class="required">
            <label for="name">Name: </label>
            <form:input path="name" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="name" cssClass="error"/>
        </div>
        
        <div align="center">
            <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="Save" />
        </div>
        <br/>
    </fieldset>   
</form:form>

