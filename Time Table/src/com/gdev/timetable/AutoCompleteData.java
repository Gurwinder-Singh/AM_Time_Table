/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable;

import com.cc.customComponents.AutoCompleteUi;
import com.cc.customComponents.AutoCompleteUiListner;
import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.model.SubjectDetail;

import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;

/**
 *
 * @author Admin
 */
public class AutoCompleteData {

    private static AutoCompleteData instance;


    public static AutoCompleteData getDefault() {
        if (instance == null) {
            instance = new AutoCompleteData();
        }
        return instance;
    }

    public AutoCompleteUiListner getSubjectListner(final long dep_id,final long branch_id) {

        AutoCompleteUiListner books = new AutoCompleteUiListner() {
            private Vector<SubjectDetail> DataVector = ((Vector) DbManager.getDefault().getSubjects(dep_id, branch_id).clone());

            @Override
            public int getVisibleColumns() {
                return 3;
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
                    SubjectDetail cRow = (SubjectDetail) getDataVector(addAll).elementAt(row);
                    switch (col) {
                        case 0:
                            return cRow.getName();
                        case 1:
                            return cRow.getSub_id();
                        case 2:
                            return cRow.getType();
                    }
                }
                return "";
            }

            @Override
            public String[] getColumnName() {
                String columns[] = {"Name", "Subject Id",  "Type"};
                return columns;
            }

            @Override
            public Class getColumnClass(int col) {
                Class[] cls = {String.class,String.class,String.class};
                return cls[col];
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
                Vector<SubjectDetail> v = getVectorWithAll(DataVector, addAll);

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
            }

            @Override
            public void addColumnSettings(JTable t, boolean addAll) {
            }

            @Override
            public void updateDataVector(Vector data, JPanel completionUi, boolean addAll) {
                if (((AutoCompleteUi) completionUi).getDataListener().getParentName().equals(this.getParentName())) {
                    DataVector = getVectorWithAll(data, addAll);
                }
            }

            @Override
            public void performAddAction() {
            }

        };
        return books;
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
}
