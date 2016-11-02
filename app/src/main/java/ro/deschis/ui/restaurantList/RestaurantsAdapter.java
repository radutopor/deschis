package ro.deschis.ui.restaurantList;

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
import ro.deschis.ui.restaurantDetail.RestaurantDetailActivity;
import ro.deschis.utils.LocationUtils;
import ro.deschis.utils.baseadapter.BaseAdapter;
import ro.deschis.utils.baseadapter.BaseViewHolder;

public class RestaurantsAdapter extends BaseAdapter<Restaurant, RestaurantsAdapter.ViewHolder> {
    public RestaurantsAdapter(List<Restaurant> dataSet) {
        super(dataSet);
    }

    class ViewHolder extends BaseViewHolder<Restaurant> {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override public int getLayoutResId() {
            return R.layout.restaurant_item;
        }

        @BindView(R.id.logo) ImageView logo;
        @BindView(R.id.logo_text_bg) View logoTextBg;
        @BindView(R.id.distance) TextView distance;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.rating_bar) RatingBar ratingBar;
        @BindView(R.id.review_count) TextView reviewCount;
        @BindView(R.id.cuisines) TextView cuisines;
        @BindView(R.id.notes) TextView notes;

        @Override public void bindToItem(Restaurant restaurant) {
            Picasso.with(getActivity()).load(restaurant.logo)
                    .placeholder(R.drawable.placeholder_logo)
                    .error(R.drawable.placeholder_logo)
                    .into(logo);

            Location currentLocation = ((BaseActivity) getActivity()).getCurrentLocation();
            if (currentLocation != null) {
                distance.setText(LocationUtils.getFormattedDistance(getActivity(), currentLocation, restaurant.location.latLng.toLocation()));
            } else {
                logoTextBg.setVisibility(View.GONE);
                distance.setVisibility(View.GONE);
            }

            name.setText(restaurant.name);

            reviewCount.setText(getActivity().getResources().getQuantityString(R.plurals.fmt_rating_count, restaurant.reviewCount, restaurant.reviewCount));
            reviewCount.setVisibility(restaurant.reviewCount == 0 ? View.GONE : View.VISIBLE);
            ratingBar.setRating(restaurant.rating);
            ratingBar.setVisibility(reviewCount.getVisibility());

            String cuisinesString = "";
            for (String cuisine : restaurant.cuisines.keySet()) {
                cuisinesString += cuisine + ", ";
            }
            cuisines.setText(cuisinesString.substring(0, cuisinesString.length() - 2));
            cuisines.setVisibility(cuisines.length() == 0 ? View.GONE : View.VISIBLE);

            notes.setText(restaurant.notes);
            notes.setVisibility(notes.length() == 0 ? View.GONE : View.VISIBLE);
        }

        @OnClick(ID_ITEM_VIEW) void onItemClick() {
            startActivity(new Intent(getActivity(), RestaurantDetailActivity.class)
                    .putExtra(RestaurantDetailActivity.KEY_RESTAURANT, getCurrentItem()));
        }
    }
}
