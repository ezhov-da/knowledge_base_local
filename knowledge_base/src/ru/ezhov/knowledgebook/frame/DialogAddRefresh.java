package ru.ezhov.knowledgebook.frame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.tree.TreePath;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTitledSeparator;
import ru.ezhov.knowledgebook.connection.TreatmentsQuerys;
import ru.ezhov.knowledgebook.connection.TreeBean;

/**
 *
 * @author rrndeonisiusezh
 */
public class DialogAddRefresh
{

    public static final DialogAddRefresh INSTANCE = new DialogAddRefresh();
    /** окно диалога для добавления */
    private final BasicFrame basicFrame = BasicFrame.INSTANCE;
    private final JDialog dialog = new JDialog(BasicFrame.INSTANCE);
    //КОМПОНЕНТЫ----------------------------------------------------------------
    private final JXTitledSeparator titledSeparatorName = new JXTitledSeparator(
            SettingsFrame.TextSettings.TITLE_NAME_PANEL_NAME,
            SwingConstants.LEFT,
            SettingsFrame.Icons.ICON_NAME);
    private final JXTitledSeparator titledSeparatorCode = new JXTitledSeparator(
            SettingsFrame.TextSettings.TITLE_NAME_PANEL_CODE,
            SwingConstants.LEFT,
            SettingsFrame.Icons.ICON_CODE);
    private final JXTitledSeparator titledSeparatorDescription = new JXTitledSeparator(
            SettingsFrame.TextSettings.TITLE_NAME_PANEL_DESCRIPTION,
            SwingConstants.LEFT,
            SettingsFrame.Icons.ICON_DESCRIPTION);
    private final JXTitledSeparator titledSeparatorFile = new JXTitledSeparator(
            SettingsFrame.TextSettings.TITLE_NAME_PANEL_FILE,
            SwingConstants.LEFT,
            SettingsFrame.Icons.ICON_FILE);
    private final JXLabel labelLang = new JXLabel(
            "язык разметки кода и описания: ");
    private final JComboBox comboBoxLang = new JComboBox(
            SettingsFrame.Syntax.ARRAY_SYNTAX);
    private final JTextField textFieldName = new JTextField();
    private final RSyntaxTextArea syntaxTextAreaCode = new RSyntaxTextArea();
    private final RSyntaxTextArea syntaxTextAreaDescription = new RSyntaxTextArea();
    private final JTextField textFieldFile = new JTextField();
    private final JButton buttonAddFile = new JButton("...");
    private final JToolBar toolBarAdd = new JToolBar();
    private final JButton buttonAddNode = new JButton(new ActionAdd());
    private final JButton buttonRefreshNode = new JButton(new ActionRefresh());
    //--------------------------------------------------------------------------
    private TreeBean treeBean;
    private TreePath treePath;
    private final Insets insets = new Insets(5,
            5,
            5,
            5);

    private DialogAddRefresh()
    {
        init();
    }

