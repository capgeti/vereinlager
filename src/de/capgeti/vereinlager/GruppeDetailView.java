package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.Gruppe;

import javax.swing.*;

/**
 * @author mwolter
 * @since 29.08.13 13:15
 */
public class GruppeDetailView {
    private JPanel parentPanel;
    private JButton zurückButton;
    private JLabel gruppenName;
    private JButton eigenschaftenButton;
    private JTabbedPane tabbedPane1;
    private JList gruppenDetailList;

    public GruppeDetailView() {
        DefaultListModel<Gruppe> model = new DefaultListModel<Gruppe>();
        gruppenDetailList.setModel(model);
        model.addElement(new Gruppe("test", 2, 3));
        model.addElement(new Gruppe("test", 2, 3));
        gruppenDetailList.setCellRenderer(new GruppenListLine());
    }
}
