package PA2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Helper {
    /**
     * check if name is valid
     *
     * @param name the name user provides
     * @return valid or not valid
     */
    public static boolean validName(String name) {
        return Constant.namePattern.matcher(name).matches();
    }

    /**
     * check if email is valid
     *
     * @param email the email user provides
     * @return valid or not valid
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return Constant.emailPattern.matcher(email).matches();
    }

    /**
     * Get username with the email
     *
     * @param email
     * @return userName
     * @throws SQLException
     */
    public static String getUserName(String email) throws SQLException {
    	String username = "";
    	String sql = "SELECT Account.username FROM Account WHERE email = ?;";
    	try (Connection conn = DriverManager.getConnection(Constant.DBLocation, Constant.DBUserName, Constant.DBPassword);
    			PreparedStatement st = conn.prepareStatement(sql);){
    		ResultSet rs = null;
            Class.forName("com.mysql.jdbc.Driver");
            st.setString(1, email);
            rs = st.executeQuery();
            while(rs.next()) {
            	username = rs.getString("username");
            }
    	} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return username;
    }

    /**
     * Get userID with email
     *
     * @param email
     * @return userID
     * @throws SQLException
     */
    public static int getUserID(String email) throws SQLException {
    	int userid = -1;
    	String sql = "SELECT Account.user_id FROM Account WHERE email = ?;";
    	try (Connection conn = DriverManager.getConnection(Constant.DBLocation, Constant.DBUserName, Constant.DBPassword);
    			PreparedStatement st = conn.prepareStatement(sql);){
    		ResultSet rs = null;
            Class.forName("com.mysql.jdbc.Driver");
            st.setString(1, email);
            rs = st.executeQuery();
            while(rs.next()) {
            	userid = rs.getInt("user_id");
            }
    	} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
        return userid;
    }

    /**
     * check if the email and password matches
     *
     * @param email
     * @param password
     */
    public static boolean checkPassword(String email, String password) throws SQLException {
        //TODO
    	String sql = "SELECT * FROM Account WHERE email = ? AND password = ?;";
    	try (Connection conn = DriverManager.getConnection(Constant.DBLocation, Constant.DBUserName, Constant.DBPassword);
    			PreparedStatement st = conn.prepareStatement(sql);){
    		ResultSet rs = null;
            Class.forName("com.mysql.jdbc.Driver");
            st.setString(1, email);
            st.setString(2, password);
            rs = st.executeQuery();
           if (rs.next()) {
        	   return true;
           }
    	} catch (ClassNotFoundException | SQLException s) {
			s.printStackTrace();
		}
        return false;
    }

    /**
     * Check if email is already registered
     *
     * @param email
     * @param request
     * @param response
     * @return email registered or not
     * @throws ServletException
     * @throws IOException
     */
    public static boolean emailAlreadyRegistered(String email, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sql = "SELECT Account.email FROM Account WHERE Account.email = ?;";
    	try (Connection conn = DriverManager.getConnection(Constant.DBLocation, Constant.DBUserName, Constant.DBPassword);
    			PreparedStatement st = conn.prepareStatement(sql);){
    		ResultSet rs = null;
            Class.forName("com.mysql.jdbc.Driver");
            st.setString(1, email);
            rs = st.executeQuery();
           if (rs.next()) {
        	   return true;
           }
           // still need servlet exception maybe about google signin
           
           
           
    	} catch (ClassNotFoundException | SQLException s) {
			s.printStackTrace();
		}
    	return false;
    }
}
