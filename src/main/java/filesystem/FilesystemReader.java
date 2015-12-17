package filesystem;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import spark.ModelAndView;
import spark.Request;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/*
* Reads the filesystem as JSON
* */
public class FilesystemReader {
    private String root = System.getProperty("user.dir");
    private String fsPath =  root + "/testdirectoryForMonitoring/";
    private ThymeleafTemplateEngine templateEngine;
    private JSONParser parser;

    public FilesystemReader() {
        this.templateEngine = new ThymeleafTemplateEngine();
        this.parser = new JSONParser();
    }

    public JSONObject fileSystemAsJson() { return this.fileSystemAsJson(fsPath); }

    public JSONObject fileSystemAsJson(String currentRoot) {
        JSONObject json = new JSONObject();
        File root = new File(currentRoot);
        return readIteratively(currentRoot, root, json);
    }

    private JSONObject readIteratively(String currentRoot, File current, JSONObject json) {
        if (current.isFile()) {
            json.put(fileName(current, currentRoot), "file");
            return json;
        }
        for (File file: current.listFiles()) {
            if (file.isDirectory()) {
                String dirName = fileName(file, fsPath);
                json.put(dirName, readIteratively(currentRoot + fileName(file, currentRoot) + "/", file, new JSONObject()));
            } else {
                JSONObject fileJson = new JSONObject();
                fileJson.put("__file", file.toString());
                json.put(fileName(file, currentRoot), fileJson);
            }
        }
        return json;
    }
    private String fileName(File file, String currentRoot) {
        return file.toString().substring(currentRoot.length());
    }

    public String fileSystemAsHtml(Request request) {
        String currentRoot = fsPath;
        String currentFile = customTrim(request.queryParams("current"));
        if (currentFile != null) {
            System.out.println(currentFile);
            currentRoot += currentFile + "/";
        }
        JSONObject json = this.fileSystemAsJson(currentRoot);
        Map map = new HashMap();
        map.put("files", json);
        ModelAndView maw = new ModelAndView(map, "filesystem");
        return templateEngine.render(maw);
    }

    public String getRootFor(String currentFolder) {
        String[] directories = fsPath.split("/");
        int i = 0;
        while(i < directories.length) {
            if (directories[i].equals(currentFolder)) {
                i++;
                break;
            }
            i++;
        }
        String path = "";
        for (int j = 0; j < i; j++) {
            path += directories[j] + "/";
        }
        return path;
    }

    public void setFsPath(String fsPath) {
        this.fsPath = fsPath;
    }

    private String customTrim(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return s;
        }
        if (s.charAt(0) == '/') {
            s = s.substring(1);
        }
        if (s.charAt(s.length() - 1) == '/') {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
