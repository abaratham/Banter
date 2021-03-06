package ca.banter.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

import ca.banter.R;


public class LoginActivity extends Activity {

    public static final String EXTRA = "ca.banter.EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    public String username;



    public void goButtonClick(View view){
        EditText et = ((EditText) findViewById(R.id.username_input));
        if (et.getText().toString() != "Enter a temporary username"){
            Intent intent = new Intent(this, MyActivity.class);
            intent.putExtra(EXTRA, et.getText().toString());
            startActivity(intent);
        }
    }
}
