package github.hotstu.demo.hof.yoko;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import github.hotstu.demo.hof.BR;
import github.hotstu.demo.hof.R;
import github.hotstu.hof.yokohama.YokoView;

public class YokoTestActivity extends AppCompatActivity {

    private YokoView yokoView;
    private ObservableInt currentSelect = new ObservableInt();
    private ViewDataBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_yoko_test);
        binding.setVariable(BR.checkedId, currentSelect);
        yokoView = findViewById(R.id.yokoView);
        yokoView.setApdater(new YokoView.YokoAdapter() {
            @Override
            public void onCollapsed(YokoView view) {
                Log.d("hof", "onCollapsed");
            }

            @Override
            public void onExpanded(YokoView view) {
                Log.d("hof", "onExpanded");
            }
        });
        mBinding = yokoView.initMenuView(R.layout.include_yoko_container_layout);

    }


    @BindingAdapter("bind:checked")
    public static void setChecked(View v, int checkedId) {
        if (v.getId() == checkedId) {
            v.setSelected(true);
        } else {
            v.setSelected(false);
        }
    }

    public void onClick(View view) {
        if (currentSelect.get() == view.getId()) {
            yokoView.toggle();
            return;
        }
        if (view.getId() == R.id.toggle) {
            yokoView.collapseThenExpand(() -> {
                mBinding.setVariable(BR.text, "栏目0内容");
            });
        }
        if (view.getId() == R.id.cte) {
            yokoView.collapseThenExpand(() -> {
                mBinding.setVariable(BR.text, "栏目1内容");

            });
        }
        if (view.getId() == R.id.cte2) {
            yokoView.collapseThenExpand(() -> {
                mBinding.setVariable(BR.text, "栏目2内容");

            });
        }
        if (view.getId() == R.id.cte3) {
            yokoView.collapseThenExpand(() -> {
                mBinding.setVariable(BR.text, "栏目3内容");

            });
        }
        currentSelect.set(view.getId());
    }
}
