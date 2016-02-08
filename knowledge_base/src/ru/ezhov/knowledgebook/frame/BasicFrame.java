package ru.ezhov.knowledgebook.frame;

import com.sun.jna.platform.win32.Kernel32;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.JXTitledSeparator;
import ru.ezhov.knowledgebook.TreatmentHotKey;
import ru.ezhov.knowledgebook.connection.TreeBean;

/**
 * основная форма для отображения
 *
 * @author rrndeonisiusezh
 */
public class BasicFrame extends JFrame {

    private static final String VERSION = " v 1.02 ";
    private final int pid = Kernel32.INSTANCE.GetCurrentProcessId();

    /**
     * синглтон
     */
    public static final BasicFrame INSTANCE = new BasicFrame();
    //КОМПОНЕНТЫ----------------------------------------------------------------
    private final PanelTree panelTree = new PanelTree();
    private final PanelName panelName = new PanelName();
    private final PanelCode panelCode = new PanelCode();

    /**
     * основная разделительная панель
     */
    private final JSplitPane splitPaneBasic = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    /**
     * добавленный файл
     */
    private final JLabel labelFile = new JLabel();

    ;
    //--------------------------------------------------------------------------

    private BasicFrame() {
        init();
    }

    /**
     * класс для первой инициализации формы
     */
    protected void init() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "hideFavorite");
        getRootPane().getActionMap().put("hideFavorite", new AbstractAction("hideFavorite") {

            @Override
            public void actionPerformed(ActionEvent e) {
                BasicFrame.INSTANCE.setVisible(false);
            }
        });
        setTitle(SettingsFrame.TextSettings.TITLE_APPLICATION + VERSION + "PID: " + pid);
        setIconImage(SettingsFrame.Icons.IMAGE_DATABASE);
        setComponents(); //устанавливаем компоненты
        setSize(SettingsFrame.Size.basicFrameWidth, SettingsFrame.Size.basicFrameHeight);
        addWindowStateListener(new WindowAdapter() {

            @Override
            public void windowStateChanged(WindowEvent e) {
                if (1 == e.getNewState()) {
                    BasicFrame.INSTANCE.setVisible(false);
                }

            }

            @Override
            public void windowIconified(WindowEvent e) {
                BasicFrame.INSTANCE.setVisible(false);
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    protected void setComponents() {
        JPanel panelBasic = (JPanel) getContentPane();  //получаем главную панель
        panelBasic.setLayout(new BorderLayout());
        panelBasic.add(splitPaneBasic, BorderLayout.CENTER); //добавляем базовый разделитель в центр
        splitPaneBasic.setLeftComponent(panelTree); //добавили панель с деревом в левую часть панели
        JPanel panelDescription = new JPanel(new BorderLayout()); //панель с отображение форм
        splitPaneBasic.setRightComponent(panelDescription); //добавили панель с описаниями в правую часть панели
        splitPaneBasic.setResizeWeight(0.2);
        //добавляем необходимые панели в панель правой части
        panelDescription.add(panelName, BorderLayout.NORTH);
        panelDescription.add(panelCode, BorderLayout.CENTER);

    }

    public void reloadTree() {

        panelTree.loadTree();

    }

    /**
     * метод делает выбранным узел
     *
     * @param treePath
     */
    public void setSelectedTreePath(TreePath treePath) {
        panelTree.tree.setSelectionPath(treePath);
    }

    /**
     * панель с деревом
     */
    private class PanelTree extends JPanel {

        /**
         * основное дерево
         */
        private final JTree tree = new JTree();
        private final TreeSelectionListener treeSelectionListener = new ListenerTree();
        private final TreatmentHotKey treatmentHotKey = new TreatmentHotKey();
        private final PopupMenuTree menu = PopupMenuTree.INSTANCE;

        public PanelTree() {
            init();

        }

        private void init() {
            loadTree();
            setLayout(new BorderLayout());
            add(new JScrollPane(tree), BorderLayout.CENTER); //добавляем дерево в центр панели
            tree.addMouseListener(new ShowPopupMenu());
            tree.setCellRenderer(new OwnTreeCellRenderer());
            tree.addKeyListener(new KeyListener());
            tree.setExpandsSelectedPaths(true);

        }

        public void loadTree() {

            {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            tree.removeTreeSelectionListener(treeSelectionListener);
                            tree.setModel(OwnTreeModel.loadTree());
                            tree.addTreeSelectionListener(treeSelectionListener);
                        } catch (Exception ex) {
                            Logger.getLogger(BasicFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

            }

        }

        /**
         * слушатель на отображение контексного меню
         */
        private class ShowPopupMenu extends MouseAdapter {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!SwingUtilities.isRightMouseButton(e)) {
                    return;
                }
                JTree jTree = (JTree) e.getSource();
                TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
                if (path == null) {
                    return;
                }
                Rectangle pathBounds = jTree.getUI().getPathBounds(tree, path);
                menu.show(jTree, pathBounds.x, pathBounds.y + pathBounds.height, path);

            }

        }

        private class OwnTreeCellRenderer extends DefaultTreeCellRenderer {

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) value;
                JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus); //To change body of generated methods, choose Tools | Templates.
                String text = label.getText();
                if (!leaf) {
                    label.setText("<html><b>" + text + "</b>");
                }
                Object object = defaultMutableTreeNode.getUserObject();

                if (object instanceof TreeBean) {

                    TreeBean treeBean = (TreeBean) defaultMutableTreeNode.getUserObject();

                    if (treeBean.isFavorites()) {
                        label.setIcon(SettingsFrame.Icons.ICON_FILL_STAR);
                        return label;
                    } else {
                        return label;
                    }
                } else {
                    return label;
                }
            }

        }

        private class KeyListener extends KeyAdapter {

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        System.out.println("enter");
                        treatmentHotKey.getTextFromTree(tree);
                        break;
                    case KeyEvent.VK_CONTEXT_MENU:
                        JTree jTree = (JTree) e.getSource();
                        TreePath path = jTree.getSelectionPath();
                        if (path == null) {
                            return;
                        }
                        Rectangle pathBounds = jTree.getUI().getPathBounds(tree, path);
                        menu.show(jTree, pathBounds.x, pathBounds.y + pathBounds.height, path);
                        break;
                }

            }

        }

    }

    /**
     * панель с названием
     */
    private class PanelName extends JPanel {

        private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private final JXTitledSeparator titledSeparator = new JXTitledSeparator(SettingsFrame.TextSettings.TITLE_NAME_PANEL_INFO, SwingConstants.LEFT, SettingsFrame.Icons.ICON_NAME);
        private final JLabel labelName = new JLabel();
        private final JLabel labelUserLastChange = new JLabel();
        private final JLabel labelDateLastChange = new JLabel();
        private final JLabel labelUserFirstChange = new JLabel();
        private final JLabel labelDateFirstChange = new JLabel();
        /**
         * отображение избранности
         */
        private final JLabel labelFavorite = new JLabel();
        /**
         * язык подсветки
         */
        private final JLabel labelLanguage = new JLabel();
        //
        private final JLabel strLabelName = new JLabel(SettingsFrame.TextSettings.LABEL_NAME_NAME);
        private final JLabel strLabelUserLastChange = new JLabel(SettingsFrame.TextSettings.LABEL_NAME_USER_LAST_UPDATE);
        private final JLabel strLabelDateLastChange = new JLabel(SettingsFrame.TextSettings.LABEL_NAME_DATE_LAST_UPDATE);
        private final JLabel strLabelUserFirstChange = new JLabel(SettingsFrame.TextSettings.LABEL_NAME_USERNAME_FIRST_ADD);
        private final JLabel strLabelDateFirstChange = new JLabel(SettingsFrame.TextSettings.LABEL_NAME_DATE_FIRST_ADD);
        /**
         * язык подсветки
         */
        private final JLabel strLabelLanguage = new JLabel(SettingsFrame.TextSettings.LABEL_NAME_LANGUAGE);
        //
        private final Insets insets = new Insets(2, 5, 2, 5);

        public PanelName() {
            init();
        }

        private void init() {
            setLayout(new BorderLayout());
            add(titledSeparator, BorderLayout.NORTH);

            JPanel panelName = new JPanel(new GridBagLayout());
            //1
            panelName.add(strLabelName, new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
            panelName.add(labelName, new GridBagConstraints(1, 0, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
            //2
            panelName.add(strLabelUserFirstChange, new GridBagConstraints(0, 1, 1, 1, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
            panelName.add(labelUserFirstChange, new GridBagConstraints(1, 1, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
            //3
            panelName.add(strLabelDateLastChange, new GridBagConstraints(0, 2, 1, 1, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
            panelName.add(labelDateFirstChange, new GridBagConstraints(1, 2, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
            //4
            panelName.add(strLabelUserLastChange, new GridBagConstraints(0, 3, 1, 1, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
            panelName.add(labelUserLastChange, new GridBagConstraints(1, 3, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
            //5
            panelName.add(strLabelDateFirstChange, new GridBagConstraints(0, 4, 1, 1, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
            panelName.add(labelDateLastChange, new GridBagConstraints(1, 4, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
            //6
            panelName.add(labelFavorite, new GridBagConstraints(1, 5, 1, 1, 0, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
            //7
            panelName.add(strLabelLanguage, new GridBagConstraints(0, 6, 1, 1, 0, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
            panelName.add(labelLanguage, new GridBagConstraints(1, 6, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));

            //--------------------------------
            labelName.setHorizontalAlignment(SwingConstants.LEFT);
            labelUserLastChange.setHorizontalAlignment(SwingConstants.LEFT);
            labelDateLastChange.setHorizontalAlignment(SwingConstants.LEFT);
            labelUserFirstChange.setHorizontalAlignment(SwingConstants.LEFT);
            labelDateFirstChange.setHorizontalAlignment(SwingConstants.LEFT);
            labelFavorite.setHorizontalAlignment(SwingConstants.LEFT);
            labelLanguage.setHorizontalAlignment(SwingConstants.LEFT);
            //
            strLabelName.setHorizontalAlignment(SwingConstants.RIGHT);
            strLabelUserFirstChange.setHorizontalAlignment(SwingConstants.RIGHT);
            strLabelDateLastChange.setHorizontalAlignment(SwingConstants.RIGHT);
            strLabelUserLastChange.setHorizontalAlignment(SwingConstants.RIGHT);
            strLabelDateFirstChange.setHorizontalAlignment(SwingConstants.RIGHT);
            strLabelLanguage.setHorizontalAlignment(SwingConstants.RIGHT);

            //--------------------------------
            add(panelName, BorderLayout.CENTER); //добавляем дерево в центр панели
        }

        public void setLabelName(String name) {
            labelName.setText(name);
        }

        public void setLabelUserLastChange(String userLastChange) {
            labelUserLastChange.setText((userLastChange == null ? "" : userLastChange));
        }

        public void setLabelDateLastChange(Timestamp dateLastChange) {
            labelDateLastChange.setText((dateLastChange == null ? "" : dateFormat.format(dateLastChange)));
        }

        public void setLabelUserFirstChange(String userFirstChange) {
            labelUserFirstChange.setText(userFirstChange);
        }

        public void setLabelDateFirstChange(Timestamp dateFirstChange) {
            labelDateFirstChange.setText((dateFirstChange == null ? "" : dateFormat.format(dateFirstChange)));
        }

        public void setLabelFavorite(Boolean favorite) {
            if (favorite) {
                labelFavorite.setText(SettingsFrame.TextSettings.LABEL_NAME_FAVORITE);
                labelFavorite.setIcon(SettingsFrame.Icons.ICON_FILL_STAR);
            } else {
                labelFavorite.setText(SettingsFrame.TextSettings.LABEL_NAME_NOT_FAVORITE);
                labelFavorite.setIcon(SettingsFrame.Icons.ICON_EMPTY_STAR);
            }
        }

        public void setLabelLanguage(String language) {
            labelLanguage.setText(language);
        }

    }

    /**
     * панель кодом и описанием
     */
    private class PanelCode extends JPanel {

        private final JXTitledSeparator titledSeparatorCode = new JXTitledSeparator(SettingsFrame.TextSettings.TITLE_NAME_PANEL_CODE, SwingConstants.LEFT, SettingsFrame.Icons.ICON_CODE);
        private final JXTitledSeparator titledSeparatorDescription = new JXTitledSeparator(SettingsFrame.TextSettings.TITLE_NAME_PANEL_DESCRIPTION, SwingConstants.LEFT, SettingsFrame.Icons.ICON_DESCRIPTION);
        /**
         * разделительная панель для описания и кода
         */
        private final JSplitPane splitPaneDescription = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        /**
         * вывод кода
         */
        private final RSyntaxTextArea textPaneCode = new RSyntaxTextArea();
        /**
         * вывод описания
         */
        private final RSyntaxTextArea textPaneDescription = new RSyntaxTextArea();

        public PanelCode() {
            init();
        }

        private void init() {
            setLayout(new BorderLayout());
            add(splitPaneDescription, BorderLayout.CENTER); //добавляем дерево в центр панели

            textPaneCode.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
            textPaneCode.setCodeFoldingEnabled(true);
            textPaneCode.setEditable(false);
            RTextScrollPane textScrollPaneCode = new RTextScrollPane(textPaneCode);
            //
            textPaneDescription.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
            textPaneDescription.setCodeFoldingEnabled(true);
            textPaneDescription.setEditable(false);
            RTextScrollPane textScrollPaneDescription = new RTextScrollPane(textPaneDescription);
            //
            JPanel panelCode = new JPanel(new BorderLayout());
            panelCode.add(titledSeparatorCode, BorderLayout.NORTH);
            panelCode.add(textScrollPaneCode, BorderLayout.CENTER);
            //
            JPanel panelDescription = new JPanel(new BorderLayout());
            panelDescription.add(titledSeparatorDescription, BorderLayout.NORTH);
            panelDescription.add(textScrollPaneDescription, BorderLayout.CENTER);
            //
            splitPaneDescription.setTopComponent(panelCode);
            splitPaneDescription.setBottomComponent(panelDescription);
            splitPaneDescription.setOneTouchExpandable(true);
            splitPaneDescription.setResizeWeight(0.7);

        }

        public void setTextPaneCode(String text, String syntax) {
            textPaneCode.setSyntaxEditingStyle("text/" + syntax);
            textPaneCode.setText(text);
        }

        public void setTextPaneDescription(String text) {
            textPaneDescription.setText(text);
        }

    }

    private class ListenerTree implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
            TreeBean treeBean = (TreeBean) defaultMutableTreeNode.getUserObject();
            //
            BasicFrame.this.panelName.setLabelName(treeBean.getName());
            BasicFrame.this.panelName.setLabelUserFirstChange(treeBean.getFirstAdd());
            BasicFrame.this.panelName.setLabelDateFirstChange(treeBean.getDateFirstAdd());
            BasicFrame.this.panelName.setLabelUserLastChange(treeBean.getLastChange());
            BasicFrame.this.panelName.setLabelDateLastChange(treeBean.getDateLastChange());
            BasicFrame.this.panelName.setLabelFavorite(treeBean.isFavorites());
            BasicFrame.this.panelName.setLabelLanguage(treeBean.getLanguageBacklight());
            //
            BasicFrame.this.panelCode.setTextPaneCode(treeBean.getCode(), treeBean.getLanguageBacklight());
            BasicFrame.this.panelCode.setTextPaneDescription(treeBean.getDescription());
        }
    }
}
