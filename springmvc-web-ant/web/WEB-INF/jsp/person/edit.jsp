<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Structure -->
<div class="error">
    <form:errors path="personDTO.*" />
</div>

<form:form commandName="personDTO" method="post" >
    <fieldset>
      <legend><fmt:message key="person.info" /></legend>
      <div class="required">
        <label for="firstName"><fmt:message key="person.firstname" /></label>
        <form:input path="firstName" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="firstName" cssClass="error"/>
      </div>

      <div class="required">
        <label for="lastName"><fmt:message key="person.lastname" /></label>
        <form:input path="lastName" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="lastName" cssClass="error"/>
      </div>

      <div class="optional">
        <label for="sex"><fmt:message key="person.sex" /></label>
        <form:select id="sex" path="sex">
            <form:option value="Male"><fmt:message key="person.male" /></form:option>
            <form:option value="Female"><fmt:message key="person.female" /></form:option>
        </form:select>
      </div>

      <div class="optional">
        <label for="ethnicGroup"><fmt:message key="person.ethnicGroup" /></label>
        <form:select id="ethnicGroup" path="ethnicGroup">
            <form:option value="Asian"><fmt:message key="person.asian" /></form:option>
            <form:option value="African"><fmt:message key="person.african" /></form:option>
            <form:option value="AfricanAmerican"><fmt:message key="person.africanAmerican" /></form:option>
            <form:option value="European"><fmt:message key="person.european" /></form:option>
            <form:option value="LatinAmerican"><fmt:message key="person.latinAmerican" /></form:option>
            <form:option value="NativeAmerican"><fmt:message key="person.nativeAmerican" /></form:option>
            <form:option value="MiddleEastern"><fmt:message key="person.middleEastern" /></form:option>
            <form:option value="Other"><fmt:message key="person.other" /></form:option>
        </form:select>
      </div>

      <div class="required">
        <label for="email"><fmt:message key="person.email" /></label>
        <form:input path="email" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="email" cssClass="error"/>
      </div>
    </fieldset>
    <br/>
    <fieldset>
      <legend><fmt:message key="person.homeAddress" /></legend>
      <div class="optional">
        <label for="homeAddress.street"><fmt:message key="person.street" /></label>
        <form:input path="homeAddress.street" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="homeAddress.street" cssClass="error"/>
      </div>

      <div class="optional">
        <label for="homeAddress.city"><fmt:message key="person.city" /></label>
        <form:input path="homeAddress.city" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="homeAddress.city" cssClass="error"/>
      </div>

      <div id="homeProvinceDiv" class="optional">
          <label for="homeAddress.province"><fmt:message key="person.province" /></label>
          <form:select path="homeAddress.province" cssClass="homeProvince" items="${homeProvinces}" itemLabel="name" itemValue="id" />
      </div>

      <div id="homeCountryDiv" class="optional">
          <label for="homeAddress.country"><fmt:message key="person.country" /></label>
          <form:select path="homeAddress.country" cssClass="homeCountry" items="${countries}" itemLabel="name" itemValue="id" />
      </div>

      <div class="optional">
        <label for="homeAddress.phone"><fmt:message key="person.phone" /></label>
        <form:input path="homeAddress.phone" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="homeAddress.phone" cssClass="error"/>
      </div>

      <div class="optional">
        <label for="homeAddress.postalOrZipCode"><fmt:message key="person.postalOrZipCode" /></label>
        <form:input path="homeAddress.postalOrZipCode" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="homeAddress.postalOrZipCode" cssClass="error"/>
      </div>
    </fieldset>
    <br/>
    <fieldset>
      <legend><fmt:message key="person.workAddress" /></legend>
      <div class="optional">
        <label for="workAddress.street"><fmt:message key="person.street" /></label>
        <form:input path="workAddress.street" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="workAddress.street" cssClass="error"/>
      </div>

      <div class="optional">
        <label for="workAddress.city"><fmt:message key="person.city" /></label>
        <form:input path="workAddress.city" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="workAddress.city" cssClass="error"/>
      </div>

      <div id="workProvinceDiv" class="optional">
          <label for="workAddress.province"><fmt:message key="person.province" /></label>
          <form:select path="workAddress.province" cssClass="workProvince" items="${workProvinces}" itemLabel="name" itemValue="id" />
      </div>

      <div id="workCountryDiv" class="optional">
          <label for="workAddress.country"><fmt:message key="person.country" /></label>
          <form:select path="workAddress.country" cssClass="workCountry" items="${countries}" itemLabel="name" itemValue="id" />
      </div>

      <div class="optional">
        <label for="workAddress.phone"><fmt:message key="person.phone" /></label>
        <form:input path="workAddress.phone" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="workAddress.phone" cssClass="error"/>
      </div>

      <div class="optional">
        <label for="workAddress.postalOrZipCode"><fmt:message key="person.postalOrZipCode" /></label>
        <form:input path="workAddress.postalOrZipCode" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="workAddress.postalOrZipCode" cssClass="error"/>
      </div>      
    </fieldset>
    <br/>
    <div align="center">
      <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.save" />" />
    </div>
</form:form>


<!-- Behavior -->
<script type="text/javascript">
	$("select.homeCountry").change(function() {		
		var countryId = this.value;			
		$.get('province.htm', 
			 { countryId: countryId	}, 
			 function(data) {				
				 $("select.homeProvince").html(data);
			 }
		);
	});	   

	$("select.workCountry").change(function() {		
		var countryId = this.value;			
		$.get('province.htm', 
			 { countryId: countryId	}, 
			 function(data) {				
				 $("select.workProvince").html(data);
			 }
		);
	});	   
</script>
