package github.hotstu.lib.hof.kanagawa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.hotstu.lib.hof.BR;
import github.hotstu.lib.hof.Presenter;
import github.hotstu.lib.hof.R;
import github.hotstu.lib.hof.kanagawa.model.Node;
import github.hotstu.lib.hof.kanagawa.widget.KanaView;
import github.hotstu.lib.hof.widget.BindingViewHolder;
import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public class KanaPresenter implements Presenter {

    private final LifecycleOwner lifecycleOwner;
    private final KanaView mKanaView;
    private final RecyclerView rv;

    public KanaPresenter(ViewGroup parent, LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        rv = parent.findViewById(R.id.rv1);
        mKanaView = parent.findViewById(R.id.kana);
    }

    protected MOTypedRecyclerAdapter.AdapterDelegate obtainDelegate(Node root, KanaPresenter presenter) {
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
                ((BindingViewHolder) viewHolder).getBinding().setVariable(BR.presenter, presenter);
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Node.class.isAssignableFrom(clazz);
            }
        };
    }

    protected void onLeafClick(Node node) {

    }

    public void onItemClick(Node node) {
        if (node.isLeaf()) {
            onLeafClick(node);
            return;
        }
        mKanaView.setPresenter(parent -> {
            KanaPresenter presenter = new KanaPresenter((ViewGroup) mKanaView.getChildAt(0), lifecycleOwner);
            presenter.setDataSet(node);
            return presenter;
        });
    }

    public void setDataSet(Node rootNode) {
        if (rootNode.isLeafParent()) {
            mKanaView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = rv.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            rv.setLayoutParams(layoutParams);
            //return;
        }
        rootNode.getItems().observe(lifecycleOwner, nodes -> {
            MOTypedRecyclerAdapter mAdapter = new MOTypedRecyclerAdapter();
            rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
            mAdapter.addDelegate(obtainDelegate(rootNode, this));
            rv.setAdapter(mAdapter);
            mAdapter.setDataSet(nodes);
        });
    }
}
