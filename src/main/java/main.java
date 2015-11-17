import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    public static void main(String[] args) throws IOException {
        boolean recursive = true;
        // register directory and process its events
        String workingDirectory = System.getProperty("user.dir");
        String testDir = "/testdirectoryForMonitoring";
        Path dir = Paths.get(workingDirectory + testDir);

        WatchDir watchDir = new WatchDir(dir, recursive);
        watchDir.processEvents(new Broadcaster());
    }
}
