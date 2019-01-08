package github.hotstu.lib.hof.chiba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.hotstu.lib.hof.BR;
import github.hotstu.lib.hof.Presenter;
import github.hotstu.lib.hof.R;
import github.hotstu.lib.hof.widget.BindingViewHolder;
import github.hotstu.naiue.widget.recycler.MOTypedRecyclerAdapter;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public abstract class ChibaPresenter implements Presenter {
    protected Expandable mClickCheckGroup;
    protected Expandable mScrollTopGroup;
    protected RecyclerView rvRight;
    protected MOTypedRecyclerAdapter mRightAdapter;
    protected MOTypedRecyclerAdapter mHeaderAdpater;
    protected ObservableField<List> data;

    public ChibaPresenter(RecyclerView rvLeft, RecyclerView rvRight) {
        this.rvRight = rvRight;
        initLeft(rvLeft);
        initRight(rvRight);
    }


    @Override
    public void setDataSet(List data) {
        if (mRightAdapter != null) {
            mRightAdapter.setDataSet(data);
        }
        if (mHeaderAdpater != null) {
            mHeaderAdpater.setDataSet(data);
        }
    }

    private void initRight(RecyclerView right) {
        mRightAdapter = new MOTypedRecyclerAdapter();
        MOTypedRecyclerAdapter.AdapterDelegate hdeaderDelegate0 = new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                return new BindingViewHolder<>(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.hof_list_item_expandable, parent, false));
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                BindingViewHolder vh = (BindingViewHolder) viewHolder;
                vh.setItem(o);
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Expandable.class.isAssignableFrom(clazz);
            }
        };
        mRightAdapter.addDelegate(hdeaderDelegate0);
        Context context = right.getContext();
        right.setLayoutManager(new LinearLayoutManager(context));
        right.setAdapter(mRightAdapter);
        right.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                Expandable group = (Expandable) holder.getItem();
                if (group == null) {
                    return;
                }
                swapScollActiveGroup(group);
            }
        });
    }

    private void initLeft(RecyclerView left) {
        mHeaderAdpater = new MOTypedRecyclerAdapter();
        MOTypedRecyclerAdapter.AdapterDelegate headerDelegate = new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.hof_list_item_header, parent, false);
                return new BindingViewHolder<>(binding);
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                ((BindingViewHolder) viewHolder).getBinding().setVariable(BR.presenter, ChibaPresenter.this);
                ((BindingViewHolder) viewHolder).getBinding().setVariable(BR.position, viewHolder.getAdapterPosition());
                ((BindingViewHolder) viewHolder).setItem(o);
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Expandable.class.isAssignableFrom(clazz);
            }
        };
        mHeaderAdpater.addDelegate(headerDelegate);
        Context context = left.getContext();
        left.setLayoutManager(new LinearLayoutManager(context));
        left.setAdapter(mHeaderAdpater);
        left.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    }

    public void onGroupClick(Expandable item, int position) {
        Expandable prevChecked = mClickCheckGroup;
        if (prevChecked != null && !prevChecked.equals(item)) {
            prevChecked.setChecked(false);
        }
        Expandable prevChecked2 = mScrollTopGroup;
        if (prevChecked2 != null && !prevChecked2.equals(item)) {
            prevChecked2.setChecked(false);
            //scroll to position
            scrollToGroup(item, position);

        }
        item.setChecked(true);
        mClickCheckGroup = item;
        mScrollTopGroup = item;
    }

    public void swapScollActiveGroup(Expandable group) {
        if (mScrollTopGroup != null && !mScrollTopGroup.equals(group)) {
            mScrollTopGroup.setChecked(false);
        }
        mScrollTopGroup = group;
        mScrollTopGroup.setChecked(true);
    }


    public void scrollToGroup(Expandable group, int position) {
        if (rvRight == null || group == null) {
            return;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvRight.getLayoutManager();
        if (layoutManager == null) {
            return;
        }
        layoutManager.scrollToPositionWithOffset(position, 0);
    }
}
