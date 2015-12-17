import filesystem.FilesystemReader;
import org.junit.Assert;
import org.junit.Test;

public class FilesystemReaderTest {

    @Test
    public void getRootFor() {
        FilesystemReader fsR = new FilesystemReader();
        fsR.setFsPath("/un/deux/trois/quatro/");
        Assert.assertEquals(fsR.getRootFor("trois"), "/un/deux/trois/");
        Assert.assertEquals(fsR.getRootFor("un"), "/un/");
        Assert.assertEquals(fsR.getRootFor("quatro"), "/un/deux/trois/quatro/");
    }
}
