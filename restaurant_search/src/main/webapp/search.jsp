<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="PA2.Business, PA2.RestaurantDataParser"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">		
		<link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Roboto:wght@300&display=swap"
            rel="stylesheet">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
            
        <link rel="stylesheet" type="text/css" href="index.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.1/css/all.css" crossorigin="anonymous">

    <%
        //TODO perform search
    	String search_type = request.getParameter("search_by");
    	String search_post = request.getParameter("search_post");
    	String sort_type = request.getParameter("sort_type");
    	ArrayList<Business.Businesses> businesses = RestaurantDataParser.getBusinesses(search_post, sort_type, search_type);
        //TODO check if logged in
    %>
</head>
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
			<h1>Results for <%=search_post %> in <%=search_type %></h1>			
		</div>
		<hr></hr>

			<% for (int i = 0; i < businesses.size(); i++){ %>			
			<div class="row mt-1 mb-1 p-1 d-flex justify-content-center">
				<div class="col">
					<img class="img-fluid" src="<%=businesses.get(i).getImageUrl()%>" alt="restaurant_pic">
				</div>
				<div class="col">
					<div class="row">
						<h3><a class="text-info" href="details.jsp?Id=<%=businesses.get(i).getId()%>"><%=businesses.get(i).getName() %></a></h3>
					</div>
					<div class="row">
						<p class="text-secondary">Price: <%=businesses.get(i).getPrice() %></p>
					</div>
					<div class="row">
						<p class="text-secondary">Review Count: <%=businesses.get(i).getReviewCount() %></p>
					</div>
					<div class="row"><p>Rating: </p>
 					<%Double rating = businesses.get(i).getRating(); while(rating > 0){%>
 					<% if (rating == 0.5)  {%>
 					<i class="fa fa-star-half-full"></i>
 					
 					<%} else{%>
 						<span class="fa fa-star checked"></span>
 					<%} rating -= 1;} %>
 					</div>
					<div class="row">
						<a class="text-secondary" href="<%=businesses.get(i).getUrl()%>">yelp link</a>
					</div>
				</div>
			</div>
			<hr></hr>

			<%} %>

	</div>
</body>
</html>