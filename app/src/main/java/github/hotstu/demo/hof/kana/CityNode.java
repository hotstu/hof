package github.hotstu.demo.hof.kana;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import github.hotstu.demo.hof.App;
import github.hotstu.hof.kanagawa.model.Node;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/10
 */
public class CityNode implements Node<CityNode> {
    private final String name;
    private final LiveData<List<CityNode>> items;
    private final int deep;
    private final ObservableBoolean checked;
    private final Node parent;
    private final Map raw;

    public CityNode(Node parent, String name, int deep, Map raw) {
        this.parent = parent;
        this.name = name;
        this.deep = deep;
        this.raw = raw;
        this.checked = new ObservableBoolean();
        this.items = new MediatorLiveData<>();
        LiveData<List<CityNode>> source = LiveDataReactiveStreams.fromPublisher(s -> {
            List<CityNode> cityNodes = CityRepo.getInstance(App.sApp).obtain(this);
            s.onNext(cityNodes);
        });
        ((MediatorLiveData<List<CityNode>>) this.items).addSource(source, nodes -> {
            ((MediatorLiveData<List<CityNode>>) this.items).setValue(nodes);
            ((MediatorLiveData<List<CityNode>>) this.items).removeSource(source);
        });
    }

    public Map getRaw() {
        return raw;
    }

    public int getDeep() {
        return deep;
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
    public LiveData<List<CityNode>> getItems() {
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
        ((CityNode) parent).onChildCheckChange(this, pre, now);
    }

    public void onChildCheckChange(Node child, boolean pre, boolean now) {
        List<? extends Node> value = getItems().getValue();
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
