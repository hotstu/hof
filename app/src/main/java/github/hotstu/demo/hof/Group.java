package github.hotstu.demo.hof;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @since 2019/1/4
 */
public class Group implements Expandable,Checkable {
    private final String name;
    public final List<Item> internalItems;
    public final List<Item> fullItems;
    public final ObservableBoolean expanded;
    private final ObservableBoolean mChecked;

    public Group(String name) {
        this.name = name;
        internalItems = new ArrayList<>();
        fullItems = new ArrayList<>();
        expanded = new ObservableBoolean();
        mChecked = new ObservableBoolean();
    }

    public void addChild(Item itemB) {
        fullItems.add(itemB);
        if (internalItems.size() < 6) {
            internalItems.add(itemB);
        }
    }


    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public void expand() {
        expanded.set(true);
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked.set(checked);
    }

    @Override
    public ObservableBoolean isChecked() {
        return mChecked;
    }

    public void toggle() {
        expanded.set(!expanded.get());
    }

    @Override
    public void toggleChecked() {
        mChecked.set(!mChecked.get());
    }

    @Override
    public void collapse() {
       expanded.set(false);
    }
}
