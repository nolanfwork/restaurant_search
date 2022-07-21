package PA2;

/**
 * The class used to model a business
 */
//TODO add a single Business class and has its element as arrays
public class Business {

	public Businesses[] businesses;
	public class Businesses {
	    public Businesses() {}
	    public Businesses(String id, String alias, String name, String imageUrl, Boolean isClosed, String url, Integer reviewCount, Category[] categories, Double rating, Coordinates coordinates, String[] transactions, String price, Location location, String phone, String displayPhone, Double distance) {
	    	this.id = id;
	    	this.alias = alias;
	    	this.name = name;
	    	this.image_url = imageUrl;
	    	this.is_closed = isClosed;
	    	this.url = url;
	    	this.review_count = reviewCount;
	    	this.categories = categories;
	    	this.rating = rating;
	    	this.coordinates = coordinates;
	    	this.transactions = transactions;
	    	this.price = price;
	    	this.location = location;
	    	this.phone = phone;
	    	this.display_phone = displayPhone;
	    	this.distance = distance;
	    	}
		private String id = null;
		private String alias = null;
		private String name = null;
		private String image_url = null;
		private Boolean is_closed = null;
		private String url = null;
		private Integer review_count = null;
		private Category[] categories = null;
		public class Category {
			public String alias = null;
			public String title = null;
		}
		private Double rating = null;
		private Coordinates coordinates = null;
		public class Coordinates {
			private Double latitude = null;
			private Double longtitude = null;
		}
		private String[] transactions = null;
		private String price = null;
		private Location location = null;
		public class Location {
			String address1 = null;
			String address2 = null;
			String address3 = null;
			String city = null;
			Integer zip_code = null;
			String country = null;
			String state = null;
			String[] display_address = null;
		}
		private String phone = null;
		private String display_phone = null;
		private Double distance = null;
		



	    //TODO Add Getters (No Setters as the business does not change in our assignment once constructed)

		public String getId() {
		return id;
		}
		
		public String getAlias() {
		return alias;
		}
		
		public String getName() {
		return name;
		}
		
		public String getImageUrl() {
		return image_url;
		}
		
		public Boolean getIsClosed() {
		return is_closed;
		}
		
		public String getUrl() {
		return url;
		}
		
		public Integer getReviewCount() {
		return review_count;
		}
		
		public Category[] getCategories() {
		return categories;
		}
		
		public Double getRating() {
		return rating;
		}
		
		public Coordinates getCoordinates() {
		return coordinates;
		}
		
		public String[] getTransactions() {
		return transactions;
		}
		
		public String getPrice() {
		return price;
		}
		
		public String getLocation() {
			String address = "";
			for (int i = 0; i < location.display_address.length; i++) {
				address += location.display_address[i] + " ";
			}
		return address;
		}
		
		public String getPhone() {
		return phone;
		}
		
		public String getDisplayPhone() {
		return display_phone;
		}
		
		public Double getDistance() {
		return distance;
		}

}
}


