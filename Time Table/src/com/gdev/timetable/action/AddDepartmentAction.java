/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.SingleFieldDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "com.libManager.action.AddDepartmentAction"
)
@ActionRegistration(
        displayName = "#CTL_AddDepartmentAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Data Entry", position = 3033),
    @ActionReference(path = "Shortcuts", name = "DO-D")
})
@Messages("CTL_AddDepartmentAction=Add Department")
public final class AddDepartmentAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        SingleFieldDialog dialog = SingleFieldDialog.getDefault();
        dialog.setKey("Department");
        dialog.setVisible(true);
    }
}
