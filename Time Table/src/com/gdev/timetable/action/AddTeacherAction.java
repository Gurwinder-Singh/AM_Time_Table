/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.dialogs.CreateTeacher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "com.gdev.timetable.action.AddTeacherAction"
)
@ActionRegistration(
        displayName = "#CTL_AddTeacherAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Data Entry", position = 3036),
    @ActionReference(path = "Shortcuts", name = "DO-T")
})
@Messages("CTL_AddTeacherAction=Add Teacher")
public final class AddTeacherAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateTeacher dialog = CreateTeacher.getDefault();
        dialog.setVisible(true);
    }
}
