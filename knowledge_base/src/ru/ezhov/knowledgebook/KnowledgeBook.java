package ru.ezhov.knowledgebook;

import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import ru.ezhov.knowledgebook.frame.BasicFrame;

/**
 * основной класс, который запускает приложение
 *
 * @author RRNDeonisiusEZH
 */
public class KnowledgeBook {

    private static final Logger LOG = Logger.getLogger(KnowledgeBook.class.getName());

    public static void main(String[] args) {
        LOG.log(Level.INFO, "run application");

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                setLF();
                try {
                    new RunApplicationTray().runTray();
                    BasicFrame.INSTANCE.setVisible(true);
                } catch (AWTException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private static void setLF() {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }

    }
}
