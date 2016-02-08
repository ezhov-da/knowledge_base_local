package ru.ezhov.knowledgebook;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author RRNDeonisiusEZH
 */
public class OwnLookAndFeel
{

    private static final Logger logger = Logger.getLogger(OwnLookAndFeel.class.getName());

    public static void setLookAndFeel(final String lf)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(lf);
                } catch (Exception ex)
                {
                    logger.log(Level.SEVERE, "не найден указанный LookAndFeel", ex);
                }
            }
        });

    }

}
