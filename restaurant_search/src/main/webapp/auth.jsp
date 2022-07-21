<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="301645777112-2rlc9gth0f5d4reimjcm9bf0kj7ahec0.apps.googleusercontent.com"
          name="google-signin-client_id">
    <title>Login / Register</title>
    
		<link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.1/css/all.css" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
    <meta name="google-signin-client_id" content="469623649869-5pv7eefs5g4hmfduqgmi55td9qt6k1ba.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <link href="index.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto"
          rel="stylesheet" type="text/css">
    <script src="https://apis.google.com/js/api:client.js"></script>
	
	
	<%
		String error = (String) request.getAttribute("error");
		String regerror = (String) request.getAttribute("regerror");
	%>
</head>
<body>
<!-- TODO -->
	<div class="container-fluid p-1">

		<div class="row">
			<%if (error != null){%>
				<div class="col pl-10 pr-10 d-flex justify-content-center text-dark bg-danger opacity-50">
				 <% out.println(error);}%>
				 </div>
				<%if (regerror != null) {%>
					<div class="col pl-10 pr-10 d-flex justify-content-center text-dark bg-danger opacity-50">
					<%out.println(regerror);%>
					</div>
				<%}%>
		</div>
		</div>
	<jsp:include page="navigation.jsp"></jsp:include>
	<div class="container-fluid p-1">
		<div class="row p-5">
			<div class="col mr-2">
				<form action="LoginDispatcher" method="GET">
					<div class="row">
						<h2>Login</h2>
					</div>
					<div class="row">
						<label for="email">Email</label>
					</div>
					<div class="row">
						<input class = "form-control" type="text" id="email" name="loginemail"></input>
					</div>
					<div class="row">
						<label for="password">Password</label>
					</div>
					<div class="row">
						<input class = "form-control" type="password" id="password" name="loginpassword"></input>
					</div>
					<div class="row">
						<button type="submit" class="btn btn-danger btn-md form-control" role="button"><i class="fas fa-sign-in-alt"></i> Sign In</button>
					</div>

					<div class="row">
  <div id="my-signin2"></div>
  <script>
    function onSuccess(googleUser) {
  	  var profile = googleUser.getBasicProfile();
      var redirectUrl = 'GoogleDispatcher';

      //using jquery to post data dynamically
      var form = $('<form action="' + redirectUrl + '" method="GET">' +
                       '<input type="hidden" name="username" value="' +
                        profile.getName() + '" />' +
                        '<input type="hidden" name="email" value="' +
                        profile.getEmail() + '" />' +
                                                             '</form>');
      $('body').append(form);
      form.submit();
      }
    function onFailure(error) {
      console.log(error);
    }
    function renderButton() {
      gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 700,
        'height': 40,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
      });
    }
  </script>				
 </div>
 				</form>
			</div>
			
			<div class="col ml-2">
				<form action="RegisterDispatcher" method="GET">
					<div class="row">
						<h2>Register</h2>
					</div>
					<div class="row">
						<label for="email">Email</label>
					</div>
					<div class="row">
						<input class = "form-control" type="text" id="email" name="regemail"></input>
					</div>
					<div class="row">
						<label for="username">Name</label>
					</div>
					<div class="row">
						<input class = "form-control" type="text" id="username" name="username"></input>
					</div>
					<div class="row">
						<label for="password">Password</label>
					</div>
					<div class="row">
						<input  class = "form-control" type="password" id="password" name="regpassword"></input>
					</div>
					<div class="row">
						<label for="confirmpass">Confirm Password</label>
					</div>
					<div class="row">
						<input  class = "form-control" type="password" id="confirmpass" name="confirmpass"></input>
					</div>
					<div class="row p-3">
					  <input class="form-check-input" type="checkbox" name="condition-rule" value="boxcheck" id="flexCheckDefault">
					    <label class="form-check-label" for="flexCheckDefault">
					    I have read and agreed to all terms and conditions of SalEats.
					  	</label>
					</div>
					<div class="row">
						<button type="submit" class="btn btn-danger btn-md form-control" role="button"><i class="fas fa-sign-in-alt"></i> Create Account</button>
					</div>
				</form>
			</div>
		</div>
	</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script> 
  <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>


</body>
</html>
