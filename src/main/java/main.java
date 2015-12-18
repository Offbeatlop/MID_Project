import server.RestAPI;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    public static void main(String[] args) throws IOException {
        new RestAPI().start();
    }

    private static void startSniffingFileSystem(boolean recursive, String workingDirectory) throws IOException {
        String testDir = "/filesystem";
        Path dir = Paths.get(workingDirectory + testDir);

        WatchDir watchDir = new WatchDir(dir, recursive);
        watchDir.processEvents(new Broadcaster());
    }
}
