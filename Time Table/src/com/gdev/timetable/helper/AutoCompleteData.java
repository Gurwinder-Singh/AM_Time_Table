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
import com.gdev.timetable.model.TeacherDetail;
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

    public AutoCompleteListner getSubjectListner(final long dep_id, final long branch_id, final long sem) {
        AutoCompleteListner listner = new AutoCompleteListner() {

            @Override
            public List getData() {
                return DbManager.getDefault().getSubjects(dep_id, branch_id, sem);
            }

            @Override
            public boolean search(String value, Object data) {
                return ((SubjectDetail) data).getName().toLowerCase().matches(stringEsc(value) + "(.*)");
            }

            @Override
            public boolean contains(String value, List data) {
                return data.stream().anyMatch(x -> ((SubjectDetail) x).getName().equalsIgnoreCase(value));
            }

            @Override
            public Object addDefault(String value) {
                SubjectDetail n = new SubjectDetail();
                n.setName(value);
                n.setType("");
                n.setSub_id("");
                return n;
            }

            @Override
            public String show(Object value) {
                SubjectDetail detail = (SubjectDetail) value;
                return String.format("%s | %10s | %10s", detail.getName(), detail.getType(), detail.getSub_id());
            }
        };

        return listner;
    }

    public AutoCompleteListner getDepartmentListner(final boolean all) {
        AutoCompleteListner listner = new AutoCompleteListner() {

            @Override
            public List getData() {
                return getVectorWithAll(DbManager.getDefault().getDepartments(), all);
            }

            @Override
            public boolean search(String value, Object data) {
                return ((IdName) data).getName().toLowerCase().matches(stringEsc(value) + "(.*)");
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
        };

        return listner;
    }

    public AutoCompleteListner getBranchListner(final boolean all) {
        AutoCompleteListner listner = new AutoCompleteListner() {

            @Override
            public List getData() {
                return getVectorWithAll(DbManager.getDefault().getBranchs(), all);
            }

            @Override
            public boolean search(String value, Object data) {
                return ((IdName) data).getName().toLowerCase().matches(stringEsc(value) + "(.*)");
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

        };

        return listner;
    }

    public AutoCompleteListner getTeacherListner() {
        AutoCompleteListner listner = new AutoCompleteListner() {

            @Override
            public List getData() {
                return DbManager.getDefault().getTeacher();
            }

            @Override
            public boolean search(String value, Object data) {
                return ((TeacherDetail) data).getName().toLowerCase().matches(stringEsc(value) + "(.*)");
            }

            @Override
            public boolean contains(String value, List data) {
                return data.stream().anyMatch(x -> ((TeacherDetail) x).getName().equalsIgnoreCase(value));
            }

            @Override
            public Object addDefault(String value) {
                TeacherDetail n = new TeacherDetail();
                n.setName(value);
                n.setTeach_no("");
                return n;
            }

            @Override
            public String show(Object value) {
                TeacherDetail detail = (TeacherDetail) value;
                return String.format("%s | %10s", detail.getName(), detail.getTeach_no());
            }
        };

        return listner;
    }

    private String stringEsc(String value){
        return value.toLowerCase().replaceAll("\"","").replaceAll("\\[","").replaceAll("\\]","").trim();
    }
    private Vector getVectorWithAll(Vector vec, boolean addAll) {
        if (vec == null) {
            vec = new Vector();
            if (addAll) {
                IdName id = new IdName();
                id.setId(-1);// -1 for all 0 for not existed 
                id.setName("All");
                vec.add(id);
            }
            return vec;

        } else {
            if (addAll) {
                Vector v = (Vector) vec.clone();
                IdName id = new IdName();
                id.setId(-1);// -1 for all 0 for not existed 
                id.setName("All");
                v.add(0, id);
                return v;
            }
            return vec;
        }
    }
}
