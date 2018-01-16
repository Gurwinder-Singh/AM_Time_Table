/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.reports;

import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.dialogs.CreateTeacher;
import com.gdev.timetable.helper.Utility;
import com.gdev.timetable.interfaces.ReportTableImplementPanelClass;
import com.gdev.timetable.model.TeacherDetail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JMenuItem;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.gdev.timetable.reports//TeacherReport//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TeacherReportTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.gdev.timetable.reports.TeacherReportTopComponent")
@ActionReferences({
    @ActionReference(path = "Menu/Reports", position = 336),
    @ActionReference(path = "Shortcuts", name = "DS-T")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TeacherReportAction",
        preferredID = "TeacherReportTopComponent"
)
@Messages({
    "CTL_TeacherReportAction=Teacher Report",
    "CTL_TeacherReportTopComponent=Teacher Report Window",
    "HINT_TeacherReportTopComponent=This is a Teacher Report window"
})
public final class TeacherReportTopComponent extends TopComponent implements ReportTableImplementPanelClass {

    private Vector dataVector = null;
    private ActionListener lis;

    public TeacherReportTopComponent() {
        initComponents();
        setName(Bundle.CTL_TeacherReportTopComponent());
        setToolTipText(Bundle.HINT_TeacherReportTopComponent());
        reportTable1.addReportListener(this);
//        new listenrer();
        dataVector = DbManager.getDefault().getTeacher();
        reportTable1.setTableModel(dataVector);
        lis = new listenrer();
        Utility.setPopupMenu("Modify;Delete", reportTable1.getPopupMenu(), lis);
        reportTable1.setTitle("Teachers");
    }

    private class listenrer implements ActionListener {

        public listenrer() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                if (e.getActionCommand().equalsIgnoreCase("Modify")) {
//                    if (reportTable1.getSelectedRow() > -1) {
//                        IdName in = (IdName) reportTable1.getSelectedModelData().elementAt(0);
//                        SingleFieldDialog dialog = SingleFieldDialog.getDefault();
//                        dialog.setKey("Modify_Department");
//                        try {
//                            dialog.setResponse(in.clone());
//                        } catch (CloneNotSupportedException ex) {
//                            Exceptions.printStackTrace(ex);
//                        }
//                        dialog.setVisible(true);
//                    }

                } else if (e.getActionCommand().equalsIgnoreCase("Delete")) {
//                    if (reportTable1.getSelectedRow() > -1) {
//                        if (MessageDisplay.showOptionDialog(null, "Do You Want to Delete This?", "Sure") == MessageDisplay.YES_OPTION) {
//                            IdName in = (IdName) reportTable1.getSelectedModelData().elementAt(0);
//                            Result r = DbManager.getDefault().deleteDepartment(in);
//                            if (r.isSuccess()) {
//                                MessageDisplay.showSuccessDialog(null, "Department Deleted Succesfully", "Success");
//                            } else {
//                                MessageDisplay.showErrorDialog(null, r.getMessage());
//                            }
//                        }
//                    }
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
            .addComponent(reportTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
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
        dataVector = DbManager.getDefault().getTeacher();
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
        Class[] cl = new Class[]{Integer.class, String.class, String.class};
        return cl[c];
    }

    public void setData() {

    }

    @Override
    public void refreshTable() {
        dataVector = DbManager.getDefault().getTeacher();
        reportTable1.setTableModel(dataVector);
    }

    @Override
    public void tableMouseClickedAction(MouseEvent evt) {
    }

    @Override
    public void getAddBtnAction() {
        CreateTeacher dialog = CreateTeacher.getDefault();
        dialog.setVisible(true);
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"S/No", "Name", "Reg. No"};
    }

    @Override
    public int getVisibleColumnCount() {
        return 3;
    }

    @Override
    public void setTableAttributes() {
        reportTable1.setColumnWidth(0, 45, 45);
    }

    @Override
    public Object getValueAt(int nrow, int ncol) {
        TeacherDetail row = (TeacherDetail) dataVector.elementAt(nrow);
        switch (ncol) {
            case 0:
                return nrow + 1;
            case 1:
                return row.getName();
            case 2:
                return row.getTeach_no();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
}
