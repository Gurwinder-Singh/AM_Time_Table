/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common;

import org.mac.common.messages.TypeDetails;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import com.gdev.common.interfaces.CompletionUiListner;
import org.mac.common.messages.CategoryDetails;
import org.mac.common.messages.ItemDetails;
import org.mac.common.messages.LedgerDetail;
import org.mac.common.messages.StyleDetails;
import com.gdev.common.util.CompletionUI;

/**
 *
 * @author Gurwinder Singh
 */
public class CompletionUiData {

    private static CompletionUiData instance;
    private CompletionUiListner typeListner = null;
    private CompletionUiListner itemListner = null;
    private CompletionUiListner styleListner = null;
    private CompletionUiListner categoryListner = null;
    private CompletionUiListner ledgerListner = null;
    private CompletionUiListner drLedgerListner = null;
    private CompletionUiListner crLedgerListner = null;

    public static CompletionUiData getDefault() {
        if (instance == null) {
            instance = new CompletionUiData();
        }
        return instance;
    }

    public void clearAll() {
        itemListner = null;
        typeListner = null;
        styleListner = null;
        categoryListner = null;
        ledgerListner = null;
        drLedgerListner = null;
        crLedgerListner = null;
    }

    public CompletionUiListner getTypeListData() {
        if (typeListner == null) {
            typeListner = getTypeListner();
        }
        return typeListner;
    }

    public CompletionUiListner getItemListData() {
        if (itemListner == null) {
            itemListner = getItemListner();
        }
        return itemListner;
    }

    public CompletionUiListner getStyleListData() {
        if (styleListner == null) {
            styleListner = getStyleListner();
        }
        return styleListner;
    }

    public CompletionUiListner getCategoryListData() {
        if (categoryListner == null) {
            categoryListner = getCategoryListner();
        }
        return categoryListner;
    }

    public CompletionUiListner getLedgerListData() {
        if (ledgerListner == null) {
            ledgerListner = getLedgerListner(null);
        }
        return ledgerListner;
    }

    public CompletionUiListner getDrLedgerListData(Vector ledgers) {
        if (drLedgerListner == null) {
            drLedgerListner = getLedgerListner(ledgers);
        }
        return drLedgerListner;
    }

    public CompletionUiListner getCrLedgerListData(Vector ledgers) {
        if (crLedgerListner == null) {
            crLedgerListner = getLedgerListner(ledgers);
        }
        return crLedgerListner;
    }

    private CompletionUiListner getTypeListner() {

        CompletionUiListner TL = new CompletionUiListner() {
            private Vector<TypeDetails> DataVector = ((Vector) Controller.getDefault().getTypesInfo().clone());

            @Override
            public int getVisibleColumns() {
                return 2;
            }

            @Override
            public Object getValueAt(int row, int col, boolean addAll) {
                if (addAll && row == 0) {
                    if (col == 0) {
                        return "All";
                    } else {
                        return null;
                    }
                } else {
                    TypeDetails cRow = (TypeDetails) getDataVector(addAll).elementAt(row);
                    switch (col) {
                        case 0:
                            return cRow.getName();
                        case 1:
                            return cRow.getRemarks();
                    }
                }
                return "";
            }

            @Override
            public String[] getColumnName() {
                String columns[] = {"Name", "Remarks"};
                return columns;
            }

            @Override
            public Class getColumnClass(int col) {
                return String.class;
            }

            @Override
            public Vector getDataVector(boolean addAll) {
                return DataVector;
            }

            @Override
            public void setDataVector(Vector v) {
                DataVector = v;
            }

            @Override
            public Object getSelectedObject(String s, boolean addAll) {
                if (s.toLowerCase().equals("all") && addAll) {
                    return "All";
                }
                Vector<TypeDetails> v = getVectorWithAll(DataVector, addAll);

                for (int i = addAll ? 1 : 0; i < v.size(); i++) {
                    if (s.equals(v.elementAt(i).getName())) {
                        return v.elementAt(i);
                    }
                }
                return null;
            }

            @Override
            public void setAttributes(JTable t) {
                RowSorter sorter = t.getRowSorter();
                sorter.toggleSortOrder(0);

            }

            @Override
            public String getParentName() {
                return "";
//                return CompletionUiConstants.TYPE_MODEL;
            }

            @Override
            public void addColumnSettings(JTable t, boolean addAll) {
            }

            @Override
            public void updateDataVector(Vector data, JPanel completionUi, boolean addAll) {
                if (((CompletionUI) completionUi).getDataListener().getParentName().equals(this.getParentName())) {
                    DataVector = getVectorWithAll(data, addAll);
                }
            }

            @Override
            public void performAddAction() {
            }

            @Override
            public boolean canAddAction() {
                return true;
            }
        };
        return TL;
    }

