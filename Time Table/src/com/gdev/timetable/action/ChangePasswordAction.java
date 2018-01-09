/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.ChangePasswordDialog;
import com.gdev.timetable.helper.TimeTableGenerator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "com.libManager.action.ChangePasswordAction"
)
@ActionRegistration(
        displayName = "#CTL_ChangePasswordAction"
)
@ActionReference(path = "Menu/Admin", position = 3333)
@Messages("CTL_ChangePasswordAction=Change Password")
public final class ChangePasswordAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TimeTableGenerator gen= new TimeTableGenerator();
        gen.generate();
//        ChangePasswordDialog dialog = ChangePasswordDialog.getDefault();
//        dialog.clear();
//        dialog.setVisible(true);
    }
}
