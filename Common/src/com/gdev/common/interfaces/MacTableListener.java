/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.interfaces;

import org.mac.common.messages.ExtendedMessage;

/**
 *
 * @author Gurwinder Singh
 */
public interface MacTableListener extends  MacTableImplementPanelClass{

    public void setResponse(ExtendedMessage msg);
    
}
