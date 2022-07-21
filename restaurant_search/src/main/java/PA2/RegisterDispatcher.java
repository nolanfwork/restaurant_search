package PA2;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.util.Scanner;

/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/RegisterDispatcher")

public class RegisterDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public RegisterDispatcher() {
    }
    // in case search is not done first
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
        String username = request.getParameter("username");
        String regemail = request.getParameter("regemail");
        String regpassword = request.getParameter("regpassword");
        String confirmpass = request.getParameter("confirmpass");
        String conditionmatch = request.getParameter("condition-rule");
        if (conditionmatch == null || !conditionmatch.contentEquals("boxcheck")) {
        	error += "<p>You need to check \"I have read and agreed to all terms and conditions of SalEats.\"</p>";
        }
        if (!regpassword.contentEquals(confirmpass)) {
        	error += "<p>Password do not match</p>";
        }
        if (!Helper.validName(username) || !Helper.isValidEmail(regemail)) {
        	error += "<p>Name or Email or Google Login is not valid. Please try again. </p>";
        }
        if (Helper.emailAlreadyRegistered(regemail, request, response)) {
        	error += "<p>Email already registered. </p>";
        }
        if (error.contentEquals("")) {
        	RestaurantDataParser.Register(username, regemail, regpassword);
			if (username.contains(" ")) {
				StringBuffer stbf = new StringBuffer(username);
				for (int i = 0; i < stbf.length(); i++) {
					if (stbf.charAt(i) == ' ') {
						stbf.replace(i, i+1, "%");
					}
				}
				username = stbf.toString();
			}
        	Cookie coo_username = new Cookie("username", username);
        	Cookie coo_email = new Cookie("email", regemail);
        	coo_username.setMaxAge(60*60);
        	coo_email.setMaxAge(60*60);
        	response.addCookie(coo_email);
        	response.addCookie(coo_username);
        	response.setContentType("text/html");
        	response.sendRedirect("index.jsp");

        }
        else {
        	request.setAttribute("regerror", error);
        	request.getRequestDispatcher("auth.jsp").include(request, response);
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
