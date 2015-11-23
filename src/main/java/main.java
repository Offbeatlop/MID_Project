import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    public static void main(String[] args) throws IOException {
        /*boolean recursive = true;
        // register directory and process its events
        String workingDirectory = System.getProperty("user.dir");
        String testDir = "/testdirectoryForMonitoring";
        Path dir = Paths.get(workingDirectory + testDir);

        WatchDir watchDir = new WatchDir(dir, recursive);
        watchDir.processEvents(new Broadcaster()); */

        String workingDirectory = System.getProperty("user.dir");
        String dir1 = workingDirectory + "/testdirectoryForMonitoring/onefile.txt";
        String dir2 = workingDirectory + "/secondDir";


        DirectoryMerger merger = new DirectoryMerger();
        merger.merge(Paths.get(dir1),Paths.get(dir2));
    }
}
