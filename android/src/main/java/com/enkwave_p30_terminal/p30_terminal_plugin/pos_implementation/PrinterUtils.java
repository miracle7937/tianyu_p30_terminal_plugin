package com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.enkwave_p30_terminal.p30_terminal_plugin.P30TerminalPlugin;
import com.whty.smartpos.tysmartposapi.ITYSmartPosApi;
import com.whty.smartpos.tysmartposapi.modules.printer.PrinterInitListener;
import com.whty.smartpos.tysmartposapi.modules.printer.PrinterListener;
import com.whty.smartpos.tysmartposapi.utils.TYLog;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrinterUtils {
    private static final String TAG = PrinterUtils.class.getSimpleName();
    public ITYSmartPosApi smartPosApi = P30TerminalPlugin.smartPosApi;


    public void print(Activity activity) {
        Log.d(TAG, ">>> initPrinter <<<\n");
;
        boolean res = smartPosApi.initPrinter(new MyPrinterInitListener());
        Log.d(TAG,"result : " + res + "\n");


        Log.d(TAG,">>> printCustomTemplate <<<\n");
        try {

            Map<String, String> data = new HashMap<>();
            InputStream is = activity.getAssets().open("ty_logo_print.bmp");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            String base64Str = getPicture(bitmap);
            data.put("Logo", base64Str);
            data.put("MerName", "Enkwave LTD");
            data.put("MerNum", "2017666888");
            data.put("TerNum", "10072686");
            data.put("RRN", "0100000");
            data.put("ExpDate", "05/29");
            data.put("Rescode", "00");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            data.put("DateTime", sdf.format(new Date()));



//                <PrintLine name="MerName" title="MERCHANT NAME:" textsize="3" align="1" feedpaper="0" ></PrintLine>
//    <PrintLine name="TerNum" title="TERMINAL NO:" textsize="3" align="1" feedpaper="0" ></PrintLine>
//    <PrintLine name="RRN" title="RRN:" textsize="3" align="1" feedpaper="0" ></PrintLine>
//    <PrintLine title="CARDNO：" textsize="3" align="1" feedpaper="0" ></PrintLine>
//    <PrintLine name="ExpDate" title="VALIDITYDATE:" textsize="3" align="1" feedpaper="0"></PrintLine>
//    <PrintLine name="Rescode" title="RESPONSE CODE:" textsize="3" align="1" feedpaper="0" ></PrintLine>
//    <PrintLine name="DateTime" title="DATETIME:" textsize="3" align="1" feedpaper="0" ></PrintLine>




                    is = activity.getAssets().open("custom_template.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) { sb.append(line);}

            smartPosApi.printCustomTemplate(data, "slip_consume_mer", sb.toString(), 0, new MyPrinterListener());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    class MyPrinterInitListener implements PrinterInitListener {

        @Override
        public void onPrinterInit(boolean isSuccess) {

        }
    }

    static class MyPrinterListener implements PrinterListener {

        @Override
        public void onPrinterOutOfPaper() {
            TYLog.i("PrinterDevice", "onPrinterOutOfPaper");
        }

        @Override
        public void onPrinterStart(String templateName) {
            TYLog.i("PrinterDevice", "onPrinterStart");
            TYLog.i("PrinterDevice", "templateName : " + templateName);
        }

        @Override
        public void onPrinterEnd(String templateName) {
            TYLog.i("PrinterDevice", "onPrinterEnd");
            TYLog.i("PrinterDevice", "templateName : " + templateName);
        }

        @Override
        public void onPrinterError(int errorCode, String errorMsg) {
            TYLog.i("PrinterDevice", "onPrinterError");
            TYLog.i("PrinterDevice", "errorCode : " + errorCode);
            TYLog.i("PrinterDevice", "errorMsg : " + errorMsg);
        }
    }

    private String getPicture(Bitmap bitmap) {
        return Base64.encodeToString(bitmap2Bytes(bitmap), Base64.DEFAULT);

    }

    private byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap newbitmap = Bitmap.createScaledBitmap(bitmap, 300, 130, true);
        newbitmap.compress(Bitmap.CompressFormat.PNG, 20, baos);
        newbitmap.recycle();
        return baos.toByteArray();
    }
}
