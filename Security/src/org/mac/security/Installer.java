/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.security;

import javax.swing.SwingUtilities;
import com.gdev.common.Controller;
import com.gdev.common.other.FileUtility;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // TODO
        System.out.println("Security Loading...");
//        if (FileUtility.getDefault().getRegProperties("Config") == null) {
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    Register reg = new Register(Controller.getDefault().getParent(), true);
//                    reg.setVisible(true);
//                }
//            });
//
//        }
        
//        CryptoUtilsTest.runDec();
    }
}
