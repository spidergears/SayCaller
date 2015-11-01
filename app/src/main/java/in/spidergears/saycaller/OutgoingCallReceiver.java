package in.spidergears.saycaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class OutgoingCallReceiver extends BroadcastReceiver {

    private String TAG = "SayCaller.OutgoingCallReceiver";

    public OutgoingCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive invoked.");
        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()){
            Log.d("SayCaller.Inspector", "Extra_" + key + " -> " + bundle.get(key));
        }
        try {
            //get phoneNumber being called
            String outgoingNumber = getResultData();
            if (outgoingNumber == null) {
                outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            }
            //get contactName
            String contactName = SayCallerUtils.findContactName(outgoingNumber, context);
            //initiate TTS
            Toast.makeText(context, "Outgoing call to " + contactName, Toast.LENGTH_LONG).show();
            SayCallerTTS.sayText("Initiated new outgoing call.");
        }
        catch (Exception e) {
            Log.e(TAG, "SayCaller encountered exception while processing the broadcast: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
