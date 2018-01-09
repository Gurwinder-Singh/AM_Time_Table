/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.model;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ConfigDetail {
    private long id;
    private long dep_id;
    private long branch_id;
    private long sem;
    private long total_lec;
    private long total_day;
    private long break_after;
    private String branch_name;
    private String dep_name;
    private int max_generations;

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
     * @return the total_lec
     */
    public long getTotal_lec() {
        return total_lec;
    }

    /**
     * @param total_lec the total_lec to set
     */
    public void setTotal_lec(long total_lec) {
        this.total_lec = total_lec;
    }

    /**
     * @return the total_day
     */
    public long getTotal_day() {
        return total_day;
    }

    /**
     * @param total_day the total_day to set
     */
    public void setTotal_day(long total_day) {
        this.total_day = total_day;
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
     * @return the break_after
     */
    public long getBreak_after() {
        return break_after;
    }

    /**
     * @param break_after the break_after to set
     */
    public void setBreak_after(long break_after) {
        this.break_after = break_after;
    }

    /**
     * @return the max_generations
     */
    public int getMax_generations() {
        return max_generations;
    }

    /**
     * @param max_generations the max_generations to set
     */
    public void setMax_generations(int max_generations) {
        this.max_generations = max_generations;
    }


    

}
