package com.yw.course.coding.class42;

/**
 * @author yangwei
 */
public class Problem_0273_IntegerToEnglishWords {

    private static final String[] tyNames = {"Twenty ", "Thirty ", "Forty ", "Fifty ", "Sixty ", "Seventy ", "Eighty ", "Ninety "};
    private static final String[] one2teenNames = {"One ", "Two ", "Three ", "Four ", "Five ", "Six ", "Seven ", "Eight ", "Nine ", "Ten ",
            "Eleven ", "Twelve ", "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ", "Seventeen", "Eighteen ", "Nineteen "};
    public static String numberToWords(int num) {
        if (num == 0) return "Zero";
        StringBuilder res = new StringBuilder();
        if (num < 0) res.append("Negative ");
        if (num == Integer.MIN_VALUE) {
            res.append("Two Billion ");
            num %= -2000000000;
        }
        num = Math.abs(num);
        int high = 1000000000, highIdx = 0;
        String[] names = {"Billion ", "Million ", "Thousand ", ""};
        while (num != 0) {
            int cur = num / high;
            num %= high;
            if (cur != 0) res.append(numTo999(cur)).append(names[highIdx]);
            high /= 1000;
            highIdx++;
        }
        return res.toString().trim();
    }
    private static String numTo999(int num) {
        if (num < 1 || num > 999) return "";
        if (num < 100) return numTo99(num);
        return numTo19(num / 100) + "Hundred " + numTo99(num % 100);
    }
    private static String numTo99(int num) {
        if (num < 1 || num > 99) return "";
        if (num < 20) return numTo19(num);
        return tyNames[num / 10 - 2] + numTo19(num % 10);
    }
    private static String numTo19(int num) {
        if (num < 1 || num > 19) return "";
        return one2teenNames[num - 1];
    }

    public static void main(String[] args) {
        int test = Integer.MIN_VALUE;
        System.out.println(test);

        test = -test;
        System.out.println(test);

        int num = -10001;
        System.out.println(numberToWords(num));
    }

}
