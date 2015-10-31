package in.spidergears.saycaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private String TAG = "SayCaller.BootCompleted";

    public BootCompletedReceiver() {
        Log.i(TAG, "Boot Completed.");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Boot Completed, starting service SayCallerTTS");
        context.startService(new Intent(context, SayCallerTTS.class));
    }
}
