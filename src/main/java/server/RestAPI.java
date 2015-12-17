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
        get("/filesystem/json", (req, res) -> fsReader.fileSystemAsJson(), gson::toJson);
        delete("/filesystem", (req, res) -> fsWriter.deleteFile(req.body()));
        post("/filesystem", (req, res) -> fsWriter.postFile(req));
        get("/filesystem", (req, res) -> fsReader.fileSystemAsHtml());
    }
}

class Person {
    String name;
}
