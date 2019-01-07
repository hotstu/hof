package github.hotstu.demo.hof;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class MainActivity extends AppCompatActivity {


    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RecyclerView rvLeft = findViewById(R.id.recyclerView1);
        RecyclerView rvRight = findViewById(R.id.recyclerView2);
        mPresenter = new Presenter(rvRight);

        rvRight.setLayoutManager(new LinearLayoutManager(this));
        final MOTypedRecyclerAdapter adapter = new MOTypedRecyclerAdapter();

        MOTypedRecyclerAdapter.AdapterDelegate hdeaderDelegate0 = new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                return new BindingViewHolder<>(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.list_item_expandable, parent, false));
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                BindingViewHolder vh = (BindingViewHolder) viewHolder;
                vh.setItem(o);
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Group.class.isAssignableFrom(clazz);
            }
        };
        MOTypedRecyclerAdapter.AdapterDelegate headerDelegate = new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.list_item_header, parent, false);

                return new BindingViewHolder<>(binding);
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                ((BindingViewHolder) viewHolder).getBinding().setVariable(BR.presenter, mPresenter);
                ((BindingViewHolder) viewHolder).getBinding().setVariable(BR.position, viewHolder.getAdapterPosition());
                ((BindingViewHolder) viewHolder).setItem(o);
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Group.class.isAssignableFrom(clazz);
            }
        };

        adapter.addDelegate(hdeaderDelegate0);
        rvRight.setAdapter(adapter);

        final MOTypedRecyclerAdapter headerAdpater = new MOTypedRecyclerAdapter();
        headerAdpater.addDelegate(headerDelegate);
        rvLeft.setAdapter(headerAdpater);
        rvLeft.setLayoutManager(new LinearLayoutManager(this));
        rvLeft.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isDragging = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                isDragging = (newState != SCROLL_STATE_IDLE);
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!isDragging) {
                    return;
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }
                int position = layoutManager.findFirstVisibleItemPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(position);
                if (vh == null) {
                    return;
                }
                BindingViewHolder holder = (BindingViewHolder) vh;
                Group group = (Group) holder.getItem();
                if (group == null) {
                    return;
                }
                mPresenter.swapScollActiveGroup(group);
            }
        });
        DataSource.get().observe(this, list -> {
            adapter.setDataSet(list);
            headerAdpater.setDataSet(list);
        });
    }

    public class Presenter {
        final RecyclerView rv;
        private Group mScrollTopGroup;
        private Group mClickCheckGroup;

        public Presenter(RecyclerView rv) {
            this.rv = rv;
        }

        public void onGroupClick(Group item, int position) {
            Group prevChecked = mClickCheckGroup;
            if (prevChecked != null && !prevChecked.equals(item)) {
                prevChecked.setChecked(false);
            }
            Group prevChecked2 = mScrollTopGroup;
            if (prevChecked2 != null && !prevChecked2.equals(item)) {
                prevChecked2.setChecked(false);
                //scroll to position
                scrollToGroup(item, position);

            }
            item.setChecked(true);
            mClickCheckGroup = item;
            mScrollTopGroup = item;
        }

        public void swapScollActiveGroup(Group group) {
            if (mScrollTopGroup != null && !mScrollTopGroup.equals(group)) {
                mScrollTopGroup.setChecked(false);
            }
            mScrollTopGroup = group;
            mScrollTopGroup.setChecked(true);
        }


        public void scrollToGroup(Group group, int position) {
            if (rv == null || group == null) {
                return;
            }
            LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
            if (layoutManager == null) {
                return;
            }
            layoutManager.scrollToPositionWithOffset(position, 0);
        }
    }
}
