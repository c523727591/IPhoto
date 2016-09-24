package com.iphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.util.ILog;

/**
 * Created by duke on 16-9-24.
 */

public class AudioManagerTestActivity extends Activity implements View.OnClickListener {
    private AudioManager mAudioManager = null;
    private TextView mRingerMode = null;
    private TextView mVibrateSetting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.audio_manager_test_activity_layout);

        mRingerMode = (TextView)findViewById(R.id.get_ringer_mode_value);
        mVibrateSetting = (TextView)findViewById(R.id.get_vibrate_setting_value);

        Button button1 = (Button)findViewById(R.id.set_ring_mode_silent);
        button1.setOnClickListener(this);

        Button button2 = (Button)findViewById(R.id.set_ring_mode_vibrate);
        button2.setOnClickListener(this);

        Button button3 = (Button)findViewById(R.id.set_ring_mode_normal);
        button3.setOnClickListener(this);

        Button button4 = (Button)findViewById(R.id.set_vibrate_off);
        button4.setOnClickListener(this);

        Button button5 = (Button)findViewById(R.id.set_vibrate_on);
        button5.setOnClickListener(this);

        Button button6 = (Button)findViewById(R.id.set_vibrate_silent);
        button6.setOnClickListener(this);

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        switch (mAudioManager.getRingerMode()) {
            case 0:
                mRingerMode.setText("RINGER_MODE_SILENT");
                break;
            case 1:
                mRingerMode.setText("RINGER_MODE_VIBRATE");
                break;
            case 2:
                mRingerMode.setText("RINGER_MODE_NORMAL");
                break;
            default:
                ILog.e("getRingerMode error.");
                break;

        }

        switch (mAudioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER)) {
            case 0:
                mVibrateSetting.setText("VIBRATE_SETTING_OFF");
                break;
            case 1:
                mVibrateSetting.setText("VIBRATE_SETTING_ON");
                break;
            case 2:
                mVibrateSetting.setText("VIBRATE_SETTING_ONLY_SILENT");
                break;
            default:
                ILog.e("getVibrateSetting error.");
                break;

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_ring_mode_silent:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(this, "RINGER_MODE_SILENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.set_ring_mode_vibrate:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(this, "RINGER_MODE_VIBRATE", Toast.LENGTH_LONG).show();
                break;
            case R.id.set_ring_mode_normal:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(this, "RINGER_MODE_NORMAL", Toast.LENGTH_LONG).show();
                break;
            case R.id.set_vibrate_off:
                mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
                Toast.makeText(this, "VIBRATE_SETTING_OFF", Toast.LENGTH_LONG).show();
                break;
            case R.id.set_vibrate_on:
                mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
                Toast.makeText(this, "VIBRATE_SETTING_ON", Toast.LENGTH_LONG).show();
                break;
            case R.id.set_vibrate_silent:
                mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ONLY_SILENT);
                Toast.makeText(this, "VIBRATE_SETTING_ONLY_SILENT", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
