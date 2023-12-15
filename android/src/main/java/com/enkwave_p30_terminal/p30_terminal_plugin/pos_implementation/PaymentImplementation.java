package com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.enkwave_p30_terminal.p30_terminal_plugin.P30TerminalPlugin;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.PrepResultRecord;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.TransactionRequest;
import com.enkwave_p30_terminal.p30_terminal_plugin.utils.PrefManager;
import com.enkwave_p30_terminal.p30_terminal_plugin.utils.StringUtil;
import com.whty.smartpos.tysmartposapi.ITYSmartPosApi;
import com.whty.smartpos.tysmartposapi.modules.cardreader.CardInfo;
import com.whty.smartpos.tysmartposapi.modules.cardreader.CardReaderListener;
import com.whty.smartpos.tysmartposapi.modules.pinpad.PinPadConfig;
import com.whty.smartpos.tysmartposapi.modules.pinpad.PinPadConstant;
import com.whty.smartpos.tysmartposapi.modules.pinpad.PinPadResult;
import com.whty.smartpos.tysmartposapi.modules.pinpad.PinPadResultListener;
import com.whty.smartpos.tysmartposapi.utils.GPMethods;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentImplementation {
    final String amount, terminalID,   accountType;
    final PrepResultRecord prepResultRecord;
    public PaymentImplementation(PrepResultRecord prepResultRecord, String amount, String terminalID, String accountType) {
        this.amount = amount;
        this.terminalID = terminalID;
        this.accountType = accountType;
        this.prepResultRecord = prepResultRecord;
    }



    private static final String TAG = PrepImplementation.class.getSimpleName();
    public ITYSmartPosApi smartPosApi = P30TerminalPlugin.smartPosApi;
    public static PinPadResult pinResult;

    public TransactionRequest run(){

        //===================================read card====================================================================
     @SuppressLint("SimpleDateFormat")
     String timeOfTransaction = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
     CardInfo cardInfo = smartPosApi.readCard(amount, timeOfTransaction, (byte) 0, (byte) 60, new UserCardReaderListener());
         Log.d(TAG, "CardInfo Info ===================> " + cardInfo.getIcData55());

        byte[] value = smartPosApi.getTlv(0x0057);
        Log.d(TAG,"Tag value 57: " + GPMethods.bytesToHexString(value) + "\n");




        //===================================Calculate Pin Block====================================================================
       if(cardInfo.getCardNo() != null){
           Bundle bundle = new Bundle();
           bundle.putString(PinPadConfig.CARD_NUM, cardInfo.getCardNo());
           bundle.putString(PinPadConfig.AMOUNT, amount);
           bundle.putString(PinPadConfig.TIMEOUT, "60");
           bundle.putString(PinPadConfig.MIN_LEN, "4");
           bundle.putString(PinPadConfig.MAX_LEN, "6");
           bundle.putBoolean(PinPadConfig.WITH_SOUND, true);
           bundle.putBoolean(PinPadConfig.WITH_DISPLAY_BAR, true);
           bundle.putInt(PinPadConfig.PIN_FORMAT, PinPadConstant.PinFormat.FORMAT_0);
           bundle.putInt(PinPadConfig.KEY_INDEX, 0);

           pinResult = null;
           smartPosApi.startPinPad(bundle, new UserPinResultListener());
           while (pinResult == null) {
               try {
                   Thread.sleep(10);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           Log.d(TAG,"result resultCode: " + pinResult.getResultCode() + "\n");
           Log.d(TAG,"result pinblock: " + pinResult.getPinBlock() + "\n");
       }

       return collectData(cardInfo,  pinResult.getPinBlock());
    }

    static class UserCardReaderListener implements CardReaderListener {

        @Override
        public void onReadCardType(CardInfo cardInfo) {

        }

        @Override
        public void onReadCard(CardInfo cardInfo) {

        }
    }

    class UserPinResultListener implements PinPadResultListener {

        @Override
        public void onKeyDown(int count) {
            System.out.println("MIMI "+ count);

        }

        @Override
        public void onGetPinPadResult(PinPadResult pinResult) {
            PaymentImplementation.pinResult = pinResult;
        }
    }



    public  TransactionRequest collectData(CardInfo cardInfo, String pinBlock){

        TransactionRequest tranReqObj = new TransactionRequest();
        tranReqObj.setAmount(String.valueOf(amount));
        tranReqObj.setExpiryDate(cardInfo.getCardValidThru());
        tranReqObj.setCardPan(cardInfo.getCardNo());
        tranReqObj.setProcessingCode("00" + accountType + "00");
        tranReqObj.setPinBlock(pinBlock);
        System.out.println("getClearSessionKey "+ prepResultRecord.getSessionKeyModel().getClearSessionKey() );
        tranReqObj.setSessionkey(prepResultRecord.getSessionKeyModel().getClearSessionKey());
        tranReqObj.setTerminalID(terminalID);
        tranReqObj.setMerchantName(prepResultRecord.getParameterModel().getMnl());
        System.out.println("getMnl "+ prepResultRecord.getParameterModel().getMnl() );

        tranReqObj.setMerchantID(prepResultRecord.getParameterModel().getMid());
        System.out.println("getMid "+ prepResultRecord.getParameterModel().getMid() );
        System.out.println("getMcc "+ prepResultRecord.getParameterModel().getMcc() );
        System.out.println("getCountryCode "+ prepResultRecord.getParameterModel().getCountryCode() );



        byte[] tractData2 = smartPosApi.getTlv(0x0057);
        Log.d(TAG,"Tag value 57: " + GPMethods.bytesToHexString(tractData2) + "\n");
        tranReqObj.setTrack2data(GPMethods.bytesToHexString(tractData2));

        tranReqObj.setICCData(StringUtil.getIcDATA3(smartPosApi));

        byte[] value5F34 = smartPosApi.getTlv(0x005F34);
        Log.d(TAG,"Tag value 5F34: " + GPMethods.bytesToHexString(value5F34) + "\n");
        String csn =  GPMethods.bytesToHexString(value5F34);
        if (csn.length() == 2) {
            csn = "0" + csn;
        }
        System.out.println("CSN ===========>"+ csn);
        tranReqObj.setCSN("001");
        tranReqObj.setMerchantCategoryCode(prepResultRecord.getParameterModel().getMcc());
        int indexOfToken = GPMethods.bytesToHexString(tractData2).indexOf("D");
        int indexOfServiceCode = indexOfToken + 5;
        int lengthOfServiceCode = 3;
        String serviceCode = GPMethods.bytesToHexString(tractData2).substring(indexOfServiceCode, indexOfServiceCode + lengthOfServiceCode);
        tranReqObj.setServiceCode(serviceCode);

        return tranReqObj;
    }
}

