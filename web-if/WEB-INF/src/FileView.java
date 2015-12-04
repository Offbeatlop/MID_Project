import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming. NamingException;

import org.json.simple.JSONObject;

public class FileView extends HttpServlet {
    public void doGet(HttpServletRequest request,
		      HttpServletResponse response) throws
			  ServletException {
	// Gets the route to be showed
	// If it does not have it, asks for the JSON which describes
	// the common filesystem
	// Creates a JSON object
	// Passes this JSON object to fileview JSP
	JsonObject json;
    }
}
