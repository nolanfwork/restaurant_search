package PA2;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

/**
 * Servlet implementation class LogoutDispatcher
 */
@WebServlet("/LogoutDispatcher")

public class LogoutDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	for (Cookie x: request.getCookies()) {
    		if (x.getName().contentEquals("google_login")) {
    			x.setValue("");
    			x.setMaxAge(60*60);
            	response.addCookie(x);
            	response.setHeader("google_login", "key=value; HttpOnly; SameSite=None");

    		}
    		else {
	    		x.setMaxAge(0);
	    		response.addCookie(x);
    		}
    	}
    	response.sendRedirect("index.jsp");
    }
    	

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }

}
