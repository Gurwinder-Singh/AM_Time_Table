/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.dialogs.CreateSubjectAllocationDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "com.gdev.timetable.action.SubjectAllocationAction"
)
@ActionRegistration(
        displayName = "#CTL_SubjectAllocationAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Data Entry", position = 3037),
    @ActionReference(path = "Shortcuts", name = "DO-A")
})
@Messages("CTL_SubjectAllocationAction=Subject Allocation")
public final class SubjectAllocationAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateSubjectAllocationDialog dialog  = CreateSubjectAllocationDialog.getDefault();
        dialog.init();
        dialog.setVisible(true);
    }
}
