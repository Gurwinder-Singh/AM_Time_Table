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
import javax.swing.JFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Edit",
id = "org.mac.actions.BackupAction")
@ActionRegistration(
    displayName = "#CTL_BackupAction")
@ActionReference(path = "Menu/Utility", position = 2600)
@Messages("CTL_BackupAction=Backup")
public final class BackupAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose Backup Location");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int i = chooser.showDialog(Utility.getDefault().getParent(), "Choose");
        if (i == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if(DbManager.getDefault().createBackup(path)){
                MessageDisplay.showSuccessDialog(Utility.getDefault().getParent(), "Backup Completed ", "Success");
            }else{
                MessageDisplay.showErrorDialog(Utility.getDefault().getParent(), "Backup Failed ", "Error");
            }
        }
    }
}
