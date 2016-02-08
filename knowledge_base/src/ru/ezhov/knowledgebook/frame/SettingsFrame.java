package ru.ezhov.knowledgebook.frame;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author RRNDeonisiusEZH
 */
public final class SettingsFrame
{

    public static final class TextSettings
    {

        public static final String TITLE_APPLICATION = "КНИГА ЗНАНИЙ";
        public static final String TITLE_FAVORITE = "ИЗБРАННОЕ";
        //ЗАОГОЛОВКИ РАЗДЕЛИТЕЛЕЙ-----------------------------------------------
        public static final String TITLE_NAME_PANEL_INFO = "информация";
        public static final String TITLE_NAME_PANEL_NAME = "название";
        public static final String TITLE_NAME_PANEL_CODE = "код";
        public static final String TITLE_NAME_PANEL_DESCRIPTION = "описание";
        public static final String TITLE_NAME_PANEL_FILE = "файл";
        //НАЗВАНИЕ ДЛЯ ДАННЫХ В ИМЕНИ-------------------------------------------
        public static final String LABEL_NAME_NAME = "<html><b>название: </b>";
        public static final String LABEL_NAME_DATE_FIRST_ADD = "<html><b>дата добавления: </b>";
        public static final String LABEL_NAME_USERNAME_FIRST_ADD = "<html><b>добавивший пользователь: </b>";
        public static final String LABEL_NAME_DATE_LAST_UPDATE = "<html><b>дата последнего обновления: </b>";
        public static final String LABEL_NAME_USER_LAST_UPDATE = "<html><b>обновивший пользователь: </b>";
        public static final String LABEL_NAME_FAVORITE = "<html><b>в избранном</b>";
        public static final String LABEL_NAME_NOT_FAVORITE = "<html><b>не в избранном</b>";
        public static final String LABEL_NAME_LANGUAGE = "<html><b>язык подсветки: </b>";
        //НАЗВАНИЕ ДЛЯ ДАННЫХ В ФАЙЛЕ-------------------------------------------
        public static final String LABEL_NAME_FILE = "добавленный файл: ";
    }

    /**
     * класс с массивом для выпадающего списка подсветки
     */
    public static final class Syntax
    {

        public static final String[] ARRAY_SYNTAX =
        {
            "plain",
            "actionscript",
            "asm",
            "bat",
            "bbcode",
            "c",
            "clojure",
            "cpp",
            "cs",
            "css",
            "d",
            "dart",
            "delphi",
            "dtd",
            "fortran",
            "groovy",
            "htaccess",
            "html",
            "java",
            "javascript",
            "jshintrc",
            "json",
            "jsp",
            "latex",
            "lisp",
            "lua",
            "makefile",
            "mxml",
            "nsis",
            "perl",
            "php",
            "properties",
            "python",
            "ruby",
            "sas",
            "scala",
            "sql",
            "tcl",
            "unix",
            "vb",
            "xml"
        };
    }

    /**
     * Класс, который содержит иконки
     */
    public static final class Icons
    {

        public static final Icon ICON_ADD = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_ADD.png"));
        public static final Icon ICON_CODE = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_CODE.png"));
        public static final Icon ICON_DATABASE = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_DATABASE.png"));
        public static final Image IMAGE_DATABASE = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_DATABASE.png")).getImage();
        public static final Icon ICON_DESCRIPTION = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_DESCRIPTION.png"));
        public static final Icon ICON_FILE = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_FILE.png"));
        public static final Icon ICON_LANG = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_LANG.png"));
        public static final Icon ICON_NAME = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_NAME.png"));
        public static final Icon ICON_REFRESH = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_REFRESH.png"));
        public static final Icon ICON_EMPTY_STAR = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_EMPTY_STAR.png"));
        public static final Icon ICON_FILL_STAR = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_FILL_STAR.png"));
        public static final Image ICON_FILL_STAR_TITLE = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_FILL_STAR.png")).getImage();

        public static final Icon ICON_ADD_16x16 = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_ADD_16x16.png"));
        public static final Icon ICON_DELETE_16x16 = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_DELETE_16x16.png"));
        public static final Icon ICON_REFRESH_16x16 = new ImageIcon(SettingsFrame.class.getResource("/ru/ezhov/knowledgebook/src/icons/ICON_REFRESH_16x16.png"));

    }

    public static final class Size
    {

        public static final int basicFrameWidth = Toolkit.getDefaultToolkit().getScreenSize().width - 200;
        public static final int basicFrameHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 200;
    }
}
