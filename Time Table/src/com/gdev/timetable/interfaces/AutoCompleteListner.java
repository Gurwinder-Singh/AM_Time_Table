/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdev.timetable.interfaces;

import java.util.List;

/**
 * 
 * @author Gurwinder Singh
 */
public interface AutoCompleteListner {
    public List getData();
    public boolean search(String value, Object data);
    public boolean contains(String value, List data);
    public Object addDefault(String value);
    public String show(Object value);
    public boolean isMandatory();
    
}
