/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.utility.Utility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.LifecycleManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "org.mac.actions.RestoreAction")
@ActionRegistration(
        displayName = "#CTL_RestoreAction")
@ActionReference(path = "Menu/Utility", position = 2700)
@Messages("CTL_RestoreAction=Restore")
public final class RestoreAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose Restore Location");
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".attbk");
                }
            }

            @Override
            public String getDescription() {
                return "AM Time Table Backup File (*.attbk)";
            }
        });

        int i = chooser.showDialog(Utility.getDefault().getParent(), "Restore");
        if (i == JFileChooser.APPROVE_OPTION) {
            int j = MessageDisplay.showConfirmDialog(Utility.getDefault().getParent(),
                    "Restore will clear your current data and Restored to backup copy \n Do you want to Restore?",
                    "Sure?");
            if (j == MessageDisplay.YES_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                if (DbManager.getDefault().restoreBackup(path)) {
                    MessageDisplay.showSuccessDialog(Utility.getDefault().getParent(), "Backup Restored Completed \n Please Restart Application", "Success");
                    LifecycleManager.getDefault().markForRestart();
                    LifecycleManager.getDefault().exit();
                } else {
                    MessageDisplay.showErrorDialog(Utility.getDefault().getParent(), "Backup Restore Failed ", "Error");
                }
            }
        }
    }
}
