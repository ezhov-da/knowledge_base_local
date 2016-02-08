package ru.ezhov.knowledgebook.listeners.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import ru.ezhov.knowledgebook.connection.TreeBean;
import ru.ezhov.knowledgebook.frame.DialogAddRefresh;
import ru.ezhov.knowledgebook.frame.SettingsFrame;

/**
 *
 * @author rrndeonisiusezh
 */
public class ActionRefreshNode extends AbstractAction {

    private TreePath treePath;

    {
        putValue(AbstractAction.NAME, "Редактировать");
        putValue(AbstractAction.SMALL_ICON, SettingsFrame.Icons.ICON_REFRESH_16x16);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        DialogAddRefresh.INSTANCE.showRefresh(((TreeBean) defaultMutableTreeNode.getUserObject()), treePath);
    }

    public void setTreePath(TreePath treePath) {
        this.treePath = treePath;
    }

}
