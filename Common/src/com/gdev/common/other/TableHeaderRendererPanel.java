/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.other;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class TableHeaderRendererPanel extends JPanel {

    private PlaceholderTextField textfield;
    private JLabel label;

    public TableHeaderRendererPanel() {
        initComponent();
    }

    private void initComponent() {
        textfield = new PlaceholderTextField();
        label = new JLabel();
        setLayout(new BorderLayout());
        int width = 45, height = 20;
        label.setSize(width, height);
        add(label, BorderLayout.NORTH);
        textfield.setPlaceholder("Filter");
        textfield.setSize(width, height);
        add(textfield, BorderLayout.SOUTH);
        textfield.setEditable(true);
 
//        label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
//        setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
       
    }

    public JTextField getTextField() {
        return textfield;
    }

    public JLabel getLabel() {
        return label;
    }
}
