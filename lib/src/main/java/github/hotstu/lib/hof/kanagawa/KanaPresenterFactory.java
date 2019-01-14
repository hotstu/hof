package github.hotstu.lib.hof.kanagawa;

import androidx.lifecycle.LifecycleOwner;
import github.hotstu.lib.hof.kanagawa.model.Node;
import github.hotstu.lib.hof.kanagawa.widget.KanaView;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/14
 */
public interface KanaPresenterFactory {
    KanaPresenter create(KanaView mKanaView, LifecycleOwner lifecycleOwner, Node node, KanaPresenterFactory factory);

}
