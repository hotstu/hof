package github.hotstu.demo.hof;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import github.hotstu.demo.hof.kana.MyNode;
import github.hotstu.lib.hof.kanagawa.model.Node;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/7
 */
public class DataSource {
    private static LiveData<List> sData;
    private static LiveData<Node> sKana;

    public static LiveData<List> get() {
        if (sData == null) {
            sData = build();
        }
        return sData;
    }

    private static LiveData<List> build() {
        LiveData<List> ret = new MutableLiveData<>();
        List list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            Group e = new Group("head" + i);
            int num = 3 + ((int) (Math.random() * 7));
            for (int j = 0; j < num; j++) {
                e.addChild(new Item("child" + j));
            }
            list.add(e);

        }
        ((MutableLiveData<List>) ret).setValue(list);
        return ret;
    }


    public static LiveData<Node> getKanaNodes() {
        if (sKana == null) {
            sKana = buildKana();
        }
        return sKana;
    }

    private static LiveData<Node> buildKana() {
        LiveData<Node> ret = new MutableLiveData<>();
        ((MutableLiveData<Node>) ret).setValue(new MyNode("root", 0));
        return ret;
    }
}
