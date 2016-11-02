package ro.deschis.ui.restaurantDetail;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import ro.deschis.R;
import ro.deschis.model.menu.MenuCategory;
import ro.deschis.model.restaurant.Restaurant;
import ro.deschis.utils.baseadapter.BaseViewHolder;

public class MenuCategoryViewHolder extends BaseViewHolder<MenuCategory> {
    public MenuCategoryViewHolder(View itemView) {
        super(itemView);
    }

    @Override public int getLayoutResId() {
        return R.layout.menu_category_item;
    }

    @BindView(R.id.underline) TextView underline;
    @BindView(R.id.name) TextView name;

    @Override public void bindToItem(MenuCategory menuCategory) {
        Restaurant restaurant = ((RestaurantDetailAdapter) getAdapter()).getRestaurant();
        underline.setBackgroundColor(Color.parseColor(restaurant.colorBackground));
        name.setText(menuCategory.name);
    }
}
