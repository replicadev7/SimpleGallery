package com.rahul.gallerymvvm.util;

import java.util.Locale;

public class TheUtility {
    public static String parseTimeFromMilliseconds(String str) {
        if (str == null) {
            return "";
        }
        long floor = (long) Math.floor((double) (Long.parseLong(str.trim()) / 1000));
        if (floor <= 59) {
            return "00:" + prependZero((int) floor) + "";
        }
        long floor2 = (long) Math.floor((double) (floor / 60));
        if (floor2 <= 59) {
            return prependZero((int) floor2) + ":" + prependZero((int) (floor % 60));
        }
        return prependZero((int) ((long) Math.floor((double) (floor2 / 60)))) + ":" + prependZero((int) (floor2 % 60)) + ":" + prependZero((int) (floor % 60));
    }

    public static String humanReadableByteCount(long j, boolean z) {
        int i = z ? 1000 : 1024;
        if (j < ((long) i)) {
            return j + " B";
        }
        StringBuilder sb = new StringBuilder();
        double d = (double) j;
        double d2 = (double) i;
        sb.append((z ? "kMGTPE" : "KMGTPE").charAt(((int) (Math.log(d) / Math.log(d2))) - 1));
        sb.append("");
        String sb2 = sb.toString();
        Locale locale = Locale.US;
        double pow = Math.pow(d2, (double) ((int) (Math.log(d) / Math.log(d2))));
        Double.isNaN(d);
        Double.isNaN(d);
        return String.format(locale, "%.1f %sB", Double.valueOf(d / pow), sb2);
    }



    private static String prependZero(int i) {
        StringBuilder sb;
        if (i < 10) {
            sb = new StringBuilder();
            sb.append("0");
            sb.append(i);
        } else {
            sb = new StringBuilder();
            sb.append(i);
            sb.append("");
        }
        return sb.toString();
    }
}
