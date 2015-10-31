package in.spidergears.saycaller;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

public class SayCallerTTS extends Service implements TextToSpeech.OnInitListener {
    private String TAG = "SayCaller.SayCallerTTS";
    public static TextToSpeech tts;

    public class LocalBinder extends Binder {
        SayCallerTTS getService() {
            return SayCallerTTS.this;
        }
    }

    public SayCallerTTS() {
        Log.i(TAG, "Starting SayCallerTTS.");
        tts = new TextToSpeech(this, this);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            Log.i(TAG, "SyaCallerTTS initialised");
        }
        else {
            Log.e(TAG, "SayCallerTTS is not initialised yet.");
        }
    }

    public static void sayText(String text){
        Log.i("SayCallerTTS", "Speaking text " + text);
        if (Build.VERSION.SDK_INT >= 21) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "SayCaller.NewIncomingCall");
        }
        else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null );
        }
    }
}
