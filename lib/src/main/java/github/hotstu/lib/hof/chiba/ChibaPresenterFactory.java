package github.hotstu.lib.hof.chiba;

import android.view.ViewGroup;

import github.hotstu.lib.hof.Presenter;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public interface ChibaPresenterFactory<T extends Presenter> {
    T create(ViewGroup parent);
}
