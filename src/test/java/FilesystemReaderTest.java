import filesystem.FilesystemReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FilesystemReaderTest {

    FilesystemReader fsR;

    @Before
    public void setUp() {
        fsR = new FilesystemReader();
    }

    @Test
    public void getRootFor() {
        fsR.setFsPath("/un/deux/trois/quatro/");
        Assert.assertEquals(fsR.getRootFor("trois"), "/un/deux/trois/");
        Assert.assertEquals(fsR.getRootFor("un"), "/un/");
        Assert.assertEquals(fsR.getRootFor("quatro"), "/un/deux/trois/quatro/");
    }

    @Test
    public void musicFiles() {
        List<String> filetypes = new ArrayList<>();
        filetypes.add(".mp3");
        JSONArray allMusic = fsR.getAllMusic(filetypes);
        System.out.println(allMusic);
    }
}
