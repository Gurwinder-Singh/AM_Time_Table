/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common;

/**
 *
 * @author SourceThink Developers
 */
public class RightClickMenusClass {

    public static RightClickMenusClass mInstance;

    public void RightClickMenusClass() {
    }

    public static RightClickMenusClass getDefault() {
        if (mInstance == null) {
            mInstance = new RightClickMenusClass();
        }
        return mInstance;
    }

    public String getMenus(int i) {
        if (i == ExplorerConstants.ACCOUNT_LIST) {
            return "Modify;"
                    + "Delete;";
        } else if (i == ExplorerConstants.SALE_REPORT) {
            return "Show Detail;"
                    + "Print[Vat Invoice, Retail Invoice];"
                    + "Modify;"
                    + "Delete;";
        }
        return "";
    }
}
