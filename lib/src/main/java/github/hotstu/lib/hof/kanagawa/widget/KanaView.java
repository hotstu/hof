package github.hotstu.lib.hof.kanagawa.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import github.hotstu.lib.hof.kanagawa.KanaPresenter;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public class KanaView extends FrameLayout {

    private KanaPresenter mPresenter;

    public KanaView(@NonNull Context context) {
        super(context);
        init();
    }

    public KanaView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KanaView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    public void setPresenter(KanaPresenter presenter) {
        if (mPresenter != null && !mPresenter.equals(presenter)) {
            mPresenter.destroy();
        }
        mPresenter = presenter;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }
}