    private Vector getVectorWithAll(Vector vec, boolean addAll) {
        if (vec == null) {
            vec = new Vector();
            if (addAll) {
                vec.add("All");
            }
            return vec;

        } else {
            if (addAll) {

                if (vec.size() == 0 || !vec.get(0).equals("All")) {
                    Vector v = (Vector) vec.clone();
                    v.add(0, "All");
                    return v;

                }

            } else {

                if (vec.size() > 0 && vec.get(0).equals("All")) {
                    Vector v = (Vector) vec.clone();
                    v.removeElementAt(0);
                    return v;
                }
            }

            return vec;
        }
    }

    private CompletionUiListner getItemListner() {

        CompletionUiListner IL = new CompletionUiListner() {
            private Vector<ItemDetails> DataVector = ((Vector) Controller.getDefault().getItemsInfo().clone());

            @Override
            public int getVisibleColumns() {
                return 2;
            }

            @Override
            public Object getValueAt(int row, int col, boolean addAll) {
                if (addAll && row == 0) {
                    if (col == 0) {
                        return "All";
                    } else {
                        return null;
                    }
                } else {
                    ItemDetails cRow = (ItemDetails) getDataVector(addAll).elementAt(row);
                    switch (col) {
                        case 0:
                            return cRow.getName();
                        case 1:
                            return cRow.getRemarks();
                    }
                }
                return "";
            }

            @Override
            public String[] getColumnName() {
                String columns[] = {"Name", "Remarks"};
                return columns;
            }

            @Override
            public Class getColumnClass(int col) {
                return String.class;
            }

            @Override
            public Vector getDataVector(boolean addAll) {
                return DataVector;
            }

            @Override
            public void setDataVector(Vector v) {
                DataVector = v;
            }

            @Override
            public Object getSelectedObject(String s, boolean addAll) {
                if (s.toLowerCase().equals("all") && addAll) {
                    return "All";
                }
                Vector<ItemDetails> v = getVectorWithAll(DataVector, addAll);

                for (int i = addAll ? 1 : 0; i < v.size(); i++) {
                    if (s.equals(v.elementAt(i).getName())) {
                        return v.elementAt(i);
                    }
                }
                return null;
            }

            @Override
            public void setAttributes(JTable t) {
                RowSorter sorter = t.getRowSorter();
                sorter.toggleSortOrder(0);

            }

            @Override
            public String getParentName() {
                return "";
//                return CompletionUiConstants.TYPE_MODEL;
            }

            @Override
            public void addColumnSettings(JTable t, boolean addAll) {
            }

            @Override
            public void updateDataVector(Vector data, JPanel completionUi, boolean addAll) {
                if (((CompletionUI) completionUi).getDataListener().getParentName().equals(this.getParentName())) {
                    DataVector = getVectorWithAll(data, addAll);
                }
            }

            @Override
            public void performAddAction() {
            }

            @Override
            public boolean canAddAction() {
                return true;
            }
        };
        return IL;
    }

    private CompletionUiListner getStyleListner() {

        CompletionUiListner SL = new CompletionUiListner() {
            private Vector<StyleDetails> DataVector = ((Vector) Controller.getDefault().getStylesInfo().clone());

            @Override
            public int getVisibleColumns() {
                return 2;
            }

            @Override
            public Object getValueAt(int row, int col, boolean addAll) {
                if (addAll && row == 0) {
                    if (col == 0) {
                        return "All";
                    } else {
                        return null;
                    }
                } else {
                    StyleDetails cRow = (StyleDetails) getDataVector(addAll).elementAt(row);
                    switch (col) {
                        case 0:
                            return cRow.getName();
                        case 1:
                            return cRow.getRemarks();
                    }
                }
                return "";
            }

            @Override
            public String[] getColumnName() {
                String columns[] = {"Name", "Remarks"};
                return columns;
            }

            @Override
            public Class getColumnClass(int col) {
                return String.class;
            }

            @Override
            public Vector getDataVector(boolean addAll) {
                return DataVector;
            }

            @Override
            public void setDataVector(Vector v) {
                DataVector = v;
            }

            @Override
            public Object getSelectedObject(String s, boolean addAll) {
                if (s.toLowerCase().equals("all") && addAll) {
                    return "All";
                }
                Vector<StyleDetails> v = getVectorWithAll(DataVector, addAll);

                for (int i = addAll ? 1 : 0; i < v.size(); i++) {
                    if (s.equals(v.elementAt(i).getName())) {
                        return v.elementAt(i);
                    }
                }
                return null;
            }

            @Override
            public void setAttributes(JTable t) {
                RowSorter sorter = t.getRowSorter();
                sorter.toggleSortOrder(0);

            }

            @Override
            public String getParentName() {
                return "";
//                return CompletionUiConstants.TYPE_MODEL;
            }

            @Override
            public void addColumnSettings(JTable t, boolean addAll) {
            }

            @Override
            public void updateDataVector(Vector data, JPanel completionUi, boolean addAll) {
                if (((CompletionUI) completionUi).getDataListener().getParentName().equals(this.getParentName())) {
                    DataVector = getVectorWithAll(data, addAll);
                }
            }

            @Override
            public void performAddAction() {
            }

            @Override
            public boolean canAddAction() {
                return true;
            }
        };
        return SL;
    }

