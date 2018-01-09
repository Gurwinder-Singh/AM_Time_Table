/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.interfaces;

/**
 *
 * @author Gurwinder Singh
 */
public interface MacTableImplementPanelClass {
   boolean isCellEditable(int nRow, int nCol);
   public Class getcolumnClass(int c);
   public void refreshTable();
   void tableMouseClickedAction(java.awt.event.MouseEvent evt);
   void getAddBtnAction();
   boolean showTotalForColumn(int col);
   String[] getColumnNames();
   int getVisibleColumnCount();
   void setTableAttributes();
   String getParentName();
   Object getValueAt(int nrow, int ncol);
   void setValueAt(Object aValue, int rowIndex, int columnIndex);
}
