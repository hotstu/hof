package github.hotstu.lib.hof;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.IdRes;
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
    @BindingAdapter(value = {"bind:hof_presenter", "bind:hof_leftRvId", "bind:hof_rightRvId"}, requireAll = true)
    public static void bindPresenter(ViewGroup parent, PresenterFactory factory, @IdRes int leftRvId, @IdRes int rightRvId) {
        RecyclerView rvLeft = parent.findViewById(leftRvId);
        RecyclerView rvRight = parent.findViewById(rightRvId);
        if (factory == null || rvLeft == null || rvRight == null) {
            return;
        }

        parent.setTag(factory.create(rvLeft, rvRight));
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
