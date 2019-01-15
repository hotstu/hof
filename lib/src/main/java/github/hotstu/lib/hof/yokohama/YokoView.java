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

    private View mView;

    public YokoView(@NonNull Context context) {
        super(context);
        init();
    }

    public YokoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YokoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hof_yoko_test_layout, this, true);
        mView = findViewById(R.id.test);
    }

    public void toggle() {
        float translationY = mView.getTranslationY();
        if (translationY != 0) {
            mView.animate()
                    .translationY(0)
                    .start();
        } else  {
            mView.animate()
                    .translationY(-mView.getHeight())
                    .start();

        }
        //mView.getMeasuredHeight();

    }
}
