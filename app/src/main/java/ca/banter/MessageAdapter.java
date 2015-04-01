package ca.banter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Stack;

/**
 * Created by abaratham on 3/31/15.
 */
public class MessageAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Stack<Message> messages;

    public MessageAdapter(Context context, Stack<Message> objects) {
        inflater = LayoutInflater.from(context);
        messages = objects;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    public void addItem(Message m) {
        messages.push(m);
    }

    public void clear() {
        messages = new Stack<Message>();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.message_layout, parent, false);
        } else {
            view = convertView;
        }
        Message m = messages.get(position);
        ((TextView)view.findViewById(R.id.username)).setText(m.getUsername());
        ((TextView)view.findViewById(R.id.message)).setText(m.getMessage());
        return view;
    }


}
