package com.enkwave_p30_terminal.p30_terminal_plugin.networking.http;

import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.ParameterModel;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.TransactionRequest;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.TransactionResponseModel;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.transaction.TransactionPurchase;


import java.util.HashMap;

public class ProcessFlowTransaction {

    public TransactionResponseModel go(String TID, TransactionRequest tranReqObj, ParameterModel parameterModel) throws Exception {
        TransactionResponseModel transactionResponseModel=    new TransactionPurchase().doWork(TID, tranReqObj,parameterModel );

        //notify remote
        System.out.println("MMMMMMMMMMM============ "+ transactionResponseModel.toString());
//        new  HTTPServiceBuilder().tranNotification().notifyT(transactionResponseModel).execute();
//        new NormalHttp().request(transactionResponseModel);
        return  transactionResponseModel;
    }
}
