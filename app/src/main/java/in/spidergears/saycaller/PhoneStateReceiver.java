package in.spidergears.saycaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {
    private String TAG = "SayCaller.PhoneStateReceiver";
    private static String oldState;
    public PhoneStateReceiver(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive invoked with state: " + intent.getStringExtra(TelephonyManager.EXTRA_STATE));
        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()){
            Log.d("SayCaller.Inspector", "Extra_" + key + " -> " + bundle.get(key));
        }
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            //Android lollipop fires duplicate broadcasts for phone state
            //if phone state actually changed
            if (!state.equals(oldState)) {
                oldState = state;
                Log.d(TAG, "CallState: " + state);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    String contactName = SayCallerUtils.findContactName(incomingNumber, context);
                    Toast.makeText(context, "Call from " + contactName, Toast.LENGTH_LONG).show();

                    Intent service = new Intent(context, SayCallerTTS.class);
                    service.putExtra("announcement", "New incoming call, please answer.");
                    context.startService(service);
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "SayCaller encountered exception while processing the broadcast: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
