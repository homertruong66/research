<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
    $(document).ready(function() {
        $("#date").attr("disabled", true);
        $("#place").attr("disabled", true);
        $("#reserver").attr("disabled", true);
    })

</script>

<div class="error">
    <form:errors path="event.*" />
</div>

<form:form commandName="event" method="post" >
    <fieldset>
      <legend><fmt:message key="event.info" /></legend>
      <div class="required">
        <label for="name"><fmt:message key="event.name" /></label>
        <form:input path="name" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="name" cssClass="error"/>
      </div>

      <div class="required">
        <label for="date"><fmt:message key="event.date" /></label>
        <form:input path="date" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="date" cssClass="error"/>
      </div>

      <div class="required">
        <label for="place"><fmt:message key="event.place" /></label>
        <form:input path="place" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="place" cssClass="error"/>
      </div>

      <div class="required">
        <label for="reserver"><fmt:message key="event.reserver" /></label>
        <form:input path="reserver" cssClass="text" size="10" maxlength="66"/>
        <form:errors path="reserver" cssClass="error"/>
      </div>
    </fieldset>
    <br/>
    
    <div align="center">
      <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="<fmt:message key="app.save" />" />
    </div>
</form:form>

