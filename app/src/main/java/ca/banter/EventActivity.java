package ca.banter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import com.parse.FindCallback;
import java.util.List;
import android.util.Log;
import com.parse.ParseException;
public class EventActivity extends Activity {

    private int game;
    private String username;
    MessageAdapter adapter;
    ListView lv;
    Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        game = getIntent().getExtras().getInt(MyActivity.EXTRA_GAME);
        System.out.println(game + "1");
        username = getIntent().getExtras().getString(MyActivity.EXTRA_USERNAME);
        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                updateMessages();
            }
        }, 0, 500);
        adapter = new MessageAdapter(this, new Stack<Message>());
        lv = (ListView)findViewById(R.id.messageList);
        lv.setAdapter(adapter);
    }

    public void updateMessages() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        query.whereEqualTo("event_id", game);
        System.out.println(game + "2");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> messages, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + messages.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
                int size = adapter.getCount();
                adapter.clear();
                for (ParseObject message : messages) {
                    adapter.addItem(new Message(message.get("comment").toString(), message.get("username").toString()));
                    adapter.notifyDataSetChanged();
                }
                //user just sent a message
                if (adapter.getCount() != size)
                    lv.setSelection(adapter.getCount());

            }
        });

    }

    public void sendMessage(View view) {
        EditText et = (EditText)findViewById(R.id.input_box);
        String msg = et.getText().toString();
        et.setText("");

        //Send data to server
        ParseObject message = new ParseObject("Message");
        message.put("username", username);
        message.put("comment", msg);
        message.put("event_id", game);
        System.out.println(game + "3");
        message.saveInBackground();
        updateMessages();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        t.cancel();
    }

    @Override
    public void onResume() {
        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                updateMessages();
            }
        }, 0, 500);
    }
}
