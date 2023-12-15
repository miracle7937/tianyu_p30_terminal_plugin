package com.enkwave_p30_terminal.p30_terminal_plugin.networking.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EodModel
{


    private List<TransactionResponseModel> payments;

    private  Object totalItems;
    private  String institutionLogo;

    public void setInstitutionLogo(String institutionLogo) {
        this.institutionLogo = institutionLogo;
    }

    public String getInstitutionLogo() {
        return institutionLogo;
    }

    public void setPayments(List<TransactionResponseModel> payments) {
        this.payments = payments;
    }

    public void setTotalItems(Object totalItems) {
        this.totalItems = totalItems;
    }

    public List<TransactionResponseModel> getPayments() {
        return payments;
    }

    public Object getTotalItems() {
        return totalItems;
    }

    public EodModel() {
        payments = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "EodModel{" +
                "payments=" + payments +
                ", totalItems=" + totalItems +
                '}';
    }

    public  EodModel setAllData(HashMap<String,Object> hashMap){
        EodModel eodModel = new EodModel();
        eodModel.setTotalItems((int) hashMap.get("totalItems"));

        if(hashMap.get("institutionLogo") != null){
            eodModel.setInstitutionLogo(hashMap.get("institutionLogo").toString());
        }

        List<Map<String, Object>> paymentMaps = (List<Map<String, Object>>) hashMap.get("payments");
        List<TransactionResponseModel> payments = eodModel.getPayments();
        for (Map<String, Object> paymentMap : paymentMaps) {
            TransactionResponseModel payment = new TransactionResponseModel();
            payment.setCardExpireData((String) String.valueOf(paymentMap.get("cardExpireData")));
            payment.setRRN((String) paymentMap.get("RRN"));
            payment.setTransactionType(TransactionType.PURCHASE);
            payment.setSTAN((String) String.valueOf(paymentMap.get("STAN")));
            payment.setPan((String) paymentMap.get("pan"));
            payment.setTransactionTime((String) String.valueOf(paymentMap.get("transactionTime")));
            payment.setStatus((Boolean) paymentMap.get("status"));
            payment.setResponseMessage((String) paymentMap.get("responseMessage"));
            payment.setCreatedAt((String) paymentMap.get("createdAt"));
            HashMap<String, String> instData = (HashMap<String, String>) paymentMap.get("institutionData");
            System.out.println(instData);
          payments.add(payment);
        }
        eodModel.setPayments(payments);
      return eodModel;
    }
}







