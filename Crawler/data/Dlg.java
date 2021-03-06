2
https://raw.githubusercontent.com/pi-181/oop-labs/master/Rgr1/src/main/java/com/demkom58/rgr1/view/Dlg.java
package com.demkom58.rgr1.view;

import com.demkom58.rgr1.model.AnyData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Dlg extends JDialog {
    private JPanel contentPane;

    private JButton buttonOK;
    private JButton buttonCancel;
    private boolean ok;

    public void setup(@NotNull final JPanel contentPane,
               @NotNull final JButton buttonOK,
               @NotNull final JButton buttonCancel) {
        this.contentPane = contentPane;
        this.buttonOK = buttonOK;
        this.buttonCancel = buttonCancel;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    protected void onOK() {
        ok = true;
        setVisible(false);
    }

    protected void onCancel() {
        ok = false;
        setVisible(false);
    }

    @Nullable
    public abstract AnyData createData() throws Exception;

    public void setEditable(boolean editable) {
        buttonOK.setVisible(editable);

        if (editable)
            buttonCancel.setText("Cancel");
        else
            buttonCancel.setText("Exit");

        for (Component component : contentPane.getComponents())
            component.setEnabled(editable);
    }

    public boolean isOk() {
        return ok;
    }
}
