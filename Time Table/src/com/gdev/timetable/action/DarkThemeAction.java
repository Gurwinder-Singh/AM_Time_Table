/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.action;

import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.utility.FileUtility;
import com.gdev.timetable.utility.Utility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.CallbackSystemAction;

@ActionID(
        category = "Edit",
        id = "com.gdev.timetable.action.DarkThemeAction"
)
@ActionRegistration(
        displayName = "#CTL_DarkThemeAction"
)
@ActionReference(path = "Menu/Utility/Theme", position = 2501)
@Messages("CTL_DarkThemeAction=Dark")
public final class DarkThemeAction extends CallbackSystemAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        FileUtility.getDefault().saveProperties("THEME", "DARK");
        MessageDisplay.showSuccessDialog(Utility.getDefault().getParent(), "Theme Changged Succesfully."
                + " you need to restart application for effect", "Success");
        
    }

    @Override
    public String getName() {
        try {
            if (FileUtility.getDefault().getProperties("THEME") != null && FileUtility.getDefault().getProperties("THEME").equals("DARK")) {
                setIcon(new ImageIcon("com/gdev/timetable/action/check.png"));
            }
        } catch (Exception ex) {
            FileUtility.getDefault().saveProperties("THEME", "");
        }
         setEnabled(true);
        return "Dark";
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
}
