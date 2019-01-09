package github.hotstu.lib.hof.kanagawa.model;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public interface Node {
    boolean isLeaf();
    boolean isLeafParent();
    LiveData<List<Node>> getItems();
}
