package ru.ezhov.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import ru.ezhov.knowledgebook.OwnLookAndFeel;
import ru.ezhov.knowledgebook.frame.BasicFrame;

/**
 * @author rrndeonisiusezh
 */
public class TestBaseFrame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override

            public void run()
            {
                BasicFrame basicFrame = BasicFrame.INSTANCE;
                basicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                basicFrame.setLocationRelativeTo(null);
                basicFrame.setVisible(true);
            }
        });
    }

}
