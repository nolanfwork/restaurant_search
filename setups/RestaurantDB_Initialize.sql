DROP DATABASE IF EXISTS Restaurant_Data;
 CREATE DATABASE Restaurant_Data;USE Restaurant_Data;
 CREATE TABLE Restaurant_details( details_id INT PRIMARY KEY AUTO_INCREMENT, image_url VARCHAR(500), address VARCHAR(100), phone_no VARCHAR(20), estimated_price VARCHAR(5), yelp_url VARCHAR(500), CONSTRAINT details UNIQUE(image_url));
 CREATE TABLE Category(category_id INT PRIMARY KEY AUTO_INCREMENT,category_name VARCHAR(50) NOT NULL,CONSTRAINT cat UNIQUE (category_name));
 CREATE TABLE Bridge_table(category_id INT,restaurant_id VARCHAR(200), CONSTRAINT bridge PRIMARY KEY (category_id, restaurant_id));
 CREATE TABLE Rating_details(rating_id INT PRIMARY KEY AUTO_INCREMENT,review_count INT,rating FLOAT);
 CREATE TABLE Restaurant(restaurant_id VARCHAR(200) PRIMARY KEY,restaurant_name VARCHAR(50) NOT NULL,details_id INT NOT NULL,rating_id INT NOT NULL,CONSTRAINT cat UNIQUE (restaurant_id), FOREIGN KEY (details_id) REFERENCES Restaurant_details(details_id),FOREIGN KEY (rating_id) REFERENCES Rating_details(rating_id)); 
 CREATE TABLE Account(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(200),
    email VARCHAR(200),
    password VARCHAR(200),
    CONSTRAINT account UNIQUE (email)
 );
