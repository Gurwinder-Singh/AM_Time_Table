/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable;


import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.helper.Utility;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Admin
 */
public class ChangePasswordDialog extends javax.swing.JDialog {

    private static ChangePasswordDialog mInstance;

    /**
     * Creates new form ChangePasswordDialog
     */
    public ChangePasswordDialog() {
        super(Utility.getDefault().getParent(), false);
        initComponents();
        setLocation(Utility.getCenterLocation(getSize()));
        Utility.addDefaultKeyListener(btnSubmit, btnCancel);
    }

    public static ChangePasswordDialog getDefault() {
        if (mInstance == null) {
            mInstance = new ChangePasswordDialog();
        }
        return mInstance;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        oldPassword = new javax.swing.JPasswordField();
        newPassword = new javax.swing.JPasswordField();
        RePassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnSubmit, org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.btnSubmit.text")); // NOI18N
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnCancel, org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.btnCancel.text")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        oldPassword.setText(org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.oldPassword.text")); // NOI18N

        newPassword.setText(org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.newPassword.text")); // NOI18N

        RePassword.setText(org.openide.util.NbBundle.getMessage(ChangePasswordDialog.class, "ChangePasswordDialog.RePassword.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSubmit)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel))
                    .addComponent(oldPassword)
                    .addComponent(newPassword)
                    .addComponent(RePassword, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(oldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(newPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(RePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        if (oldPassword.getPassword().length == 0) {
            MessageDisplay.showErrorDialog(this, "Please Enter Old Password");
            oldPassword.requestFocus();
            return;
        }
        if (newPassword.getPassword().length == 0) {
            MessageDisplay.showErrorDialog(this, "Please Enter New Password");
            newPassword.requestFocus();
            return;
        }
        if (!Arrays.toString(newPassword.getPassword()).equals(Arrays.toString(RePassword.getPassword()))) {
            MessageDisplay.showErrorDialog(this, "New Password and ReType Password Not Match");
            return;
        }
        String newPass = new String(newPassword.getPassword());
        String nPass = DatatypeConverter.printBase64Binary(newPass.getBytes());
        String oldPass = new String(oldPassword.getPassword());
        String oPass = DatatypeConverter.printBase64Binary(oldPass.getBytes());
        if(DbManager.getDefault().updatePassword(oPass, nPass)){
            MessageDisplay.showSuccessDialog(this, "Password Changed", "Success");
            this.dispose();
        }else{
            MessageDisplay.showErrorDialog(this, "Old Password Not Matched.");
            
        }

    }//GEN-LAST:event_btnSubmitActionPerformed


    public void clear(){
       oldPassword.setText("");
       newPassword.setText("");
       RePassword.setText("");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField RePassword;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField newPassword;
    private javax.swing.JPasswordField oldPassword;
    // End of variables declaration//GEN-END:variables

}
