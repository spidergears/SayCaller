package in.spidergears.saycaller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

public class SayCallerTTS extends Service implements TextToSpeech.OnInitListener, AudioManager.OnAudioFocusChangeListener {
    private static final String TAG = "SayCaller.SayCallerTTS";
    public static TextToSpeech tts;
    private String text;

    @Override
    public void onCreate(){
        super.onCreate();
        tts = new TextToSpeech(this, this);
        tts.setSpeechRate(1f);
        Log.i(TAG, "SayCallerTTS service created.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        Log.i(TAG, "SayCallerTTS service destroyed.");
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            Log.i(TAG, "SyaCallerTTS initialised");
            sayText(text);
            //stopSelf();
        }
        else {
            Log.e(TAG, "SayCallerTTS is not initialised yet.");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i(TAG, "SayCallerTTS onStartCommand.");
        if (intent.getExtras() != null){
            text = intent.getStringExtra("announcement");
        }
        else {
            text = "No announcement for you.";
        }
        return START_STICKY;
    }

    public void sayText(String text){
        Log.i("SayCallerTTS", "Speaking text " + text);
        if (Build.VERSION.SDK_INT >= 21) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "SayCaller.NewIncomingCall");
            Log.i(TAG, "Spoken text: " + text);
        }
        else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null );
        }
        while(tts.isSpeaking()) {
            SystemClock.sleep(100);
        }
        stopSelf();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        return;
    }
}
