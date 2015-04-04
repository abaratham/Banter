package ca.banter;

import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by abaratham on 3/28/15.
 */
public class GameContent {

    public static ArrayList<Game> ITEMS = new ArrayList<Game>();

    public static void addItem(Game game) {
        ITEMS.add(game);
    }
}
