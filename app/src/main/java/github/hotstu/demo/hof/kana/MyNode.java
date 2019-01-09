package github.hotstu.demo.hof.kana;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import github.hotstu.lib.hof.kanagawa.model.Node;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/9
 */
public class MyNode implements Node {
    private final String name;
    private final LiveData<List<Node>> items;
    private final int deep;

    public MyNode(String name, int deep) {
        this.name = name;
        this.deep = deep;
        this.items = LiveDataReactiveStreams.fromPublisher(s -> {
            List<Node> ret = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                ret.add(new MyNode(deep+"-" + i, deep + 1));
            }
            s.onNext(ret);
        });

    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isLeaf() {
        return  deep >= 3;
    }

    @Override
    public boolean isLeafParent() {
        return deep >= 2;
    }


    @Override
    public LiveData<List<Node>> getItems() {
        return items;
    }
}
