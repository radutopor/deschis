package ro.deschis.ui.restaurantDetail;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import ro.deschis.BaseActivity;
import ro.deschis.R;
import ro.deschis.model.restaurant.Restaurant;

public class RestaurantDetailActivity extends BaseActivity {
    public static final String KEY_RESTAURANT = "restaurant";

    @BindView(R.id.banner) ImageView banner;
    @BindView(R.id.logo) ImageView logo;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.menu_list) RecyclerView menuList;

    private Restaurant restaurant;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detail_activity);
        restaurant = (Restaurant) getIntent().getSerializableExtra(KEY_RESTAURANT);
        populateUI();
    }

    private void populateUI() {
        populateBanner();
        Picasso.with(this).load(restaurant.logo)
                .placeholder(R.drawable.placeholder_logo)
                .error(R.drawable.placeholder_logo)
                .priority(Picasso.Priority.HIGH)
                .into(logo);
        name.setText(restaurant.name);
        populateMenuList();
    }

    private void populateBanner() {
        ColorDrawable restaurantColorBackground = new ColorDrawable(Color.parseColor(restaurant.colorBackground));
        if (restaurant.banner != null) {
            Picasso.with(this).load(restaurant.banner)
                    .placeholder(restaurantColorBackground)
                    .error(restaurantColorBackground)
                    .fit()
                    .centerCrop()
                    .into(banner);
        } else {
            banner.setImageDrawable(restaurantColorBackground);
        }
    }

    private void populateMenuList() {
        FirebaseDatabase.getInstance().getReference("menus/" + restaurant.id + "/categories").orderByChild("displayOrder").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                List<Restaurant> restaurantList = (List<Restaurant>) getIntent().getSerializableExtra(KEY_RESTAURANT_LIST);

                RestaurantDetailAdapter restaurantDetailAdapter = new RestaurantDetailAdapter(restaurantList);
                menuList.setAdapter(restaurantDetailAdapter);
            }

            @Override public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
