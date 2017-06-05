package com.example.saputra.facedetection;
import android.app.Activity;
import android.util.Log;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
/**
 * Created by Verdiyanto Saputra on 29/05/2017.
 */
public class FaceTracker extends Tracker<Face> {

    private static final String TAG = "face_detection";
    private MainActivity mActivity;
    public FaceTracker(Activity activity){
        this.mActivity = (MainActivity) activity;
    }
    public void onNewItem(int id, Face face) {
        Log.i(TAG, "Awesome person detected.  Hello!");
        mActivity.drawCircleOnFace(face);
    }
    public void onUpdate(Detector.Detections<Face> detections, Face face) {
        if (face.getIsSmilingProbability() > 0.75) {
            Log.i(TAG, "I see a smile.  They must really enjoy your app.");
        }
        mActivity.drawCircleOnFace(face);
    }
    public void onDone() {
        Log.i(TAG, "Elvis has left the building.");
    }
}
