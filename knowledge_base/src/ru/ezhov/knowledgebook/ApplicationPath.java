package ru.ezhov.knowledgebook;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author RRNDeonisiusEZH
 */
public class ApplicationPath {

    public static final String ENC = "UTF-8";

    public static String getPath() throws UnsupportedEncodingException {
        File path = new File("test");
        String pathString = path.getAbsoluteFile().getParent() + File.separator;
        return pathString;
    }
}
