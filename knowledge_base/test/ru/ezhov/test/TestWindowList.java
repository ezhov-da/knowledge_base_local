package ru.ezhov.test;

import javax.swing.SwingUtilities;
import ru.ezhov.knowledgebook.OwnLookAndFeel;
import ru.ezhov.knowledgebook.frame.WindowFavorite;

/**
 *
 * @author rrndeonisiusezh
 */
public class TestWindowList
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
                WindowFavorite favorite = WindowFavorite.INSTANCE;
                favorite.setVisible(true);
            }
        });
    }

}
