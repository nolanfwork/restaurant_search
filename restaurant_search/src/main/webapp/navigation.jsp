<!DOCTYPE html>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.Cookie"%>

<%@ page import="java.io.IOException" %>
<%@ page import="java.io.Serial" %>

<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
            <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="index.css">
            <meta name="google-signin-client_id" content="469623649869-5pv7eefs5g4hmfduqgmi55td9qt6k1ba.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
        
        <%Cookie[] cookies = request.getCookies();
        String username = "";
        String email = "";
        Boolean google_login = false;
        if (cookies != null){
	         for (Cookie x: cookies) {
	        	 if(x.getName().contentEquals("google_login")){
	        		 if (x.getValue().contentEquals("itisset")){
		        		 google_login = true;
	        		 }
	        		 else {
	        			 google_login = false;
	        		 }
	        	 }
				if (x.getName().contentEquals("username")){
					username = x.getValue();
					if (username.contains("%")) {
						StringBuffer stbf = new StringBuffer(username);
						for (int i = 0; i < stbf.length(); i++) {
							if (stbf.charAt(i) == '%') {
								stbf.replace(i, i+1, " ");
							}
						}
						username = stbf.toString();
					}
				}
				if (x.getName().contentEquals("email")){
					email = x.getValue();
				}
	         }
       	 }
         %>
</head>
<body>
<nav class="container-fluid p-1">
	<div class="row">
	        <div class="col-6 header d-flex justify-content pt-2">
				<h1>
	            	<a class="p-2 text-danger" href="../PA2/index.jsp">SalEats!</a>
				</h1>
			</div>
			<%if (!username.contentEquals("")) {%>
						<div class="col-2 d-flex flex-row-inverse justify-content pt-2">
			
				<h6> Hi <%=username%>!</h6>
							</div>
				<%} %>
			<div class="col-2 d-flex flex-row-inverse justify-content pt-2">
				<h6>
	            	<a class="p-2 text-secondary" href="../PA2/index.jsp">Home</a>
				</h6>
			</div>
			<div class="col-2 d-flex flex-row-inverse justify-content pt-2">
			<%if (username == null || username.contentEquals("")) {%>
				<h6>
	            	<a class="p-2 text-secondary" href="../PA2/auth.jsp">Login/Register</a>
				</h6>
				<%;} else{%>
				<h6>
				<% if (!google_login) {%>
	            	<a class="p-2 text-secondary" href="LogoutDispatcher">Logout</a>
	            	<%} else { %>
	            	<a href="#" onclick="signOut();">Sign out</a>
	            	<%} %>
				</h6>
				<%;} %>
			</div>
	</div>
		<hr></hr>
	
</nav>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script> 
<script>
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
    var redirectUrl = 'LogoutDispatcher';

    //using jquery to post data dynamically
    var form = $('<form action="' + redirectUrl + '" method="GET">' + '</form>');
    $('body').append(form);
    form.submit();
<%--     <%google_login = false;%>
 --%>  }
function onLoad() {
    gapi.load('auth2', function() {
      gapi.auth2.init();
    });
  }
</script>

</body>
</html>