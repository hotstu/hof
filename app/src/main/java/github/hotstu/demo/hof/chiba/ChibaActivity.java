package github.hotstu.demo.hof.chiba;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import github.hotstu.demo.hof.BR;
import github.hotstu.demo.hof.DataSource;
import github.hotstu.demo.hof.R;
import github.hotstu.hof.chiba.ChibaPresenterFactory;
import github.hotstu.hof.chiba.ChibaPresenter;

public class ChibaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewDataBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_chiba);

        ChibaPresenterFactory factory = (parent) -> {
            ChibaPresenter chibaPresenter = new ChibaPresenter(parent) {};
            DataSource.get().observe(this, list -> {
                chibaPresenter.setDataSet(list);

            });
            return chibaPresenter;
        };
        binding.setVariable(BR.presenter, factory);


    }

}
