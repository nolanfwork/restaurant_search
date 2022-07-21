package PA2;


import com.google.gson.*;

import java.sql.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
/**
 * A class that pretends to be the Yelp API
 */
public class RestaurantDataParser {
    private static Boolean ready = false;
    static public Business business;
    /**
     * Initializes the DB with json data
     *
     * @param responseString the Yelp json string
     * @throws JsonParseException 
     */
    public static void Init(String responseString) {
        if (ready) {
            return;
        }
        try {
			Class.forName("com.google.gson.Gson");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Gson gson = new Gson();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //TODO check if you've done the initialization
            business = gson.fromJson(responseString, Business.class);
            for(int i = 0; i < business.businesses.length; i++){
            	if (business.businesses[i].getId()==null) {
            		throw new ClassNotFoundException();
            	}
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ready = true;


        try (Connection conn = DriverManager.getConnection(Constant.DBLocation, Constant.DBUserName, Constant.DBPassword);
            	Statement st = conn.createStatement();)
        {        	
        	String insertion1 = "", insertion2 = "", insertion3 = "", insertion4 = "", insertion5 = "";
        	for (int i = 0; i < business.businesses.length; i++) {
        		insertion1 = " INSERT IGNORE INTO Restaurant_details (image_url, address, phone_no, estimated_price, yelp_url)"
        				+ " VALUES (\"" + business.businesses[i].getImageUrl()+"\",\""+business.businesses[i].getLocation()+"\",\""+business.businesses[i].getPhone()+"\",\""+business.businesses[i].getPrice()+"\",\""+business.businesses[i].getUrl()+"\");";
        		insertion2 = " INSERT INTO Rating_details(review_count, rating)"
        				+ " VALUES("+business.businesses[i].getReviewCount()+","+business.businesses[i].getRating()+");";
            	st.executeUpdate(insertion1);
            	st.executeUpdate(insertion2);

        				for (int j = 0; j < business.businesses[i].getCategories().length; j++) {
        					insertion3 = "INSERT IGNORE INTO Category (category_name)"
        							+ " VALUES (\""+business.businesses[i].getCategories()[j].title+"\");";
        	            	st.executeUpdate(insertion3);
        				}
        				insertion4 = " INSERT IGNORE INTO Restaurant (restaurant_id, restaurant_name, details_id, rating_id)"
        						+ " SELECT \""+business.businesses[i].getId()+"\", \""+business.businesses[i].getName()+"\", d.details_id, r.rating_id"
    							+ " FROM Restaurant_details d, Rating_details r"
    							+ " WHERE d.image_url = \""+business.businesses[i].getImageUrl()+"\" AND r.review_count = \""+business.businesses[i].getReviewCount() 
        						+ "\" ;";
                    	st.executeUpdate(insertion4);

        				for (int k = 0; k < business.businesses[i].getCategories().length; k++) {
        				insertion5 = " INSERT IGNORE INTO Bridge_table (category_id, restaurant_id)"
        						+ " SELECT Category.category_id, Restaurant.restaurant_id FROM"
        						+ " Category, Restaurant WHERE Category.category_name = \""+business.businesses[i].getCategories()[k].title+"\" AND"
        								+ " Restaurant.restaurant_name = \""+business.businesses[i].getName()+"\";";
                    	st.executeUpdate(insertion5);

        				}
        	}
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public static Business.Businesses getBusiness(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            for (int i = 0; i < business.businesses.length; i++) {
            	if (business.businesses[i].getId().contentEquals(id)) {
            		return business.businesses[i];
            	}
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param keyWord    the search keyword
     * @param sort       the sort option (price, review count, rating)
     * @param searchType search in category or name
     * @return the list of business matching the criteria
     */
    public static ArrayList<Business.Businesses> getBusinesses(String keyWord, String sort, String searchType) {
        ArrayList<Business.Businesses> busisnesses = new ArrayList<Business.Businesses>();
        String sql = "";
      
    	if (searchType.contentEquals("category")) {
            if (sort == null) {
            	sql = "SELECT Restaurant.restaurant_id"
            			+ " FROM Restaurant JOIN Bridge_table ON Restaurant.restaurant_id = Bridge_table.restaurant_id"
            			+ " JOIN Category ON Bridge_table.category_id = Category.category_id"
            			+ " JOIN Restaurant_details ON Restaurant.details_id = Restaurant_details.details_id"
            			+ " JOIN Rating_details ON Restaurant.rating_id = Rating_details.rating_id"
            			+ " WHERE Category.category_name LIKE ? GROUP BY Restaurant.restaurant_id ORDER BY Restaurant.restaurant_name ASC;";
            	}
            else if (sort.contentEquals("Restaurant_details.estimated_price")) {
            	sql = "SELECT Restaurant.restaurant_id"
            			+ " FROM Restaurant JOIN Bridge_table ON Restaurant.restaurant_id = Bridge_table.restaurant_id"
            			+ " JOIN Category ON Bridge_table.category_id = Category.category_id"
            			+ " JOIN Restaurant_details ON Restaurant.details_id = Restaurant_details.details_id"
            			+ " JOIN Rating_details ON Restaurant.rating_id = Rating_details.rating_id"
            			+ " WHERE Category.category_name LIKE ? GROUP BY Restaurant.restaurant_id ORDER BY Restaurant_details.estimated_price ASC;";
            	}
        	else if (sort.contentEquals("Rating_details.rating")) {
            	sql = "SELECT Restaurant.restaurant_id"
            			+ " FROM Restaurant JOIN Bridge_table ON Restaurant.restaurant_id = Bridge_table.restaurant_id"
            			+ " JOIN Category ON Bridge_table.category_id = Category.category_id"
            			+ " JOIN Restaurant_details ON Restaurant.details_id = Restaurant_details.details_id"
            			+ " JOIN Rating_details ON Restaurant.rating_id = Rating_details.rating_id"
            			+ " WHERE Category.category_name LIKE ? GROUP BY Restaurant.restaurant_id ORDER BY Rating_details.rating DESC;";
            	}
        	else if (sort.contentEquals("Rating_details.review_count")) {
            	sql = "SELECT Restaurant.restaurant_id"
            			+ " FROM Restaurant JOIN Bridge_table ON Restaurant.restaurant_id = Bridge_table.restaurant_id"
            			+ " JOIN Category ON Bridge_table.category_id = Category.category_id"
            			+ " JOIN Restaurant_details ON Restaurant.details_id = Restaurant_details.details_id"
            			+ " JOIN Rating_details ON Restaurant.rating_id = Rating_details.rating_id"
            			+ " WHERE Category.category_name LIKE ? GROUP BY Restaurant.restaurant_id ORDER BY Rating_details.review_count DESC;";
            	}
        }
        else if (searchType.contentEquals("name")) {
            if (sort == null) {
            	sql = "SELECT Restaurant.restaurant_id"
            			+ " FROM Restaurant JOIN Bridge_table ON Restaurant.restaurant_id = Bridge_table.restaurant_id"
            			+ " JOIN Category ON Bridge_table.category_id = Category.category_id"
            			+ " JOIN Restaurant_details ON Restaurant.details_id = Restaurant_details.details_id"
            			+ " JOIN Rating_details ON Restaurant.rating_id = Rating_details.rating_id"
            			+ " WHERE Restaurant.Restaurant_name LIKE ? GROUP BY Restaurant.restaurant_id ORDER BY Restaurant.restaurant_name ASC;";
            	}
        	else if (sort.contentEquals("Restaurant_details.estimated_price")) {
        	sql = "SELECT Restaurant.restaurant_id"
        			+ " FROM Restaurant JOIN Bridge_table ON Restaurant.restaurant_id = Bridge_table.restaurant_id"
        			+ " JOIN Category ON Bridge_table.category_id = Category.category_id"
        			+ " JOIN Restaurant_details ON Restaurant.details_id = Restaurant_details.details_id"
        			+ " JOIN Rating_details ON Restaurant.rating_id = Rating_details.rating_id"
        			+ " WHERE Restaurant.Restaurant_name LIKE ? GROUP BY Restaurant.restaurant_id ORDER BY Restaurant_details.estimated_price ASC;";
        	}
        	else if (sort.contentEquals("Rating_details.rating")) {
            	sql = "SELECT Restaurant.restaurant_id"
            			+ " FROM Restaurant JOIN Bridge_table ON Restaurant.restaurant_id = Bridge_table.restaurant_id"
            			+ " JOIN Category ON Bridge_table.category_id = Category.category_id"
            			+ " JOIN Restaurant_details ON Restaurant.details_id = Restaurant_details.details_id"
            			+ " JOIN Rating_details ON Restaurant.rating_id = Rating_details.rating_id"
            			+ " WHERE Restaurant.Restaurant_name LIKE ? GROUP BY Restaurant.restaurant_id ORDER BY Rating_details.rating DESC;";
            	}
        	else if (sort.contentEquals("Rating_details.review_count")) {
            	sql = "SELECT Restaurant.restaurant_id"
            			+ " FROM Restaurant JOIN Bridge_table ON Restaurant.restaurant_id = Bridge_table.restaurant_id"
            			+ " JOIN Category ON Bridge_table.category_id = Category.category_id"
            			+ " JOIN Restaurant_details ON Restaurant.details_id = Restaurant_details.details_id"
            			+ " JOIN Rating_details ON Restaurant.rating_id = Rating_details.rating_id"
            			+ " WHERE Restaurant.Restaurant_name LIKE ? GROUP BY Restaurant.restaurant_id ORDER BY Rating_details.review_count DESC;";
            	}
        }
        try (Connection conn = DriverManager.getConnection(Constant.DBLocation, Constant.DBUserName, Constant.DBPassword);
            	PreparedStatement st = conn.prepareStatement(sql);){
            ResultSet rs = null;

            Class.forName("com.mysql.jdbc.Driver");
            st.setString(1, "%" + keyWord + "%");
            rs = st.executeQuery();
            while(rs.next()) {
	            for (int i = 0; i < business.businesses.length; i++) {
	
	    				if (business.businesses[i].getId().contentEquals(rs.getString("restaurant_id"))) {
	    					busisnesses.add(business.businesses[i]);
	    				}
	            }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return busisnesses;

    }
    
    public static void Register(String username, String regemail, String regpassword) {
    	String sql = "INSERT INTO Account(username, email, password)"
    			+ "VALUES(?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(Constant.DBLocation, Constant.DBUserName, Constant.DBPassword);
            	PreparedStatement st = conn.prepareStatement(sql);){
            int rs = 0;

            Class.forName("com.mysql.jdbc.Driver");
            st.setString(1, username);
            st.setString(2, regemail);
            st.setString(3, regpassword);
            rs = st.executeUpdate();
            if (rs == 0) {
            	throw new SQLException();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void Register(String username, String regemail) {
    	String sql = "INSERT INTO Account(username, email, password)"
    			+ "VALUES(?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(Constant.DBLocation, Constant.DBUserName, Constant.DBPassword);
            	PreparedStatement st = conn.prepareStatement(sql);){
            int rs = 0;

            Class.forName("com.mysql.jdbc.Driver");
            st.setString(1, username);
            st.setString(2, regemail);
            st.setString(3, "");
            rs = st.executeUpdate();
            if (rs == 0) {
            	throw new SQLException();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

//Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
//class BusinessDeserializer implements JsonDeserializer<Business> {
//    @Override
//    public Business deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
//        JsonElement content = je.getAsJsonObject();
//        return new Gson().fromJson(content, Business.class);
//    }
//}