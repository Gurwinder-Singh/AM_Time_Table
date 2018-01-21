/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.dialogs.AddConfigDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "com.gdev.timetable.action.AddConfigAction"
)
@ActionRegistration(
        displayName = "#CTL_AddConfigAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Admin", position = 3133),
    @ActionReference(path = "Shortcuts", name = "DO-C")
})
@Messages("CTL_AddConfigAction=Add Configuration")
public final class AddConfigAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        AddConfigDialog dialog = AddConfigDialog.getDefault();
        dialog.init();
        dialog.setVisible(true);
    }
}
