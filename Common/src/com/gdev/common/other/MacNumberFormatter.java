
package com.gdev.common.other;

import java.math.BigDecimal;
import java.util.HashMap;

public class MacNumberFormatter {
    private static MacNumberFormatter instance;
    private static int printFigureIn;
    private static int displayFigureInVoucher;
    private static int displayFigureInReport;
    private static int noOfDecPlaces;

    public static MacNumberFormatter getDefault() {
        if (instance == null) {
            instance = new MacNumberFormatter();
        }
        return instance;
    }

    public static int getPrintFigureIn() {
        return printFigureIn;
    }

    public static void setPrintFigureIn(int aPrintFigureIn) {
        printFigureIn = aPrintFigureIn;
    }

    public static int getDisplayFigureInVoucher() {
        return displayFigureInVoucher;
    }

    public static void setDisplayFigureInVoucher(int aDisplayFigureInVoucher) {
        displayFigureInVoucher = aDisplayFigureInVoucher;
    }

    public static int getDisplayFigureInReport() {
        return displayFigureInReport;
    }

    public static void setDisplayFigureInReport(int aDisplayFigureInReport) {
        displayFigureInReport = aDisplayFigureInReport;
    }

    public static void setNoOfDecPlaces(int aNoOfDecPlaces) {
        noOfDecPlaces = aNoOfDecPlaces;
    }

    public String getDrCrAmount(BigDecimal b, String cur, int type) {
        if (b != null) {
            if (b.compareTo(BigDecimal.ZERO) == -1) {
                return "Cr " + this.setAmountToString(this.getDisplayAmount(b.abs(), type), cur);
            }
            return "Dr " + this.setAmountToString(this.getDisplayAmount(b.abs(), type), cur);
        }
        return "Dr 0.00";
    }

    public BigDecimal getAmountFromString(String amount) {
        try {
            amount = amount.replace(",", "").trim();
            return new BigDecimal(amount);
        }
        catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getDisplayAmount(BigDecimal amount, int type) {
        switch (type) {
            case 4: {
                return amount;
            }
            case 6: {
                return amount.divide(new BigDecimal(10));
            }
            case 5: {
                return amount.divide(new BigDecimal(100));
            }
            case 3: {
                return amount.divide(new BigDecimal(1000));
            }
            case 2: {
                return amount.divide(new BigDecimal(100000));
            }
            case 1: {
                return amount.divide(new BigDecimal(10000000));
            }
        }
        return amount;
    }

    public BigDecimal setAmount(BigDecimal amount) {
        return amount != null ? amount.setScale(noOfDecPlaces, 4) : BigDecimal.ZERO.setScale(noOfDecPlaces);
    }

    public BigDecimal setAmountForInputVoucher(BigDecimal amount) {
        return this.getDisplayAmount(this.setAmount(amount), displayFigureInVoucher);
    }

    public BigDecimal setAmountForReportClass(BigDecimal amount) {
        return this.getDisplayAmount(this.setAmount(amount), displayFigureInReport);
    }

    public BigDecimal setAmountForPrintReport(BigDecimal amount) {
        return this.getDisplayAmount(this.setAmount(amount), printFigureIn);
    }

    public String setCurrSymbol(String curr) {
        return curr == null ? "INR" : curr;
    }

    public String format(BigDecimal amount, Object contents) {
        if (contents != null && contents instanceof String) {
            return this.format(amount, contents.toString());
        }
        if (contents != null && contents instanceof HashMap) {
            return this.format(amount, (HashMap)contents);
        }
        return "0.00";
    }

    public String format(BigDecimal amount, String currSymbol, int type) {
        if (amount != null) {
            return (amount.compareTo(BigDecimal.ZERO) == -1 ? "-" : "") + this.setAmountToString(this.getDisplayAmount(amount, type), currSymbol);
        }
        return "0.00";
    }

    public String format(BigDecimal amount, String currSymbol) {
        if (amount != null) {
            return (amount.compareTo(BigDecimal.ZERO) == -1 ? "-" : "") + this.setAmountToString(this.getDisplayAmount(amount, MacNumberFormatter.getPrintFigureIn()), currSymbol);
        }
        return "0.00";
    }

    public String formatForVoucher(BigDecimal amount, String currSymbol) {
        if (amount != null) {
            return (amount.compareTo(BigDecimal.ZERO) == -1 ? "-" : "") + this.setAmountToString(this.getDisplayAmount(amount, MacNumberFormatter.getDisplayFigureInVoucher()), currSymbol);
        }
        return "0.00";
    }

    public String formatCrossTab(BigDecimal amount, String currSymbol) {
        if (amount != null && !this.equalsToZero(amount)) {
            return (amount.compareTo(BigDecimal.ZERO) == -1 ? "-" : "") + this.setAmountToString(this.getDisplayAmount(amount, MacNumberFormatter.getPrintFigureIn()), currSymbol);
        }
        return "";
    }

    public boolean equalsToZero(BigDecimal amount) {
        BigDecimal amt = this.setAmount(amount);
        return amt.compareTo(BigDecimal.ZERO) == 0;
    }

    public String format(BigDecimal amount, HashMap map) {
        if (amount != null) {
            String currSymbol = this.setCurrSymbol((String)map.get("currSymbol"));
            Integer type = (Integer)map.get("type");
            return this.setAmountToString(this.getDisplayAmount(amount, type), currSymbol);
        }
        return "0.00";
    }

    public String setAmountToString(BigDecimal amount, String currSymbol) {
        amount = this.setAmount(amount).abs();
        currSymbol = this.setCurrSymbol(currSymbol);
        String val = amount.toString();
        String dec = val.substring(val.indexOf("."), val.length());
        if (amount.doubleValue() > 0.0) {
            if (val.indexOf(".") != -1) {
                val = val.substring(0, val.indexOf("."));
            }
            if (val.length() > 3) {
                int k = 3;
                StringBuffer str = new StringBuffer(val).reverse();
                while (k < str.length()) {
                    str = str.insert(k, ",");
                    if (currSymbol.equals("INR")) {
                        k += 3;
                        continue;
                    }
                    k += 4;
                }
                val = str.reverse().toString() + dec;
            } else {
                val = val + dec;
            }
        }
        return val;
    }

    static {
        printFigureIn = 4;
        displayFigureInVoucher = 4;
        displayFigureInReport = 4;
        noOfDecPlaces = 2;
    }
}

