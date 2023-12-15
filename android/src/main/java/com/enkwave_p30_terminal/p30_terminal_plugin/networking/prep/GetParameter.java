package com.enkwave_p30_terminal.p30_terminal_plugin.networking.prep;

import static com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.NetworkVariables.*;

import com.enkwave_p30_terminal.p30_terminal_plugin.networking.miscellaneous.CommSocket;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.miscellaneous.EncDec;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.miscellaneous.ISO8583;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.miscellaneous.ProfileParser;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.miscellaneous.SSLTLS;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.miscellaneous.Utilities;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.GetParamTags;
import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.ParameterModel;
import com.enkwave_p30_terminal.p30_terminal_plugin.utils.Debug;

import org.jpos.iso.ISOUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetParameter  {
    private final String TAG = GetParameter.class.getSimpleName();


    public ParameterModel doWork(String terminalID, String clearSessionKey) {


        ISO8583 packISO8583 = new ISO8583();
        packISO8583.setMit("0800");
        packISO8583.clearBit();
        byte[] field3 = "9C0000".getBytes();
        packISO8583.setBit(3, field3, field3.length);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
        String datetime = simpleDateFormat.format(new Date());
        byte[] field7 = datetime.getBytes();
        packISO8583.setBit(7, field7, field7.length);
        simpleDateFormat = new SimpleDateFormat("hhmmss");
        String stan = simpleDateFormat.format(new Date());
        byte[] field11 = stan.getBytes();
        packISO8583.setBit(11, field11, field11.length);
        byte[] field12 = stan.getBytes();
        packISO8583.setBit(12, field12, field12.length);
        simpleDateFormat = new SimpleDateFormat("MMdd");
        String date = simpleDateFormat.format(new Date());
        byte[] field13 = date.getBytes();
        packISO8583.setBit(13, field13, field13.length);
        byte[] field41 = terminalID.getBytes();
        packISO8583.setBit(41, field41, field41.length);
        String sn = "4567273648402939339";
        String cs = String.format("%03d", sn.length());

        String sF64 = "01" + cs + sn;//sn
        byte[] field62 = sF64.getBytes();
        packISO8583.setBit(62, field62, field62.length);

        byte use = 0x0;
        char ch = (char) use;
        byte[] field64 = Character.toString(ch).getBytes();
        packISO8583.setBit(64, field64, field64.length);
        ISO8583.sec = false;
        byte[] unMac = packISO8583.getMacIso();
        Debug.print(TAG+ "NACK: " +unMac.length);

        byte[] unMacTest = new byte[unMac.length - 64];

        System.arraycopy(unMac, 0, unMacTest, 0, unMac.length - 64);
        Debug.print(TAG+ "A: ISO BEFORE MAC: " + new String(unMacTest));

        EncDec enc = new EncDec();
        String gotten = null;
        try {
            Debug.print(TAG+ "CLEAR SESSION KEY USED: " + clearSessionKey);
            gotten = enc.getMacNibss(clearSessionKey, unMacTest);
            Debug.print(TAG+ "PARAMETER Key: " + gotten);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert gotten != null;
        field64 = gotten.getBytes();
        packISO8583.setBit(64, field64, field64.length);
        byte[] packData = packISO8583.isotostr();

        Debug.print(TAG+ "PARAMETER. ISO TO HOST: " + new String(packData));
        Debug.print(TAG+ "ISO TO HOST: " + ISOUtil.hexString(packData));
        Debug.print(TAG+ "IP: " + ip);
        Debug.print(TAG+ "PORT: " + port);
        Debug.print(TAG+ "SSL: " + ssl);
        byte[] recvarr = null;
        ISO8583 unpackISO8583 = new ISO8583();
        if(ssl.equals("true"))
        {

            try {
                Debug.print("PARAMETER BODY " + new String(packData));

                recvarr =  SSLTLS.doSSL(ip, port, packData);
                Debug.print("Response " + recvarr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (recvarr != null && recvarr.length > 65)
            {
                ProfileParser.parahost1 = new byte[recvarr.length - 4];
                System.arraycopy(recvarr, 4, ProfileParser.parahost1, 0, recvarr.length - 4);
                unpackISO8583.strtoiso(ProfileParser.parahost1);
            }
        }else {
            try {
            CommSocket send = new CommSocket();
            if (send.open(ip, port) == true) {
                Debug.print(TAG+ "OPEN SUCCESS");
                int count = send.send(packData);
                Debug.print(TAG+ "SENT: " + count);
                recvarr = send.recv();
                Debug.print(TAG+ "----------> "+ recvarr);
                send.close();
            } else {

                Debug.print(TAG+ "PARAMETER FAILED");
                Debug.print(TAG+ "Could Not Open: " + ProfileParser.hostip + ":" + ProfileParser.hostport);
            }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (recvarr != null && recvarr.length > 65)
            {
                ProfileParser.parahost1 = new byte[recvarr.length - 2];
                System.arraycopy(recvarr, 2, ProfileParser.parahost1, 0, recvarr.length - 2);
                unpackISO8583.strtoiso(ProfileParser.parahost1);
            }
        }
        String[] receiving;

        if (recvarr == null || recvarr.length < 65) {
            Debug.print(TAG+  "PARAMETER DOWNLAOD FAILED");
            Debug.print(TAG+ "PARAMETER FAILED");
            receiving = new String[128];
            return null;
        }
        Debug.print(TAG+"RECV: " + new String(recvarr));
        Debug.print(TAG+ "2. RECV: " + ISOUtil.hexString(recvarr));
        //Log.i(TAG, "DONE WITH CONNECTION");
        receiving = new String[128];
        Utilities.logISOMsg(unpackISO8583, receiving);

        if(receiving[62] == null)
        {
            receiving = new String[128];
            Debug.print(TAG+ "PARAMETER DOWNLOAD SUCCESSFUL - HOST TWO");


        }

        String key = receiving[62];

        if(key.length() < 50 ||  !receiving[39].equals("00"))
        {
            Debug.print(TAG+ "PARAMETER FAILED\" - HOST TWO");
        }
        Debug.print(TAG + "FIELD 62:=====> " + key);
        return new GetParamTags().get(key);



    }

    public static String generateHash256Value(String msg, String key)  {
        MessageDigest m = null;
        String hashText = null;
        byte[] actualKeyBytes = key.getBytes();
        try {
            m = MessageDigest.getInstance("SHA-256");
            m.update(actualKeyBytes, 0, actualKeyBytes.length);

            m.update(msg.getBytes("UTF-8"), 0, msg.length());
            hashText = (new BigInteger(1, m.digest())).toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException var9) {
            var9.printStackTrace();
        }
        if (hashText.length() < 64) {
            int numberOfZeroes = 64 - hashText.length();
            String zeroes = "";

            for(int i = 0; i < numberOfZeroes; ++i) {
                zeroes = zeroes + "0";
            }

            hashText = zeroes + hashText;
        }

        return hashText;
    }

}