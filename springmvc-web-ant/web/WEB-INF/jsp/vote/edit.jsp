<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
    $(document).ready(function() {
        $('#date').attr("disabled", true);
        $('.user').attr("disabled", true);
    })
</script>

<script type="text/javascript">
    function selectAll () {        
        numPlaces = $('#numPlaces').val();
        for (i = 1; i <= numPlaces; i = i + 1) {
            restId = 'places' + i;
            $('#' + restId).attr("checked", true);
        }
    }

    function deselectAll () {
        numPlaces = $('#numPlaces').val();
        for (i = 1; i <= numPlaces; i = i + 1) {
            restId = 'places' + i;
            $('#' + restId).removeAttr("checked");
        }
    }
</script>

<form:form commandName="voteDTO" method="post" >
    <fieldset>
        <legend>Vote Information</legend>
        <br/>        
        <div class="required">
            <label for="date">Date: </label>
            <form:input path="date" cssClass="text" size="10" maxlength="50"/>            
        </div>
        <div class="required">
            <label for="user.username">User: </label>
            <form:input path="user.username" cssClass="text" size="10" maxlength="50"/>
        </div>
        <div class="required">
            <label>Places: </label>
            <div class="checkboxes">
                  <form:checkboxes path="places" items="${allPlaces}" />
            </div>
            <input id="numPlaces" type="hidden" value="${fn:length(allPlaces)}" />
        </div>
        <br/>
        <div align="center">
            <a href="javascript: selectAll()">
                <span style="border: 1px solid black; padding: 3px; background: silver; color: blue; font-size: 0.7em; margin-right: 6px;">
                    Select All
                </span>
            </a>
            <a href="javascript: deselectAll()">
                <span style="border: 1px solid black; padding: 3px; background: silver; color: blue; font-size: 0.7em;">
                    Deselect All
                </span>
            </a>
            <br/><br/>
            <input type="submit" class="inputSubmit" value="Save" />
        </div>
        <br/>
    </fieldset>   
</form:form>
