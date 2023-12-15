package com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation;

import android.util.Log;

import com.enkwave_p30_terminal.p30_terminal_plugin.P30TerminalPlugin;
import com.whty.smartpos.tysmartposapi.ITYSmartPosApi;

public class PrepImplementation {
    private static final String TAG = PrepImplementation.class.getSimpleName();

    final  String clearPinKey;
    final  String clearMasterKey;

    public PrepImplementation(String clearPinKey, String clearMasterKey) {
        this.clearPinKey = clearPinKey;
        this.clearMasterKey = clearMasterKey;
    }
    public ITYSmartPosApi smartPosApi = P30TerminalPlugin.smartPosApi;

    public boolean execute() {
        int res = smartPosApi.updateTransKey(0, "00000000000000000000000000000000");
        Log.d(TAG, "updateTransKey==================> " + res);
        int  res2 = smartPosApi.updateMainKey(0, clearMasterKey);
        Log.d(TAG, "updateMainKey===================>  " + res2);
        int   res3 = smartPosApi.updateWorkKey(0, clearPinKey);
        Log.d(TAG, "updateWorkKey================> " + res3);
        return res == 0 || res2 == 0 || res3 == 0;
    }
}
