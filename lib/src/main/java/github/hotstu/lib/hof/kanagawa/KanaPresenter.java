package github.hotstu.lib.hof.kanagawa;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.hotstu.lib.hof.Presenter;
import github.hotstu.lib.hof.R;
import github.hotstu.lib.hof.kanagawa.widget.HOFRecyclerContainer;
import github.hotstu.lib.hof.widget.BindingViewHolder;
import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public class KanaPresenter implements Presenter {

    private final MOTypedRecyclerAdapter mAdapter;
    private final LifecycleOwner lifecycleOwner;

    public KanaPresenter(ViewGroup parent, LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        RecyclerView rv = parent.findViewById(R.id.rv1);
        HOFRecyclerContainer container = parent.findViewById(R.id.container);
        rv.setLayoutManager(new LinearLayoutManager(parent.getContext()));
        mAdapter = new MOTypedRecyclerAdapter();
        mAdapter.addDelegate(obtainDelegate());
        rv.setAdapter(mAdapter);
    }

    protected MOTypedRecyclerAdapter.AdapterDelegate obtainDelegate() {
        return new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.hof_list_item_tree_root, parent, false);
                return new BindingViewHolder<>(binding);
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                ((BindingViewHolder) viewHolder).setItem(o);
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Object.class.isAssignableFrom(clazz);
            }
        };
    }

    private KanaPresenter born() {
        return null;
    }

    @Override
    public void setDataSet(List data) {
        mAdapter.setDataSet(data);
    }
}
