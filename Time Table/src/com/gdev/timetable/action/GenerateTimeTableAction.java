/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.dialogs.GenerateTimeTableDialog;
import com.gdev.timetable.ga.TimeTableGenerator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "com.gdev.timetable.action.GenerateTimeTableAction"
)
@ActionRegistration(
        displayName = "#CTL_GenerateTimeTableAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Admin", position = 3233),
    @ActionReference(path = "Shortcuts", name = "DO-G")
})
@Messages("CTL_GenerateTimeTableAction=Generate Time Table")
public final class GenerateTimeTableAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        GenerateTimeTableDialog dialog = GenerateTimeTableDialog.getDefault();
        dialog.init();
        dialog.setVisible(true);
                
    }
}
