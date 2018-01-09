/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.security;

import GNetworkUtility.NetworkManager;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import com.gdev.common.Controller;
import com.gdev.common.other.FileUtility;
import com.gdev.common.other.MessageDisplay;
import com.gdev.common.other.Utility;
import org.openide.LifecycleManager;

/**
 *
 * @author Gurwinder Singh
 */
public class Register extends javax.swing.JDialog {
     private final String REG_KEY = "xUR";

    /**
     * Creates new form Register
     */
    public Register(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(Utility.getCenterLocation(this.getSize()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtKey = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnReg = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtKey.setText(org.openide.util.NbBundle.getMessage(Register.class, "Register.txtKey.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(Register.class, "Register.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnReg, org.openide.util.NbBundle.getMessage(Register.class, "Register.btnReg.text")); // NOI18N
        btnReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnExit, org.openide.util.NbBundle.getMessage(Register.class, "Register.btnExit.text")); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReg)
                .addGap(18, 18, 18)
                .addComponent(btnExit)
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReg)
                    .addComponent(btnExit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
             LifecycleManager.getDefault().exit();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegActionPerformed
        // TODO add your handling code here:
        if(txtKey.getText().trim().length()==0){
            MessageDisplay.showErrorDialog(Controller.getDefault().getParent(), "Enter Register Key");
            return;
        }
        Hashtable<String, String> param = new Hashtable<String, String>();
        param.put("key", txtKey.getText().trim());

        JSONObject obj;
        try {
            obj = NetworkManager.getDefault().sendPostForJson("http://macsolution.pe.hu/valid.php", param, true);
            int res = obj.getInt("success");
            if (res == 0) {
                JOptionPane.showMessageDialog(rootPane, obj.getString("message"), "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                JSONArray arr = obj.getJSONArray("data");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject data = arr.getJSONObject(i);
                    String key = data.getString("valid");
                   
//                    String pr = CryptoUtils.encrypt(key);
                    FileUtility.getDefault().saveRegProperties("Config", key);
                    final Preferences userRoot = Preferences.userRoot();
                    userRoot.put(REG_KEY, key);
                
                JOptionPane.showMessageDialog(rootPane, data.getString("message"), "Success", JOptionPane.INFORMATION_MESSAGE);
                LifecycleManager.getDefault().markForRestart();
                LifecycleManager.getDefault().exit();
                }
                }
        } catch (Exception ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            MessageDisplay.showErrorDialog(Controller.getDefault().getParent(), "Error on Register "
                    + " {Try to run as Administrator for Registration Or Check Your Internet Connection.}  ");
        }
    }//GEN-LAST:event_btnRegActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnReg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtKey;
    // End of variables declaration//GEN-END:variables
}
