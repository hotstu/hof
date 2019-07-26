package github.hotstu.demo.hof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import github.hotstu.demo.hof.chiba.ChibaActivity;
import github.hotstu.demo.hof.kana.KanaTest1Activity;
import github.hotstu.demo.hof.kana.KanaTest2Activity;
import github.hotstu.demo.hof.yoko.YokoTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.chiba) {
            Intent i = new Intent(this, ChibaActivity.class);
            startActivity(i);
        } else if (id == R.id.kana) {
            Intent i = new Intent(this, KanaTest1Activity.class);
            startActivity(i);
        } else if (id == R.id.kana2) {
            Intent i = new Intent(this, KanaTest2Activity.class);
            startActivity(i);
        } else if (id == R.id.yoko) {
            Intent i = new Intent(this, YokoTestActivity.class);
            startActivity(i);
        }
    }
}
