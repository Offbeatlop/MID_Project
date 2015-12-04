import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FilesystemWriter {

    private JSONParser parser = new JSONParser();

    public String makeChangesToFilesystem(String jsonString) {
        JSONObject json = null;
        try {
            json = (JSONObject)parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        return "OK";
    }
}
