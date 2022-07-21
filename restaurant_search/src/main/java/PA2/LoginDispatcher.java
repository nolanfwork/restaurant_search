package PA2;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Servlet implementation class LoginDispatcher
 */
@WebServlet("/LoginDispatcher")

public class LoginDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        InputStream st = servletContext.getResourceAsStream(Constant.FileName);
        String json = "";
        try (Scanner sc = new Scanner(st)){
        	while (sc.hasNext()) {
        		json += sc.nextLine();
        	}
        }
        RestaurantDataParser.Init(json);
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String error = "";
    	String loginpassword = request.getParameter("loginpassword");
    	String loginemail = request.getParameter("loginemail");
    	String loginusername = "";
    	try {
    		if (!Helper.checkPassword(loginemail, loginpassword)) {
    			error += "<p>Email and Password does not match/ account does not exist</p>";
    			request.setAttribute("error", error);
    			request.getRequestDispatcher("auth.jsp").include(request, response);
    		}
    		else {
    			loginusername = Helper.getUserName(loginemail);
    			if (loginusername.contains(" ")) {
    				StringBuffer stbf = new StringBuffer(loginusername);
    				for (int i = 0; i < stbf.length(); i++) {
    					if (stbf.charAt(i) == ' ') {
    						stbf.replace(i, i+1, "%");
    					}
    				}
    				loginusername = stbf.toString();
    			}
            	Cookie coo_username = new Cookie("username", loginusername);
            	Cookie coo_email = new Cookie("email", loginemail);

            	coo_username.setMaxAge(60*60);
            	coo_email.setMaxAge(60*60);
            	response.addCookie(coo_email);
            	response.addCookie(coo_username);
            	response.setContentType("text/html");
            	response.sendRedirect("index.jsp");
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
