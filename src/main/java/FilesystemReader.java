import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

/*
* Reads the filesystem as JSON
* */
public class FilesystemReader {
    private String root = System.getProperty("user.dir");
    private String fsPath =  root + "/testdirectoryForMonitoring";


    public JSONObject getFileSystemAsJson() throws IOException {
        JSONObject json = new JSONObject();
        File root = new File(fsPath);
        return readRecursively(root, json);
    }

    private JSONObject readRecursively(File current, JSONObject json) {
        if (current.isFile()) {
            json.put(fileName(current), "file");
            return json;
        }
        for (File file: current.listFiles()) {
            if (file.isDirectory()) {
                json.put(fileName(file), readRecursively(file, new JSONObject()));
            } else {
                json.put(fileName(file), "file");
            }
        }
        return json;
    }
    private String fileName(File file) {
        return file.toString().substring(root.length());
    }
}
