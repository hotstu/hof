package github.hotstu.demo.hof;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import github.hotstu.demo.hof.databinding.ActivityMainBinding;
import github.hotstu.lib.hof.PresenterFactory;
import github.hotstu.lib.hof.chiba.ChibaPresenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        PresenterFactory factory = (rvleft, rvRight) -> {
            ChibaPresenter chibaPresenter = new ChibaPresenter(rvleft, rvRight) {};
            DataSource.get().observe(this, list -> {
                chibaPresenter.setDataSet(list);

            });
            return chibaPresenter;
        };
        binding.setVariable(BR.presenter, factory);


    }

}
