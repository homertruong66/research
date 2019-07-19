<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

<html>
    <head>
        <title>Login to YGS Java Framework</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />        
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta name="description" content="" />
		<meta name="keywords" content="" />
        
        <!-- Style --> 
        <link type="text/css" rel="stylesheet" href="asset/themes/redmond/jquery-ui-1.8.5.custom.css" />
        <link type="text/css" rel="stylesheet" href="asset/css/login.css" />
        
        <!-- Behavior -->
        <script type="text/javascript" src="asset/js/jquery-1.4.2.min.js"></script>        
        <script type="text/javascript">
        	$(function() {
        		$('#username').focus();
        		$('.error').hide();
        		
        		$('#btnLogin').click(function () {
            		if ($('#username').val() == "" ) {
            			$('.error').show();
                		return false;
            		}
            		else {
            			$('#btnLogin').submit();
            		}
        		});
        	});
        </script>
    </head>


	<!-- Structure -->
    <body>    	
        <div id="container" class="ui-corner-all" >
            <form method="post" action="<c:url value='j_spring_security_check'/>" >            	
                <div id="welcome">                                       
                	<label>Welcome to YGS Java Framework</label>
				</div>
				
				<div id="loginFail">                	
                    <c:if test="${not empty param.error}">
                        <label>                            
                            Login failed, please try again.<br/>
                            Reason: <%= ((AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).getMessage()%>.                                                      
                        </label>                        
                    </c:if>                    
                </div>
                
                <div id="fields">
                	<div class="field required">
                		<label for="username">Username</label>
	                    <input id="username" type='text' name='j_username' class="text"
	                     	<c:if test="${not empty param.error}">
	                            value="<%= session.getAttribute(WebAttributes.LAST_USERNAME)%>"
	                        </c:if> 
	                    />
	                    <label class="error">Username is required !</label>
                	</div>
                    
                    <div class="field required">
                    	<label for="password">Password</label>
                    	<input id="password" type='password' name='j_password' class="text" />                    	        
                    </div>
                    
                    <div id="buttonBar" >
	                    <input id="btnLogin" type="submit" class="ui-button ui-widget ui-state-default ui-corner-all button" value="Go"  />
	                    <input type="reset" class="ui-button ui-widget ui-state-default ui-corner-all button" />
                	</div>
                </div>                                
                
                <div id="help">
                	<a href="mailto:vuahoang66@gmail.com">Forgot password</a> | <a href="mailto:vuahoang66@gmail.com">Contact us</a>
                </div>
            </form>                        
        </div>
    </body>
</html>
