package com.util;

/**
 * Created by duke on 16-8-2.
 */
public class IUtils {
    // 0x0 ~ 0xffffffff
    public static int hexIntStringToInt(String hexString, int defaultValue) {
        if (null == hexString || 0 == hexString.length()) {
            ILog.d("IUtils.hexIntStringToInt hexString is null or empty.");
            return defaultValue;
        }

        String hexInt = hexString;
        if (hexString.length() > 2) {
            String head = hexString.substring(0, 2).toLowerCase();
            if (head.equals("0x")) {
                hexInt = hexString.substring(2);
            }
        }

        int hexLength = hexInt.length();
        if (hexLength > 8) {
            ILog.d("LtUtils.hexIntStringToInt color error.");
            throw new IllegalArgumentException("The int value is too large, should between 0x0 and 0xffffffff.");
        }

        /** The follow code is another method, work ok.
        int value = 0;
        char[] hexChar = hexInt.toCharArray();
        for (int i = 0; i < hexLength; ++i) {
            value = value + Integer.parseInt(String.valueOf(hexChar[i]), 16) * (int)Math.pow(16, hexLength - i -1);
        }
        return value;
        **/
        long value = Long.parseLong(hexInt, 16);
        return (int) value;
    }
}