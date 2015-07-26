package sys.android.app.runner;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;


import java.lang.reflect.Method;
import java.util.Date;
import java.util.logging.Logger;

import sys.android.app.DataUtil;
import sys.android.app.ui.activity.LoginActivity;
import sys.android.app.ui.activity.LoginOrSiginActivity;
import sys.android.app.ui.activity.RunnerPasswordActivity;
import sys.android.app.ui.activity.UsersActivity;

public class MyRunnerReceiver extends BroadcastReceiver {
    public MyRunnerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        String phoneNumber = "";
        if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
            final String originalNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d("APP", "outgoing,ringing: " + originalNumber);
            phoneNumber =  intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        }


//
//        String phoneNumber = "";
//
//        phoneNumber = intent.getStringExtra("incoming_number");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

        switch (tm.getCallState()) {

            case TelephonyManager.CALL_STATE_RINGING:
//                Setting.setCallStateRinging(context, new Date().getTime());
//                Setting.setPhoneNumber(context, intent.getStringExtra("incoming_number"));
                Log.d("log", "CALL_STATE_RINGING");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
//                Setting.setCallStateOffhook(context, new Date().getTime());
                Log.d("log", "CALL_STATE_OFFHOOK");
                break;
            case TelephonyManager.CALL_STATE_IDLE:
//                Setting.setCallStateIdle(context, new Date().getTime());
//
//
//                new Send().execute();
                Log.d("log","CALL_STATE_IDLE");
                phoneNumber =  intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.d("log","phoneNumber " +phoneNumber);
                if (phoneNumber!=null&&phoneNumber.equals(DataUtil.getRunnerPassword(context))) {
                    Log.d("log", "del_" + phoneNumber);

                    if(DataUtil.getRunnerPassword(context).equals(DataUtil.DEFAULT_PASS)){
                        Log.d("log", "RunnerPasswordActivity.launch ");
                        Intent runIntent =  new Intent(context, RunnerPasswordActivity.class);
                        runIntent.putExtra("run","run app invisible");
                        RunnerPasswordActivity.launch(context, runIntent );
                    }
                    else if(!DataUtil.getSettingValue(context, DataUtil.EMAIL).isEmpty()){
                        UsersActivity.launch(context, new Intent(context, UsersActivity.class));
                    }else {
                        Log.d("log", "LoginOrSiginActivity.launch " );
                        LoginActivity.launch(context, new Intent(context, LoginActivity.class));
                    }
//                    context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number = ?", new String[]{phoneNumber});
                }
                break;
        }
////
//        if (phoneNumber.equals(DataUtil.getRunnerPassword(context))) {
//            Log.d("log", "del_" + phoneNumber);
////            endCall(context);
//            RunnerPasswordActivity.launch(context, new Intent(context, RunnerPasswordActivity.class));
//        }
    }



}
