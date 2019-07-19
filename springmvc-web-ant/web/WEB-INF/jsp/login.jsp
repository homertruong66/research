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
        <style type="text/css">			
			#loginContainer {
				margin: 20px auto 0px;	padding: 10px;;	background: #5C9CCC;	width: 350px;	
				color: white; 	 text-align: left;	font-family: Verdana, "Lucida Grande", "Lucida Sans", sans-serif; 
			}
			
			#loginContainer label {
				float: none;	display: block;	
				text-align: left;
			}
			
			#loginContainer #welcome {
				margin-bottom: 15px;	
				text-align: center;	font-weight: bold;	
			}						
			
			#loginContainer #welcome label {
				width: 100%;	text-align: center;	
			}
									
			#loginContainer #loginFail label {
				margin-bottom: 15px;	width: 100%;	
				font-size: 13px;	font-weight: bold;	color: yellow;
			}
			
			#loginContainer #fields div.field {
				margin: 10px 0;
			}
			
			#loginContainer #fields div.field input.text {
				margin: 0px; 
				background: #F0F0F0;		width: 100%;
			}
			
			#loginContainer #fields div.field label {
				width: 100px;
				font-weight: bold;		font-size: 12px;	
			}
			
			#loginContainer #fields div.field label.error {
				float: none;
				color: #FDD017;	
			}
			   
			#loginContainer #buttonBar {
				margin: 15px 0;
			   	text-align: center;   	
			}
			
			#loginContainer #buttonBar input.button {
			    padding: 3px;	width: 66px; 
			    font-size: 12px;     
			}
			
			#loginContainer  #help  {
			   	text-align: center;   	
			}
			
			.ui-corner-all {
				-moz-border-radius: 20px 20px 20px 20px;
			}        
        </style>
         
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
        <div id="loginContainer" class="ui-corner-all" >
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
	                    <label class="error">required</label>
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
