package ro.deschis.ui.restaurantDetail;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ro.deschis.R;
import ro.deschis.model.menu.MenuCategory;
import ro.deschis.model.menu.MenuItem;
import ro.deschis.model.restaurant.Restaurant;
import ro.deschis.utils.baseadapter.BaseAdapter;
import ro.deschis.utils.baseadapter.BaseViewHolder;

public class RestaurantDetailAdapter extends BaseAdapter {
    private Restaurant restaurant;

    public RestaurantDetailAdapter(Restaurant restaurant, List dataSet) {
        super(dataSet);
        this.restaurant = restaurant;
    }

    @Override protected Class getViewHolderType(Object item, int position) {
        return item instanceof Restaurant ? RestaurantDetailViewHolder.class
                : item instanceof MenuCategory ? MenuCategoryViewHolder.class
                : MenuItemViewHolder.class;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
