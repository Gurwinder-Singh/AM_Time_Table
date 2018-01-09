/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.interfaces;

/**
 *
 * @author Gurwinder Singh
 */
public interface ITotalTableFooter {
    public boolean showTotalForColumn(int column);
    public Object getTotal(int c);
}
