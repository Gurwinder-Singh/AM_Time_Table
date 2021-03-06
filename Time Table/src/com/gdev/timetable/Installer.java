/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable;

import com.gdev.timetable.utility.FileUtility;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall {

    private Frame parent;

    @Override
    public void restored() {
        super.restored();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (FileUtility.getDefault().getProperties("THEME") != null && FileUtility.getDefault().getProperties("THEME").equals("DARK")) {
                    try {
                        UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");
                    } catch (ClassNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (InstantiationException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (IllegalAccessException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    WindowManager.getDefault().updateUI();
                }
                LogInDialog.getDefault().setVisible(true);
                parent = WindowManager.getDefault().getMainWindow();
                parent.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowActivated(WindowEvent e) {
                        parent.setExtendedState(Frame.MAXIMIZED_BOTH);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                WindowManager.getDefault().updateUI();
//                                StartUpTopComponent component = new StartUpTopComponent();
//                                component.open();
//                                component.requestActive();
                                DepartmentReportTopComponent component = new DepartmentReportTopComponent();
                                component.open();
                                component.requestActive();

                            }
                        });
                        parent.removeWindowListener(this);
                    }
                });
            }
        });
    }

    @Override
    public boolean closing() {
        int i = JOptionPane.showConfirmDialog(null, "Do You want to close Application?", "Sure?", JOptionPane.YES_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;

    }

}
