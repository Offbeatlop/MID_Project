import filesystem.FilesystemReader;
import filesystem.FilesystemWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import spark.Request;

import javax.servlet.http.Part;
import java.io.File;

public class FilesystemWriterTest {

    private FilesystemWriter fWriter;
    private FilesystemReader fReader;

    @Before
    public void setup() {
        fWriter = new FilesystemWriter();
        fReader = new FilesystemReader();
    }

    @Test
    public void createFileInvokesPart() throws Exception {
        Part partMock = Mockito.mock(Part.class);
        //fWriter.createFile("asdasd", partMock);
        Mockito.verify(partMock).write(Mockito.anyString());
    }
}
