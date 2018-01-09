package com.gdev.common.other;

//import com.as.common.AdjustmentDetails;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JTree;

//import com.as.common.ItemMasterTypeStyle;
//import com.as.common.VouDetail;
//import com.as.common.messages.*;
//import com.as.common.utils.Constants;
//import com.as.interfaces.UiFiles.WordCases;
//import com.as.interfaces.cellRenderer.ADCListeners;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.JTextComponent;
import com.gdev.common.Constants;
import com.gdev.common.Controller;
import org.mac.common.messages.LedgerDetail;
import com.gdev.common.util.CompletionUI;

public class Utility {

    private static Utility instance;
    public static boolean displaySerialNumberAsView;
    private static BigDecimal balQty;

    static SimpleDateFormat getSdf() {
        return new SimpleDateFormat("dd-MM-YYYY");
    }

    public static String getFormatedDate(Date dt) {
        return getSdf().format(dt);
    }

    public static Date getFormatDate(Date dt) throws Exception {
        return getSdf().parse(getFormatedDate(dt));
    }
    private Hashtable<Long, BigDecimal> interest;

    /**
     * @return the balQty
     */
    public static BigDecimal getBalQty() {
        return balQty;
    }

    /**
     * @param aBalQty the balQty to set
     */
    public static void setBalQty(BigDecimal aBalQty) {
        balQty = aBalQty;
    }

    /**
     * Creates a new instance of Utility
     */
    public Utility() {
    }

    public static Utility getDefault() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }

    public static String getTimeInString(java.util.Date d) {

        String hours = String.valueOf(d.getHours());
        String mins = String.valueOf(d.getMinutes());
        String secs = String.valueOf(d.getSeconds());

        return (hours + ":" + mins + ":" + secs);
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

    public static Point getRightCenterLocation(Dimension frameSize) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        int x = (screenSize.width - frameSize.width);
        int y = (screenSize.height - frameSize.height) / 2;
        Point location = new Point(x, y);
        return location;
    }

    public static Point getLeftCenterLocation(Dimension frameSize) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        int x = (0);
        int y = (screenSize.height - frameSize.height) / 2;
        Point location = new Point(x, y);
        return location;
    }

    public static int[] getCenterLocationXY(Dimension frameSize) {
        int i[] = new int[2];
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        i[0] = x;
        i[1] = y;
        return i;
    }

    public static int getAccountGroupTypeId(String name) {
        if (name.trim().equals("Sundry Debtor")) {
            return Constants.SUNDRY_DEBTOR;
        } else if (name.trim().equals("Sundry Creditor")) {
            return Constants.SUNDRY_CREDITOR;
        } else if (name.trim().equals("Cash")) {
            return Constants.CASH;
        } else if (name.trim().equals("Bank")) {
            return Constants.BANK;
        } else if (name.trim().equals("Income")) {
            return Constants.INCOME;
        } else if (name.trim().equals("Other Expensive")) {
            return Constants.OTHER_EXPENSIVE;
        }
        return 0;
    }

    public static String getAccountGroupTypeName(int id) {
        if (Constants.SUNDRY_DEBTOR == id) {
            return "Sundry Debtor";
        } else if (Constants.SUNDRY_CREDITOR == id) {
            return "Sundry Creditor";
        } else if (Constants.CASH == id) {
            return "Cash";
        } else if (Constants.BANK == id) {
            return "Bank";
        } else if (Constants.INCOME == id) {
            return "Income";
        } else if (Constants.OTHER_EXPENSIVE == id) {
            return "Other Expensive";
        }
        return "";

    }

