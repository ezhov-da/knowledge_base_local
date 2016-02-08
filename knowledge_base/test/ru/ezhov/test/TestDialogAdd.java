package ru.ezhov.test;

import javax.swing.SwingUtilities;
import ru.ezhov.knowledgebook.OwnLookAndFeel;
import ru.ezhov.knowledgebook.frame.DialogAddRefresh;

/**
 *
 * @author rrndeonisiusezh
 */
public class TestDialogAdd
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
                DialogAddRefresh dialogAdd = DialogAddRefresh.INSTANCE;
                dialogAdd.showAdd(null, null);
            }
        });
    }

}
