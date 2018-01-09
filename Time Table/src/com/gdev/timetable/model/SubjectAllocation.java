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
public class SubjectAllocation implements Cloneable {

    private long id;
    private long teacher_id;
    private long branch_id;
    private long dep_id;
    private long subject_id;
    private long load;
    private long length;
    private long config_id;
    private int priority;
    private int group;
    private long sem;
    private int repeat;
    private String tech_name;
    private String branch_name;
    private String dep_name;
    private String subject_name;
    private String type;
    private String session;

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
     * @return the teacher_id
     */
    public long getTeacher_id() {
        return teacher_id;
    }

    /**
     * @param teacher_id the teacher_id to set
     */
    public void setTeacher_id(long teacher_id) {
        this.teacher_id = teacher_id;
    }

    /**
     * @return the branch_id
     */
    public long getBranch_id() {
        return branch_id;
    }

    /**
     * @param branch_id the branch_id to set
     */
    public void setBranch_id(long branch_id) {
        this.branch_id = branch_id;
    }

    /**
     * @return the dep_id
     */
    public long getDep_id() {
        return dep_id;
    }

    /**
     * @param dep_id the dep_id to set
     */
    public void setDep_id(long dep_id) {
        this.dep_id = dep_id;
    }

    /**
     * @return the subject_id
     */
    public long getSubject_id() {
        return subject_id;
    }

    /**
     * @param subject_id the subject_id to set
     */
    public void setSubject_id(long subject_id) {
        this.subject_id = subject_id;
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
     * @return the length
     */
    public long getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the tech_name
     */
    public String getTech_name() {
        return tech_name;
    }

    /**
     * @param tech_name the tech_name to set
     */
    public void setTech_name(String tech_name) {
        this.tech_name = tech_name;
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
     * @return the subject_name
     */
    public String getSubject_name() {
        return subject_name;
    }

    /**
     * @param subject_name the subject_name to set
     */
    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the sem
     */
    public long getSem() {
        return sem;
    }

    /**
     * @param sem the sem to set
     */
    public void setSem(long sem) {
        this.sem = sem;
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
     * @return the group
     */
    public int getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(int group) {
        this.group = group;
    }

    /**
     * @return the repeat
     */
    public int getRepeat() {
        return repeat;
    }

    /**
     * @param repeat the repeat to set
     */
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
    
    @Override
    public String toString(){
        return  id+"";
    }
}
