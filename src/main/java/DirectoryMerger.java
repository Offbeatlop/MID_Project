import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class DirectoryMerger {

    public void merge(Path firstDir, Path secondDir) throws IOException {

        Set<FileVisitOption> fileVisitOptions = new HashSet<FileVisitOption>();
        fileVisitOptions.add(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(secondDir,fileVisitOptions , 100, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException
            {


                //IF files with same name
                    //check last modified -- take newest
                //ELSE
                    // copy normally
              for (File file : dir.toFile().listFiles()) {
                  if (file.isFile()) {
                      Files.copy(file.toPath(), firstDir, StandardCopyOption.COPY_ATTRIBUTES);
                  }
              }

                return FileVisitResult.CONTINUE;
            }
        });
    }
}
