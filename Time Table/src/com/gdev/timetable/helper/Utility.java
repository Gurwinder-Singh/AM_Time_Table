package com.gdev.timetable.helper;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.openide.windows.WindowManager;

public class Utility {

    private static Utility instance;



    /**
     * Creates a new instance of Utility
     */
    public Utility() {
    }

    public Frame getParent() {
        return WindowManager.getDefault().getMainWindow();
    }

    public static Utility getDefault() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }

    public static BigDecimal getBigDecimalValue(String val) {
        try {
            return val == null ? BigDecimal.ZERO : new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static String getValue(BigDecimal val) {
        try {
            return val == null ? "0.0" : val.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            return "0.0";
        }
    }

    public static BigDecimal getBigDecimalValue(BigDecimal val) {
        try {
            return val == null ? BigDecimal.ZERO : val.setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static long getLongValue(String val) {
        try {
            return val == null ? 0l : Long.valueOf(val);
        } catch (Exception e) {
            return 0l;
        }
    }

    public static Point getCenterLocation(Dimension frameSize) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        Point location = new Point(x, y);
        return location;
    }

   
    public static void addDefaultKeyListener(final JButton save, final JButton close) {
        KeyStroke k1 = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
        ActionListener saveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ActionListener list : save.getActionListeners()) {
                    list.actionPerformed(e);
                }
            }
        };
        save.registerKeyboardAction(saveListener, k1, JComponent.WHEN_IN_FOCUSED_WINDOW);
        if (close != null) {
            KeyStroke k2 = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK);
            ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (ActionListener list : close.getActionListeners()) {
                        list.actionPerformed(e);
                    }
                }
            };

            save.registerKeyboardAction(closeListener, k2, JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
    }

    public static Border getBorder(String title, JPanel panel) {
        return javax.swing.BorderFactory.createTitledBorder(null, title,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                ((TitledBorder) panel.getBorder()).getTitleFont(),
                ((TitledBorder) panel.getBorder()).getTitleColor());
    }

    public static java.sql.Date toSqlDate(java.util.Date d) {
        long time = d.getTime();
        return (new java.sql.Date(time));
    }

    public static java.sql.Date toSqlDate(java.sql.Date d) {
        return d;
    }

    public static Color getColor(String str) {
        try {
            int red = Integer.parseInt(str.substring(0, 2), 16);
            int green = Integer.parseInt(str.substring(2, 4), 16);
            int blue = Integer.parseInt(str.substring(4, 6), 16);
            return new Color(red, green, blue);
        } catch (Exception e) {
            return Color.white;
        }
    }


    public static java.util.Date getDateAfter(java.util.Date date, int val, String type) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        if (type.equals("D")) {
            gc.add(Calendar.DAY_OF_YEAR, val);
        } else if (type.equals("M")) {
            gc.add(Calendar.MONTH, val);
        } else if (type.equals("W")) {
            gc.add(Calendar.WEEK_OF_YEAR, val);
        } else if (type.equals("Y")) {
            gc.add(Calendar.YEAR, val);
        }
        return gc.getTime();
    }
    
    public static void setPopupMenu(String data, JPopupMenu popupMenu, ActionListener aThis) {
        if (data != null) {
            String[] arr = data.split(";");
            for (int i = 0; i < arr.length; i++) {
                String s = arr[i].trim();
                String parent = "";
                if (s.contains("[") && s.contains("]")) {
                    parent = s.substring(0, s.indexOf("["));
                    String child = s.substring(s.indexOf("[") + 1, s.indexOf("]"));
                    String[] childs = child.split(",");
                    JMenu par = new JMenu(parent.trim());
                    for (int j = 0; j < childs.length; j++) {
                        JMenuItem ch = new JMenuItem(childs[j].trim());
                        ch.addActionListener(aThis);
                        par.add(ch);
                    }
                    popupMenu.add(par);
                } else {
                    parent = s;
                    JMenuItem par = new JMenuItem(parent.trim());
                    par.addActionListener(aThis);
                    popupMenu.add(par);
                }
            }
        }
    }
}
