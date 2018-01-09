/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.other;

import java.awt.event.MouseEvent;
import com.gdev.common.Controller;
import com.gdev.common.interfaces.MacTableListener;
import org.mac.common.messages.ExtendedMessage;

/**
 *
 * @author Gurwinder Singh
 */
public class ReportPanel extends javax.swing.JPanel implements MacTableListener {

    /**
     * Creates new form ReportPanel
     */
    MacTableListener report = null;
    Controller controller =null;

    public ReportPanel() {
        initComponents();
        controller = Controller.getDefault();
    }

    public void setReport(String name, ReportPanelTopComponent component) {
      report =  controller.getReportPanel(name, component);
    }
    
    public MacTable getMacTable(){
        return macTable1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        macTable1 = new com.gdev.common.other.MacTable();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(macTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(macTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.gdev.common.other.MacTable macTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean isCellEditable(int nRow, int nCol) {
        return report.isCellEditable(nRow, nCol);
    }

    @Override
    public Class getcolumnClass(int c) {
        return report.getcolumnClass(c);
    }

    @Override
    public void refreshTable() {
        report.refreshTable();
    }

    @Override
    public void tableMouseClickedAction(MouseEvent evt) {
        report.tableMouseClickedAction(evt);
    }

    @Override
    public void getAddBtnAction() {
        report.getAddBtnAction();
    }

    @Override
    public boolean showTotalForColumn(int col) {
        return report.showTotalForColumn(col);
    }

    @Override
    public String[] getColumnNames() {
        return report.getColumnNames();
    }

    @Override
    public int getVisibleColumnCount() {
        return report.getVisibleColumnCount();
    }

    @Override
    public void setTableAttributes() {
        report.setTableAttributes();
    }

    @Override
    public String getParentName() {
        return report.getParentName();
    }

    @Override
    public Object getValueAt(int nrow, int ncol) {
        return report.getValueAt(nrow, ncol);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        report.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public void setResponse(ExtendedMessage msg) {
        report.setResponse(msg);
    }
}