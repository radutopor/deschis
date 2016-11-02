package ro.deschis.utils.baseadapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.deschis.BaseActivity;

public class BaseAdapter<ItemDataType, ViewHolderType extends BaseViewHolder<ItemDataType>> extends RecyclerView.Adapter<ViewHolderType> {
    public List<ItemDataType> dataSet;
    private Map<Integer, Class> layoutResIdToViewHolderType;
    private RecyclerView recyclerView;
    private Activity activity;
    private LayoutInflater layoutInflater;

    public BaseAdapter(List<ItemDataType> dataSet) {
        this.dataSet = dataSet;
        layoutResIdToViewHolderType = new HashMap<>();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        Context context = recyclerView.getContext();
        activity = (BaseActivity) (context instanceof Activity ? context : ((ContextWrapper) context).getBaseContext());
        layoutInflater = LayoutInflater.from(activity);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * Override this method to provide different ViewHolders types for different items when implementing heterogeneous collections.
     *
     * @param item     Item for which to provide the RestaurantDetailViewHolder class
     * @param position Position of the item within the adapter
     * @return The class of the RestaurantDetailViewHolder to be used when representing the item in the collection.
     */
    protected Class<? extends ViewHolderType> getViewHolderType(ItemDataType item, int position) {
        return (Class<ViewHolderType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public int getItemViewType(int position) {
        // Returns the layoutResId to be used for the position, which will be passed to onCreateViewHolder().
        Class viewHolderType = getViewHolderType(dataSet.get(position), position);
        if (!layoutResIdToViewHolderType.containsValue(viewHolderType)) {
            ViewHolderType prototype = generateViewHolder(viewHolderType, new View(activity));
            layoutResIdToViewHolderType.put(prototype.getLayoutResId(), viewHolderType);
        }
        for (Integer layoutResId : layoutResIdToViewHolderType.keySet()) {
            if (layoutResIdToViewHolderType.get(layoutResId) == viewHolderType) {
                return layoutResId;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolderType onCreateViewHolder(ViewGroup parent, int layoutResId) {
        View itemView = layoutInflater.inflate(layoutResId, parent, false);
        ImageView divider = null;
        if (getDividerResId() != -1 && recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayout itemDividerContainer = new LinearLayout(activity);
            itemDividerContainer.setLayoutParams(itemView.getLayoutParams());
            itemDividerContainer.setOrientation(((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation());
            itemDividerContainer.addView(itemView);
            divider = new ImageView(activity);
            divider.setImageResource(getDividerResId());
            itemDividerContainer.addView(divider);
            itemView = itemDividerContainer;
        }
        ViewHolderType viewHolder = generateViewHolder(layoutResIdToViewHolderType.get(layoutResId), itemView);
        viewHolder.adapter = this;
        viewHolder.divider = divider;
        return viewHolder;
    }

    private ViewHolderType generateViewHolder(Class viewHolderType, View view) throws InstantiationError {
        try {
            Constructor<ViewHolderType> constrForNested = viewHolderType.getDeclaredConstructor(getClass(), View.class);
            constrForNested.setAccessible(true);
            return constrForNested.newInstance(this, view);
        } catch (Exception e) {
            try {
                return (ViewHolderType) viewHolderType.getDeclaredConstructor(View.class).newInstance(view);
            } catch (Exception e1) {
                throw new InstantiationError("Declared BaseViewHolder has to have a public constructor with a single View argument.");
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolderType holder, int pos) {
        holder.currentItem = dataSet.get(pos);
        if (holder.divider != null) {
            holder.divider.setVisibility(pos != getItemCount() - 1 && getViewHolderType(dataSet.get(pos + 1), pos + 1) == holder.getClass() ? View.VISIBLE : View.GONE);
        }
        holder.bindToItem(holder.currentItem);
    }

    protected int getDividerResId() {
        return -1;
    }

    public Activity getActivity() {
        return activity;
    }

    // HELPER METHODS

    public void startActivity(Intent intent) {
        activity.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }
}
