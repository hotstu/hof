package github.hotstu.demo.hof;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/4
 */
public class bindingAdpaters {
    @BindingAdapter("items")
    public static void setChild(RecyclerView rv, List<Item> items) {
        if (rv.getAdapter() == null) {
            InnerRecyclerAdapter adapter = new InnerRecyclerAdapter();
            MOTypedRecyclerAdapter.AdapterDelegate childDelegate = new MOTypedRecyclerAdapter.AdapterDelegate() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                    return new BindingViewHolder<>(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.list_item_selectable, parent, false));
                }

                @Override
                public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                    BindingViewHolder vh = (BindingViewHolder) viewHolder;
                    vh.getBinding().setVariable(BR.item, o);
                    vh.getBinding().executePendingBindings();
                }

                @Override
                public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                    return Item.class.isAssignableFrom(clazz);
                }
            };
            adapter.addDelegate(childDelegate);
            rv.setLayoutManager(new GridLayoutManager(rv.getContext(), 3));
            rv.setAdapter(adapter);
        }

        InnerRecyclerAdapter adapter = (InnerRecyclerAdapter) rv.getAdapter();
        adapter.setDataSet(items);

    }
}
