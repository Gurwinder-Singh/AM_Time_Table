/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.helper;

import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.interfaces.AutoCompleteListner;
import com.gdev.timetable.model.IdName;
import com.gdev.timetable.model.SubjectDetail;
import java.util.List;
import java.util.Vector;

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

    public AutoCompleteListner getSubjectListner(final long dep_id, final long branch_id) {
        AutoCompleteListner listner = new AutoCompleteListner() {

            @Override
            public List getData() {
                return DbManager.getDefault().getSubjects(dep_id, branch_id);
            }

            @Override
            public boolean search(String value, Object data) {
                return ((SubjectDetail) data).getName().toLowerCase().matches(value.toLowerCase() + "(.*)");
            }

            @Override
            public boolean contains(String value, List data) {
                return data.stream().anyMatch(x -> ((IdName) x).getName().equalsIgnoreCase(value));
            }

            @Override
            public Object addDefault(String value) {
                IdName n = new IdName();
                n.setName(value);
                return n;
            }

            @Override
            public String show(Object value) {
                SubjectDetail detail = (SubjectDetail) value;
                return String.format("%s | %10s | %10s", detail.getName(), detail.getType(), detail.getSub_id());
            }

            @Override
            public boolean isMandatory() {
                return true;
            }
        };

        return listner;
    }

    public AutoCompleteListner getDepartmentListner() {
        AutoCompleteListner listner = new AutoCompleteListner() {

            @Override
            public List getData() {
                return DbManager.getDefault().getDepartments();
            }

            @Override
            public boolean search(String value, Object data) {
                return ((IdName) data).getName().toLowerCase().matches(value.toLowerCase() + "(.*)");
            }

            @Override
            public String show(Object value) {
                return ((IdName) value).getName();
            }

            @Override
            public boolean isMandatory() {
                return true;
            }

            @Override
            public boolean contains(String value, List data) {
                return data.stream().anyMatch(x -> ((IdName) x).getName().equalsIgnoreCase(value));
            }

            @Override
            public Object addDefault(String value) {
                IdName n = new IdName();
                n.setName(value);
                return n;
            }
        };

        return listner;
    }

    public AutoCompleteListner getBranchListner() {
        AutoCompleteListner listner = new AutoCompleteListner() {

            @Override
            public List getData() {
                return DbManager.getDefault().getBranchs();
            }

            @Override
            public boolean search(String value, Object data) {
                return ((IdName) data).getName().toLowerCase().matches(value.toLowerCase() + "(.*)");
            }

            @Override
            public String show(Object value) {
                return ((IdName) value).getName();
            }

            @Override
            public boolean contains(String value, List data) {
                return data.stream().anyMatch(x -> ((IdName) x).getName().equalsIgnoreCase(value));
            }

            @Override
            public Object addDefault(String value) {
                IdName n = new IdName();
                n.setName(value);
                return n;
            }

            @Override
            public boolean isMandatory() {
                return true;
            }
        };

        return listner;
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
