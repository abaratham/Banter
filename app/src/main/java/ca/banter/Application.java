package ca.banter;

/**
 * Created by abaratham on 3/31/15.
 */
import com.parse.Parse;
import com.parse.ParseUser;

public class Application extends android.app.Application {

    public void onCreate() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "pl3PoMR9Z770PuFeZhB9tq5lBbpU0TZP6KnuvYMB", "R6zcgh2l6otaSmQfQceMo3ISxxs5TV1WqnOchfs4");    }

}
