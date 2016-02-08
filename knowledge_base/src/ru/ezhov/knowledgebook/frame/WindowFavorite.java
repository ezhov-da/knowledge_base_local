package ru.ezhov.knowledgebook.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.im.InputMethodRequests;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.JXTitledSeparator;
import ru.ezhov.connectionproccessor.treatment.RecipientInformation;
import ru.ezhov.knowledgebook.TreatmentHotKey;
import ru.ezhov.knowledgebook.connection.ApplicationConnection;
import ru.ezhov.knowledgebook.connection.Querys;
import ru.ezhov.knowledgebook.connection.TreeBean;

/**
 *
 * @author rrndeonisiusezh
 */
public class WindowFavorite extends JDialog
{

    //КОМПОНЕНТЫ----------------------------------------------------------------
    private final OwnModelList modelList = new OwnModelList();
    private JList list = new JList();
    private final JXTitledSeparator titledSeparatorCode = new JXTitledSeparator(SettingsFrame.TextSettings.TITLE_NAME_PANEL_CODE, SwingUtilities.LEFT, SettingsFrame.Icons.ICON_CODE);
    private final JXTitledSeparator titledSeparatorDescription = new JXTitledSeparator(SettingsFrame.TextSettings.TITLE_NAME_PANEL_DESCRIPTION, SwingUtilities.LEFT, SettingsFrame.Icons.ICON_DESCRIPTION);
    private final JSplitPane splitPaneBasic = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private final JSplitPane splitPaneSecond = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private final RSyntaxTextArea syntaxTextAreaCode = new RSyntaxTextArea();
    private final RSyntaxTextArea syntaxTextAreaDescription = new RSyntaxTextArea();
    //--------------------------------------------------------------------------
    private final int width = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth()) - (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4));
    private final int height = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight()) - (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4));
    private final Dimension dimension = new Dimension(width, height);

    public static final WindowFavorite INSTANCE = new WindowFavorite();

    private WindowFavorite()
    {
        init();
    }

    private void init()
    {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "hideFavorite");
        getRootPane().getActionMap().put("hideFavorite", new AbstractAction("hideFavorite")
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                WindowFavorite.INSTANCE.setVisible(false);
            }
        });

        add(splitPaneBasic);
        splitPaneBasic.setLeftComponent(getListPanel());
        splitPaneBasic.setResizeWeight(0.3);
        splitPaneBasic.setRightComponent(getTextPanel());
        setTitle(SettingsFrame.TextSettings.TITLE_FAVORITE);
        setIconImage(SettingsFrame.Icons.ICON_FILL_STAR_TITLE);
        setUndecorated(true);
        setSize(dimension);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        list.setModel(modelList);
        list.addListSelectionListener(new OwnListSelectionListener());
        list.addKeyListener(new OwnKeyListener());
        modelList.reloadModel();

    }

    private JPanel getListPanel()
    {
        list = new JList();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }

    private JPanel getTextPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(splitPaneSecond, BorderLayout.CENTER);
        //1
        JPanel panelCode = new JPanel(new BorderLayout());
        panelCode.add(titledSeparatorCode, BorderLayout.NORTH);
        panelCode.add(new RTextScrollPane(syntaxTextAreaCode), BorderLayout.CENTER);
        syntaxTextAreaCode.setEditable(false);
        //2
        JPanel panelDescription = new JPanel(new BorderLayout());
        panelDescription.add(titledSeparatorDescription, BorderLayout.NORTH);
        panelDescription.add(new RTextScrollPane(syntaxTextAreaDescription), BorderLayout.CENTER);
        syntaxTextAreaDescription.setEditable(false);
        //
        splitPaneSecond.setTopComponent(panelCode);
        splitPaneSecond.setBottomComponent(panelDescription);
        splitPaneSecond.setResizeWeight(0.7);
        return panel;

    }

    /**
     * обновляем избранное
     */
    public void reloadModel()
    {
        modelList.reloadModel();
    }

    private class OwnModelList extends DefaultListModel
    {

        private final RecipientInformation recipientInformation = new RecipientInformation();

        public void reloadModel()
        {
            try
            {
                Connection connection = ApplicationConnection.getInstance();
                ResultSet resultSet = connection.createStatement().executeQuery(Querys.SELECT_TREE_FOR_LIST);
                List<TreeBean> list = recipientInformation.getDataFromBase(resultSet, TreeBean.class);
                resultSet.close();
                clearAndFillList(list);
            } catch (Exception ex)
            {
                Logger.getLogger(WindowFavorite.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        private void clearAndFillList(final List<TreeBean> treeBeans)
        {
            SwingUtilities.invokeLater(new Runnable()
            {

                @Override
                public void run()
                {
                    //очищаем список
                    removeAllElements();
                    //наполняем список
                    for (TreeBean treeBean : treeBeans)
                    {
                        addElement(treeBean);
                    }
                }
            });

        }
    }

    private class OwnListSelectionListener implements ListSelectionListener
    {

        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            TreeBean treeBean = (TreeBean) list.getSelectedValue();
            syntaxTextAreaCode.setText(treeBean.getCode());
            syntaxTextAreaCode.setSyntaxEditingStyle("text/" + treeBean.getLanguageBacklight());
            syntaxTextAreaDescription.setText(treeBean.getDescription());
            syntaxTextAreaDescription.setSyntaxEditingStyle("text/" + treeBean.getLanguageBacklight());
        }
    }

    private class OwnKeyListener extends KeyAdapter
    {

        private final TreatmentHotKey thk = new TreatmentHotKey();

        @Override
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                thk.getTextFromList(list);
            }
        }

    }
}
