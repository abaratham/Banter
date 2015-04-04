package ca.banter.Activities;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ca.banter.Game;
import ca.banter.GameContent;
import ca.banter.Fragments.GamesFragment;
import ca.banter.Fragments.LoadingFragment;
import ca.banter.Fragments.NavigationDrawerFragment;
import ca.banter.R;


public class MyActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, GamesFragment.OnFragmentInteractionListener {

    public static final String EXTRA_GAME = "ca.banter.GAME";
    public static final String EXTRA_USERNAME = "ca.banter.USERNAME";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    //hello kugilfgliygilygyilg
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                new ReadGameInfoTask(this).execute("hockey");
                getActionBar().setTitle("Hockey");
                break;
            case 1:
                new ReadGameInfoTask(this).execute("baseball");
                getActionBar().setTitle("Baseball");
                break;
            case 2:
                new ReadGameInfoTask(this).execute("basketball");
                getActionBar().setTitle("Basketball");
                break;
        }
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Hockey";
                //                getActionBar().setTitle(t);
                break;
            case 2:
                mTitle = "Baseball";
//                getActionBar().setTitle(mTitle);
//                new ReadGameInfoTask().execute();
                break;
            case 3:
                mTitle = "Basketball";
//                getActionBar().setTitle(mTitle);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.my, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int pos) {
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra(EXTRA_GAME, GameContent.ITEMS.get(pos).generateID());
        i.putExtra(EXTRA_USERNAME, getIntent().getExtras().getString(LoginActivity.EXTRA));
        startActivity(i);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MyActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    private class ReadGameInfoTask extends AsyncTask<String, Integer, Void> {

        private String hockeyURL = "http://sports.yahoo.com/nhl/scoreboard/?date=";
        private String basketballURL = "http://sports.yahoo.com/nba/scoreboard/?date=";
        private String baseballURL = "http://sports.yahoo.com/mlb/scoreboard/?date=";
        Activity activity;

        public ReadGameInfoTask(Activity a) {
            super();
            activity = a;
        }

        @Override
        protected void onPreExecute() {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            String m = "" + month;
            if (month < 10) {
                m = "0"+m;
            }
            int day = c.get(Calendar.DAY_OF_MONTH);
            String d = "" + day;
            if (day < 10) {
                d = "0"+d;
            }
            hockeyURL += "" + year + "-" + m + "-" + d;
            basketballURL += "" + year + "-" + m + "-" + d;
            baseballURL += "" + year + "-" + m + "-" + d;

            Fragment fragment = new LoadingFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

        @Override
        protected Void doInBackground(String... s){
            ArrayList<Game> games = new ArrayList<Game>();
            GameContent.ITEMS = new ArrayList<Game>();
            Document doc = new Document("");
            String url = "";
            if (s[0].equals("hockey")) {
                url = hockeyURL;
            }
            else if (s[0].equals("basketball")) {
                url = basketballURL;
            }
            else if (s[0].equals("baseball")) {
                url = baseballURL;
            }
            try {
                System.out.println(url);
                doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10").get();
                publishProgress(5);
                Elements liveSummaries = doc.getElementsByAttributeValue("class", "game live link");
                String searchKey = "[class*=game pre link]";
                if (s[0].equals("baseball"))
                    searchKey = "[class*=game link]";
                Elements summaries = doc.select(searchKey);

                int totalSize = summaries.size() + liveSummaries.size(), processed = 5;
                System.out.println("Total size: " + totalSize);

                for (Element elem : liveSummaries) {
                    String time = elem.getElementsByAttributeValue("class", "time")
                            .text();
                    Elements teams = elem.getElementsByTag("em");
                    ArrayList<String> teamNames = new ArrayList<String>();
                    for (Element team : teams) {
                        teamNames.add(team.text());
                    }

                    assert teamNames.size() == 2;

                    Game g = new Game(teamNames.get(0), teamNames.get(1), time);
                    games.add(g);
                    GameContent.addItem(g);

                    int added = (int)((1.0/(double)totalSize) * 100);
                    processed += added;
                    publishProgress(processed);

                }

                Elements dates = doc.select("[class*=time]");
                Calendar c = Calendar.getInstance();
                String today = " " + c.get(Calendar.DAY_OF_WEEK) + " "
                        + c.get(Calendar.DAY_OF_MONTH) + " "
                        + (c.get(Calendar.MONTH) + 1) + " " + c.get(Calendar.YEAR);

                for (Element elem : summaries) {
                    String time = elem.getElementsByAttributeValue("class", "time")
                            .text() + today;
                    Elements teams = elem.getElementsByTag("em");
                    ArrayList<String> teamNames = new ArrayList<String>();
                    for (Element team : teams) {
                        teamNames.add(team.text());
                    }
                    DateFormat format = new SimpleDateFormat("hh:m a z F d M y",
                            Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = format.parse(time);
                    }catch (ParseException e){}

                    assert teamNames.size() == 2;

                    Game g = new Game(teamNames.get(0), teamNames.get(1), date);
                    games.add(g);
                    GameContent.addItem(g);

                    int added = (int)((1.0/(double)totalSize) * 100);
                    processed += added;
                    publishProgress(processed);

                }
            } catch (IOException exception) {
            }
            publishProgress(100);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            ((ProgressBar)activity.findViewById(R.id.progressBar)).setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            System.out.println(GameContent.ITEMS);
            Fragment fragment = new GamesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

}
