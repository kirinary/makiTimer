package net.kirinadev.voicetimer;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.media.MediaPlayer;
import android.media.AudioManager;

public class MainActivity extends Activity implements Chronometer.OnChronometerTickListener{
    MediaPlayer mp = null;
    Chronometer mChronometer;
    SoundPool sp = null;
    private int mFile;
    private int ct;

    int[] spId = new int[24];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFile = R.raw.vrtam100001;

        mChronometer = ((Chronometer)findViewById(R.id.c));

        //音声ファイルの初期化
        sp = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build())
                .setMaxStreams(1)
                .build();
        for(int i=0;i < 24;i++){
            spId[i] = sp.load(getApplicationContext(), mFile, 1);
            mFile = mFile + 1;
        }
    }

    // スタート
    public void onStart( View v ){
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        mChronometer.setOnChronometerTickListener(this);
    }

    // ストップ
    public void onStop( View v ){
        mChronometer.stop();

        if(mp.isPlaying()){
            mp.stop();
        }
        mp.release();
        mp = null;
    }

    // Chronometerイベントリスナー
    public void onChronometerTick(Chronometer chronometer) {
        sp.play(spId[ct], 1.0f, 1.0f, 0, 0, 1.0f);
        sp.unload(spId[ct]);
        ct = ct + 1;
    }
}
