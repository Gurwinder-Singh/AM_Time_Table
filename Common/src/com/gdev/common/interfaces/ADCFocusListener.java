/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.interfaces;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 * @author Gurwinder Singh
 */
public interface ADCFocusListener extends FocusListener{
    @Override
    void focusGained(FocusEvent e);
    @Override
    void focusLost(FocusEvent e);
}
