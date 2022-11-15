package com.ugo.ejerciciojokes.Main;

import java.sql.SQLException;

import com.ugo.ejerciciojokes.Data.MenuAplication;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
    	MenuAplication menu = new MenuAplication();
        menu.ejecutar();
    }
}
