package github.hotstu.demo.hof.chiba;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import github.hotstu.hof.chiba.Expandable;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @since 2019/1/4
 */
public class Group implements Expandable {
    private final String name;
    private final List<Item> collapseItems;
    private final List<Item> fullItems;
    private final ObservableBoolean expanded;
    private final ObservableBoolean mChecked;

    public Group(String name) {
        this.name = name;
        collapseItems = new ArrayList<>();
        fullItems = new ArrayList<>();
        expanded = new ObservableBoolean();
        mChecked = new ObservableBoolean();
    }

    public void addChild(Item itemB) {
        fullItems.add(itemB);
        if (collapseItems.size() < 6) {
            collapseItems.add(itemB);
        }
    }

    @Override
    public List<Item> getCollapseItems() {
        return collapseItems;
    }
    @Override
    public List<Item> getFullItems() {
        return fullItems;
    }
    @Override
    public ObservableBoolean isExpanded() {
        return expanded;
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

    @Override
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
