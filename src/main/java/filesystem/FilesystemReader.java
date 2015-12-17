package filesystem;

import org.json.simple.JSONObject;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
* Reads the filesystem as JSON
* */
public class FilesystemReader {
    private String root = System.getProperty("user.dir");
    private String fsPath =  root + "/testdirectoryForMonitoring";
    private ThymeleafTemplateEngine templateEngine;

    public FilesystemReader() {
        this.templateEngine = new ThymeleafTemplateEngine();
    }

    public JSONObject fileSystemAsJson() {
        JSONObject json = new JSONObject();
        File root = new File(fsPath);
        return readIteratively(root, json);
    }

    private JSONObject readIteratively(File current, JSONObject json) {
        if (current.isFile()) {
            json.put(fileName(current), "file");
            return json;
        }
        for (File file: current.listFiles()) {
            if (file.isDirectory()) {
                json.put(fileName(file), readIteratively(file, new JSONObject()));
            } else {
                json.put(fileName(file), "file");
            }
        }
        return json;
    }
    private String fileName(File file) {
        return file.toString().substring(root.length());
    }











    public String fileSystemAsHtml() {
        JSONObject json = this.fileSystemAsJson();
        Map map = new HashMap();
        map.put("files", json);
        ModelAndView maw = new ModelAndView(map, "filesystem");
        return templateEngine.render(maw);
    }
}
