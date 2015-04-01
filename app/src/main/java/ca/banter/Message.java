package ca.banter;

/**
 * Created by abaratham on 3/31/15.
 */
public class Message {

    private String message, username;

    public Message(String msg, String username) {
        this.message = msg;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
