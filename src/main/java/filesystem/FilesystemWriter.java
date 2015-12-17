package filesystem;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesystemWriter {

    private JSONParser parser = new JSONParser();
    private String rootPath = System.getProperty("user.dir");
    private String fileSystemPath = rootPath + "/testdirectoryForMonitoring/";

    public boolean deleteFile(String jsonString) {
        File file = new File(rootPath + getFilePath(jsonString));
        return file.delete();
    }

    public boolean postFile(Request request) {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(fileSystemPath);
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        try {
            Part file = request.raw().getPart("file");
            String location = convertStreamToString(request.raw().getPart("location").getInputStream());
            return createFile(location, file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createFile(String location, Part file) {
        try {
            String newPath = "";
            if (location.length() > 0) {
                newPath += location +"/";
            }
            System.out.println(newPath);
            Path directories = Files.createDirectories(Paths.get(fileSystemPath + newPath));
            System.out.println(directories);
            newPath += file.getSubmittedFileName();
            if(new File(fileSystemPath + newPath).createNewFile()) {
                file.write(newPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
