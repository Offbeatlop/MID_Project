package filesystem;

import com.google.common.io.ByteStreams;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
* Reads the filesystem as JSON
* */
public class FilesystemReader {
    private String root = System.getProperty("user.dir");
    private String fsPath =  root + "/src/main/resources/filesystem/";
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
        String currentFile = customTrim(request.splat());
        if (currentFile != null) {
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

    private String customTrim(String[] s) {
        if (s == null) {
            return null;
        }
        if (s.length == 0) {
            return null;
        }
        String str = s[0];
        if (str.charAt(0) == '/') {
            str = str.substring(1);
        }
        if (str.charAt(str.length() - 1) == '/') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public Object downloadFile(Request request, Response response) {
        try {
            String path = fsPath + request.splat()[0];
            File file = new File(path);
            response.raw().setContentType("application/octet-stream");
            response.raw().setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            OutputStream out = response.raw().getOutputStream();
            InputStream in = new FileInputStream(file);
            ByteStreams.copy(in, out);
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("FAILED!");
            e.printStackTrace();
            return false;
        }
        return response.raw();
    }

    public JSONArray getAllMusic(List<String> musicFileTypes) {
        try {
            List<Path> musicFiles = Files.walk(Paths.get(fsPath))
                    .filter((path) -> path.toFile().isFile())
                    .filter(
                            (path) -> musicFileTypes.stream().anyMatch(
                                    (fileType) -> path.toFile().getName().contains(fileType)
                            )
                    ).collect(Collectors.toList());
            JSONArray musicJson = new JSONArray();
            musicFiles.stream().forEach((path) -> {
                File music = path.toFile();
                JSONObject musicEntry = new JSONObject();
                musicEntry.put("name", music.getName());
                musicEntry.put("path", fileName(music, fsPath));
                musicJson.add(musicEntry);
            });
            return musicJson;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
