/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.helper;

import java.awt.Container;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MessageDisplay {
    public static int YES_OPTION = JOptionPane.YES_OPTION;
    public static int NO_OPTION = JOptionPane.NO_OPTION;
    public static void showErrorDialog(Container parent, String msg){
       showErrorDialog(parent, msg, "Error");
    }
     public static void showErrorDialog(Container parent, String msg, String title){
        JOptionPane.showMessageDialog(parent, msg, title, JOptionPane.ERROR_MESSAGE);
    }
    public static void showSuccessDialog(Container parent, String msg, String title){
        JOptionPane.showMessageDialog(parent, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static int showConfirmDialog(Container parent, String msg, String title){
        return JOptionPane.showConfirmDialog(parent, msg, title, JOptionPane.YES_NO_OPTION);
    }
     public static int showOptionDialog(Container parent, String msg, String title){
        return showOptionDialog(parent, msg,title, "Yes", "No", "Cancel");
    }
     public static int showOptionDialog(Container parent, String msg, String title, String btn1, String btn2, String btn3){
        return JOptionPane.showOptionDialog(parent, msg, title, JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, null, new String[]{btn1, btn2, btn3}, title);
    }
     
    
}
