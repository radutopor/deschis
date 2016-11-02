package ro.deschis.model.restaurant;

import android.location.Location;

import java.io.Serializable;

public class LatLng implements Serializable {
    public double lat;
    public double lng;

    public Location toLocation() {
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }
}
