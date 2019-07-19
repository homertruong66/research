<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="error">
    <form:errors path="itemDTO.*" />
</div>

<form:form commandName="itemDTO" method="post" >
    <fieldset>
        <legend>Item Information</legend>
        <br/>
        <div class="required">
            <label for="name">Name: </label>
            <form:input path="name" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="name" cssClass="error"/>
        </div>        
        <div class="required">
            <label for="description">Description: </label>
            <form:input path="description" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="description" cssClass="error"/>
        </div>
        <div class="required">
            <label for="dateCreated">Date Created: </label>
            <form:input path="dateCreated" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="dateCreated" cssClass="error"/>
        </div>
        <div class="required">
            <label for="dateModified">Date Modified: </label>
            <form:input path="dateModified" cssClass="text" size="10" maxlength="50"/>
            <form:errors path="dateModified" cssClass="error"/>
        </div>
        <br/>
        <div align="center">
            <c:url var="viewSentItemsUrl" value="/item/index.htm">
                <c:param name="mId" value="${itemDTO.mailerId}"/>
            </c:url>
             <input type="submit" class="inputSubmit" value="Save" />
            <a href="${viewSentItemsUrl}" class="action">Back</a>
        </div>        
        <br/>
    </fieldset>

    <div>
        <form:hidden path="mailerId" />
    </div>
</form:form>

