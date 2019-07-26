package github.hotstu.demo.hof;

import android.app.Application;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/10
 */
public class App extends Application {
    public static App sApp;
    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }
}
