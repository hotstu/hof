package github.hotstu.lib.hof.chiba;

import java.util.List;

import androidx.databinding.ObservableBoolean;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @since 2019/1/3
 * 说明
 */
public interface Expandable extends Checkable {
    void expand();

    void collapse();

    void toggle();

    List getCollapseItems();

    List getFullItems();

    ObservableBoolean isExpanded();
}
