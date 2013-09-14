package de.capgeti.vereinlager;

import de.capgeti.vereinlager.model.Person;
import de.capgeti.vereinlager.model.Voice;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import static java.awt.event.MouseEvent.BUTTON3;

/**
 * @author mwolter
 * @since 29.08.13 09:28
 */
public class MainView extends JFrame {
    private static final int ROOT_LEVEL = 1;
    private static final int FIRST_LEVEL = 2;
    private static final int SECOND_LEVEL = 3;
    private final DefaultTreeModel userModel;
    private final DefaultTreeModel kabuffModel;
    private final DefaultMutableTreeNode voiceRootNode = new DefaultMutableTreeNode("Stimmgruppen");
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

        userModel = new DefaultTreeModel(voiceRootNode);
        userTree.setModel(userModel);
        userTree.setCellEditor(new DefaultCellEditor(new JTextField()));
        userTree.setEditable(true);

        userModel.addTreeModelListener(new TreeModelListener() {
            @Override public void treeNodesChanged(TreeModelEvent e) {
                System.err.println("change " + e.toString());
            }

            @Override public void treeNodesInserted(TreeModelEvent e) {
                System.err.println("insert " + e.getTreePath());
            }

            @Override public void treeNodesRemoved(TreeModelEvent e) {
                System.err.println("removed " + e.getTreePath());
            }

            @Override public void treeStructureChanged(TreeModelEvent e) {
                System.err.println("structure change " + e.getTreePath());
            }
        });

        updateVoiceModel();

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

    private void updateVoiceModel() {
        final Map<String, Voice> voiceMap = presenter.getVoices();
        if (voiceMap == null) return;
        for (Voice voice : voiceMap.values()) {
            final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(voice.getName());
            voiceRootNode.add(newChild);

            if (voice.getPersons() == null) continue;
            for (Person person : voice.getPersons()) {
                newChild.add(new DefaultMutableTreeNode(person.getName()));
            }
        }
        userTree.expandRow(0);
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
        final JMenuItem umbenennen = new JMenuItem("Umbenennen");
        umbenennen.addActionListener(new
                                             ActionListener() {
                                                 @Override public void actionPerformed(ActionEvent e) {
                                                     showRenameVoice();
                                                 }
                                             });
        menu.add(umbenennen);
        menu.add(new JPopupMenu.Separator());
        menu.add(new JMenuItem("Löschen"));
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
                voiceRootNode.add(new DefaultMutableTreeNode(voice.getName()));
                userModel.reload();
            }
        }.showDialog();
    }

    private void showRenameVoice() {
        final DefaultMutableTreeNode model = (DefaultMutableTreeNode) userTree.getSelectionPath().getLastPathComponent();
        final Voice voice = (Voice) model.getUserObject();
        new SingleTextDialog(voice.getName() + " umbenennen") {
            @Override public void onOK(String name) {
                final Voice newVoice = presenter.onRenameVoice(voice, name);
                userModel.reload();
            }
        }.showDialog();
    }

    private void showNewPerson() {
        new SingleTextDialog("Neue Person") {
            @Override public void onOK(String name) {
                final DefaultMutableTreeNode model = (DefaultMutableTreeNode) userTree.getSelectionPath().getLastPathComponent();
                Person person = presenter.onCreatePerson(model.toString(), name);
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(person.getName());
                model.add(newChild);
                userModel.reload(newChild.getParent());
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
