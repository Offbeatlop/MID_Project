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
	
	JSONObject json = new JSONObject();
	json.accumulate("/paloma", "file");
	json.accumulate("/diego", "dir");
	//ask for jason object
	String defaultpath;
        try{
                HttpSession session = request.getSession(true); //take into consideratino session.invalidate()...
                //User u = m.checkUser(request.getParameter("email"), request.getParameter("pass"));
                //get path and add it to jason
                        session.setAttribute("json",json);
                        response.sendRedirect("fileview");
                }else{
                        response.sendRedirect("error.html");
                }
        }catch(NamingException e){
			e.printStackTrace();
        }
    
    }
}
