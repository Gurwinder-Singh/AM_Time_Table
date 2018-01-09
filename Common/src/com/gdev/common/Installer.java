/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.Key;
import java.util.prefs.Preferences;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.SwingUtilities;
import org.mac.common.messages.GetBook;
import com.gdev.common.other.FileUtility;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private final String REG_KEY = "xUR";
    private static String key = "Sat Kartar Gur Bar Akal";
    private Frame parent;

    @Override
    public void restored() {
        // TODO
        super.restored();
        System.out.println("common loaded");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                parent = WindowManager.getDefault().getMainWindow();
                parent.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowActivated(WindowEvent e) {
                        parent.setExtendedState(Frame.MAXIMIZED_BOTH);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                WindowManager.getDefault().updateUI();
                                final Preferences userRoot = Preferences.userRoot();
                                String v = userRoot.get(REG_KEY, "Error");
                                if (v.equals(FileUtility.getDefault().getRegProperties("Config"))) {
                                    Controller.getDefault().setRegister(true);
                                }
                                 
                            }
                        });
                        parent.removeWindowListener(this);
                    }
                });
            }
        });

    }

    private static String decrypt(String inputFile) {
        return doCrypto(Cipher.DECRYPT_MODE, inputFile);
    }

    private static String doCrypto(int cipherMode, String input) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            byte[] outputBytes = cipher.doFinal(input.getBytes());

            return outputBytes.toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
