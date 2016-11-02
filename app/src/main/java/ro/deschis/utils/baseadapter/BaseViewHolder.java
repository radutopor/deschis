package ro.deschis.utils.baseadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import ro.deschis.utils.FontUtil;

public abstract class BaseViewHolder<ItemDataType> extends RecyclerView.ViewHolder {
    protected static final int ID_ITEM_VIEW = 9824;

    BaseAdapter adapter;
    View divider;
    ItemDataType currentItem;

    public BaseViewHolder(View itemView) {
        super(itemView);
        if (itemView.getClass() != View.class) {
            // BaseViewHolder is live, not prototype
            itemView.setId(ID_ITEM_VIEW);
            ButterKnife.bind(this, itemView);
//            FontUtil.setFonts(itemView); TODO
        }
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public ItemDataType getCurrentItem() {
        return currentItem;
    }

    public abstract int getLayoutResId();

    public void bindToItem(ItemDataType item) {
    }
}
