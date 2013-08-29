package de.capgeti.vereinlager;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author mwolter
 * @since 29.08.13 09:28
 */
public class MainView extends JFrame {
    private JTabbedPane tabbedPane1;
    private JButton button1;
    private JButton button2;
    private JPanel parentPanel;
    private JTree tree1;
    private JComboBox<String> comboBox1;
    private JList<String> userlist;


    public MainView() {
        $$$setupUI$$$();
        setContentPane(parentPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Kabuff");

        DefaultMutableTreeNode newChild = new DefaultMutableTreeNode("Hemden 9/6");
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 38)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 39)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 40)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 40)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 40)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 40)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 40)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 41)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 41)"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 40) -> 1. Stimme: Sebastian Hentschel"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 42) -> 2. Stimme: Marcel Scheibe"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 41) -> Marschtrommler: Alexander Kaschig"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 41) -> Marschtrommler: Clemens Werler"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 40) -> Marschtrommler: Michael Wolter"));
        newChild.add(new DefaultMutableTreeNode("Hemd (Gr: 39) -> Tom: Daniela Hesse"));
        root.add(newChild);

        DefaultMutableTreeNode shirts = new DefaultMutableTreeNode("T-Shirts 7/6");
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 40)"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 40)"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 40)"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 40)"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 40)"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 41)"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 41)"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 40) -> 1. Stimme: Sebastian Hentschel"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 42) -> 2. Stimme: Marcel Scheibe"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 41) -> Marschtrommler: Alexander Kaschig"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 41) -> Marschtrommler: Clemens Werler"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 40) -> Marschtrommler: Michael Wolter"));
        shirts.add(new DefaultMutableTreeNode("T-Shirt (Gr: 39) -> Tom: Daniela Hesse"));
        root.add(newChild);

        DefaultMutableTreeNode newChild2 = new DefaultMutableTreeNode("Schuhe Neu 3/6");
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 38)"));
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 39)"));
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 40)"));
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 40) -> 1. Stimme: Sebastian Hentschel"));
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 42) -> 2. Stimme: Marcel Scheibe"));
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 41) -> Marschtrommler: Alexander Kaschig"));
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 41) -> Marschtrommler: Clemens Werler"));
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 40) -> Marschtrommler: Michael Wolter"));
        newChild2.add(new DefaultMutableTreeNode("Schuhe (Gr: 39) -> Tom: Daniela Hesse"));
        root.add(newChild2);
        root.add(new DefaultMutableTreeNode("Schuhe Alt 0/0"));


        DefaultMutableTreeNode mts = new DefaultMutableTreeNode("MT Lefimas 5/3");
        mts.add(new DefaultMutableTreeNode("MT Lefima (Nr: 1)"));
        mts.add(new DefaultMutableTreeNode("MT Lefima (Nr: 2)"));
        mts.add(new DefaultMutableTreeNode("MT Lefima (Nr: 3)"));
        mts.add(new DefaultMutableTreeNode("MT Lefima (Nr: 4)"));
        mts.add(new DefaultMutableTreeNode("MT Lefima (Nr: 5)"));
        mts.add(new DefaultMutableTreeNode("MT Lefima (Nr: 6) -> Marschtrommler: Alexander Kaschig"));
        mts.add(new DefaultMutableTreeNode("MT Lefima (Nr: 7) -> Marschtrommler: Michael Wolter"));
        mts.add(new DefaultMutableTreeNode("MT Lefima (Nr: 8) -> Marschtrommler: Tobias Reismann"));
        root.add(mts);

        DefaultMutableTreeNode toms = new DefaultMutableTreeNode("Toms 2/23");
        toms.add(new DefaultMutableTreeNode("Tom (Nr: 8) -> Tom: Jennifer Hatzky"));
        root.add(toms);


        DefaultMutableTreeNode sonstiges = new DefaultMutableTreeNode("Sonstiges");
        sonstiges.add(new DefaultMutableTreeNode("Stab Groß"));
        sonstiges.add(new DefaultMutableTreeNode("Stab Klein"));
        sonstiges.add(new DefaultMutableTreeNode("Fahne"));
        root.add(sonstiges);

        DefaultTreeModel newModel = new DefaultTreeModel(root);
        tree1.setModel(newModel);

        DefaultListModel<String> model = new DefaultListModel<String>();
        model.addElement("Michael Wolter");
        model.addElement("Tobias Reismann");
        model.addElement("Clemens Werler");
        model.addElement("Markus Hietzke");
        model.addElement("Lukas Reinhardt");
        model.addElement("Jens Hatzky");
        userlist.setModel(model);


        DefaultComboBoxModel<String> aModel = new DefaultComboBoxModel<String>();
        aModel.addElement("Marschtrommler");
        aModel.addElement("1. Stimme");
        aModel.addElement("2. Stimme");
        aModel.addElement("3. Stimme");
        aModel.addElement("4. Stimme");
        aModel.addElement("Toms");
        aModel.addElement("Knirpse");
        comboBox1.setModel(aModel);


        userlist.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {

                    userlist.setSelectedIndex(userlist.locationToIndex(e.getPoint()));

                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem details = new JMenuItem("Details");
                    details.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                            System.err.println(e.getActionCommand());
                        }
                    });
                    menu.add(details);
                    menu.add(new JPopupMenu.Separator());
                    menu.add(new JMenuItem("Löschen"));
                    menu.show(userlist, e.getX(), e.getY());
                }
            }
        });
        tree1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {

                    tree1.setSelectionPath(tree1.getPathForLocation(e.getX(), e.getY()));

                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem details = new JMenuItem("Duplizieren");
                    details.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                            System.err.println(tree1.getSelectionModel());
                        }
                    });
                    menu.add(new JMenuItem("Eigenschaften"));
                    menu.add(details);
                    menu.add(new JPopupMenu.Separator());
                    menu.add(new JMenuItem("Verknüpfung aufheben"));
                    menu.add(new JPopupMenu.Separator());
                    menu.add(new JMenuItem("Löschen"));
                    menu.show(tree1, e.getX(), e.getY());
                }
            }
        });


    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public void showWindow() {
        setBounds(100, 100, 900, 700);
        setVisible(true);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        parentPanel = new JPanel();
        parentPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setTabLayoutPolicy(0);
        tabbedPane1.setTabPlacement(1);
        parentPanel.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Lager", panel1);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tree1 = new JTree();
        tree1.setRootVisible(true);
        scrollPane1.setViewportView(tree1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, -1), null, null, 0, false));
        comboBox1 = new JComboBox();
        panel2.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel2.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        userlist = new JList();
        userlist.setSelectionMode(1);
        scrollPane2.setViewportView(userlist);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Exportieren", panel3);
        button1 = new JButton();
        button1.setText("Button");
        panel3.add(button1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Optionen", panel4);
        button2 = new JButton();
        button2.setText("Button");
        panel4.add(button2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return parentPanel;
    }
}
