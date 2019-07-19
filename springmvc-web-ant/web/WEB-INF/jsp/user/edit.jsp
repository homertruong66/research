<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
    function personClicked() {
        var cbxPerson = document.getElementById("cbxPerson")
        var cbxOrganization = document.getElementById("cbxOrganization");        
        if (cbxPerson.checked == 1) {
            cbxOrganization.checked = 0;
        }
        else {
            cbxOrganization.checked = 1;
        }
    }

    function organizationClicked() {
        var cbxPerson = document.getElementById("cbxPerson")
        var cbxOrganization = document.getElementById("cbxOrganization");        
        if (cbxOrganization.checked == 1) {
            cbxPerson.checked = 0;
        }
        else {
            cbxPerson.checked = 1;
        }
    }
</script>

<style type="text/css">
    form select {
        width: 270px;
    }
</style>

<div class="error">
    <form:errors path="userDTO.*" />
</div>

<form:form commandName="userDTO" method="post" >
    <fieldset>
        <legend><fmt:message key="user.info" /></legend>
        <c:if test="${fn:contains(formAction, 'create')}">
          <div class="required">
            <label for="username"><fmt:message key="user.username" /></label>
            <form:input path="username" cssClass="text" size="10" maxlength="66"/>
            <form:errors path="username" cssClass="error"/>            
          </div>

          <div class="required">
            <label for="password"><fmt:message key="user.password" /></label>
            <form:password path="password" cssClass="text" size="10" maxlength="66"/>
            <form:errors path="password" cssClass="error"/>
          </div>

          <div class="required">
            <label for="confirmedPassword"><fmt:message key="user.confirmedPassword" /></label>
            <form:password path="confirmedPassword" cssClass="text" size="10" maxlength="66"/>
          </div>
        </c:if>

        <c:if test="${fn:contains(formAction, 'update')}">
          <div>
              <label for="username"><fmt:message key="user.username" /></label>
              <form:input disabled="true" path="username" cssClass="text" size="10" maxlength="66"/>
          </div>

          <div>
              <label>
                  <c:url var="changePasswordUrl" value="${changePasswordUri}">
                      <c:param name="username" value="${userDTO.username}" />
                  </c:url>
                  <a href="${changePasswordUrl}">
                      <fmt:message key="user.changePassword" />
                  </a>
              </label>
              <br/>
          </div>
        </c:if>

          <div class="required">
            <label for="email"><fmt:message key="user.email" /></label>
            <form:input path="email" cssClass="text" size="10" maxlength="66"/>
            <form:errors path="email" cssClass="error"/>
          </div>

          <div>
              <label for="cbxPerson"><fmt:message key="user.person" /></label>              
              <form:checkbox id="cbxPerson" path="hasPerson" cssClass="inputCheckbox" onclick="personClicked()" />
              <form:select path="person" items="${persons}" itemLabel="fullName" itemValue="id" />
          </div>

          <div>
              <label class="rdoLabel" for="cbxOrganization"><fmt:message key="user.organization" /></label>              
              <form:checkbox id="cbxOrganization" path="hasOrganization" cssClass="inputCheckbox" onclick="organizationClicked()" />
              <form:select path="organization" items="${organizations}" itemLabel="name" itemValue="id" />
          </div>

          <div class="required">
              <label class="rdoLabel" ><fmt:message key="user.roles" /></label>
              <div class="checkboxes scroller">
                  <form:checkboxes path="roles" items="${allRoles}" />
              </div>
          </div>
    </fieldset>
    <br/>
    <div align="center">
      <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.save" />" />
    </div>
</form:form>

