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
 * Servlet implementation class GoogleDispatcher
 */
@WebServlet("/GoogleDispatcher")

public class GoogleDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String username = request.getParameter("username");
        String regemail = request.getParameter("email");
        if (!Helper.emailAlreadyRegistered(regemail, request, response)) {
        	RestaurantDataParser.Register(username, regemail);
        }
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
        	Cookie google_login = new Cookie("google_login", "itisset");
        	coo_username.setMaxAge(60*60);
        	coo_email.setMaxAge(60*60);
        	google_login.setMaxAge(60*60);
        	response.addCookie(coo_email);
        	response.addCookie(coo_username);
        	response.addCookie(google_login);
        	response.setHeader("coo_email", "key=value; HttpOnly; SameSite=None");
        	response.setHeader("coo_username", "key=value; HttpOnly; SameSite=None");
        	response.setHeader("google_login", "key=value; HttpOnly; SameSite=None");
        	response.setContentType("text/html");
        	response.sendRedirect("index.jsp");


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
