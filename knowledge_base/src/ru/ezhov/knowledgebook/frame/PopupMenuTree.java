package ru.ezhov.knowledgebook.frame;

import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import ru.ezhov.knowledgebook.connection.TreeBean;
import ru.ezhov.knowledgebook.listeners.actions.ActionAddFavorite;
import ru.ezhov.knowledgebook.listeners.actions.ActionAddNode;
import ru.ezhov.knowledgebook.listeners.actions.ActionAddNotFavorite;
import ru.ezhov.knowledgebook.listeners.actions.ActionDeleteNode;
import ru.ezhov.knowledgebook.listeners.actions.ActionRefreshNode;

/**
 *
 * @author rrndeonisiusezh
 */
public class PopupMenuTree extends JPopupMenu {

    public static final PopupMenuTree INSTANCE = new PopupMenuTree();
    private final ActionAddFavorite actionAddFavorite = new ActionAddFavorite();
    private final ActionAddNotFavorite actionAddNotFavorite = new ActionAddNotFavorite();
    private final ActionAddNode actionAddNode = new ActionAddNode();
    private final ActionRefreshNode actionRefreshNode = new ActionRefreshNode();
    private final ActionDeleteNode actionDeleteNode = new ActionDeleteNode();
    private final JMenuItem menuItemAddFavorite = new JMenuItem(actionAddFavorite);
    private final JMenuItem menuItemAddNotFavorite = new JMenuItem(actionAddNotFavorite);
    private final JMenuItem menuItemAddNode = new JMenuItem(actionAddNode);
    private final JMenuItem menuItemRefreshNode = new JMenuItem(actionRefreshNode);
    private final JMenuItem menuItemDeleteNode = new JMenuItem(actionDeleteNode);

    private PopupMenuTree() {
        add(menuItemAddFavorite);
        add(menuItemAddNotFavorite);
        addSeparator();
        add(menuItemAddNode);
        add(menuItemRefreshNode);
        add(menuItemDeleteNode);

    }

    public void show(Component invoker, int x, int y, TreePath treePath) {
        initActions(treePath);
        configureMenu(treePath);
        super.show(invoker, x, y);
    }

    private void initActions(TreePath treePath) {
        actionAddFavorite.setTreePath(treePath);
        actionAddNotFavorite.setTreePath(treePath);
        actionAddNode.setTreePath(treePath);
        actionRefreshNode.setTreePath(treePath);
        actionDeleteNode.setTreePath(treePath);
    }

    private void configureMenu(TreePath treePath) {
        if (isFavorite(treePath)) {
            setNotFavorite();
        } else {
            setFavorite();
        }
    }

    private boolean isFavorite(TreePath treePath) {
        return ((TreeBean) ((DefaultMutableTreeNode) treePath.getLastPathComponent()).getUserObject()).isFavorites();
    }

    private void setNotFavorite() {
        menuItemAddNotFavorite.setEnabled(true);
        menuItemAddFavorite.setEnabled(false);
    }

    private void setFavorite() {
        menuItemAddNotFavorite.setEnabled(false);
        menuItemAddFavorite.setEnabled(true);
    }

}
