package github.hotstu.hof.kanagawa.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import github.hotstu.hof.chiba.Checkable;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public interface Node<T> extends Checkable {
    boolean isLeaf();
    boolean isLeafParent();
    boolean isRoot();
    LiveData<List<T>> getItems();

}
