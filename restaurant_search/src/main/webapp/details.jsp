<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="PA2.Business, PA2.RestaurantDataParser"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Details</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/3204349982.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="index.css">
</head>
<%
    //TODO perform search
	String id = request.getParameter("Id");
	Business.Businesses business = RestaurantDataParser.getBusiness(id);
    //TODO check if logged in
%>

<body>
<!-- TODO -->
	<jsp:include page="navigation.jsp"></jsp:include>
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
				<div class="container-fluid">
		<div class="row">
			<h1><%=business.getName() %></h1>			
		</div>
					<div class="row mt-1 mb-1 p-1 d-flex justify-content-center">
				<div class="col">
					<img class="img-fluid" src="<%=business.getImageUrl()%>" alt="restaurant_pic">
				</div>
				<div class="col">
					<div class="row">
						<p class="text-secondary">Address: <%=business.getLocation() %></p>
					</div>
					<div class="row">
						<p class="text-secondary"><%=business.getPhone()%></p>
					</div>
					<div class="row">
						<p class="text-secondary">Categories: 
						<% for (int i = 0; i < business.getCategories().length; i++){ %>
						<%if (i + 1 == business.getCategories().length){%>
						<%=business.getCategories()[i].title %>
						<%} else{ %>
						<%=business.getCategories()[i].title %>,
						<%}} %>
						</p>
					</div>
					<div class="row">
						<p class="text-secondary">Price: <%=business.getPrice() %></p>
					</div>
					<div class="row"><p>Rating: </p>
 					<%Double rating = business.getRating(); while(rating > 0){%>
 					<% if (rating == 0.5)  {%>
 					<i class="fa fa-star-half-full"></i>
 					
 					<%} else{%>
 						<span class="fa fa-star checked"></span>
 					<%} rating -= 1;} %>
 					</div>
				</div>
			</div>
			<hr></hr>
		
		<hr></hr>




	</div>

</body>
</html>