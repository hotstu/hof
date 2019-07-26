package github.hotstu.demo.hof.chiba;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import github.hotstu.hof.chiba.Checkable;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @since 2019/1/4
 */
public class Item implements Checkable {
    private final String name;
    private final ObservableBoolean checked;


    public Item(String name) {
        this.name = name;
        checked = new ObservableBoolean();
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    @Override
    public ObservableBoolean isChecked() {
        return checked;
    }


    public void toggleChecked() {
        this.checked.set(!this.checked.get());
    }
}