    private void init()
    {
        dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "hideFavorite");
        dialog.getRootPane().getActionMap().put("hideFavorite", new AbstractAction("hideFavorite")
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                dialog.setVisible(false);
            }
        });
        dialog.setModal(true);
        //ставим слушателя на выпадающий список для смены подсветки синтаксиса
        comboBoxLang.addItemListener(new ItemListenerComboBox());
        JPanel panelBasic = new JPanel(new BorderLayout());
        panelBasic.add(getCenterPanel(),
                BorderLayout.CENTER);
        panelBasic.add(getBottomPanel(),
                BorderLayout.NORTH);
        toolBarAdd.setFloatable(false);
        dialog.add(panelBasic);
    }

    /**
     * центровая панель
     *
     * @return панель по центру
     */
    private JPanel getCenterPanel()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        //
        panel.add(titledSeparatorName,
                new GridBagConstraints(0,
                        0,
                        3,
                        1,
                        1,
                        0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        insets,
                        0,
                        0));
        //
        panel.add(textFieldName,
                new GridBagConstraints(0,
                        1,
                        3,
                        1,
                        1,
                        0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        insets,
                        0,
                        0));
        //
        panel.add(getCenterSplitPane(),
                new GridBagConstraints(0,
                        2,
                        3,
                        1,
                        1,
                        1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        insets,
                        0,
                        0));
        //
        panel.add(titledSeparatorFile,
                new GridBagConstraints(0,
                        3,
                        3,
                        1,
                        1,
                        0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        insets,
                        0,
                        0));
        //
        panel.add(textFieldFile,
                new GridBagConstraints(0,
                        4,
                        2,
                        1,
                        1,
                        0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        insets,
                        0,
                        0));
        panel.add(buttonAddFile,
                new GridBagConstraints(2,
                        4,
                        1,
                        1,
                        0,
                        0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        insets,
                        0,
                        0));

        return panel;
    }

    /**
     * разделительная полоса
     *
     * @return
     */
    private JSplitPane getCenterSplitPane()
    {
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        //панель с кодом
        JPanel panelCode = new JPanel(new GridBagLayout());
        //
        panelCode.add(titledSeparatorCode,
                new GridBagConstraints(0,
                        0,
                        1,
                        1,
                        1,
                        0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        insets,
                        0,
                        0));
        panelCode.add(labelLang,
                new GridBagConstraints(1,
                        0,
                        1,
                        1,
                        0,
                        0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        insets,
                        0,
                        0));
        panelCode.add(comboBoxLang,
                new GridBagConstraints(2,
                        0,
                        1,
                        1,
                        0,
                        0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        insets,
                        0,
                        0));
        //
        panelCode.add(new RTextScrollPane(syntaxTextAreaCode),
                new GridBagConstraints(0,
                        1,
                        3,
                        1,
                        1,
                        1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        insets,
                        0,
                        0));
        //панель с описанием
        JPanel panelDescroption = new JPanel(new GridBagLayout());
        //
        panelDescroption.add(titledSeparatorDescription,
                new GridBagConstraints(
                        0,
                        0,
                        3,
                        1,
                        1,
                        0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        insets,
                        0,
                        0));
        //
        panelDescroption.add(new RTextScrollPane(syntaxTextAreaDescription),
                new GridBagConstraints(0,
                        1,
                        3,
                        1,
                        1,
                        1,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        insets,
                        0,
                        0));
        //
        splitPane.setTopComponent(panelCode);
        splitPane.setBottomComponent(panelDescroption);
        splitPane.setResizeWeight(0.7);
        return splitPane;
    }

    /**
     * панель с баром
     *
     * @return панель
     */
    private JPanel getBottomPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(toolBarAdd,
                BorderLayout.CENTER);
        return panel;
    }

    private void show()
    {
        dialog.setSize(BasicFrame.INSTANCE.getWidth() - 50,
                BasicFrame.INSTANCE.getHeight() - 50);
        dialog.setLocationRelativeTo(BasicFrame.INSTANCE);
        dialog.setVisible(true);
    }

    /**
     * слушатель на изменение выпадающего списка
     */
    private class ItemListenerComboBox implements ItemListener
    {

        @Override
        public void itemStateChanged(ItemEvent e)
        {
            String textStyle = "text/";
            String syntax = DialogAddRefresh.this.comboBoxLang.getSelectedItem().toString();
            DialogAddRefresh.this.syntaxTextAreaCode.setSyntaxEditingStyle(
                    textStyle + syntax);

        }

    }

    public void showAdd(TreeBean treeBean, TreePath treePath)
    {
        dialog.setTitle("добавить к узлу: " + treeBean.getName());
        this.treeBean = treeBean;
        this.treePath = treePath;
        toolBarAdd.removeAll();
        toolBarAdd.add(buttonAddNode);
        //ЧИСТИМ ПОЛЯ-----------------------------------------------------------
        textFieldName.setText("");
        syntaxTextAreaCode.setText("");
        syntaxTextAreaDescription.setText("");
        show();
    }

    public void showRefresh(TreeBean treeBean, TreePath treePath)
    {
        dialog.setTitle("обновить узел: " + treeBean.getName());
        this.treeBean = treeBean;
        this.treePath = treePath;
        fillComponentForRefresh();
        toolBarAdd.removeAll();
        toolBarAdd.add(buttonRefreshNode);
        show();
    }

    /**
     * наполняем компоненты данными для обновления
     */
    private void fillComponentForRefresh()
    {
        textFieldName.setText(treeBean.getName());
        comboBoxLang.setSelectedItem(treeBean.getLanguageBacklight());
        syntaxTextAreaCode.setText(treeBean.getCode());
        syntaxTextAreaDescription.setText(treeBean.getDescription());
        textFieldFile.setText(treeBean.getNameFile());
    }

    private class ActionAdd extends AbstractAction
    {

        
        {
            putValue(AbstractAction.SMALL_ICON,
                    SettingsFrame.Icons.ICON_ADD);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                TreatmentsQuerys.insertNode(
                        textFieldName.getText(), treeBean.getId(), syntaxTextAreaCode.getText(), syntaxTextAreaDescription.getText(), comboBoxLang.getSelectedItem().toString()
                );
                BasicFrame.INSTANCE.reloadTree();
                BasicFrame.INSTANCE.setSelectedTreePath(treePath);

            } catch (Exception ex)
            {
                Logger.getLogger(DialogAddRefresh.class.getName()).log(
                        Level.SEVERE,
                        null,
                        ex);
            }
        }

    }

    private class ActionRefresh extends AbstractAction
    {

        
        {
            putValue(AbstractAction.SMALL_ICON,
                    SettingsFrame.Icons.ICON_REFRESH);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                TreatmentsQuerys.updateNode(treeBean.getId(), textFieldName.getText(), syntaxTextAreaCode.getText(), syntaxTextAreaDescription.getText(), comboBoxLang.getSelectedItem().toString());
                BasicFrame.INSTANCE.reloadTree();
                BasicFrame.INSTANCE.setSelectedTreePath(treePath);
            } catch (Exception ex)
            {
                Logger.getLogger(DialogAddRefresh.class.getName()).log(
                        Level.SEVERE,
                        null,
                        ex);
            }
        }

    }
}
