package github.hotstu.hof.chiba;

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
import github.hotstu.hof.BR;
import github.hotstu.hof.Presenter;
import github.hotstu.hof.R;
import github.hotstu.naiue.widget.recycler.BindingViewHolder;
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
    protected MOTypedRecyclerAdapter mHeaderAdapter;
    protected ObservableField<List> data;

    public ChibaPresenter(ViewGroup parent) {
        this.rvRight = parent.findViewById(R.id.recyclerView2);
        initLeft(parent.findViewById(R.id.recyclerView1));
        initRight(rvRight);
    }


    public void setDataSet(List data) {
        if (mRightAdapter != null) {
            mRightAdapter.setDataSet(data);
        }
        if (mHeaderAdapter != null) {
            mHeaderAdapter.setDataSet(data);
        }
    }

    /**
     *  custom the item by override this method
     * @return
     */
    protected MOTypedRecyclerAdapter.AdapterDelegate obtainLeftDelegate() {
        return new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.hof_list_item_header, parent, false);
                return new BindingViewHolder<>(binding);
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                ((BindingViewHolder) viewHolder).setItem(BR.presenter, ChibaPresenter.this);
                ((BindingViewHolder) viewHolder).setItem(BR.position, viewHolder.getAdapterPosition());
                ((BindingViewHolder) viewHolder).setItem(BR.item, o);
                ((BindingViewHolder) viewHolder).executePendingBindings();
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Expandable.class.isAssignableFrom(clazz);
            }
        };
    }

    /**
     * custom the item by override this method
     * @return
     */
    protected MOTypedRecyclerAdapter.AdapterDelegate obtainRightDelegate() {
        return new MOTypedRecyclerAdapter.AdapterDelegate() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(MOTypedRecyclerAdapter adapter, ViewGroup parent) {
                return new BindingViewHolder<>(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.hof_list_item_expandable, parent, false));
            }

            @Override
            public void onBindViewHolder(MOTypedRecyclerAdapter moTypedRecyclerAdapter, RecyclerView.ViewHolder viewHolder, Object o) {
                BindingViewHolder vh = (BindingViewHolder) viewHolder;
                vh.setItem(BR.item, o);
                vh.executePendingBindings();
            }

            @Override
            public boolean isDelegateOf(Class<?> clazz, Object item, int position) {
                return Expandable.class.isAssignableFrom(clazz);
            }
        };
    }

    private void initRight(RecyclerView right) {
        mRightAdapter = new MOTypedRecyclerAdapter();
        MOTypedRecyclerAdapter.AdapterDelegate hdeaderDelegate0 = obtainRightDelegate();
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
                Expandable group = (Expandable) holder.getItem(BR.item);
                if (group == null) {
                    return;
                }
                swapScollActiveGroup(group);
            }
        });
    }



    private void initLeft(RecyclerView left) {
        mHeaderAdapter = new MOTypedRecyclerAdapter();
        MOTypedRecyclerAdapter.AdapterDelegate headerDelegate = obtainLeftDelegate();
        mHeaderAdapter.addDelegate(headerDelegate);
        Context context = left.getContext();
        left.setLayoutManager(new LinearLayoutManager(context));
        left.setAdapter(mHeaderAdapter);
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
