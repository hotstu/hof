package github.hotstu.lib.hof.kanagawa.model;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/8
 */
public interface ParentNode extends Node {
    @Override
    default boolean isLeaf() {
        return false;
    }
}
