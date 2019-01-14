package github.hotstu.demo.hof.hack;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/11
 */
@Route(path = "/hack/demo")
public class MyProvider implements IProvider {
    public MyProvider() {
        System.out.println("construct");
    }

    @Override
    public void init(Context context) {
        System.out.println("init");

    }
}
