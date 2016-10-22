package com.example.vbelwal94.eventnotifier;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button mButton;
    private int CAMERA_PERMISSION_CODE=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton= (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if(isCameraAllowed()) {
                        Toast.makeText(MainActivity.this, "Camera Allowed", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,ScanUsingCamera.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        MainActivity.this.startActivity(intent);
                    }
                    requestCameraPermission();
                }
                else{
                    Intent intent=new Intent(MainActivity.this,ScanUsingCamera.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    MainActivity.this.startActivity(intent);
                }
            }

        });
    }
    private boolean isCameraAllowed() {
        int result=ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    private void requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)) {
            Toast.makeText(MainActivity.this, "Camera permission is necessary", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);

    }
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if(requestCode==CAMERA_PERMISSION_CODE) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Camera permission now granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "App wont work without camera permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
