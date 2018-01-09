/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.interfaces;

import com.gdev.common.other.ReportPanel;
import com.gdev.common.other.ReportPanelTopComponent;

/**
 *
 * @author Gurwinder Singh
 */
public interface ReportListener {
    public MacTableListener getReportPanel(String name, ReportPanelTopComponent panel);
}
