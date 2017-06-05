package com.example.saputra.facedetection;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import java.io.IOException;
public class MainActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    CameraSource mCameraSource;
    TextView textView;
    FaceDetector faceDetector;
    public FaceView mView;
    final int RequestCameraPermisson = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView) findViewById(R.id.surface);
        textView = (TextView) findViewById(R.id.text);
        mView = (FaceView) findViewById(R.id.view1);
        faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setProminentFaceOnly(true)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();
        faceDetector.setProcessor(
                new LargestFaceFocusingProcessor.Builder(faceDetector, new FaceTracker(this))
                        .build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mCameraSource.stop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraSource.release();
    }
    private void startCameraSource() {
        if (!faceDetector.isOperational()) {
            Log.d("xxx", "heemmm");
        } else {
            mCameraSource = new CameraSource.Builder(getApplicationContext(), faceDetector)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1600, 1200)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermisson);
                        }
                        mCameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                    mCameraSource.stop();
                }
            });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermisson: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        mCameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void drawCircleOnFace(final Face face){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.setContent(face);
            }
        });
    }
}
