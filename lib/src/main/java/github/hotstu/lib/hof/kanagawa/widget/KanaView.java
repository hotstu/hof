package github.hotstu.lib.hof.kanagawa.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import github.hotstu.lib.hof.PresenterFactory;
import github.hotstu.lib.hof.R;
import github.hotstu.lib.hof.kanagawa.KanaPresenter;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public class KanaView extends FrameLayout {

    private ViewDataBinding binding;
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

    public void setPresenter(PresenterFactory presenter) {
        if (binding != null) {
            binding.unbind();
        }
        removeAllViews();
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.hof_kana_view, this, true);
        mPresenter = (KanaPresenter) presenter.create(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
