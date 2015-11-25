import java.io.IOException;

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
        String dir1 = workingDirectory + "/testdirectoryForMonitoring";
        String dir2 = workingDirectory + "/secondDir";
        String dir3 = workingDirectory + "/dir3";

        DirectoryMerger merger = new DirectoryMerger();
        merger.copyDifferentFolderFilesIntoOne(dir2, dir2,dir1);
    }
}
