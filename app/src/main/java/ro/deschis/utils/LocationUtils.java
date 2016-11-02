package ro.deschis.utils;

import android.content.Context;
import android.location.Location;

import java.text.DecimalFormat;

import ro.deschis.R;

public class LocationUtils {
    // TODO: enbetter
    public static String getFormattedDistance(Context context, Location location1, Location location2) {
        float distance = location1.distanceTo(location2);
        if (distance < 1000) {
            int orderOfMagnitude = (int) Math.pow(10, String.valueOf((int) distance).length() - 1);
            int distanceInt = Math.round(distance / orderOfMagnitude) * orderOfMagnitude;
            return context.getString(R.string.fmt_distance_subunit, String.valueOf(distanceInt));
        } else {
            return context.getString(R.string.fmt_distance_unit, new DecimalFormat("#.#").format(distance / 1000));
        }
    }
}
