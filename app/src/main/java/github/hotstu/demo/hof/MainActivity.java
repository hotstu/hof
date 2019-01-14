package github.hotstu.demo.hof;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.chiba:
                ARouter.getInstance().build("/app/chiba").navigation();
                break;
            case R.id.kana:
                //hack ARouter??
                ARouter.getInstance().build("/app/kana").navigation();
                break;
            case R.id.kana2:
                //hack ARouter??
                ARouter.getInstance().build("/app/kana2").navigation();
                break;
            case R.id.test:
                //hack ARouter??
                Object navigation = ARouter.getInstance().build("/hack/demo").navigation();
                System.out.println(navigation);
                break;

        }
    }
}
