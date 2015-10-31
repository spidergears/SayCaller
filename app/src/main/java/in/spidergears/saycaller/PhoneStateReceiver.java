package in.spidergears.saycaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
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
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String contactName = findContactName(incomingNumber, context);
                Toast.makeText(context, "Call from " + contactName, Toast.LENGTH_LONG).show();
                SayCallerTTS.sayText("New Incoming Call. Please Answer.");
            }
        }
        catch (Exception e) {
            Log.e(TAG, "SayCaller encountered exception while processing the broadcast: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private String findContactName(String contactNumber, Context context){
        Log.i(TAG, "Initiating contact lookup for number: " + contactNumber);
        //Get contact name from Contacts
        String contactName = null;
        Uri lookUpUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(contactNumber));
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor lookUpCursor = context.getContentResolver().query(lookUpUri, projection, null, null, null);
        try {
            if (lookUpCursor.getCount() > 0) {
                while (lookUpCursor.moveToNext()) {
                    contactName = lookUpCursor.getString(lookUpCursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                    Log.d(TAG, "ContactName: " + contactName);
                }
            }
            else {
                 Log.i(TAG, "Contact number does not exist");
                contactName = "unknown number";
            }

        }
        catch (Exception e) {
            Log.e(TAG, "Encountered exception while looking up contact name: "  + e.getLocalizedMessage());
            e.printStackTrace();
        }
        finally {
            lookUpCursor.close();
            return contactName;
        }
    }
}
