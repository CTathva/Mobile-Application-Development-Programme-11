package com.akash.cp.vtu.vtupartb_3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MSGReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsMsg = null;
        String smsStr = "";
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsMsg = new SmsMessage[pdus.length];
            for (int i = 0; i < smsMsg.length; i++) {
                smsMsg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                smsStr = smsMsg[i].getMessageBody().toString();
                String Sender = smsMsg[i].getOriginatingAddress();
                Intent smsIntent = new Intent("msg");
                smsIntent.putExtra("message", smsStr);
                smsIntent.putExtra("Sender", Sender);
                LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
            }
        }
    }
}