//    public static String getImageWithPath(String image) {
//        if (image != null && !image.isEmpty()) {
//            if (image.contains(com.as.common.utils.Utility.getmSettings().getImgDir())
//                    || image.startsWith("http")) {
//                image = image;
//            } else {
//                image = com.as.common.utils.Utility.getmSettings().getImgDir() + "\\" + image;
//            }
//        }
//        return image;
//    }
    public static void setDialogWidthMaximize(Dialog d) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = d.getHeight();
        d.setSize(screenSize.width, height);
    }

    public static void expandTree(JTree tree) {
        if (tree != null) {
            int row = 0;
            while (row < tree.getRowCount()) {
                tree.expandRow(row);
                row++;
            }
        }
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

    public static void collapseTree(JTree tree) {
        if (tree != null) {
            int row = tree.getRowCount() - 1;
            while (row >= 1) {
                tree.collapseRow(row);
                row--;
            }
        }
    }

    /**
     * Method check value of field if it is negtive then method set postive
     * value to field else if value is not a vaild number then it put zero in
     * field
     */
//    public static void checkFieldValue(JTextField jt, int noOfDecPlaces) {
//        Functions decplaces = ClientController.getDefault().getFuncs();
//        try {
//            if (jt.getText().trim().length() != 0) {
//                BigDecimal amt = new BigDecimal(jt.getText().trim());
//                if (amt.signum() == -1) {
//                    amt = amt.negate();
//                }
//                jt.setText(decplaces.setNoofDecPlaces(noOfDecPlaces, amt).toString());
//            }
//        } catch (Exception e) {
//            jt.setText(decplaces.setNoofDecPlaces(noOfDecPlaces, "0.0").toString());
//        }
//    }
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

    public static Color getTextColor(String str) {
        try {
            int green = Integer.parseInt(str.substring(2, 4), 16);
            if (green < 103) {
                return Color.white;
            } else {
                return Color.black;
            }
        } catch (Exception e) {
            return Color.black;
        }
    }

    public Color getRedColor() {
        try {
            return getColor("FF9966");
//            return getColor("FFE8E4");
        } catch (Exception e) {
            return Color.red;
        }
    }
    
    public Color getRowBgColor(){
          try {
            return  getColor("ddebf7");
        } catch (Exception e) {
            return Color.red;
        }
    }

      public Color getTableFooterColor(){
          try {
            return getColor("89CFF0");
        } catch (Exception e) {
            return Color.red;
        }
    }
      
    public Color getPinkColor() {
        try {
            return getColor("FAAFBE");
//            return getColor("FFE8E4");
        } catch (Exception e) {
            return Color.PINK;
        }
    }

    public Color getBlueColor() {
        try {
            return getColor("CCCCFF");
        } catch (Exception e) {
            return Color.BLUE;
        }
    }

    public Color getYellowColor() {
        return Color.YELLOW;
    }

    public Color getGreenColor() {
        try {
            return getColor("66DD88");
//            return getColor("E9FCE6");
        } catch (Exception e) {
            return Color.green;
        }
    }

    public static String getColorCode(int r, int g, int b) {
        return toHex(r) + "" + (toHex(g)) + "" + toHex(b);
    }

    private static String toHex(int n) {
        return "" + ("0123456789ABCDEF".charAt((n - n % 16) / 16)) + ("0123456789ABCDEF".charAt(n % 16));
    }

    public String toHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
            hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour;
    }

    public static void clear(Container con) {
        Component[] comps = con.getComponents();
        for (Component c : comps) {
            if (c instanceof CompletionUI) {
                ((CompletionUI) c).setValue("");
            } else if (c instanceof JTextComponent) {
                ((JTextComponent) c).setText("");
            } else if (c instanceof DateControl) {
                ((DateControl) c).setDate(new Date());
            } else if (c instanceof JPanel) {
                clear((JPanel) c);
            }
        }
    }

    public static String getItemType(String str) {
        if (str.equals("Manufacturing")) {
            return "M";
        } else if (str.equals("Genuine")) {
            return "G";
        } else if (str.equals("Non Genuine")) {
            return "N";
        } else {
            return "T";
        }
    }

    public static String setItemType(String type) {
        if (type.trim().equals("M")) {
            return "Manufacturing";
        } else if (type.trim().equals("G")) {
            return "Genuine";
        } else if (type.trim().equals("N")) {
            return "Non Genuine";
        } else {
//            default is for trading
            return "Trading";
        }
    }

//    public static String getSmTableToolTipText(CreateType flag, String flagmsg) {
//        return "<html> <table border=" + "1><tr>"
//                + (flag == null ? "" : ("<td>Description</td>" + "<td>:</td>" + "<td>"
//                + (flag.getRemarks() != null ? flag.getRemarks() : "") + "</td>")) + "</tr>"
//                + "<tr>" + "<td>Remarks</td>" + "<td>:</td>" + "<td>" + (flagmsg != null ? flagmsg : "") + "</td>" + "</tr>"
//                + "</table></html>";
//    }
    public static void fieldFocusGained(JTextField txt, boolean mand) {
        if (mand) {
            txt.setBackground(getColor("FED2FE"));
        } else {
            txt.setBackground(getColor("CCDEF5"));
        }
        txt.setForeground(Color.BLACK);
        txt.selectAll();
    }

    public static void fieldFocusLost(JTextField txt, boolean mand) {
        if (!mand || (txt.getText() != null && txt.getText().trim().length() > 0)) {
            txt.setBackground(mand ? getColor("F8CFCA") : Color.white);
        } else {
//            com.as.common.utils.Utility.makeErrorSound();
            txt.setBackground(Color.red);
//                txt.requestFocus();
        }

    }

    public static void fieldFocusGained(JTextArea txt, boolean mand) {
        if (mand) {
            txt.setBackground(getColor("FED2FE"));
        } else {
            txt.setBackground(getColor("CCDEF5"));
        }
        txt.setForeground(Color.BLACK);
        txt.selectAll();
    }

    public static void fieldFocusLost(JTextArea txt, boolean mand) {
        if (!mand || (txt.getText() != null && txt.getText().trim().length() > 0)) {
            txt.setBackground(mand ? getColor("F8CFCA") : Color.white);
        } else {
//            com.as.common.utils.Utility.makeErrorSound();
            txt.setBackground(Color.red);
//                txt.requestFocus();
        }
    }

    public static void checkWordCases(JTextComponent field, boolean check) {
        if (check) {
//            String newString = WordCases.getString(field.getText());
//            field.setText(newString);
        }
    }

    public static Vector convertToVector(List sourceList) {
        Vector dest = new Vector();
        for (Object object : sourceList) {
            dest.add(object);
        }
        return dest;
    }

