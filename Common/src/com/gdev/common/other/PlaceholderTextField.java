/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.other;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 *
 * @author SourceThink Developers
 */
@SuppressWarnings("serial")
public class PlaceholderTextField extends JTextField {

    private String placeholder;

    public PlaceholderTextField() {
    }

    public PlaceholderTextField(final Document doc, final String text, final int columns) {
        super(doc, text, columns);
    }

    public PlaceholderTextField(final String text, final int columns) {
        super(text, columns);
    }

    public PlaceholderTextField(final String text) {
        super(text);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pg) {
        super.paintComponent(pg);
        if (placeholder == null || (placeholder.length() == 0 || getText().length() > 0)) {
            return;
        }
        final Graphics2D g = (Graphics2D) pg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        Font f = g.getFont();
        if (f != null) {
            Font nF = new Font(f.getName(), Font.ITALIC, f.getSize());
            g.setFont(nF);
        }
        g.drawString(placeholder, getInsets().left, pg.getFontMetrics().getMaxAscent() + getInsets().top);
    }

    public void setPlaceholder(final String text) {
        placeholder = text;
    }
}
