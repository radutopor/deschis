package ro.deschis.ui.restaurantDetail;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import ro.deschis.R;
import ro.deschis.model.menu.MenuItem;
import ro.deschis.utils.baseadapter.BaseViewHolder;

class MenuItemViewHolder extends BaseViewHolder<MenuItem> {
    public MenuItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override public int getLayoutResId() {
        return R.layout.menu_item;
    }

    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.image_text_bg) View imageTextBg;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.currency) TextView currency;

    @Override public void bindToItem(MenuItem menuItem) {
        Activity activity = getAdapter().getActivity();

        name.setText(menuItem.name);
        description.setText(menuItem.description);
        price.setText(activity.getString(R.string.fmt_price, menuItem.price));

        if (menuItem.image != null) {
            Picasso.with(activity).load(menuItem.image)
                    .placeholder(R.drawable.placeholder_logo)
                    .error(R.drawable.placeholder_logo)
                    .into(image);
        }
        image.setVisibility(menuItem.image != null ? View.VISIBLE : View.GONE);
        imageTextBg.setVisibility(image.getVisibility());
        price.setTextColor(activity.getResources().getColor(imageTextBg.getVisibility() == View.VISIBLE ? android.R.color.primary_text_dark : android.R.color.primary_text_light));
        currency.setTextColor(price.getCurrentTextColor());
    }

    @OnClick(ID_ITEM_VIEW) void onItemClick() {
//        activity.startActivity(new Intent(activity, RestaurantDetailActivity.class)
//                .putExtra(RestaurantDetailActivity.KEY_RESTAURANT, currentItem));
    }
}
