<%@ page language='java' contentType='text/html;charset=utf-8'%>

<!DOCTYPE html>
<html>
  <head>
    <title>File view</title>
  </head>
  <body>
    <%-- Iterate through the JSON showing the files and directories
	 with their options
	 --%>
    <ul>
      <!--The following list is supposed to be always shown in
	  the page foot with nice icons and all that -->
		<% JSONObject json = session.getAttribute("json"); %>
		<% //Iterator<String> keys = json.keys();
			for(String key : json.keys() ){
				if (json.getString(key) = "file") { %> <!--value of the key -->
				<p><%= key %> <a href="example.com"> Download file </a> <a href=...> Delete file </a></p>
				
		<% }else{ <%> //it is a directory
				<p><%= key %> <a href="example.com"> Go to directory <%= key %></a> </p>
			<%	}
			}
		
		
		%>
      <li> Upload files to this directory </li>
      <li> Commit your changes to the shared filesystem </li>
      <li> Download all elements in this directory </li>
      <li> Erase every element in this directory </li>                                                                                                  
    </ul>
  </body>
</html>
