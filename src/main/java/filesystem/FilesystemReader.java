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
    private String fsPath =  root + "/src/main/resources/public/filesystem/";
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
        for (File file: current.listFiles()) {
            if (file.isDirectory()) {
                String dirName = fileName(file, fsPath);
                json.put(dirName, readIteratively(currentRoot + fileName(file, currentRoot) + "/", file, new JSONObject()));
            } else {
                String fileName = fileName(file, currentRoot);
                if (!fileName.equals(".DS_Store")) {
                    JSONObject fileJson = new JSONObject();
                    fileJson.put("__file", fileName(file, fsPath));
                    json.put(fileName, fileJson);
                }
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
        if (currentFile == null) {
            map.put("current", "");
        } else {
            map.put("current", currentFile);
        }
        map.put("files", json);
        System.out.println(map);
        ModelAndView maw = new ModelAndView(map, "fileview");
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
