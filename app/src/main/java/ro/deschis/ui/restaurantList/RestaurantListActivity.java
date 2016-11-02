package ro.deschis.ui.restaurantList;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import ro.deschis.BaseActivity;
import ro.deschis.R;
import ro.deschis.model.restaurant.Restaurant;

public class RestaurantListActivity extends BaseActivity {
    public static final String KEY_RESTAURANT_LIST = "restaurant_list";

    @BindView(R.id.restaurant_list_view) RecyclerView restaurantListView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list_activity);
        List<Restaurant> restaurantList = (List<Restaurant>) getIntent().getSerializableExtra(KEY_RESTAURANT_LIST);
        RestaurantsAdapter restaurantsAdapter = new RestaurantsAdapter(restaurantList);
        restaurantListView.setAdapter(restaurantsAdapter);
    }
}
