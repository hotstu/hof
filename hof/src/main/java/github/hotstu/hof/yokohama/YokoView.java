package github.hotstu.hof.yokohama;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/15
 */
public class YokoView extends FrameLayout {


    public interface YokoAdapter {
        void onCollapsed(YokoView view);

        void onExpanded(YokoView view);
    }

    private View mView;
    private YokoAdapter mAdapter;
    private boolean animating;

    public YokoView(@NonNull Context context) {
        this(context, null);
    }

    public YokoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YokoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewDataBinding initMenuView(@LayoutRes int resId) {
        removeAllViews();
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                resId, this, true);
        mView = binding.getRoot();
        mView.setClickable(true);
        sync();
        setOnClickListener(v -> {
            if (!animating) {
                toggle();
            }
        });
        return binding;
    }

    public View getMenuView() {
        return mView;
    }

    public void sync() {
        setVisibility(isCollapsed() ? View.GONE : View.VISIBLE);
    }

    public void setApdater(YokoAdapter adapter) {
        this.mAdapter = adapter;
    }

    private void performCallback(int type) {
        if (mAdapter == null) {
            return;
        }
        if (type == 0) {
            mAdapter.onCollapsed(this);
        }
        if (type == 1) {
            mAdapter.onExpanded(this);
        }
    }


    public void collapse() {
        getMenuView().animate()
                .translationY(-getMenuView().getHeight())
                .withStartAction(() -> {
                    animating = true;
                })
                .withEndAction(() -> {
                    animating = false;
                    performCallback(0);
                    setVisibility(View.GONE);
                })
                .start();
    }

    public boolean isCollapsed() {
        return getMenuView().getTranslationY() <= -getMenuView().getHeight();
    }

    public boolean isExpanded() {
        return getMenuView().getTranslationY() >= 0;
    }

    public void expand(@Nullable Runnable beforeExpand) {
        getMenuView().animate()
                .translationY(0)
                .withStartAction(() -> {
                    animating = true;
                    setVisibility(View.VISIBLE);
                    if (beforeExpand != null) {
                        beforeExpand.run();
                    }
                })
                .withEndAction(() -> {
                    animating = false;
                    performCallback(1);
                })
                .start();
    }


    public void collapseThenExpand(@Nullable Runnable beforeExpand) {
        if (isCollapsed()) {
            expand(beforeExpand);
        } else {
            getMenuView().animate()
                    .translationY(-getMenuView().getHeight())
                    .withStartAction(() -> {
                        animating = true;
                    })
                    .withEndAction(() -> {
                        animating = false;
                        expand(beforeExpand);
                    })
                    .start();
        }
    }

    public void toggle() {
        if (isCollapsed()) {
            expand(null);
        } else {
            collapse();
        }
    }
}
