/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common;

import com.gdev.common.interfaces.MacTableListener;
import org.mac.common.messages.ExtendedMessage;
import com.gdev.common.other.ReportPanelTopComponent;

/**
 *
 * @author Gurwinder Singh
 */
public interface SubController {

    public void handleMessage(ExtendedMessage message) ;

    public void sendMessage(ExtendedMessage message);
    
    public MacTableListener getReportPanel(String name, ReportPanelTopComponent panel);
}
