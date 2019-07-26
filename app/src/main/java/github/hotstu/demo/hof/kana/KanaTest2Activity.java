package github.hotstu.demo.hof.kana;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import github.hotstu.demo.hof.DataSource;
import github.hotstu.demo.hof.R;
import github.hotstu.demo.hof.databinding.ActivityKanaBinding;
import github.hotstu.hof.kanagawa.KanaPresenter;
import github.hotstu.hof.kanagawa.KanaPresenterFactory;

public class KanaTest2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityKanaBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_kana);
        //演示自定义样式
        KanaPresenterFactory factory = (mKanaView, lifecycleOwner, node, factory1) -> new KanaPresenter(mKanaView, lifecycleOwner, node, factory1);
        DataSource.getKanaNodes2().observe(this, node -> {
            binding.kana.setPresenter(factory.create(binding.kana, this, node, factory));
        });
    }

}
