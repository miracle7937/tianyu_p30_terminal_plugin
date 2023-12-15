package com.enkwave_p30_terminal.p30_terminal_plugin.utils;

import android.util.Log;

import com.enkwave_p30_terminal.p30_terminal_plugin.networking.miscellaneous.Utilities;
import com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation.PrepImplementation;
import com.whty.smartpos.tysmartposapi.ITYSmartPosApi;
import com.whty.smartpos.tysmartposapi.utils.GPMethods;

public class StringUtil {
    private static final String TAG = PrepImplementation.class.getSimpleName();
    public static boolean isNotEmpty(String s) {
        return s != null && !s.isEmpty() && !s.equals("null");
    }

    public static String convertAmountToField4(String amountString) {
        return Utilities.padLeftZeros(String.valueOf((Integer.parseInt(amountString) * 100)), 12);
    }



    public static  String  getIcDATA3(  ITYSmartPosApi smartPosApi){

        StringBuilder iccDataConcatenatedBuilder = new StringBuilder();

        byte[] value9F26 = smartPosApi.getTlv(0x009F26);
        Log.d(TAG,"Tag value 9F26: " + GPMethods.bytesToHexString(value9F26) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F26",  GPMethods.bytesToHexString(value9F26) ));




        byte[] value9F27 = smartPosApi.getTlv(0x009F27);
        Log.d(TAG,"Tag value 9F27: " + GPMethods.bytesToHexString(value9F27) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F26",  GPMethods.bytesToHexString(value9F27) ));




        byte[] value9F33 = smartPosApi.getTlv(0x009F33);
        Log.d(TAG,"Tag value 9F33: " + GPMethods.bytesToHexString(value9F33) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F33",  GPMethods.bytesToHexString(value9F33) ));




        byte[] value5F34 = smartPosApi.getTlv(0x005F34);
        Log.d(TAG,"Tag value 5F34: " + GPMethods.bytesToHexString(value9F33) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("5F34",  GPMethods.bytesToHexString(value5F34) ));





        byte[] value9F35 = smartPosApi.getTlv(0x009F35);
        Log.d(TAG,"Tag value 9F35: " + GPMethods.bytesToHexString(value9F33) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F35",  GPMethods.bytesToHexString(value9F35) ));





        byte[] value9F34 = smartPosApi.getTlv(0x009F34);
        Log.d(TAG,"Tag value 9F34: " + GPMethods.bytesToHexString(value9F34) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F34",  GPMethods.bytesToHexString(value9F35) ));






        byte[] value9F10 = smartPosApi.getTlv(0x009F10);
        Log.d(TAG,"Tag value 9F10: " + GPMethods.bytesToHexString(value9F10) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F10",  GPMethods.bytesToHexString(value9F10) ));


        byte[] value9F37 = smartPosApi.getTlv(0x009F37);
        Log.d(TAG,"Tag value 9F37: " + GPMethods.bytesToHexString(value9F37) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F37",  GPMethods.bytesToHexString(value9F37) ));




        byte[] value9F36 = smartPosApi.getTlv(0x009F36);
        Log.d(TAG,"Tag value 9F36: " + GPMethods.bytesToHexString(value9F36) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F36",  GPMethods.bytesToHexString(value9F36) ));





        byte[] value95 = smartPosApi.getTlv(0x0095);
        Log.d(TAG,"Tag value 95: " + GPMethods.bytesToHexString(value95) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("95",  GPMethods.bytesToHexString(value95) ));






        byte[] value9A = smartPosApi.getTlv(0x009A);
        Log.d(TAG,"Tag value 9A: " + GPMethods.bytesToHexString(value9A) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9A",  GPMethods.bytesToHexString(value9A) ));






        byte[] value9C = smartPosApi.getTlv(0x009C);
        Log.d(TAG,"Tag value 9C: " + GPMethods.bytesToHexString(value9C) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9C",  GPMethods.bytesToHexString(value9C) ));




        byte[] value9F02 = smartPosApi.getTlv(0x009F02);
        Log.d(TAG,"Tag value 9F02: " + GPMethods.bytesToHexString(value9C) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F02",  GPMethods.bytesToHexString(value9F02) ));


        //replace it with currency code gotten from get parameter
        byte[] value5F2A = smartPosApi.getTlv(0x005F2A);  // Change the tag to 5F2A
        Log.d(TAG, "Tag value 5F2A: " + GPMethods.bytesToHexString(value5F2A) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("5F2A", GPMethods.bytesToHexString(value5F2A)));





        byte[] value82 = smartPosApi.getTlv(0x0082);  // Change the tag to 82
        Log.d(TAG, "Tag value 82: " + GPMethods.bytesToHexString(value82) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("82", GPMethods.bytesToHexString(value82)));




        byte[] value9F1A = smartPosApi.getTlv(0x009F1A);  // Change the tag to 9F1A
        Log.d(TAG, "Tag value 9F1A: " + GPMethods.bytesToHexString(value9F1A) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F1A", GPMethods.bytesToHexString(value9F1A)));


        byte[] value84 = smartPosApi.getTlv(0x0084);  // Change the tag to 84
        Log.d(TAG, "Tag value 84: " + GPMethods.bytesToHexString(value84) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("84", GPMethods.bytesToHexString(value84)));


        byte[] value9F03 = smartPosApi.getTlv(0x009F03);  // Change the tag to 9F03
        Log.d(TAG, "Tag value 9F03: " + GPMethods.bytesToHexString(value9F03) + "\n");
        iccDataConcatenatedBuilder.append(concatLength2Str("9F03", GPMethods.bytesToHexString(value9F03)));


        return  iccDataConcatenatedBuilder.toString();
    }

    public static String concatLength2Str(String tag, String value) {
        int length = value.length() / 2;
        String hexlenght;
        if (length <= 9) {
            hexlenght = "0" + length;
        } else {
            hexlenght = Integer.toHexString(length);
        }
        String tlv = tag + hexlenght + value;
        return tlv;
    }

}
