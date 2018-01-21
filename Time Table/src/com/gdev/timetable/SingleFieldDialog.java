/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable;


import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.utility.Utility;
import com.gdev.timetable.model.IdName;
import com.gdev.timetable.model.Result;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class SingleFieldDialog extends javax.swing.JDialog {

    private static SingleFieldDialog mInstance;
    private String key = null;
    private Object response;

    /**
     * Creates new form NewJDialog
     */
    public SingleFieldDialog() {
        super(Utility.getDefault().getParent(), false);
        initComponents();
        setLocation(Utility.getCenterLocation(this.getSize()));
        Utility.getDefault().addDefaultKeyListener(btnSubmit, btnCancel);
    }

    public static SingleFieldDialog getDefault() {
        if (mInstance == null) {
            mInstance = new SingleFieldDialog();
        }
        return mInstance;
    }

    public void setKey(String key) {
        this.key = key;
         txtName.setText("");
        if (key.equalsIgnoreCase("Department")) {
            jPanel1.setBorder(Utility.getBorder("Add Department", jPanel1));
            jLabel1.setText("Name");
        } else if (key.equalsIgnoreCase("Modify_Department")) {
            jPanel1.setBorder(Utility.getBorder("Modify Department", jPanel1));
            jLabel1.setText("Name");
        } else if (key.equalsIgnoreCase("Branch")) {
            jPanel1.setBorder(Utility.getBorder("Add Branch", jPanel1));
            jLabel1.setText("Name");
        } else if (key.equalsIgnoreCase("Modify_Branch")) {
            jPanel1.setBorder(Utility.getBorder("Modify Branch", jPanel1));
            jLabel1.setText("Name");
        } 
    }

    public void setResponse(Object obj) {
        response = obj;
        if (key.equalsIgnoreCase("Modify_Department") || key.equalsIgnoreCase("Modify_Branch")) {
            IdName in = (IdName) obj;
            txtName.setText(in.getName());
        } 
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
        txtName = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(SingleFieldDialog.class, "SingleFieldDialog.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(0, 51, 153))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SingleFieldDialog.class, "SingleFieldDialog.jLabel1.text")); // NOI18N

        txtName.setText(org.openide.util.NbBundle.getMessage(SingleFieldDialog.class, "SingleFieldDialog.txtName.text")); // NOI18N
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnSubmit, org.openide.util.NbBundle.getMessage(SingleFieldDialog.class, "SingleFieldDialog.btnSubmit.text")); // NOI18N
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnCancel, org.openide.util.NbBundle.getMessage(SingleFieldDialog.class, "SingleFieldDialog.btnCancel.text")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(25, 25, 25)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSubmit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel)
                    .addComponent(btnSubmit))
                .addGap(0, 8, Short.MAX_VALUE))
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
        if (txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Value first.");
            txtName.requestFocus();
            txtName.selectAll();
            return;
        }
        if (key.equalsIgnoreCase("Department")) {
            IdName in = new IdName();
            in.setName(txtName.getText());
            Result r = DbManager.getDefault().addDepartment(in);
            if (r.isSuccess()) {
                MessageDisplay.showSuccessDialog(null, "Department Added Succesfully", "Success");
                txtName.setText("");
            } else {
                MessageDisplay.showErrorDialog(null, r.getMessage());
            }
        } else if (key.equalsIgnoreCase("Modify_Department")) {
            IdName in = (IdName) response;
            in.setName(txtName.getText());
            Result r = DbManager.getDefault().modifyDepartment(in);
            if (r.isSuccess()) {
                MessageDisplay.showSuccessDialog(null, "Department Modify Succesfully", "Success");
                txtName.setText("");
                this.dispose();
            } else {
                MessageDisplay.showErrorDialog(null, r.getMessage());
            }
        } else if (key.equalsIgnoreCase("Branch")) {
            IdName in = new IdName();
            in.setName(txtName.getText());
            Result r = DbManager.getDefault().addBranch(in);
            if (r.isSuccess()) {
                MessageDisplay.showSuccessDialog(null, "Branch Added Succesfully", "Success");
                txtName.setText("");
            } else {
                MessageDisplay.showErrorDialog(null, r.getMessage());
            }
        } else if (key.equalsIgnoreCase("Modify_Branch")) {
            IdName in = (IdName) response;
            in.setName(txtName.getText());
            Result r = DbManager.getDefault().modifyBranch(in);
            if (r.isSuccess()) {
                MessageDisplay.showSuccessDialog(null, "Branch Modify Succesfully", "Success");
                txtName.setText("");
                this.dispose();
            } else {
                MessageDisplay.showErrorDialog(null, r.getMessage());
            }
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
        btnSubmitActionPerformed(evt);
    }//GEN-LAST:event_txtNameActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
