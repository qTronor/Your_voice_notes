package com.example.audionotes.Fragments;


import android.Manifest;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.audionotes.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecorderFrag extends Fragment {
    View view;
    ImageButton button;
    TextView textView;
    Chronometer chronometer;

    private static String fileName;
    private MediaRecorder mediaRecorder;
    private boolean isRecording;

    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Your_Record");

    java.util.logging.Logger log = java.util.logging.Logger.getLogger(RecorderFrag.class.getName());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_record, container, false);

        button = view.findViewById(R.id.RecordBtn);
        textView = view.findViewById(R.id.RecordStatus);
        chronometer = view.findViewById(R.id.RecordTime);
        isRecording = false;

        askForPermission();

        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault());
        String date = format.format(new Date());

        fileName = path + "/record_" + date + ".amr";

        if(!path.exists()){
            path.mkdir();
        }

        button.setOnClickListener(view -> {
            if(!isRecording){
                try {
                    startRecord();
                    isRecording = true;

                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    button.setImageResource(R.drawable.ic_stop);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(isRecording) {
                stopRecord();
                isRecording = false;

                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();
                button.setImageResource(R.drawable.ic_launcher_round);
            }
        });

        return view;
    }

    private void startRecord() throws IOException {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(fileName);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        }
        catch (IllegalStateException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private void stopRecord(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void askForPermission(){
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

    }
}
