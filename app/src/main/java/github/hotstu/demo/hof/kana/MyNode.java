package github.hotstu.demo.hof.kana;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
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
    private final ObservableBoolean checked;
    private final Node parent;

    public MyNode(Node parent, String name, int deep) {
        this.parent = parent;
        this.name = name;
        this.deep = deep;
        this.checked = new ObservableBoolean();
        this.items = new MediatorLiveData<>();
        LiveData<List<Node>> source = LiveDataReactiveStreams.fromPublisher(s -> {
            List<Node> ret = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                ret.add(new MyNode(this, name+"-" + i, deep + 1));
            }
            s.onNext(ret);
        });
        ((MediatorLiveData<List<Node>>) this.items).addSource(source, nodes -> {
            ((MediatorLiveData<List<Node>>) this.items).setValue(nodes);
            ((MediatorLiveData<List<Node>>) this.items).removeSource(source);
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
    public boolean isRoot() {
        return parent == null;
    }


    @Override
    public LiveData<List<Node>> getItems() {
        return items;
    }



    @Override
    public void setChecked(boolean value) {
        if (checked.get() == value) {
            return;
        }
        notifyParent(checked.get(), value);
        checked.set(value);
    }

    private void notifyParent(boolean pre, boolean now) {
        if (parent == null) {
            return;
        }
        ((MyNode) parent).onChildCheckChange(this, pre, now);
    }

    public void onChildCheckChange(Node child, boolean pre, boolean now) {
        List<Node> value = getItems().getValue();
        if (value == null) {
            return;
        }
        if (now && !isLeafParent()) {
            for (Node node : value) {
                if (node != child && node.isChecked().get()) {
                    node.setChecked(false);
                }
            }
        }

    }

    @Override
    public ObservableBoolean isChecked() {
        return checked;
    }

    @Override
    public void toggleChecked() {
        setChecked(!checked.get());
    }
}
