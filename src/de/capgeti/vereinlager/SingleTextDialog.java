package de.capgeti.vereinlager;

import javax.swing.*;
import java.awt.event.*;

public abstract class SingleTextDialog extends JDialog {
    private JPanel contentPane;
    private JTextField textField1;
    private JButton buttonOK;
    private JButton buttonCancel;

    public SingleTextDialog(String title) {
        setContentPane(contentPane);
        setModal(true);
        setTitle(title);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(textField1.getText());
                dispose();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new
                                  WindowAdapter() {
                                      public void windowClosing(WindowEvent e) {
                                          onCancel();
                                      }
                                  });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public abstract void onOK(String name);

    private void onCancel() {
        dispose();
    }

    public void showDialog() {
        pack();
        setVisible(true);
    }
}
