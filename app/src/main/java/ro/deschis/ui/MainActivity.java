package ro.deschis.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import ro.deschis.InitActivity;
import ro.deschis.R;
import ro.deschis.model.restaurant.Restaurant;
import ro.deschis.ui.restaurantList.RestaurantListActivity;

public class MainActivity extends InitActivity {
    private ArrayList<Restaurant> restaurantList;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("restaurants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                restaurantList = new ArrayList(dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Restaurant>>() {
                }).values());
                if (isInitDone()) {
                    startListActivity();
                }
            }

            @Override public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override protected void setUI() {
        setContentView(R.layout.main_activity);
        if (restaurantList != null) {
            startListActivity();
        }
    }

    private void startListActivity() {
        startActivity(new Intent(MainActivity.this, RestaurantListActivity.class)
                .putExtra(RestaurantListActivity.KEY_RESTAURANT_LIST, restaurantList));
    }
}
