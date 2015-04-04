package ca.banter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by abaratham on 4/3/15.
 */
public class GameAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Game> games;

    public GameAdapter(Context context, ArrayList<Game> objects) {
        inflater = LayoutInflater.from(context);
        games = objects;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    public void addItem(Game g) {
        games.add(g);
    }

    public void clear() {
        games = new ArrayList<Game>();
    }

    @Override
    public Game getItem(int position) {
        return games.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.game_item_layout, parent, false);
        } else {
            view = convertView;
        }
        Game g = games.get(position);
        TextView home = (TextView)view.findViewById(R.id.home_team), away = (TextView)view.findViewById(R.id.away_team), time = (TextView)view.findViewById(R.id.time);
        home.setText(g.getHomeTeam());
        away.setText(g.getAwayTeam());
        if (g.inProgress) {
            time.setText(g.getTimeLeft());
        }
        else {
            time.setText(g.getDate().toString());
        }

        return view;
    }

}
