package ru.ezhov.knowledgebook;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import ru.ezhov.knowledgebook.connection.TreeBean;

/**
 *
 * @author RRNDeonisiusEZH
 */
public class TreatmentHotKey
{

    private static final Logger logger = Logger.getLogger(TreatmentHotKey.class.getName());

    private String resultText;
    private final Clipboard clipboard;
    private String testFromBuffer;

    
    {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public void getTextFromTree(JTree tree)
    {
        TreePath treePath = tree.getSelectionPath();
        if (treePath == null)
        {
            return;
        }

        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        TreeBean treeBean = (TreeBean) defaultMutableTreeNode.getUserObject();
        resultText = treeBean.getCode();
        try
        {
            getBufferText();
            setBufferText();
        } catch (Exception ex)
        {
            logger.throwing(TreatmentHotKey.class.getName(), "getTextFromList", ex);
        }
    }

    public void getTextFromList(JList list)
    {
        if (list.getSelectedValue() == null)
        {
            return;
        }
        TreeBean treeBean = (TreeBean) list.getSelectedValue();
        resultText = treeBean.getCode();
        try
        {
            getBufferText();
            setBufferText();
        } catch (Exception ex)
        {
            logger.throwing(TreatmentHotKey.class.getName(), "getTextFromList", ex);
        }
    }

    private void getBufferText() throws UnsupportedFlavorException, IOException
    {
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
        {
            testFromBuffer = (String) clipboard.getData(DataFlavor.stringFlavor);
        }
    }

    private void setBufferText()
    {
        clipboard.setContents(new StringSelection(resultText), null);
    }
}
