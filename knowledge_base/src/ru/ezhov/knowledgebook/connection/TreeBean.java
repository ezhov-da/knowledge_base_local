package ru.ezhov.knowledgebook.connection;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import ru.ezhov.connectionproccessor.annotation.OutputColumn;
import ru.ezhov.connectionproccessor.annotation.OutputDataObject;

/**
 * класс из которого строится дерево
 *
 * @author rrndeonisiusezh
 */
@OutputDataObject
public class TreeBean
{

    @OutputColumn(nameColumn = "id")
    private int id;
    @OutputColumn(nameColumn = "fullPath")
    private String fullPath;
    @OutputColumn(nameColumn = "level")
    private int level;
    @OutputColumn(nameColumn = "name")
    private String name;
    @OutputColumn(nameColumn = "parentId")
    private int parentId;
    @OutputColumn(nameColumn = "code")
    private String code;
    @OutputColumn(nameColumn = "description")
    private String description;
    @OutputColumn(nameColumn = "firstAdd")
    private String firstAdd;
    @OutputColumn(nameColumn = "dateFirstAdd")
    private Timestamp dateFirstAdd;
    @OutputColumn(nameColumn = "favorites")
    private boolean favorites;
    @OutputColumn(nameColumn = "languageBacklight")
    private String languageBacklight;
    @OutputColumn(nameColumn = "lastChange")
    private String lastChange;
    @OutputColumn(nameColumn = "dateLastChange")
    private Timestamp dateLastChange;
    @OutputColumn(nameColumn = "nameFile")
    private String nameFile;
    @OutputColumn(nameColumn = "sizeFile")
    private int sizeFile;

    public TreeBean(
            int id,
            String fullPath,
            int level,
            String name,
            int parentId,
            String code,
            String description,
            String firstAdd,
            Timestamp dateFirstAdd,
            boolean favorites,
            String languageBacklight,
            String lastChange,
            Timestamp dateLastChange,
            String nameFile,
            int sizeFile)
    {
        this.id = id;
        this.fullPath = fullPath;
        this.level = level;
        this.name = name;
        this.parentId = parentId;
        this.code = code;
        this.description = description;
        this.firstAdd = firstAdd;
        this.dateFirstAdd = dateFirstAdd;
        this.favorites = favorites;
        this.languageBacklight = languageBacklight;
        this.lastChange = lastChange;
        this.dateLastChange = dateLastChange;
        this.nameFile = nameFile;
        this.sizeFile = sizeFile;
    }

    public TreeBean()
    {
    }

    public int getId()
    {
        return id;
    }

    public String getFullPath()
    {
        return fullPath;
    }

    public int getLevel()
    {
        return level;
    }

    public String getName()
    {
        return name;
    }

    public int getParentId()
    {
        return parentId;
    }

    public String getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    public String getFirstAdd()
    {
        return firstAdd;
    }

    public Timestamp getDateFirstAdd()
    {
        return dateFirstAdd;
    }

    public boolean isFavorites()
    {
        return favorites;
    }

    public String getLanguageBacklight()
    {
        return languageBacklight;
    }

    public String getLastChange()
    {
        return lastChange;
    }

    public Timestamp getDateLastChange()
    {
        return dateLastChange;
    }

    public String getNameFile()
    {
        return nameFile;
    }

    public int getSizeFile()
    {
        return sizeFile;
    }

    @Override
    public String toString()
    {
        return name;
    }

}
