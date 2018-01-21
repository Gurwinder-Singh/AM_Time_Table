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
public class SubjectDetail implements Cloneable {

    private long id;
    private String name;
    private String alias;
    private String sub_id;
    private String type;
    private String dep_name;
    private String branch_name;
    private int sem;
    private long dep_id;
    private long branch_id;

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
     * @return the sub_id
     */
    public String getSub_id() {
        return sub_id;
    }

    /**
     * @param sub_id the sub_id to set
     */
    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
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
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
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
    
    //Do not modify this
    @Override
    public String toString() {
        return name;
    }
}
