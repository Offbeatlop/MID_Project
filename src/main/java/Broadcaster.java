import org.json.simple.JSONObject;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class Broadcaster {

    /*
    * Broadcasts the changes to all known machines.
    * */
    public void broadcast(WatchEvent event, Path file) {
        // CURRENTLY ONLY PRINTS THE SHIT
        JSONObject json = new JSONObject();
        json.put("event", event.kind().name());
        json.put("file",file);
        System.out.println(json);
    }
}
