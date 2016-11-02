package ro.deschis.model.restaurant;

import java.io.Serializable;

public class Review implements Serializable {
    public String user;
    public String date;
    public float rating;
    public String comment;
}
