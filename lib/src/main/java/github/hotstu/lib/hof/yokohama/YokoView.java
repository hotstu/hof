package github.hotstu.lib.hof.yokohama;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import github.hotstu.lib.hof.R;

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

    private final FrameLayout mView;
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
        LayoutInflater.from(getContext()).inflate(R.layout.hof_yoko_container_layout, this, true);
        mView = findViewById(R.id.container);
        mView.setClickable(true);
        setOnClickListener(v ->{
            if (!animating) {
                toggle();
            }
        });
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

    public FrameLayout getMenuView() {
        return mView;
    }

    public void collapse() {
        mView.animate()
                .translationY(-mView.getHeight())
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
        return mView.getTranslationY() <= -mView.getHeight();
    }

    public boolean isExpanded() {
        return mView.getTranslationY() >= 0;
    }

    public void expand(@Nullable Runnable beforeExpand) {
        mView.animate()
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
            mView.animate()
                    .translationY(-mView.getHeight())
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
