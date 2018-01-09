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
public interface ResponseListener {
    void ResponseSuccess(ExtendedMessage message);
    void ResponseFail(ExtendedMessage message);
    void ResponseDuplicate(ExtendedMessage message);
}
