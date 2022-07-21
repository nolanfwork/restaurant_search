<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Home</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">		
		<link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.1/css/all.css" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="index.css">
        
    </head>

    <body>
	<jsp:include page="navigation.jsp"></jsp:include>
	<img src="banner.jpeg"/> 
	
	<div class="container-fluid">
	<form action="SearchDispatcher" action="GET">
		<div class="row pt-2">
		<!-- category part in search row -->
			<div class="col-2 p-0">
				<select class ="search_comp" name="search_by">
				<option value="category">Category</option>
				<option selected value="name">Name</option>
				</select>
			</div>
			<div class="col-6 p-0">
				<input class="search_comp" type="text" name="search_post" placeholder="find restaruant..."></input>
			</div>
			<div class="col p-1">
						<button type="submit" class="btn btn-danger btn-md" role="button"><i class="fas fa-search"></i></button>
			</div>
			<div class="col-1 p-1">
				
				<input type="radio" name="sort_type" value="Restaurant_details.estimated_price"> Price</input>
			</div>
			<div class="col-1 p-1">
				<input type="radio" name="sort_type" value="Rating_details.review_count"> Review Count</input>
			</div>
			<div class="col-1 p-1">
				<input type="radio" name="sort_type" value="Rating_details.rating"> Rating</input>
			</div>
		</div>
	</form>
		</div>
	
	</body>

    </html>