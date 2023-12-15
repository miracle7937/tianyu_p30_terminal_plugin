package com.enkwave_p30_terminal.p30_terminal_plugin.terminal_process;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.enkwave_p30_terminal.p30_terminal_plugin.networking.http.ProcessFlowTransaction;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.NetworkVariables;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.PrepResultRecord;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.TransactionRequest;
import com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation.CustomLoader;
import com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation.PaymentImplementation;
import com.enkwave_p30_terminal.p30_terminal_plugin.utils.DateUtils;
import com.enkwave_p30_terminal.p30_terminal_plugin.utils.Debug;
import com.enkwave_p30_terminal.p30_terminal_plugin.utils.NetworkUtils;
import com.enkwave_p30_terminal.p30_terminal_plugin.utils.PrefManager;

import java.util.HashMap;
import java.util.Objects;

public class Purchase {
    final Activity activity;
    final CustomLoader customLoader;
    final HashMap<String, String> transactionData;

    public Purchase(Activity activity, HashMap<String, String> transactionData) {
        this.activity = activity;

        customLoader = new CustomLoader(activity);
        this.transactionData = transactionData;
    }

    public  void payNow(){
        if (!NetworkUtils.isNetworkConnected(activity)) {
            Toast.makeText(activity, "Not  network connection", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean keyPassedTime=   DateUtils.hourPassed(3, new PrefManager().getPrepTime());
        if(keyPassedTime){
            Debug.print( "Fetching key: " + NetworkVariables.port);
            new KeyExChange(activity, transactionData.get("tid")).run();

        }
        onProcessing();
    }

    private  void  onProcessing(){

//=============================== localize key ====================================
        PrefManager prefManager = new PrefManager();
        PrepResultRecord prepResultRecord = prefManager.getPrepData();
        customLoader.showLoader("Processing Payment ...");

        new Thread() {
            @Override
            public void run() {
                try
                {
                    //================================= Read Card Info ==========================================================
                    TransactionRequest tranReqObj  =  new PaymentImplementation(
                            prepResultRecord,
                            transactionData.get("amount"),
                            transactionData.get("tid"),
                            transactionData.get("accountType")
                            ).run();

                    System.out.println("RESPONSE========> "+tranReqObj);
                    // check if server is ok before transaction
//                    new ProcessFlowTransaction().go( transactionData.get("tid"), tranReqObj, prepResultRecord.getParameterModel());
                    activity.runOnUiThread(() ->{
                        customLoader.hideLoader();
                       ////================================Navigate thi to status page================================================
                    });

                } catch (Exception e) {
                    activity.runOnUiThread(() ->{
                        customLoader.hideLoader();
                        Toast.makeText(activity, "Key Exchange Failed", Toast.LENGTH_SHORT).show();
                    });

                    Log.e("MyThread", "Error in thread: " + e.getMessage());
                }


            }
        }.start();

        }

}
