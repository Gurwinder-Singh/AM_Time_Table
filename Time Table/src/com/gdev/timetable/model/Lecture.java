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
public class Lecture {
    private SubjectAllocation group1;
    //Set group2 if class spilt into groups (Only 2 groups supported yet)
    private SubjectAllocation group2;

    /**
     * @return the group1
     */
    public SubjectAllocation getGroup1() {
        return group1;
    }

    /**
     * @param group1 the group1 to set
     */
    public void setGroup1(SubjectAllocation group1) {
        this.group1 = group1;
    }

    /**
     * @return the group2
     */
    public SubjectAllocation getGroup2() {
        return group2;
    }

    /**
     * @param group2 the group2 to set
     */
    public void setGroup2(SubjectAllocation group2) {
        this.group2 = group2;
    }
 
    @Override
    public String toString(){
        return  group1 +" - "+ group2;
    }
    
}
