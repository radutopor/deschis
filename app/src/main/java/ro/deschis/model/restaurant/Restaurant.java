package ro.deschis.model.restaurant;

import java.util.Map;

public class Restaurant {
    public String name;
    public String tagline;
    public String logo;
    public String banner;
    public String colorBackground;
    public String colorHighlights;
    public String description;
    public String notes;
    public float rating;
    public int reviewCount;
    public float deliveryCost;
    public float minimumOrder;
    public Map<String, String> contactInfo;
    public Map<String, Boolean> cuisines;
    public Location location;
    public Map<String, HoursForDay> hours;
}
