package github.hotstu.hof.chiba;

import android.view.ViewGroup;

import github.hotstu.hof.Presenter;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public interface ChibaPresenterFactory<T extends Presenter> {
    T create(ViewGroup parent);
}
