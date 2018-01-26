/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.reports;

import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.dialogs.CreateSubject;
import com.gdev.timetable.dialogs.FourFieldComboDialog;
import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.interfaces.ReportTableImplementPanelClass;
import com.gdev.timetable.model.Result;
import com.gdev.timetable.model.SubjectDetail;
import com.gdev.timetable.utility.Utility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JMenuItem;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.gdev.timetable.reports//SubjectReport//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SubjectReportTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.gdev.timetable.reports.SubjectReportTopComponent")
@ActionReferences({
    @ActionReference(path = "Menu/Reports", position = 335),
    @ActionReference(path = "Shortcuts", name = "DS-S")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SubjectReportAction",
        preferredID = "SubjectReportTopComponent"
)
@Messages({
    "CTL_SubjectReportAction=Subject Report",
    "CTL_SubjectReportTopComponent=Subject Report ",
    "HINT_SubjectReportTopComponent=This is a Subject Report "
})
public final class SubjectReportTopComponent extends TopComponent implements ReportTableImplementPanelClass {

    private Vector dataVector = null;
    private ActionListener lis;

    public SubjectReportTopComponent() {
        initComponents();
        setName(Bundle.CTL_SubjectReportTopComponent());
        setToolTipText(Bundle.HINT_SubjectReportTopComponent());
        reportTable1.addReportListener(this);
//        new listenrer();
        dataVector = DbManager.getDefault().getSubjects(0, 0, 0);
        reportTable1.setTableModel(dataVector);
        lis = new listenrer();
        Utility.setPopupMenu("Modify;Delete", reportTable1.getPopupMenu(), lis);
        reportTable1.setTitle("Subjects");
    }

    private class listenrer implements ActionListener {

        public listenrer() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                if (e.getActionCommand().equalsIgnoreCase("Modify")) {
                    if (reportTable1.getSelectedRow() > -1) {
                        SubjectDetail in = (SubjectDetail) reportTable1.getSelectedModelData().elementAt(0);
                        FourFieldComboDialog dialog = FourFieldComboDialog.getDefault();
                        dialog.setKey("MODIFY_SUBJECT");
                        try {
                            Vector v = new Vector();
                            v.add(in.clone());
                            dialog.setInfo(v);
                        } catch (CloneNotSupportedException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        dialog.setVisible(true);
                    }

                } else if (e.getActionCommand().equalsIgnoreCase("Delete")) {
                    if (reportTable1.getSelectedRow() > -1) {
                        if (MessageDisplay.showOptionDialog(null, "Do You Want to Delete This?", "Sure") == MessageDisplay.YES_OPTION) {
                            SubjectDetail in = (SubjectDetail) reportTable1.getSelectedModelData().elementAt(0);
                            Result r = DbManager.getDefault().deleteSubject(in);
                            if (r.isSuccess()) {
                                MessageDisplay.showSuccessDialog(null, "Subject Deleted Succesfully", "Success");
                            } else {
                                MessageDisplay.showErrorDialog(null, r.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reportTable1 = new com.gdev.timetable.helper.ReportTable();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reportTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(reportTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.gdev.timetable.helper.ReportTable reportTable1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        dataVector = DbManager.getDefault().getSubjects(0, 0, 0);
        reportTable1.setTableModel(dataVector);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public boolean isCellEditable(int nRow, int nCol) {
        return false;
    }

    @Override
    public Class getcolumnClass(int c) {
        Class[] cl = new Class[]{Integer.class, String.class, String.class, Integer.class, String.class, String.class, String.class, String.class};
        return cl[c];
    }

    public void setData() {

    }

    @Override
    public void refreshTable() {
        dataVector = DbManager.getDefault().getSubjects(0, 0, 0);
        reportTable1.setTableModel(dataVector);
    }

    @Override
    public void tableMouseClickedAction(MouseEvent evt) {
    }

    @Override
    public void getAddBtnAction() {
        CreateSubject subject = CreateSubject.getDefault();
        subject.init();
        subject.setVisible(true);
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"S/No", "Department", "Branch", "Sem", "Name", "Alias", "Subject Id", "Type"};
    }

    @Override
    public int getVisibleColumnCount() {
        return 8;
    }

    @Override
    public void setTableAttributes() {
        reportTable1.setColumnWidth(0, 45, 45);
    }

    @Override
    public Object getValueAt(int nrow, int ncol) {
        SubjectDetail row = (SubjectDetail) dataVector.elementAt(nrow);
        switch (ncol) {
            case 0:
                return nrow + 1;
            case 1:
                return row.getDep_name();
            case 2:
                return row.getBranch_name();
            case 3:
                return row.getSem();
            case 4:
                return row.getName();
            case 5:
                return row.getAlias();
            case 6:
                return row.getSub_id();
            case 7:
                return row.getType();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
}
