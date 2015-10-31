package in.spidergears.saycaller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by spidergears on 01/11/15.
 */
public class SayCallerUtils {
    private static String TAG = "SayCallerUtils";

    public static String findContactName(String contactNumber, Context context){
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
