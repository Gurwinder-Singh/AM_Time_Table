/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.interfaces;


import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTable;
import com.gdev.common.util.CompletionUI;

/**
 *
 * @author Gurwinder Singh
 */
public interface CompletionUiListner {
    void performAddAction();
    Object getSelectedObject(String val, boolean all);
    Object getValueAt(int rowIndex, int columnIndex,boolean all);
    String[] getColumnName();
    Vector getDataVector(boolean all);
    void setDataVector(Vector data);
    void setAttributes(JTable attr);
    String getParentName();
    void addColumnSettings(JTable attr, boolean all);
    int getVisibleColumns();
    Class getColumnClass(int c);
    void updateDataVector(Vector data, JPanel completionUi, boolean inAll);
    boolean canAddAction();
}
