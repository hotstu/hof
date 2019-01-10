package github.hotstu.demo.hof;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import github.hotstu.demo.hof.databinding.ActivityKanaBinding;
import github.hotstu.lib.hof.kanagawa.KanaPresenter;

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
