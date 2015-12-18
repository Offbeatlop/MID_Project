package server;

import com.google.gson.Gson;
import filesystem.FilesystemReader;
import filesystem.FilesystemWriter;

import static spark.Spark.*;

public class RestAPI {

    private FilesystemReader fsReader;
    private FilesystemWriter fsWriter;

    public void start() {
        fsReader = new FilesystemReader();
        fsWriter = new FilesystemWriter();
        staticFileLocation("/public");
        createRoutes();
    }

    public void close() {
        stop();
    }

    private void createRoutes() {
        Gson gson = new Gson();
        get("/fileview/json", (req, res) -> fsReader.fileSystemAsJson(), gson::toJson);
        delete("/fileview", (req, res) -> fsWriter.deleteFile(req.body()));
        post("/fileview", (req, res) -> {
            boolean b = fsWriter.postFile(req, res);
            
            return b;
        });
        get("/fileview", (req, res) -> fsReader.fileSystemAsHtml(req));
    }
}
