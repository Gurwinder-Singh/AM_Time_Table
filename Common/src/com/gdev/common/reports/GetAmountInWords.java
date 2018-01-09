package com.gdev.common.reports;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class GetAmountInWords {

    private String[] tokens = null;
    private String splitcase = ",";
    private static final String[] tensNames = {"", " ten", " twenty",
        " thirty", " forty", " fifty", " sixty", " seventy", " eighty",
        " ninety"};
    private static final String[] numNames = {"", " one", " two", " three",
        " four", " five", " six", " seven", " eight", " nine", " ten",
        " eleven", " twelve", " thirteen", " fourteen", " fifteen",
        " sixteen", " seventeen", " eighteen", " nineteen"};

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }

        if (number == 0) {
            return soFar;
        }
        return numNames[number] + " hundred" + soFar;

    }

    private static String convert(long number) {

        // 0 to 999 99 99 999
        if (number == 0) {
            return "zero";

        }

        String snumber = Long.toString(number);
        // pad with "0"
        String mask = "0000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnn
        int crores = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXnnnnn
        int lakhs = Integer.parseInt(snumber.substring(3, 5));
        // nnnnnXXnnn
        int thousands = Integer.parseInt(snumber.substring(5, 7));
        // nnnnnnnXXX
        int hundred = Integer.parseInt(snumber.substring(7, 10));

        String tradCrore;

        switch (crores) {
            case 0:
                tradCrore = "";
                break;
            case 1:
                tradCrore = convertLessThanOneThousand(crores) + " crore ";
                break;
            default:
                tradCrore = convertLessThanOneThousand(crores) + " crore ";
        }
        String result = tradCrore;

        String tradLakhs;

        switch (lakhs) {
            case 0:
                tradLakhs = "";
                break;
            case 1:
                tradLakhs = convertLessThanOneThousand(lakhs) + " lakh ";
                break;
            default:
                tradLakhs = convertLessThanOneThousand(lakhs) + " lakh ";
        }
        result = result + tradLakhs;

        String tradHundredThousands;
        switch (thousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(thousands)
                        + " thousand ";
        }
        result = result + tradHundredThousands;

        String tradHundred;
        tradHundred = convertLessThanOneThousand(hundred);
        result = result + tradHundred;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    public String getWords(Double amt, boolean chq) {
        if (amt > 0.000) {
            BigDecimal B_amt = new BigDecimal(amt.toString()).abs();
            String word = getWords(B_amt, chq);
            return word;
        } else {
            return "Zero";
        }
    }

    public String getWords(BigDecimal big, boolean chq) {
//		Functions obj=ClientController.getDefault().getFuncs();
//		big = obj.setNoofDecPlaces(2, big).abs();
        big = big.setScale(2, BigDecimal.ROUND_HALF_UP);
        String amount = big.toString();
        amount = amount.replace(".", ",");
        tokens = amount.split(splitcase);

        //System.out.print(convert(Long.parseLong(tokens[0])));
        amount = (chq ? "" : getCurrencyName()) + convert(Long.parseLong(tokens[0]));

        if (tokens.length == 2) {
            if (Long.parseLong(tokens[1]) > 0) {
                //System.out.print(" "+ convert(Long.parseLong(tokens[1])) + " paisa");
                amount += " and " + getCurrencyNameForDecimals() + convert(Long.parseLong(tokens[1]));
            }

        }
//                        if(ClientController.getDefault().getCompInfo().getCountry_id()!=38){
        amount += " only";
//                        }


        return amount;
    }

    public String getWordOfAmount(BigDecimal big, boolean chq) {
        return getWords(big, chq);
    }

    public String getWordOfAmount(Double db, boolean chq) {
        return getWords(db, chq);
    }

    public String getCurrencyName() {
//             if(ClientController.getDefault().getCompInfo().getCurrSymbol().equals("INR")){
        return "Rupees ";
//           }else  if(ClientController.getDefault().getCompInfo().getCurrSymbol().equals("$")){
//             return "Dollars ";
//           }else  if(ClientController.getDefault().getCompInfo().getCurrSymbol().equals("LKR")){
//             return "Rupee ";
//            }else  if(ClientController.getDefault().getCompInfo().getCurrSymbol().equals("\u20ac")){
//             return "Pounds ";
//            }else{
//               return "AUD ";
//           }
    }

    public String getCurrencyNameForDecimals() {
//             if(ClientController.getDefault().getCompInfo().getCurrSymbol().equals("INR")){
        return " paise ";
//           }else  if(ClientController.getDefault().getCompInfo().getCurrSymbol().equals("$")){
//             return " cents ";
//           }else  if(ClientController.getDefault().getCompInfo().getCurrSymbol().equals("LKR")){
//             return " paise ";
//            }else  if(ClientController.getDefault().getCompInfo().getCurrSymbol().equals("\u20ac")){
//             return " cents ";
//            }else{
//               return " cents ";
//           }
    }
}
