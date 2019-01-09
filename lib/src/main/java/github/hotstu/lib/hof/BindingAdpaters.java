package github.hotstu.lib.hof;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.hotstu.lib.hof.chiba.Checkable;
import github.hotstu.lib.hof.widget.BindingViewHolder;
import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public class BindingAdpaters {
    @BindingAdapter("bind:hof_chiba_presenter")
    public static void bindChibaPresenter(ViewGroup parent, PresenterFactory factory) {

        if (factory == null || parent == null) {
            return;
        }
        parent.setTag(factory.create(parent));
    }


    @BindingAdapter("items")
    public static void setChild(RecyclerView rv, List<Checkable> items) {
        if (rv.getAdapter() == null) {
            MOTypedRecyclerAdapter adapter = new MOTypedRecyclerAdapter();
            MOTypedRecyclerAdapter.AdapterDelegate childDelegate = new MOTypedRecyclerAdapter.AdapterDelegate() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                    return new BindingViewHolder<>(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.hof_list_item_selectable, parent, false));
                }

                @Override
                public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                    BindingViewHolder vh = (BindingViewHolder) viewHolder;
                    vh.getBinding().setVariable(github.hotstu.lib.hof.BR.item, o);
                    vh.getBinding().executePendingBindings();
                }

                @Override
                public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                    return Checkable.class.isAssignableFrom(clazz);
                }
            };
            adapter.addDelegate(childDelegate);
            rv.setLayoutManager(new GridLayoutManager(rv.getContext(), 3));
            rv.setAdapter(adapter);
        }

        MOTypedRecyclerAdapter adapter = (MOTypedRecyclerAdapter) rv.getAdapter();
        adapter.setDataSet(items);

    }
}
