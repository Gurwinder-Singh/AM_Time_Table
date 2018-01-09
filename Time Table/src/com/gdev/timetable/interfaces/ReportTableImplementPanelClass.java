/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.interfaces;

/**
 *
 * @author Admin
 */
public interface ReportTableImplementPanelClass {
   boolean isCellEditable(int nRow, int nCol);
   public Class getcolumnClass(int c);
   public void refreshTable();
   void tableMouseClickedAction(java.awt.event.MouseEvent evt);
   void getAddBtnAction();
   String[] getColumnNames();
   int getVisibleColumnCount();
   void setTableAttributes();
   Object getValueAt(int nrow, int ncol);
   void setValueAt(Object aValue, int rowIndex, int columnIndex);
}
