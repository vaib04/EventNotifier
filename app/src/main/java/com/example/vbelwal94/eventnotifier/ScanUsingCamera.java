package com.example.vbelwal94.eventnotifier;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Calendar;

public class ScanUsingCamera extends AppCompatActivity {
    BarcodeDetector barcodeDetector;
    SurfaceView svCameraView;
    //TextView tvBarcodeInfo;
    CameraSource cameraSource;
    DisplayMetrics displayMetrics;
    int width,height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_using_camera);
        displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;
        svCameraView= (SurfaceView) findViewById(R.id.surface);
        svCameraView.getHolder().setFixedSize(width,(height/2));
        //tvBarcodeInfo= (TextView) findViewById(R.id.data);
        barcodeDetector=new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(width,(height/2)).build();
        svCameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(svCameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodeSparseArray=detections.getDetectedItems();
                if(barcodeSparseArray.size() != 0) {
                    //tvBarcodeInfo.post(new Runnable() {

                        //public void run() {
                            //tvBarcodeInfo.setText(barcodeSparseArray.valueAt(0).displayValue);
                            //createEvent(barcodeSparseArray.valueAt(0).displayValue);
                            Intent intent=new Intent(ScanUsingCamera.this,MainActivity.class);
                            intent.putExtra("data",barcodeSparseArray.valueAt(0).displayValue);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            ScanUsingCamera.this.startActivity(intent);
                        //}
                    //});
                }
            }
        });
    }

    public void onBackPressed() {
        Intent intent=new Intent(ScanUsingCamera.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ScanUsingCamera.this.startActivity(intent);
    }
}
