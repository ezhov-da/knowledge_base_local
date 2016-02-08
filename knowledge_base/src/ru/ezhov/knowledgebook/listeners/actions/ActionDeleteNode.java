package ru.ezhov.knowledgebook.listeners.actions;

import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import ru.ezhov.knowledgebook.connection.TreatmentsQuerys;
import ru.ezhov.knowledgebook.connection.TreeBean;
import ru.ezhov.knowledgebook.frame.BasicFrame;
import ru.ezhov.knowledgebook.frame.DialogAddRefresh;
import ru.ezhov.knowledgebook.frame.SettingsFrame;

/**
 *
 * @author rrndeonisiusezh
 */
public class ActionDeleteNode extends AbstractAction
{

    private TreePath treePath;

    
    {
        putValue(AbstractAction.NAME, "Удалить");
        putValue(AbstractAction.SMALL_ICON, SettingsFrame.Icons.ICON_DELETE_16x16);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            String text;

            DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            TreeBean treeBean = ((TreeBean) defaultMutableTreeNode.getUserObject());

            if (treeBean.getLevel() == 0)
            {
                text = "Невозможно удалить корень дерева";
                JOptionPane.showMessageDialog(BasicFrame.INSTANCE, text, null, JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            text
                    = "Вы действительно хотите удалить узел: " + treeBean.getName() + "?\n"
                    + "Если вы удалите узел, все его потомки будут больше не видны.";
            int answear = JOptionPane.showConfirmDialog(BasicFrame.INSTANCE, text, null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answear == JOptionPane.YES_OPTION)
            {
                TreatmentsQuerys.deleteNode(treeBean.getId());
                BasicFrame.INSTANCE.reloadTree();
            }

        } catch (Exception ex)
        {
            Logger.getLogger(ActionDeleteNode.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setTreePath(TreePath treePath)
    {
        this.treePath = treePath;
    }

}
