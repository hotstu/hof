package github.hotstu.demo.hof.kana;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import github.hotstu.demo.hof.DataSource;
import github.hotstu.demo.hof.R;
import github.hotstu.demo.hof.databinding.ActivityKanaBinding;
import github.hotstu.lib.hof.kanagawa.KanaPresenter;

@Route(path = "/app/kana", name = "组织树控件测试")
public class KanaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityKanaBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_kana);
        DataSource.getKanaNodes().observe(this, node -> {
            binding.kana.setPresenter(new KanaPresenter(binding.kana, this, node));
        });



    }

}
