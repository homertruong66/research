<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Structure -->
<div class="error">
    <form:errors path="organizationDTO.*" />
</div>

<form:form commandName="organizationDTO" method="post" >
    <fieldset>
      <legend><fmt:message key="organization.info" /></legend>
      <div class="field required">
        <label for="name"><fmt:message key="organization.name" /></label>
        <form:input path="name" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="name" cssClass="error"/>
      </div>

      <div class="field required">
        <label for="email"><fmt:message key="organization.email" /></label>
        <form:input path="email" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="email" cssClass="error"/>
      </div>
    </fieldset>
    <br/>
    <fieldset>
      <legend><fmt:message key="organization.address" /></legend>
      <div class="field">
        <label for="address.street"><fmt:message key="organization.street" /></label>
        <form:input path="address.street" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="address.street" cssClass="error"/>
      </div>

      <div class="field">
        <label for="address.city"><fmt:message key="organization.city" /></label>
        <form:input path="address.city" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="address.city" cssClass="error"/>
      </div>

      <div class="field">
          <label for="address.province"><fmt:message key="organization.province" /></label>
          <form:select path="address.province" cssClass="province" items="${provinces}" itemLabel="name" itemValue="id" />
      </div>

      <div class="field">
          <label for="address.country"><fmt:message key="organization.country" /></label>
          <form:select path="address.country" cssClass="country" items="${countries}" itemLabel="name" itemValue="id" />
      </div>

      <div class="field">
        <label for="address.phone"><fmt:message key="organization.phone" /></label>
        <form:input path="address.phone" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="address.phone" cssClass="error"/>
      </div>

      <div class="field">
        <label for="address.postalOrZipCode"><fmt:message key="organization.postalOrZipCode" /></label>
        <form:input path="address.postalOrZipCode" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="address.postalOrZipCode" cssClass="error"/>
      </div>
    </fieldset>
    <br/>
    <div align="center">
      <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.save" />" />
    </div>    
</form:form>


<!-- Behavior -->
<script type="text/javascript">	
	$("select.country").change(function() {		
		var countryId = this.value;			
		$.get('province.htm', 
			 { countryId: countryId	}, 
			 function(data) {				
				 $("select.province").html(data);
			 }
		);
	});	    
</script>
