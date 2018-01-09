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
    private long day;
    private long lec;
    private String session;
    private Lecture lecture;
    private int error;
    private String errorReason;
    private long load;

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
     * @return the session
     */
    public String getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(String session) {
        this.session = session;
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
}
