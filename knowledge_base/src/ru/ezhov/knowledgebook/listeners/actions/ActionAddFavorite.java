package ru.ezhov.knowledgebook.listeners.actions;

import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import ru.ezhov.knowledgebook.connection.TreatmentsQuerys;
import ru.ezhov.knowledgebook.connection.TreeBean;
import ru.ezhov.knowledgebook.frame.BasicFrame;
import ru.ezhov.knowledgebook.frame.DialogAddRefresh;
import ru.ezhov.knowledgebook.frame.SettingsFrame;
import ru.ezhov.knowledgebook.frame.WindowFavorite;

/**
 *
 * @author rrndeonisiusezh
 */
public class ActionAddFavorite extends AbstractAction
{

    private TreePath treePath;

    
    {
        putValue(AbstractAction.NAME, "Добавить в избранное");
        putValue(AbstractAction.SMALL_ICON, SettingsFrame.Icons.ICON_FILL_STAR);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            TreeBean treeBean = (TreeBean) defaultMutableTreeNode.getUserObject();
            TreatmentsQuerys.favorite(true, treeBean.getId());
            BasicFrame.INSTANCE.reloadTree();
            WindowFavorite.INSTANCE.reloadModel();
        } catch (Exception ex)
        {
            Logger.getLogger(ActionAddFavorite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTreePath(TreePath treePath)
    {
        this.treePath = treePath;
    }

}
