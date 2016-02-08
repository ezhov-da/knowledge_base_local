/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ezhov.test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.knowledgebook.frame.OwnTreeModel;

/**
 *
 * @author rrndeonisiusezh
 */
public class TestLoadTree
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            OwnTreeModel.loadTree();
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestLoadTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(TestLoadTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(TestLoadTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
