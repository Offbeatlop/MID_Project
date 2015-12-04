import org.json.simple.JSONObject;
import com.google.gson.Gson;
import static spark.Spark.*;

public class RestAPI {

    private FilesystemReader fsReader;
    private FilesystemWriter fsWriter;

    public void start() {
        fsReader = new FilesystemReader();
        createRoutes();
    }

    private void createRoutes() {
        Gson gson = new Gson();
        get("/filesystem", (req, res) -> fsReader.getFileSystemAsJson(), gson::toJson);
        post("/filesystem", (req, res) -> fsWriter.makeChangesToFilesystem(req.body()));
    }
}