//    public static ArrayList convertToArrayList(Vector sourceList, boolean t) {
//
//        ArrayList dest = new ArrayList();
//        for (int i = 0; i < sourceList.size(); i++) {
//            CreateLedger row = (CreateLedger) sourceList.elementAt(i);
//            if (row.getName() != null) {
//                dest.add(row.getName());
//            }
//        }
//        if (t == true) {
//            dest.add("All");
//        }
//        return dest;
//    }
//    public static LinkedList convertToList(LinkedList sourceList, boolean t) {
//        LinkedList dest = new LinkedList();
//        for (Iterator<CreateLedger> list = sourceList.iterator(); list.hasNext();) {
//            CreateLedger row = list.next();
//            if (row.getName() != null) {
//                dest.add(row.getName());
//            }
//        }
//        if (t == true) {
//            dest.add("All");
//        }
//        return dest;
//    }
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

    public void setAncestor(JPanel p, final JDialog d) {
        AncestorListener lis = new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                d.dispose();
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        };
        for (Container comp = p.getParent(); comp != null; comp = comp.getParent()) {
//            if (comp instanceof Asui_Report_Panel) {
//                final Asui_Report_Panel par = (Asui_Report_Panel) comp;
//                par.addAncestorListener(lis);
//                break;
//            } else
            if (comp.getParent() != null && comp.getParent() instanceof JDialog) {
                final JComponent par = (JComponent) comp;
                par.addAncestorListener(lis);
                break;
//            } else if (comp.getParent() != null && comp.getParent() instanceof TopComponent) {
//                final JComponent par = (JComponent) comp;
//                par.addAncestorListener(lis);
//                break;
            }
        }
    }

    public static void freeMemory() {
        Runtime rt = Runtime.getRuntime();
        System.out.println("Available Free Memory: " + rt.freeMemory());
        System.gc();//call garbage collector
        System.runFinalization();//run Finalization
        System.out.println("Free Memory after call to gc(): " + rt.freeMemory());
    }

    public static FocusListener mandatoryFocusListener(final boolean mand) {

        return new com.gdev.common.interfaces.ADCFocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (e.getSource() instanceof JTextField) {
                    Utility.fieldFocusGained((JTextField) e.getSource(), mand);
                } else if (e.getSource() instanceof JTextArea) {
                    Utility.fieldFocusGained((JTextArea) e.getSource(), mand);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (e.getSource() instanceof JTextField) {
                    Utility.fieldFocusLost((JTextField) e.getSource(), mand);
                } else if (e.getSource() instanceof JTextArea) {
                    Utility.fieldFocusLost((JTextArea) e.getSource(), mand);
                }
            }
        };
    }
    private EmailValidator emailValidator;

    public boolean emailValidate(JTextComponent emailField) {
        if (emailValidator == null) {
            emailValidator = new EmailValidator();
        }
        String text = emailField.getText().trim();
        boolean validate = false;
        if (text.contains(";")) {
            String[] array = text.split(";");
            for (String a : array) {
                validate = emailValidator.validate(a);
                if (!validate) {
                    return false;
                }
            }
            return validate;
        } else {
            validate = emailValidator.validate(text);
            return validate;
        }
    }

    class EmailValidator {

        private Pattern pattern;
        private Matcher matcher;
        private static final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

        public EmailValidator() {
            pattern = Pattern.compile(EMAIL_PATTERN);
        }

        /**
         * Validate hex with regular expression
         *
         * @param hex for validation
         * @return true valid hex, false invalid hex
         */
        public boolean validate(final String hex) {
            matcher = pattern.matcher(hex);
            return matcher.matches();
        }
    }

    public ArrayList getArrayList(Vector v) {
        return new ArrayList(v);
    }

    public Hashtable<Long, BigDecimal> getIntersInfoById() {
        if (this.interest == null) {
            this.interest = new Hashtable<Long, BigDecimal>();
        }
        return this.interest;
    }

    public String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY-h-m-s");
        return sdf.format(new Date());
    }

    public Vector getDrLedgers() {
        Vector data = new Vector();
        Vector<LedgerDetail> v = Controller.getDefault().getLedgerInfo();
        for (Iterator<LedgerDetail> it = v.iterator(); it.hasNext();) {
            LedgerDetail detail = it.next();
            if (detail.getGroup_id() == Constants.CASH
                    || detail.getGroup_id() == Constants.BANK) {
                data.add(detail);
            }
        }
        return data;
    }

    public Vector getCrLedgers() {
        Vector data = new Vector();
        Vector<LedgerDetail> v = Controller.getDefault().getLedgerInfo();
        for (Iterator<LedgerDetail> it = v.iterator(); it.hasNext();) {
            LedgerDetail detail = it.next();
            if (detail.getGroup_id() != Constants.CASH && detail.getGroup_id() != Constants.BANK) {
                data.add(detail);
            }
        }
        return data;
    }
}