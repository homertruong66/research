<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="error">
    <form:errors path="mail.*" />
</div>

<form:form commandName="mail" method="post" >
    <fieldset>
        <legend> Mail Information </legend>               
        <div class="required">
            <label for="host">Your Host:</label>
            <form:input path="host" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="host" cssClass="error"/>
        </div>

        <div class="required">
            <label for="username">Username:</label>
            <form:input path="username" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="username" cssClass="error"/>
        </div>

        <div class="required">
            <label for="password">Password:</label>
            <form:input path="password" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="password" cssClass="error"/>
        </div>

        <div class="required">
            <label for="senderHost">Sender Host:</label>
            <form:input path="senderHost" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="senderHost" cssClass="error"/>
        </div>

        <div class="required">
            <label for="senderAddress">Sender Address:</label>
            <form:input path="senderAddress" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="senderAddress" cssClass="error"/>
        </div>

        <div class="optional">
            <label for="senderName">Sender Name:</label>
            <form:input path="senderName" cssClass="text" size="10" maxlength="50"/>            
        </div>

        <div class="required">
            <label for="subject">Subject:</label>
            <form:input path="subject" cssClass="text" size="10" maxlength="50" />
            <form:errors path="subject" cssClass="error"/>
        </div>

        <div class="required">
            <label for="timeInterval">Time Interval (s):</label>
            <form:input path="timeInterval" cssClass="text" size="6" maxlength="6"  />
            <form:errors path="timeInterval" cssClass="error"/>
        </div>       
        <br/>
        <div align="center">
            <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="Save" />
        </div>
        <br/>
    </fieldset>    
</form:form>
