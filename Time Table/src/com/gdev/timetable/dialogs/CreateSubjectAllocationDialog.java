/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.dialogs;

import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.helper.AutoCompleteData;
import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.utility.Utility;
import com.gdev.timetable.model.IdName;
import com.gdev.timetable.model.Result;
import com.gdev.timetable.model.SubjectAllocation;
import com.gdev.timetable.model.SubjectDetail;
import com.gdev.timetable.model.TeacherDetail;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Gurwinder Singh
 */
public class CreateSubjectAllocationDialog extends javax.swing.JDialog {

    private static CreateSubjectAllocationDialog mInsatnce;
    private Vector dataVector;
    private boolean modify = false;

    /**
     * Creates new form CreateSubject
     */
    public CreateSubjectAllocationDialog() {
        super(Utility.getDefault().getParent(), false);
        initComponents();
        setLocation(Utility.getCenterLocation(getSize()));
        Utility.addDefaultKeyListener(btnSave, btnClose);
        dataVector = new Vector();
        inputTable.setModel(new CustomTableModel());
        setTitle("Allocate Subject");
    }

    public static CreateSubjectAllocationDialog getDefault() {
        if (mInsatnce == null) {
            mInsatnce = new CreateSubjectAllocationDialog();
        }
        return mInsatnce;
    }

