package github.hotstu.demo.hof;

import androidx.databinding.ObservableBoolean;

/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @desc
 * @since 2019/1/4
 */
public interface Checkable {

    void setChecked(boolean checked);

    ObservableBoolean isChecked();

    void toggleChecked();
}
