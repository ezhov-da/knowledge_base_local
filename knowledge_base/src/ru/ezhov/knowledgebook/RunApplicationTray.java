package ru.ezhov.knowledgebook;

import com.sun.jna.platform.win32.Kernel32;
import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.KeyStroke;
import ru.ezhov.knowledgebook.frame.BasicFrame;
import ru.ezhov.knowledgebook.frame.SettingsFrame;
import ru.ezhov.knowledgebook.frame.WindowFavorite;

/**
 *
 * @author RRNDeonisiusEZH
 */
public class RunApplicationTray {

    private final int pid = Kernel32.INSTANCE.GetCurrentProcessId();
    private TrayIcon trayIcon;
    private Provider provider;

    public void runTray() throws AWTException {
        //trayIcon = new TrayIcon(SettingsFrame.TRAY_ICON, "книга знаний");
        trayIcon = new TrayIcon(SettingsFrame.Icons.IMAGE_DATABASE, SettingsFrame.TextSettings.TITLE_APPLICATION + " PID:" + pid + ". Нажмите ПКМ для выхода.");
        if (SystemTray.isSupported()) {
            setProvider();
            trayIcon.addMouseListener(getListenerMouse());
            SystemTray.getSystemTray().add(trayIcon);
        }

    }

    private MouseAdapter getListenerMouse() {
        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    BasicFrame.INSTANCE.setVisible(true);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    stopProvider();
                    System.exit(0);
                }
            }
        };
        return mouseAdapter;

    }

    private void setProvider() {
        HotKeyListener hotKeyListenerShowBasicFrame = getHotKeyListenerShowBasicFrame();
        HotKeyListener hotKeyListenerShowFavoriteFrame = getHotKeyListenerShowFavoriteFrame();
        provider = Provider.getCurrentProvider(true);
        provider.register(KeyStroke.getKeyStroke("ctrl alt ENTER"), hotKeyListenerShowBasicFrame);
        provider.register(KeyStroke.getKeyStroke("ctrl alt SPACE"), hotKeyListenerShowFavoriteFrame);
    }

    private HotKeyListener getHotKeyListenerShowBasicFrame() {
        HotKeyListener hotKeyListener = new HotKeyListener() {
            @Override
            public void onHotKey(HotKey hotkey) {
                BasicFrame.INSTANCE.setVisible(true);
            }
        };
        return hotKeyListener;
    }

    private HotKeyListener getHotKeyListenerShowFavoriteFrame() {
        HotKeyListener hotKeyListener = new HotKeyListener() {
            @Override
            public void onHotKey(HotKey hotkey) {
                WindowFavorite.INSTANCE.setVisible(true);
            }
        };
        return hotKeyListener;
    }

    private void stopProvider() {
        provider.reset();
        provider.stop();
    }
}