    private CompletionUiListner getCategoryListner() {

        CompletionUiListner CL = new CompletionUiListner() {
            private Vector<CategoryDetails> DataVector = ((Vector) Controller.getDefault().getCategoryInfo().clone());

            @Override
            public int getVisibleColumns() {
                return 2;
            }

            @Override
            public Object getValueAt(int row, int col, boolean addAll) {
                if (addAll && row == 0) {
                    if (col == 0) {
                        return "All";
                    } else {
                        return null;
                    }
                } else {
                    CategoryDetails cRow = (CategoryDetails) getDataVector(addAll).elementAt(row);
                    switch (col) {
                        case 0:
                            return cRow.getName();
                        case 1:
                            return cRow.getRemarks();
                    }
                }
                return "";
            }

            @Override
            public String[] getColumnName() {
                String columns[] = {"Name", "Remarks"};
                return columns;
            }

            @Override
            public Class getColumnClass(int col) {
                return String.class;
            }

            @Override
            public Vector getDataVector(boolean addAll) {
                return DataVector;
            }

            @Override
            public void setDataVector(Vector v) {
                DataVector = v;
            }

            @Override
            public Object getSelectedObject(String s, boolean addAll) {
                if (s.toLowerCase().equals("all") && addAll) {
                    return "All";
                }
                Vector<CategoryDetails> v = getVectorWithAll(DataVector, addAll);

                for (int i = addAll ? 1 : 0; i < v.size(); i++) {
                    if (s.equals(v.elementAt(i).getName())) {
                        return v.elementAt(i);
                    }
                }
                return null;
            }

            @Override
            public void setAttributes(JTable t) {
                RowSorter sorter = t.getRowSorter();
                sorter.toggleSortOrder(0);

            }

            @Override
            public String getParentName() {
                return "";
//                return CompletionUiConstants.TYPE_MODEL;
            }

            @Override
            public void addColumnSettings(JTable t, boolean addAll) {
            }

            @Override
            public void updateDataVector(Vector data, JPanel completionUi, boolean addAll) {
                if (((CompletionUI) completionUi).getDataListener().getParentName().equals(this.getParentName())) {
                    DataVector = getVectorWithAll(data, addAll);
                }
            }

            @Override
            public void performAddAction() {
            }

            @Override
            public boolean canAddAction() {
                return true;
            }
        };
        return CL;
    }

    private CompletionUiListner getLedgerListner(final Vector led) {

        CompletionUiListner LD = new CompletionUiListner() {
            private Vector<LedgerDetail> dataVector = led;

            {
                if (dataVector == null) {
                    dataVector = ((Vector) Controller.getDefault().getLedgerInfo().clone());
                }
            }

            @Override
            public int getVisibleColumns() {
                return 2;
            }

            @Override
            public Object getValueAt(int row, int col, boolean addAll) {
                if (addAll && row == 0) {
                    if (col == 0) {
                        return "All";
                    } else {
                        return null;
                    }
                } else {
                    LedgerDetail cRow = (LedgerDetail) getDataVector(addAll).elementAt(row);
                    switch (col) {
                        case 0:
                            return cRow.getName();
                        case 1:
                            return cRow.getMobile();
                    }
                }
                return "";
            }

            @Override
            public String[] getColumnName() {
                String columns[] = {"Name", "Mobile"};
                return columns;
            }

            @Override
            public Class getColumnClass(int col) {
                Class c[] = {String.class, Long.class};
                return c[col];
            }

            @Override
            public Vector getDataVector(boolean addAll) {
                return dataVector;
            }

            @Override
            public void setDataVector(Vector v) {
                dataVector = v;
            }

            @Override
            public Object getSelectedObject(String s, boolean addAll) {
                if (s.toLowerCase().equals("all") && addAll) {
                    return "All";
                }
                Vector<LedgerDetail> v = getVectorWithAll(dataVector, addAll);

                for (int i = addAll ? 1 : 0; i < v.size(); i++) {
                    if (s.equals(v.elementAt(i).getName())) {
                        return v.elementAt(i);
                    }
                }
                return null;
            }

            @Override
            public void setAttributes(JTable t) {
                RowSorter sorter = t.getRowSorter();
                sorter.toggleSortOrder(0);

            }

            @Override
            public String getParentName() {
                return "";
//                return CompletionUiConstants.TYPE_MODEL;
            }

            @Override
            public void addColumnSettings(JTable t, boolean addAll) {
            }

            @Override
            public void updateDataVector(Vector data, JPanel completionUi, boolean addAll) {
                if (((CompletionUI) completionUi).getDataListener().getParentName().equals(this.getParentName())) {
                    dataVector = getVectorWithAll(data, addAll);
                }
            }

            @Override
            public void performAddAction() {
            }

            @Override
            public boolean canAddAction() {
                return true;
            }
        };
        return LD;
    }
}
