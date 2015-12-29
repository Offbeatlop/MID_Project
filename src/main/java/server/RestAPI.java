package server;

import com.google.gson.Gson;
import filesystem.FilesystemReader;
import filesystem.FilesystemWriter;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class RestAPI {

    private FilesystemReader fsReader;
    private FilesystemWriter fsWriter;

    public void start() {
        fsReader = new FilesystemReader();
        fsWriter = new FilesystemWriter();
        System.out.println(System.getProperty("user.dir"));
        staticFileLocation("/public");
        createRoutes();
    }

    public void close() {
        stop();
    }

    private void createRoutes() {
        Gson gson = new Gson();

        List<String> filetypes = new ArrayList<>();
        filetypes.add(".mp3");
        get("/fileview/music", (req, res) -> fsReader.getAllMusic(filetypes), gson::toJson);

        get("/fileview/json", (req, res) -> fsReader.fileSystemAsJson(), gson::toJson);
        post("/fileview/delete", (req, res) -> fsWriter.deleteFile(req, res));
        post("/fileview", (req, res) -> fsWriter.postFile(req, res));
        get("/fileview/*", (req, res) -> fsReader.fileSystemAsHtml(req));
        get("/filesystem/*", (req, res) -> fsReader.downloadFile(req, res));
    }
}
