package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.model.Voice;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.event.MouseEvent.BUTTON3;

/**
 * @author mwolter
 * @since 29.08.13 09:28
 */
public class MainView extends JFrame {
    private static final int ROOT_LEVEL = 1;
    private static final int FIRST_LEVEL = 2;
    private static final int SECOND_LEVEL = 3;
    private final TreeModel userModel;
    private final DefaultTreeModel kabuffModel;
    private JPanel parentPanel;
    private JTree kabuffTree;
    private JCheckBox summierteAnzeigeCheckBox;
    private JTextField textField1;
    private JCheckBox vorDemLöschenBestätigenCheckBox;
    private JTree userTree;
    private MainDisplay presenter;

    public MainView(MainDisplay presenter) {
        this.presenter = presenter;
        setContentPane(parentPanel);
        setTitle("Kabuffverwahrung v0.9");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        kabuffModel = new DefaultTreeModel(new DefaultMutableTreeNode("Kabuff"));
        kabuffTree.setModel(kabuffModel);

        userModel = new DefaultTreeModel(new DefaultMutableTreeNode("Stimmgruppen"));
        userTree.setModel(userModel);


        userTree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == BUTTON3) {
                    userTree.setSelectionPath(userTree.getPathForLocation(e.getX(), e.getY()));

                    final TreePath selectionPath = userTree.getSelectionPath();
                    if (selectionPath == null) return;

                    final int pathCount = selectionPath.getPathCount();
                    final JPopupMenu menu = new JPopupMenu();
                    if (pathCount == ROOT_LEVEL) createVoiceContextMenu(menu);
                    if (pathCount == FIRST_LEVEL) createPersonContextMenu(menu);
                    if (pathCount == SECOND_LEVEL) detailPersonContextMenu(menu);
                    menu.show(userTree, e.getX(), e.getY());
                }
            }
        });
        kabuffTree.addMouseListener(new

                                            MouseAdapter() {
                                                public void mousePressed(MouseEvent e) {
                                                    if (e.getButton() == BUTTON3) {
                                                        kabuffTree.setSelectionPath(kabuffTree.getPathForLocation(e.getX(), e.getY()));

                                                        final TreePath selectionPath = kabuffTree.getSelectionPath();
                                                        if (selectionPath == null) return;

                                                        final int pathCount = selectionPath.getPathCount();
                                                        final JPopupMenu menu = new JPopupMenu();
                                                        if (pathCount == ROOT_LEVEL) createCategoryContextMenu(menu);
                                                        if (pathCount == FIRST_LEVEL) createCategoryContextMenu(menu);
                                                        if (pathCount == SECOND_LEVEL) createCategoryContextMenu(menu);
                                                        menu.show(kabuffTree, e.getX(), e.getY());
                                                    }
                                                }
                                            });


    }

    private void createVoiceContextMenu(JPopupMenu menu) {
        final JMenuItem menuItem = new JMenuItem("Neue Stimme");
        menuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                showNewVoice();
            }
        });
        menu.add(menuItem);
    }

    private void createPersonContextMenu(JPopupMenu menu) {
        final JMenuItem menuItem = new JMenuItem("Neue Person");
        menuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                showNewPerson();
            }
        });
        menu.add(menuItem);
    }

    private void detailPersonContextMenu(JPopupMenu menu) {
        menu.add(new JMenuItem("Eigenschaften"));
        menu.add(new JPopupMenu.Separator());
        menu.add(new JMenuItem("Löschen"));
    }

    private void createCategoryContextMenu(JPopupMenu menu) {
        JMenuItem newCat = new JMenuItem("Neue Kategorie");
        newCat.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
            }
        });
        menu.add(newCat);
    }

    private void showNewVoice() {
        new SingleTextDialog("Neue Stimme") {
            @Override public void onOK(String name) {
                final Voice voice = presenter.onCreateVoice(name);
                final DefaultMutableTreeNode root = (DefaultMutableTreeNode) userModel.getRoot();
                root.add(new DefaultMutableTreeNode(voice.getName()));
                userTree.expandPath(new TreePath(root));
            }
        }.showDialog();
    }

    private void showNewPerson() {
        new SingleTextDialog("Neue Person") {
            @Override public void onOK(String name) {
                final DefaultMutableTreeNode model = (DefaultMutableTreeNode) userTree.getSelectionPath().getLastPathComponent();
                Person person = presenter.onCreatePerson(model.toString(), name);
                model.add(new DefaultMutableTreeNode(person.getName()));
                userTree.expandPath(userTree.getSelectionPath());
            }
        }.showDialog();
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public void showWindow() {
        setBounds(100, 100, 900, 700);
        setVisible(true);
    }
}
