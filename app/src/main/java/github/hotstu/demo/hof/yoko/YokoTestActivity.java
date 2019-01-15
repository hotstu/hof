package github.hotstu.demo.hof.yoko;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.appcompat.app.AppCompatActivity;
import github.hotstu.demo.hof.R;
import github.hotstu.lib.hof.yokohama.YokoView;

@Route(path = "/app/yoko", name = "抽屉菜单")
public class YokoTestActivity extends AppCompatActivity {

    private YokoView yokoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoko_test);
        yokoView = findViewById(R.id.yokoView);
    }

    public void onClick(View view) {
        yokoView.toggle();
    }
}
