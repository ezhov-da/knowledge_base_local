package ru.ezhov.knowledgebook.frame;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import ru.ezhov.connectionproccessor.treatment.RecipientInformation;
import ru.ezhov.knowledgebook.connection.ApplicationConnection;
import ru.ezhov.knowledgebook.connection.Querys;
import ru.ezhov.knowledgebook.connection.TreeBean;

/**
 * собственная модель дерева, которая загружает его сама
 *
 * @author rrndeonisiusezh
 */
public class OwnTreeModel extends DefaultTreeModel
{

    private static final RecipientInformation recipientInformation = new RecipientInformation();

    private OwnTreeModel(TreeNode root)
    {
        super(root);
    }

    public static DefaultTreeModel loadTree() throws ClassNotFoundException, SQLException, UnsupportedEncodingException
    {
        Connection connection = ApplicationConnection.getInstance();
        ResultSet resultSet = connection.createStatement().executeQuery(Querys.SELECT_TREE);
        List<TreeBean> list = recipientInformation.getDataFromBase(resultSet, TreeBean.class);
        return new DefaultTreeModel(getRootForModel(list));

    }

    private static DefaultMutableTreeNode getRootForModel(List<TreeBean> list)
    {
        DefaultMutableTreeNode defaultMutableTreeNode;
        DefaultMutableTreeNode defaultMutableTreeNodeTwo;
        DefaultMutableTreeNode defaultMutableTreeNodeOld;
        Map<Integer, DefaultMutableTreeNode> hashMap = new HashMap<Integer, DefaultMutableTreeNode>(100);
        for (TreeBean treeBean : list)
        {
            defaultMutableTreeNode = new DefaultMutableTreeNode(treeBean);
            defaultMutableTreeNodeOld = hashMap.get(treeBean.getLevel());
            if (defaultMutableTreeNodeOld != null)
            {
                hashMap.remove(treeBean.getLevel());
            }

            defaultMutableTreeNodeTwo = hashMap.get(treeBean.getLevel() - 1);
            if (defaultMutableTreeNodeTwo == null)
            {
                hashMap.put(treeBean.getLevel(), defaultMutableTreeNode);
            } else
            {
                defaultMutableTreeNodeTwo.add(defaultMutableTreeNode);
                hashMap.put(treeBean.getLevel(), defaultMutableTreeNode);

            }

        }

        return hashMap.get(0);
    }

}
