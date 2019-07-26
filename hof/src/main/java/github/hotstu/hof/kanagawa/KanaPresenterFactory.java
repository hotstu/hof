package github.hotstu.hof.kanagawa;

import androidx.lifecycle.LifecycleOwner;
import github.hotstu.hof.kanagawa.model.Node;
import github.hotstu.hof.kanagawa.widget.KanaView;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/14
 */
public interface KanaPresenterFactory {
    KanaPresenter create(KanaView mKanaView, LifecycleOwner lifecycleOwner, Node node, KanaPresenterFactory factory);
}
