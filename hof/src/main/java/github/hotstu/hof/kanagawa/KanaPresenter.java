package github.hotstu.hof.kanagawa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.hotstu.hof.BR;
import github.hotstu.hof.Presenter;
import github.hotstu.hof.R;
import github.hotstu.hof.kanagawa.model.Node;
import github.hotstu.hof.kanagawa.widget.KanaView;
import github.hotstu.naiue.widget.recycler.BindingViewHolder;
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
    private final Node rootNode;
    private final Observer<List<? extends Node>> listObserver;
    private final KanaPresenterFactory kanaPresenterFactory;

    public KanaPresenter(KanaView parent, LifecycleOwner lifecycleOwner, Node root, KanaPresenterFactory factory) {
        ViewGroup container = parent.findViewById(R.id.container);
        if (container == null) {
            LayoutInflater.from(parent.getContext()).inflate(R.layout.hof_kana_view, parent, true);
        }
        container = parent.findViewById(R.id.container);
        kanaPresenterFactory = factory;
        this.lifecycleOwner = lifecycleOwner;
        rv = container.findViewById(R.id.rv1);
        mKanaView = container.findViewById(R.id.kana);
        mKanaView.removeAllViews();
        this.rootNode = root;
        if (rootNode.isLeafParent()) {
            mKanaView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = rv.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            rv.setLayoutParams(layoutParams);
            //return;
        }
        listObserver = obtainListObserver(mKanaView, rv, rootNode);
        rootNode.getItems().observe(lifecycleOwner, listObserver);
    }

    public void destroy() {
        rootNode.getItems().removeObserver(listObserver);
    }

    /**
     * 继承这个方法如果你想对adapter进行设置
     *
     * @return
     */
    protected Observer<List<? extends Node>> obtainListObserver(KanaView mKanaView, RecyclerView rv, Node rootNode) {
        return nodes -> {
            Context context = mKanaView.getContext();
            MOTypedRecyclerAdapter mAdapter = new MOTypedRecyclerAdapter();
            rv.setLayoutManager(new LinearLayoutManager(context));
            for (int i = 0; i < rv.getItemDecorationCount(); i++) {
                rv.removeItemDecorationAt(i);
            }
            DividerItemDecoration decor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            if (rootNode.isRoot()) {
                decor.setDrawable(ContextCompat.getDrawable(context, android.R.drawable.divider_horizontal_bright));
            } else {
                decor.setDrawable(ContextCompat.getDrawable(context, R.drawable.hof_inset_left_divider));
            }
            rv.addItemDecoration(decor);
            mAdapter.addDelegate(obtainDelegate(rootNode, this));
            rv.setAdapter(mAdapter);
            mAdapter.setDataSet(nodes);
            for (Node node : nodes) {
                if (!node.isLeaf() && node.isChecked().get()) {
                    born(node);
                    break;
                }
            }
        };
    }

    /**
     * 继承这个方法如果你想改变item的呈现方式
     *
     * @param root
     * @param presenter
     * @return
     */
    protected MOTypedRecyclerAdapter.AdapterDelegate obtainDelegate(Node root, KanaPresenter presenter) {
        return new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                ViewDataBinding binding;
                if (root.isRoot()) {
                    binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.hof_list_item_tree_root, parent, false);
                } else if (root.isLeafParent()) {
                    binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.hof_list_item_tree_leaf, parent, false);
                } else {
                    binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.hof_list_item_tree_middle, parent, false);
                }

                return new BindingViewHolder<>(binding);
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                ((BindingViewHolder) viewHolder).setItem(BR.item, o);
                ((BindingViewHolder) viewHolder).setItem(BR.presenter, presenter);
                ((BindingViewHolder) viewHolder).executePendingBindings();
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Node.class.isAssignableFrom(clazz);
            }
        };
    }

    protected void onLeafClick(Node node) {
        node.toggleChecked();
    }

    public void onItemClick(Node node) {
        if (node.isLeaf()) {
            onLeafClick(node);
            return;
        }
        if (node.isChecked().get()) {
            return;
        }
        node.setChecked(true);
        born(node);
    }

    private void born(Node node) {
        mKanaView.setPresenter(kanaPresenterFactory.create(mKanaView, lifecycleOwner, node, kanaPresenterFactory));
    }
}
