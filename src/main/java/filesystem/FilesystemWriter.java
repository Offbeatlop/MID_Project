package filesystem;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesystemWriter {

    private JSONParser parser = new JSONParser();
    private String rootPath = System.getProperty("user.dir");
    private String fileSystemPath = rootPath + "/src/main/resources/filesystem/";

    public boolean deleteFile(Request request, Response response) {
        String body = request.body();
        body = body.replace("%2F", "/");
        String[] parts = body.split("&");
        String fileName = parts[0].substring(5);
        String current = parts[1].substring(8);
        File file = new File(fileSystemPath + fileName);
        try {
            response.redirect("/fileview/" + current);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return file.delete();
    }

    public boolean postFile(Request request, Response response) {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(fileSystemPath);
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        try {
            Part file = request.raw().getPart("file");
            String newDirectory = convertStreamToString(request.raw().getPart("newDirectory").getInputStream());
            String directories = convertStreamToString(request.raw().getPart("directories").getInputStream());
            String newPath = directories + "/";
            if (directories == null || directories.length() == 0) {
                newPath = "";
            }
            if (newDirectory.length() > 0) {
                newPath += newDirectory +"/";
            }
            String pathToNewStuff = fileSystemPath + newPath;
            Files.createDirectories(Paths.get(pathToNewStuff));
            if (file != null) {
                String filePath = newPath + file.getSubmittedFileName();
                if(new File(fileSystemPath + filePath).createNewFile()) {
                    file.write(filePath);
                }
            }
            response.redirect("/fileview/" + newPath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/error.html");
            return false;
        }
    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private String getFilePath(String json) {
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj.get("file").toString();
    }
}
