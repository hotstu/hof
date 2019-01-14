package github.hotstu.demo.hof.kana;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import github.hotstu.demo.hof.DataSource;
import github.hotstu.demo.hof.R;
import github.hotstu.demo.hof.databinding.ActivityKanaBinding;
import github.hotstu.lib.hof.BR;
import github.hotstu.lib.hof.kanagawa.KanaPresenter;
import github.hotstu.lib.hof.kanagawa.KanaPresenterFactory;
import github.hotstu.lib.hof.kanagawa.model.Node;
import github.hotstu.naiue.widget.recycler.BindingViewHolder;
import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;

@Route(path = "/app/kana", name = "组织树控件测试")
public class KanaTest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityKanaBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_kana);
        //演示自定义样式
        KanaPresenterFactory factory = (mKanaView, lifecycleOwner, node, factory1) -> new KanaPresenter(mKanaView, lifecycleOwner, node, factory1) {
            @Override
            protected MOTypedRecyclerAdapter.AdapterDelegate obtainDelegate(Node root, KanaPresenter presenter) {
                return new MOTypedRecyclerAdapter.AdapterDelegate() {
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                        ViewDataBinding binding1;
                        if (root.isRoot()) {
                            binding1 = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                    github.hotstu.lib.hof.R.layout.hof_list_item_tree_root, parent, false);
                        } else if (root.isLeafParent()) {
                            binding1 = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                    R.layout.custom_list_item_tree_leaf, parent, false);
                        } else {
                            binding1 = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                    github.hotstu.lib.hof.R.layout.hof_list_item_tree_middle, parent, false);
                        }

                        return new BindingViewHolder<>(binding1);
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
        };
        DataSource.getKanaNodes().observe(this, node -> {
            binding.kana.setPresenter(factory.create(binding.kana, this, node, factory));
        });
    }

}
