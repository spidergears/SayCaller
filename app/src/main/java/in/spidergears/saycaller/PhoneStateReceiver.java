package in.spidergears.saycaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {
    String TAG = "SayCaller.PhoneStateReceiver";

    public PhoneStateReceiver(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive invoked with state: " + intent.getStringExtra(TelephonyManager.EXTRA_STATE));

        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            Log.d(TAG, "CallState: " + state);
            if (state.equals("RINGING")){
                Log.e(TAG, "call state ringing");
                Toast.makeText(context, "Call from " + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER), Toast.LENGTH_LONG).show();
                SayCallerTTS.sayText("New Incoming Call. Please Answer.");
            }
        }
        catch (Exception e) {
            Log.e(TAG, "SayCaller encountered exception while processing the broadcast: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
