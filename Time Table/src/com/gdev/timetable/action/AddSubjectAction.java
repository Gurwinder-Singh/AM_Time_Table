/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.dialogs.CreateSubject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "com.gdev.timetable.action.AddSubjectAction"
)
@ActionRegistration(
        displayName = "#CTL_AddSubjectAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Data Entry", position = 3035),
    @ActionReference(path = "Shortcuts", name = "DO-S")
})
@Messages("CTL_AddSubjectAction=Add Subject")
public final class AddSubjectAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateSubject subject = CreateSubject.getDefault();
        subject.init();
        subject.setVisible(true);
    }
}
