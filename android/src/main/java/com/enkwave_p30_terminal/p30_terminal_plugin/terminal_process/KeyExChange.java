package com.enkwave_p30_terminal.p30_terminal_plugin.terminal_process;

import android.app.Activity;
import android.widget.Toast;

import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.PrepResultRecord;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.prep.SynchronizePrep;
import com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation.CustomLoader;
import com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation.PrepImplementation;
import com.enkwave_p30_terminal.p30_terminal_plugin.utils.PrefManager;

public class KeyExChange {
    final Activity activity;
    final String terminalID;
    final  CustomLoader customLoader;

    public KeyExChange(Activity activity, String terminalID) {
        this.activity = activity;
        this.terminalID = terminalID;
        customLoader = new CustomLoader(activity);
    }

    public  void  run(){
        customLoader.showLoader("key Loading...");

        new Thread() {
            @Override
            public void run() {
                super.run();
                PrepResultRecord prepResultRecord = new SynchronizePrep(terminalID).Init();
                //save data
                new PrefManager().savePrepData(prepResultRecord);
                //prep save time;
                new PrefManager().savePrepTime( System.currentTimeMillis());
                boolean isSuccess =   new PrepImplementation(prepResultRecord.getPinKeyModel().getClearPinKey()
                        , prepResultRecord.getMasterKeyModel().getClearMasterKey()).execute();
                activity.runOnUiThread(() ->{
                    customLoader.hideLoader();
                    if(isSuccess){
                        Toast.makeText(activity, "Key Exchange Successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(activity, "Key Exchange Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }.start();

    }
}
