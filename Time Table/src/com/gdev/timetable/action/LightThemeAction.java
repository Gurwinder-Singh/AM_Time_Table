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
import java.io.File;
import javax.swing.ImageIcon;
import org.openide.LifecycleManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.CallbackSystemAction;

@ActionID(
        category = "Edit",
        id = "com.gdev.timetable.action.LightThemeAction"
)
@ActionRegistration(
        //        iconBase = "com/gdev/timetable/action/check.png",
        displayName = "#CTL_LightThemeAction"
)
@ActionReference(path = "Menu/Utility/Theme", position = 2500)
@Messages("CTL_LightThemeAction=Light")
public final class LightThemeAction extends CallbackSystemAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        FileUtility.getDefault().saveProperties("THEME", "");
        MessageDisplay.showSuccessDialog(Utility.getDefault().getParent(), "Theme Changged you need to restart application for effect", "Success");
    }

    @Override
    public String getName() {
        try {
            if (FileUtility.getDefault().getProperties("THEME") == null || FileUtility.getDefault().getProperties("THEME").equals("")) {
//                setIcon(new ImageIcon("com/gdev/timetable/action/check.png"));
                putValue("iconBase","com/gdev/timetable/action/check.png");
            }
        } catch (Exception ex) {
            FileUtility.getDefault().saveProperties("THEME", "");
        }
        setEnabled(true);
        return "Light";
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

}
