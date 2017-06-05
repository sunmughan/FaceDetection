package com.example.saputra.facedetection;
/**
 * Created by Verdiyanto Saputra on 29/05/2017.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
/**
 * View which displays a bitmap containing a face along with overlay graphics that identify the
 * locations of detected facial landmarks.
 */
public class FaceView extends View {
    private Face mFace;
    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * Sets the bitmap background and the associated face detections.
     */
    void setContent(Face face) {
        mFace = face;
        invalidate();
    }
    /**
     * Draws the bitmap background and the associated face landmarks.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFace != null) {
            drawFaceAnnotations(canvas, mFace);
        }
    }
    /**
     * Draws a small circle for each detected landmark, centered at the detected landmark position.
     * <p>
     *
     * Note that eye landmarks are defined to be the midpoint between the detected eye corner
     * positions, which tends to place the eye landmarks at the lower eyelid rather than at the
     * pupil position.
     */
    private void drawFaceAnnotations(Canvas canvas, Face face) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        for (Landmark landmark : face.getLandmarks()) {
            // jika posisi kamera front, maka ganti menjadi "float cx = (canvas.getWidth() - landmark.getPosition().x);" karena mirror
            float cx = (landmark.getPosition().x);
            float cy = (landmark.getPosition().y);
            canvas.drawCircle(cx, cy, 10, paint);
        }
    }
}
