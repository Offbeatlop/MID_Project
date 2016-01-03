import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.NamingException;

import org.json.simple.JSONObject;

//command to compile -> Nobody:src paloma$ javac -cp ~/Desktop/libr/ FileView.java
public class FileView extends HttpServlet {
    public void doGet(HttpServletRequest request,
		      HttpServletResponse response) throws
			  ServletException {
	// Gets the route to be shown
	// If it does not have it, asks for the JSON which describes
	// the common filesystem
	// Creates a JSON object
	// Passes this JSON object to fileview JSP
	
	JSONObject json = new JSONObject();
	
	json.accumulate("/paloma", "file"); //error
	json.accumulate("/diego", "dir"); //error
	//ask for jason object
	
        try{
                HttpSession session = request.getSession(true); //take into consideratino session.invalidate()...
                //User u = m.checkUser(request.getParameter("email"), request.getParameter("pass"));
                //get path and add it to jason
                        session.setAttribute("json",json);
                        response.sendRedirect("fileview");
                //}else{
                //        response.sendRedirect("error.html");
                //}
        }catch(NamingException e){
			e.printStackTrace();
        }catch(IOException e){
			e.printStackTrace();
        }
    
    }
}
