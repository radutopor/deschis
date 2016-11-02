package ro.deschis.ui.restaurantDetail;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ro.deschis.BaseActivity;
import ro.deschis.R;
import ro.deschis.model.restaurant.Restaurant;
import ro.deschis.utils.LocationUtils;
import ro.deschis.utils.baseadapter.BaseAdapter;
import ro.deschis.utils.baseadapter.BaseViewHolder;

public class RestaurantDetailViewHolder extends BaseViewHolder<Restaurant> {
    public RestaurantDetailViewHolder(View itemView) {
        super(itemView);
    }

    @Override public int getLayoutResId() {
        return R.layout.restaurant_detail_item;
    }

    @BindView(R.id.notes) TextView notes;
    @BindView(R.id.cuisines) TextView cuisines;
    @BindView(R.id.rating_bar) RatingBar ratingBar;
    @BindView(R.id.review_count) TextView reviewCount;
    @BindView(R.id.delivery_cost) TextView deliveryCost;
    @BindView(R.id.minimum_order) TextView minimumOrder;

    @Override public void bindToItem(Restaurant restaurant) {
        Activity activity = getAdapter().getActivity();

        notes.setText(restaurant.notes);
        notes.setVisibility(notes.length() == 0 ? View.GONE : View.VISIBLE);

        String cuisinesString = "";
        for (String cuisine : restaurant.cuisines.keySet()) {
            cuisinesString += cuisine + ", ";
        }
        cuisines.setText(cuisinesString.substring(0, cuisinesString.length() - 2));
        cuisines.setVisibility(cuisines.length() == 0 ? View.GONE : View.VISIBLE);

        ratingBar.setRating(restaurant.rating);
        ratingBar.setVisibility(restaurant.reviewCount == 0 ? View.GONE : View.VISIBLE);
        reviewCount.setText(activity.getResources().getQuantityString(R.plurals.fmt_rating_count, restaurant.reviewCount, restaurant.reviewCount));

        deliveryCost.setText(activity.getResources().getQuantityString(R.plurals.fmt_delivery_cost, (int) Math.ceil(restaurant.deliveryCost), restaurant.deliveryCost));
        minimumOrder.setText(activity.getString(R.string.fmt_minimum_order, restaurant.minimumOrder));
        minimumOrder.setVisibility(restaurant.minimumOrder == 0 ? View.GONE : View.VISIBLE);
    }
}
