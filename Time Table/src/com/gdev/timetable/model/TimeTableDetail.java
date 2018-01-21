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
public class TimeTableDetail implements Cloneable {

    private long id;
    private long sub_alloc_id;
    private long sub_alloc_id2;
    private long day;
    private long lec;
    private long config_id;
    private Lecture lecture;
    private int error;
    private String errorReason;
    private long load;
    private String dep_name;
    private String branch_name;
    private int sem;

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
     * @return the sub_alloc_id
     */
    public long getSub_alloc_id() {
        return sub_alloc_id;
    }

    /**
     * @param sub_alloc_id the sub_alloc_id to set
     */
    public void setSub_alloc_id(long sub_alloc_id) {
        this.sub_alloc_id = sub_alloc_id;
    }

    /**
     * @return the day
     */
    public long getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(long day) {
        this.day = day;
    }

    /**
     * @return the lec
     */
    public long getLec() {
        return lec;
    }

    /**
     * @param lec the lec to set
     */
    public void setLec(long lec) {
        this.lec = lec;
    }

    /**
     * @return the lecture
     */
    public Lecture getLecture() {
        return lecture;
    }

    /**
     * @param lecture the lecture to set
     */
    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
 
    @Override
    public String toString(){
//        return " Id "+ id + " Day "+ day + " Lec "+ lec + " Lecture " + lecture;
        return " Lecture " + lecture + "--L" + load + (error>0? "----("+ error +")" + errorReason +"----" :"");
        
    }

    /**
     * @return the error
     */
    public int getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(int error) {
        this.error = error;
    }

    /**
     * @return the errorReason
     */
    public String getErrorReason() {
        return errorReason;
    }

    /**
     * @param errorReason the errorReason to set
     */
    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    /**
     * @return the load
     */
    public long getLoad() {
        return load;
    }

    /**
     * @param load the load to set
     */
    public void setLoad(long load) {
        this.load = load;
    }

    /**
     * @return the config_id
     */
    public long getConfig_id() {
        return config_id;
    }

    /**
     * @param config_id the config_id to set
     */
    public void setConfig_id(long config_id) {
        this.config_id = config_id;
    }

    /**
     * @return the dep_name
     */
    public String getDep_name() {
        return dep_name;
    }

    /**
     * @param dep_name the dep_name to set
     */
    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    /**
     * @return the branch_name
     */
    public String getBranch_name() {
        return branch_name;
    }

    /**
     * @param branch_name the branch_name to set
     */
    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    /**
     * @return the sem
     */
    public int getSem() {
        return sem;
    }

    /**
     * @param sem the sem to set
     */
    public void setSem(int sem) {
        this.sem = sem;
    }

    /**
     * @return the sub_alloc_id2
     */
    public long getSub_alloc_id2() {
        return sub_alloc_id2;
    }

    /**
     * @param sub_alloc_id2 the sub_alloc_id2 to set
     */
    public void setSub_alloc_id2(long sub_alloc_id2) {
        this.sub_alloc_id2 = sub_alloc_id2;
    }
}
