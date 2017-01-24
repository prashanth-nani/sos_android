package com.example.android.sos;

import java.io.File;
import java.io.IOException;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;


/**
 * Created by prashanth on 10/11/16.
 */

public class recorder extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    int i;
    SharedPreferences sharedpref;
    SharedPreferences.Editor editor;
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;
    private static String audioFilePath;

    private boolean recorded = false;
    private boolean isRecording = false;
    private boolean playing = false;

    private ToggleButton recordToggle;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recorder);
        playButton = (Button) findViewById(R.id.playBtn);
        recordToggle = (ToggleButton) findViewById(R.id.recordToggle);
        recordToggle.setOnCheckedChangeListener(this);

        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/SOSAudio");
        folder.mkdirs();


        if (!hasMicrophone()) {
            recordToggle.setEnabled(false);
            Toast.makeText(this, "No microphone is available to record audio", Toast.LENGTH_SHORT).show();
        } else {
            recordToggle.setEnabled(true);
        }


        sharedpref = getSharedPreferences("state", MODE_PRIVATE);
        editor = sharedpref.edit();
        String count = sharedpref.getString("countAudio", "");
        if (count.isEmpty()) {
            editor.putString("countAudio", "0");
            i = 0;
            editor.apply();
        } else {
            i = Integer.parseInt(count);
        }

    }

    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }

    public void recordAudio() throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            do {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
            while (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED);
        }


        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SOSAudio" + "/myaudio" + i + ".3gp";
        isRecording = true;

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        i++;
        editor.putString("countAudio", "" + i);
        editor.apply();
        mediaRecorder.start();
    }

    public void stopAudio() {
        editor.putString("status", "0");
        editor.apply();

        if (isRecording) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    public void playAudio(View view) throws IOException {
        if(recorded) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        else
            Toast.makeText(this, "Record audio to play", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton recordButton, boolean isRecording) {
        if (isRecording) {
            Toast.makeText(this, "Recording audio...", Toast.LENGTH_SHORT).show();
            try {
                recordAudio();
                recorded = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            stopAudio();
            Toast.makeText(this, "Saved recording to sdcard/SOSAudio/", Toast.LENGTH_SHORT).show();
        }
    }

    public void share(View view){
        String sharePath = "file://"+audioFilePath;
        Log.d("test", sharePath);
        Uri uri = Uri.parse(sharePath);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setType("audio/3gp");
        startActivity(Intent.createChooser(share, "Share Sound File"));
    }
}