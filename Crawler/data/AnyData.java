2
https://raw.githubusercontent.com/pi-181/oop-labs/master/Rgr1/src/main/java/com/demkom58/rgr1/model/AnyData.java
package com.demkom58.rgr1.model;

import com.demkom58.rgr1.view.Dlg;
import org.jetbrains.annotations.NotNull;

public abstract class AnyData {
   protected String name;

    public AnyData(@NotNull final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract Dlg showDialog(boolean editable);

    public abstract Dlg showSonDialog();

}
