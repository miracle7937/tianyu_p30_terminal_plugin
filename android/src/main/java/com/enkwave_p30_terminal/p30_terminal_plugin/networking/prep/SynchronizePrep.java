package com.enkwave_p30_terminal.p30_terminal_plugin.networking.prep;



import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.MasterKeyModel;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.ParameterModel;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.PinKeyModel;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.PrepResultRecord;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.SessionKeyModel;



public final class SynchronizePrep {
    private final String terminalID;

    public SynchronizePrep(String terminalID) {
        this.terminalID = terminalID;
    }




    @Override
    public String toString() {
        return "SynchronizePrep[" +
                "terminalID=" + terminalID + ']';
    }

    public PrepResultRecord Init() {
        MasterKeyModel masterKeyModel = new GetMasterKey().doWork(terminalID);
        SessionKeyModel sessionKeyModel = new GetSessionKey().doWork(terminalID, masterKeyModel.getClearMasterKey());
        PinKeyModel pinKeyModel = new GetPinKey().doWork(terminalID, masterKeyModel.getClearMasterKey());
        ParameterModel parameterModel = new GetParameter().doWork(terminalID, sessionKeyModel.getClearSessionKey());
        PrepResultRecord resultRecord = new PrepResultRecord();
        resultRecord.setMasterKeyModel(masterKeyModel);
        resultRecord.setSessionKeyModel(sessionKeyModel);
        resultRecord.setPinKeyModel(pinKeyModel);
        resultRecord.setParameterModel(parameterModel);
        return resultRecord;

    }
}
