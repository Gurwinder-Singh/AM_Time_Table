/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.model;

/**
 *
 * @author Admin
 */
public class TeacherDetail implements Cloneable{

    private long id;
    private String name;
    private String teach_no;


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the teach_no
     */
    public String getTeach_no() {
        return teach_no;
    }

    /**
     * @param teach_no the teach_no to set
     */
    public void setTeach_no(String teach_no) {
        this.teach_no = teach_no;
    }
}