    public void init() {
        combBranch.setData(AutoCompleteData.getDefault().getBranchListner(false));
        combDep.setData(AutoCompleteData.getDefault().getDepartmentListner(false));
        combTeacher.setData(AutoCompleteData.getDefault().getTeacherListner());

        clear();
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
        combBranch = new com.gdev.timetable.helper.AutoCompleteComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        combDep = new com.gdev.timetable.helper.AutoCompleteComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtLength = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        combType = new javax.swing.JComboBox();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        combSem = new javax.swing.JComboBox();
        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        combTeacher = new com.gdev.timetable.helper.AutoCompleteComboBox();
        combSubject = new com.gdev.timetable.helper.AutoCompleteComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtLoad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        combGroup = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        txtRepeat = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N

        combBranch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combBranchActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel2.text")); // NOI18N

        combDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combDepActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel4.text")); // NOI18N

        txtLength.setText(org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.txtLength.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel6.text")); // NOI18N

        combType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Theory", "Practical" }));
        combType.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(btnDelete, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.btnDelete.text")); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnAdd, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.btnAdd.text")); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        inputTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(inputTable);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel7.text")); // NOI18N

        combSem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        combSem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combSemActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnSave, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.btnSave.text")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnClose, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.btnClose.text")); // NOI18N

        combSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combSubjectActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel9.text")); // NOI18N

        txtLoad.setText(org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.txtLoad.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel10.text")); // NOI18N

        combGroup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2" }));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel11.text")); // NOI18N

        txtRepeat.setText(org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.txtRepeat.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(CreateSubjectAllocationDialog.class, "CreateSubjectAllocationDialog.jLabel12.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(combTeacher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtLoad)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(combGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(combSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(combType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtLength, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnSave)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnClose))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnAdd)
                                        .addGap(20, 20, 20)
                                        .addComponent(btnDelete)))))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combDep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(combBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combSem, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(combBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combSem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(combType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combTeacher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(combSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtLoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(combGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnDelete))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        if (combTeacher.getSelectedItem() == null || ((TeacherDetail) combTeacher.getSelectedItem()).getId() == 0) {
            MessageDisplay.showErrorDialog(this, "Please Select valid Teacher");
            return;
        }
        if (combSubject.getSelectedItem() == null || ((SubjectDetail) combSubject.getSelectedItem()).getId() == 0) {
            MessageDisplay.showErrorDialog(this, "Please Select valid Subject");
            return;
        }
        if (Utility.getLongValue(txtLength.getText()) <= 0) {
            MessageDisplay.showErrorDialog(this, "Please Enter Lecture length");
            txtLength.requestFocus();
            return;
        }
        if (Utility.getLongValue(txtLoad.getText()) <= 0) {
            MessageDisplay.showErrorDialog(this, "Please Enter Lecture Total Load");
            txtLoad.requestFocus();
            return;
        }

        SubjectAllocation detail = new SubjectAllocation();
        TeacherDetail teacher = (TeacherDetail) combTeacher.getSelectedItem();
        detail.setTeacher_id(teacher.getId());
        detail.setTech_name(teacher.getName());
        SubjectDetail subject = (SubjectDetail) combSubject.getSelectedItem();
        detail.setSubject_id(subject.getId());
        detail.setSubject_name(subject.getName());
        detail.setType(combType.getSelectedItem().toString());
        detail.setLength(Utility.getLongValue(txtLength.getText()));
        detail.setLoad(Utility.getLongValue(txtLoad.getText()));
        detail.setGroup(combGroup.getSelectedIndex() + 1);
        detail.setRepeat(Utility.getIntValue(txtRepeat.getText()));
        if (dataVector.stream().anyMatch((Object x1) -> {
            SubjectAllocation row = (SubjectAllocation) x1;
            return row.getSubject_id() == (detail.getSubject_id());
        })) {
            MessageDisplay.showErrorDialog(this, "Duplicate Record");
            return;
        }
        dataVector.add(detail);
        inputTable.tableChanged(null);
        combSubject.setSelectedItem(null);
        combTeacher.setSelectedItem(null);

        txtLength.setText("");
        txtLoad.setText("");
        combGroup.setSelectedIndex(0);
        txtRepeat.setText("");
        combType.setSelectedIndex(0);
        combDep.setEnabled(false);
        combBranch.setEnabled(false);
        combSem.setEnabled(false);

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (dataVector.isEmpty()) {
            MessageDisplay.showErrorDialog(this, "Please Enter data");
            return;
        }
        if (combDep.getSelectedItem() == null || ((IdName) combDep.getSelectedItem()).getId() == 0) {
            MessageDisplay.showErrorDialog(this, "Please Select valid Department");

            return;
        }
        if (combBranch.getSelectedItem() == null || ((IdName) combBranch.getSelectedItem()).getId() == 0) {
            MessageDisplay.showErrorDialog(this, "Please Select valid Branch");

            return;
        }
        for (int i = 0; i < dataVector.size(); i++) {
            SubjectAllocation element = (SubjectAllocation) dataVector.elementAt(i);
            element.setDep_id(((IdName) combDep.getSelectedItem()).getId());
            element.setBranch_id(((IdName) combBranch.getSelectedItem()).getId());
            element.setSem(combSem.getSelectedIndex() + 1);
        }
        Result r = null;
        if (modify) {
//            detail.setId(id);
//            r = DbManager.getDefault().modifyTeacher(detail);
        } else {
            r = DbManager.getDefault().allocSubject(dataVector);
        }
        if (r.isSuccess()) {
            MessageDisplay.showSuccessDialog(null, "Subjects Allocation Succesfully", "Success");
            clear();
        } else {
            MessageDisplay.showErrorDialog(null, r.getMessage());
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (inputTable.getSelectedRow() != -1) {
            dataVector.remove(inputTable.getSelectedRow());
            inputTable.tableChanged(null);
        }
        if (dataVector.isEmpty()) {
            combDep.setEnabled(true);
            combBranch.setEnabled(true);
            combSem.setEnabled(true);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void combDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combDepActionPerformed
        setSubjectData();
    }//GEN-LAST:event_combDepActionPerformed

    private void combBranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combBranchActionPerformed
        setSubjectData();
    }//GEN-LAST:event_combBranchActionPerformed

    private void combSemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combSemActionPerformed
        setSubjectData();
    }//GEN-LAST:event_combSemActionPerformed

    private void combSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combSubjectActionPerformed
        SubjectDetail detail = ((SubjectDetail) combSubject.getSelectedItem());
        if (detail != null && detail.getId()> 0) {
            combType.setSelectedItem(detail.getType());
        }

    }//GEN-LAST:event_combSubjectActionPerformed

    private void setSubjectData() {
        if (combDep.getSelectedItem() != null && ((IdName) combDep.getSelectedItem()).getId() > 0
                && combBranch.getSelectedItem() != null && ((IdName) combBranch.getSelectedItem()).getId() > 0) {

            combSubject.setData(AutoCompleteData.getDefault().getSubjectListner(((IdName) combDep.getSelectedItem()).getId(),
                    ((IdName) combBranch.getSelectedItem()).getId(),
                    combSem.getSelectedIndex() + 1));
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private com.gdev.timetable.helper.AutoCompleteComboBox combBranch;
    private com.gdev.timetable.helper.AutoCompleteComboBox combDep;
    private javax.swing.JComboBox combGroup;
    private javax.swing.JComboBox combSem;
    private com.gdev.timetable.helper.AutoCompleteComboBox combSubject;
    private com.gdev.timetable.helper.AutoCompleteComboBox combTeacher;
    private javax.swing.JComboBox combType;
    private javax.swing.JTable inputTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField txtLength;
    private javax.swing.JTextField txtLoad;
    private javax.swing.JTextField txtRepeat;
    // End of variables declaration//GEN-END:variables

    private void clear() {
        combDep.setSelectedItem(null);
        combBranch.setSelectedItem(null);
        combSem.setSelectedIndex(0);
        combType.setSelectedIndex(0);
//        txtName.setText("");
//        txtAlias.setText("");
        txtLength.setText("");
        dataVector.clear();
         inputTable.tableChanged(null);
        combDep.getInputField().requestFocus();
    }

    class CustomTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return dataVector.size();
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public String getColumnName(int column) {
            String[] name = {"S.No", "Teacher", "Subject", "Type", "Length", "Load", "Group", "Repeat"};
            return name[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SubjectAllocation row = (SubjectAllocation) dataVector.elementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return rowIndex + 1;
                case 1:
                    return row.getTech_name();
                case 2:
                    return row.getSubject_name();
                case 3:
                    return row.getType();
                case 4:
                    return row.getLength();
                case 5:
                    return row.getLoad();
                case 6:
                    return row.getGroup();
                case 7:
                    return row.getRepeat();

            }
            return null;
        }

    }
}