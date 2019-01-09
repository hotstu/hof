package github.hotstu.demo.hof;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import github.hotstu.demo.hof.databinding.ActivityKanaBinding;
import github.hotstu.lib.hof.PresenterFactory;
import github.hotstu.lib.hof.kanagawa.KanaPresenter;

public class KanaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityKanaBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_kana);

        PresenterFactory factory = (parent) -> {
            KanaPresenter presenter = new KanaPresenter(parent, this) {};
            DataSource.getKanaNodes().observe(this, node -> {
                presenter.setDataSet(node);
            });
            return presenter;
        };
        binding.setVariable(BR.presenter, factory);


    }

}
