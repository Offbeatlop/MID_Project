import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class DirectoryMerger {

    public void merge(Path firstDir, Path secondDir) throws IOException {
        Files.walkFileTree(secondDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException
            {
                Files.copy(file, firstDir, StandardCopyOption.ATOMIC_MOVE);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void copyDifferentFolderFilesIntoOne(String mergedFolderStr,
                                                String... foldersStr) throws IOException{
        final File mergedFolder = new File(mergedFolderStr);
        final Map<String, File> filesMap = new HashMap<String, File>();
        for (String folder : foldersStr) {
            updateFilesMap(new File(folder), filesMap, null);
        }

        for (final Map.Entry<String, File> fileEntry : filesMap.entrySet()) {
            final String relativeName = fileEntry.getKey();
            final File srcFile = fileEntry.getValue();
            File destFile = new File(mergedFolder, relativeName);
            if (!srcFile.toString().equals(destFile.toString())) {
                FileUtils.copyFile(srcFile, destFile);
            }
        }
    }

    private void updateFilesMap(final File baseFolder, final Map<String, File> filesMap,
                                final String relativeName) {
        for (final File file : baseFolder.listFiles()) {
            final String fileRelativeName = getFileRelativeName (relativeName, file.getName());

            if (file.isDirectory()) {
                updateFilesMap(file, filesMap, fileRelativeName);
            } else {
                final File existingFile = filesMap.get (fileRelativeName);
                if (existingFile == null || file.lastModified() > existingFile.lastModified() ) {
                    filesMap.put (fileRelativeName, file);
                }
            }
        }
    }

    private String getFileRelativeName(final String baseName, final String fileName) {
        return baseName == null ? fileName : baseName + "/" + fileName;
    }

    private void ifSameFile(String file1, String file2) throws IOException {
        if (file1.equals(file2)){
            //check timestamp
            File fileObject1 = new File(file1);
            File fileObject2 = new File(file2);
            fileObject1.lastModified();
            if(fileObject1.lastModified() > fileObject2.lastModified()){
                copyDifferentFolderFilesIntoOne(file2, file1);
            }else{
                copyDifferentFolderFilesIntoOne(file1, file2);
            }
        }
    }
}
